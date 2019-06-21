package com.porkalite.sdb.web.rest;

import com.porkalite.sdb.PorkaLightStockDataBaseApp;

import com.porkalite.sdb.domain.Lote;
import com.porkalite.sdb.domain.Produto;
import com.porkalite.sdb.repository.LoteRepository;
import com.porkalite.sdb.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.porkalite.sdb.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LoteResource REST controller.
 *
 * @see LoteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PorkaLightStockDataBaseApp.class)
public class LoteResourceIntTest {

    private static final LocalDate DEFAULT_DT_ENTRADA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_ENTRADA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_QT_ENTRADA = 1;
    private static final Integer UPDATED_QT_ENTRADA = 2;

    private static final LocalDate DEFAULT_DT_VENCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_VENCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NR_LOTE = 1;
    private static final Integer UPDATED_NR_LOTE = 2;

    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restLoteMockMvc;

    private Lote lote;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LoteResource loteResource = new LoteResource(loteRepository);
        this.restLoteMockMvc = MockMvcBuilders.standaloneSetup(loteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lote createEntity(EntityManager em) {
        Lote lote = new Lote()
            .dtEntrada(DEFAULT_DT_ENTRADA)
            .qtEntrada(DEFAULT_QT_ENTRADA)
            .dtVencimento(DEFAULT_DT_VENCIMENTO)
            .nrLote(DEFAULT_NR_LOTE);
        // Add required entity
        Produto produto = ProdutoResourceIntTest.createEntity(em);
        em.persist(produto);
        em.flush();
        lote.setProduto(produto);
        return lote;
    }

    @Before
    public void initTest() {
        lote = createEntity(em);
    }

    @Test
    @Transactional
    public void createLote() throws Exception {
        int databaseSizeBeforeCreate = loteRepository.findAll().size();

        // Create the Lote
        restLoteMockMvc.perform(post("/api/lotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lote)))
            .andExpect(status().isCreated());

        // Validate the Lote in the database
        List<Lote> loteList = loteRepository.findAll();
        assertThat(loteList).hasSize(databaseSizeBeforeCreate + 1);
        Lote testLote = loteList.get(loteList.size() - 1);
        assertThat(testLote.getDtEntrada()).isEqualTo(DEFAULT_DT_ENTRADA);
        assertThat(testLote.getQtEntrada()).isEqualTo(DEFAULT_QT_ENTRADA);
        assertThat(testLote.getDtVencimento()).isEqualTo(DEFAULT_DT_VENCIMENTO);
        assertThat(testLote.getNrLote()).isEqualTo(DEFAULT_NR_LOTE);
    }

    @Test
    @Transactional
    public void createLoteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = loteRepository.findAll().size();

        // Create the Lote with an existing ID
        lote.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoteMockMvc.perform(post("/api/lotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lote)))
            .andExpect(status().isBadRequest());

        // Validate the Lote in the database
        List<Lote> loteList = loteRepository.findAll();
        assertThat(loteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDtEntradaIsRequired() throws Exception {
        int databaseSizeBeforeTest = loteRepository.findAll().size();
        // set the field null
        lote.setDtEntrada(null);

        // Create the Lote, which fails.

        restLoteMockMvc.perform(post("/api/lotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lote)))
            .andExpect(status().isBadRequest());

        List<Lote> loteList = loteRepository.findAll();
        assertThat(loteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNrLoteIsRequired() throws Exception {
        int databaseSizeBeforeTest = loteRepository.findAll().size();
        // set the field null
        lote.setNrLote(null);

        // Create the Lote, which fails.

        restLoteMockMvc.perform(post("/api/lotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lote)))
            .andExpect(status().isBadRequest());

        List<Lote> loteList = loteRepository.findAll();
        assertThat(loteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLotes() throws Exception {
        // Initialize the database
        loteRepository.saveAndFlush(lote);

        // Get all the loteList
        restLoteMockMvc.perform(get("/api/lotes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lote.getId().intValue())))
            .andExpect(jsonPath("$.[*].dtEntrada").value(hasItem(DEFAULT_DT_ENTRADA.toString())))
            .andExpect(jsonPath("$.[*].qtEntrada").value(hasItem(DEFAULT_QT_ENTRADA)))
            .andExpect(jsonPath("$.[*].dtVencimento").value(hasItem(DEFAULT_DT_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].nrLote").value(hasItem(DEFAULT_NR_LOTE)));
    }
    
    @Test
    @Transactional
    public void getLote() throws Exception {
        // Initialize the database
        loteRepository.saveAndFlush(lote);

        // Get the lote
        restLoteMockMvc.perform(get("/api/lotes/{id}", lote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lote.getId().intValue()))
            .andExpect(jsonPath("$.dtEntrada").value(DEFAULT_DT_ENTRADA.toString()))
            .andExpect(jsonPath("$.qtEntrada").value(DEFAULT_QT_ENTRADA))
            .andExpect(jsonPath("$.dtVencimento").value(DEFAULT_DT_VENCIMENTO.toString()))
            .andExpect(jsonPath("$.nrLote").value(DEFAULT_NR_LOTE));
    }

    @Test
    @Transactional
    public void getNonExistingLote() throws Exception {
        // Get the lote
        restLoteMockMvc.perform(get("/api/lotes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLote() throws Exception {
        // Initialize the database
        loteRepository.saveAndFlush(lote);

        int databaseSizeBeforeUpdate = loteRepository.findAll().size();

        // Update the lote
        Lote updatedLote = loteRepository.findById(lote.getId()).get();
        // Disconnect from session so that the updates on updatedLote are not directly saved in db
        em.detach(updatedLote);
        updatedLote
            .dtEntrada(UPDATED_DT_ENTRADA)
            .qtEntrada(UPDATED_QT_ENTRADA)
            .dtVencimento(UPDATED_DT_VENCIMENTO)
            .nrLote(UPDATED_NR_LOTE);

        restLoteMockMvc.perform(put("/api/lotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLote)))
            .andExpect(status().isOk());

        // Validate the Lote in the database
        List<Lote> loteList = loteRepository.findAll();
        assertThat(loteList).hasSize(databaseSizeBeforeUpdate);
        Lote testLote = loteList.get(loteList.size() - 1);
        assertThat(testLote.getDtEntrada()).isEqualTo(UPDATED_DT_ENTRADA);
        assertThat(testLote.getQtEntrada()).isEqualTo(UPDATED_QT_ENTRADA);
        assertThat(testLote.getDtVencimento()).isEqualTo(UPDATED_DT_VENCIMENTO);
        assertThat(testLote.getNrLote()).isEqualTo(UPDATED_NR_LOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingLote() throws Exception {
        int databaseSizeBeforeUpdate = loteRepository.findAll().size();

        // Create the Lote

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoteMockMvc.perform(put("/api/lotes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lote)))
            .andExpect(status().isBadRequest());

        // Validate the Lote in the database
        List<Lote> loteList = loteRepository.findAll();
        assertThat(loteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLote() throws Exception {
        // Initialize the database
        loteRepository.saveAndFlush(lote);

        int databaseSizeBeforeDelete = loteRepository.findAll().size();

        // Delete the lote
        restLoteMockMvc.perform(delete("/api/lotes/{id}", lote.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Lote> loteList = loteRepository.findAll();
        assertThat(loteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lote.class);
        Lote lote1 = new Lote();
        lote1.setId(1L);
        Lote lote2 = new Lote();
        lote2.setId(lote1.getId());
        assertThat(lote1).isEqualTo(lote2);
        lote2.setId(2L);
        assertThat(lote1).isNotEqualTo(lote2);
        lote1.setId(null);
        assertThat(lote1).isNotEqualTo(lote2);
    }
}
