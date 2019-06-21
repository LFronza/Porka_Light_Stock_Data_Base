package com.porkalite.sdb.web.rest;

import com.porkalite.sdb.PorkaLightStockDataBaseApp;

import com.porkalite.sdb.domain.Saida;
import com.porkalite.sdb.domain.Produto;
import com.porkalite.sdb.repository.SaidaRepository;
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
 * Test class for the SaidaResource REST controller.
 *
 * @see SaidaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PorkaLightStockDataBaseApp.class)
public class SaidaResourceIntTest {

    private static final LocalDate DEFAULT_DT_SAIDA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_SAIDA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_QT_SAIDA = 1;
    private static final Integer UPDATED_QT_SAIDA = 2;

    @Autowired
    private SaidaRepository saidaRepository;

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

    private MockMvc restSaidaMockMvc;

    private Saida saida;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SaidaResource saidaResource = new SaidaResource(saidaRepository);
        this.restSaidaMockMvc = MockMvcBuilders.standaloneSetup(saidaResource)
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
    public static Saida createEntity(EntityManager em) {
        Saida saida = new Saida()
            .dtSaida(DEFAULT_DT_SAIDA)
            .qtSaida(DEFAULT_QT_SAIDA);
        // Add required entity
        Produto produto = ProdutoResourceIntTest.createEntity(em);
        em.persist(produto);
        em.flush();
        saida.setProduto(produto);
        return saida;
    }

    @Before
    public void initTest() {
        saida = createEntity(em);
    }

    @Test
    @Transactional
    public void createSaida() throws Exception {
        int databaseSizeBeforeCreate = saidaRepository.findAll().size();

        // Create the Saida
        restSaidaMockMvc.perform(post("/api/saidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saida)))
            .andExpect(status().isCreated());

        // Validate the Saida in the database
        List<Saida> saidaList = saidaRepository.findAll();
        assertThat(saidaList).hasSize(databaseSizeBeforeCreate + 1);
        Saida testSaida = saidaList.get(saidaList.size() - 1);
        assertThat(testSaida.getDtSaida()).isEqualTo(DEFAULT_DT_SAIDA);
        assertThat(testSaida.getQtSaida()).isEqualTo(DEFAULT_QT_SAIDA);
    }

    @Test
    @Transactional
    public void createSaidaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = saidaRepository.findAll().size();

        // Create the Saida with an existing ID
        saida.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaidaMockMvc.perform(post("/api/saidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saida)))
            .andExpect(status().isBadRequest());

        // Validate the Saida in the database
        List<Saida> saidaList = saidaRepository.findAll();
        assertThat(saidaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDtSaidaIsRequired() throws Exception {
        int databaseSizeBeforeTest = saidaRepository.findAll().size();
        // set the field null
        saida.setDtSaida(null);

        // Create the Saida, which fails.

        restSaidaMockMvc.perform(post("/api/saidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saida)))
            .andExpect(status().isBadRequest());

        List<Saida> saidaList = saidaRepository.findAll();
        assertThat(saidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQtSaidaIsRequired() throws Exception {
        int databaseSizeBeforeTest = saidaRepository.findAll().size();
        // set the field null
        saida.setQtSaida(null);

        // Create the Saida, which fails.

        restSaidaMockMvc.perform(post("/api/saidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saida)))
            .andExpect(status().isBadRequest());

        List<Saida> saidaList = saidaRepository.findAll();
        assertThat(saidaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSaidas() throws Exception {
        // Initialize the database
        saidaRepository.saveAndFlush(saida);

        // Get all the saidaList
        restSaidaMockMvc.perform(get("/api/saidas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saida.getId().intValue())))
            .andExpect(jsonPath("$.[*].dtSaida").value(hasItem(DEFAULT_DT_SAIDA.toString())))
            .andExpect(jsonPath("$.[*].qtSaida").value(hasItem(DEFAULT_QT_SAIDA)));
    }
    
    @Test
    @Transactional
    public void getSaida() throws Exception {
        // Initialize the database
        saidaRepository.saveAndFlush(saida);

        // Get the saida
        restSaidaMockMvc.perform(get("/api/saidas/{id}", saida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(saida.getId().intValue()))
            .andExpect(jsonPath("$.dtSaida").value(DEFAULT_DT_SAIDA.toString()))
            .andExpect(jsonPath("$.qtSaida").value(DEFAULT_QT_SAIDA));
    }

    @Test
    @Transactional
    public void getNonExistingSaida() throws Exception {
        // Get the saida
        restSaidaMockMvc.perform(get("/api/saidas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSaida() throws Exception {
        // Initialize the database
        saidaRepository.saveAndFlush(saida);

        int databaseSizeBeforeUpdate = saidaRepository.findAll().size();

        // Update the saida
        Saida updatedSaida = saidaRepository.findById(saida.getId()).get();
        // Disconnect from session so that the updates on updatedSaida are not directly saved in db
        em.detach(updatedSaida);
        updatedSaida
            .dtSaida(UPDATED_DT_SAIDA)
            .qtSaida(UPDATED_QT_SAIDA);

        restSaidaMockMvc.perform(put("/api/saidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSaida)))
            .andExpect(status().isOk());

        // Validate the Saida in the database
        List<Saida> saidaList = saidaRepository.findAll();
        assertThat(saidaList).hasSize(databaseSizeBeforeUpdate);
        Saida testSaida = saidaList.get(saidaList.size() - 1);
        assertThat(testSaida.getDtSaida()).isEqualTo(UPDATED_DT_SAIDA);
        assertThat(testSaida.getQtSaida()).isEqualTo(UPDATED_QT_SAIDA);
    }

    @Test
    @Transactional
    public void updateNonExistingSaida() throws Exception {
        int databaseSizeBeforeUpdate = saidaRepository.findAll().size();

        // Create the Saida

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaidaMockMvc.perform(put("/api/saidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saida)))
            .andExpect(status().isBadRequest());

        // Validate the Saida in the database
        List<Saida> saidaList = saidaRepository.findAll();
        assertThat(saidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSaida() throws Exception {
        // Initialize the database
        saidaRepository.saveAndFlush(saida);

        int databaseSizeBeforeDelete = saidaRepository.findAll().size();

        // Delete the saida
        restSaidaMockMvc.perform(delete("/api/saidas/{id}", saida.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Saida> saidaList = saidaRepository.findAll();
        assertThat(saidaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Saida.class);
        Saida saida1 = new Saida();
        saida1.setId(1L);
        Saida saida2 = new Saida();
        saida2.setId(saida1.getId());
        assertThat(saida1).isEqualTo(saida2);
        saida2.setId(2L);
        assertThat(saida1).isNotEqualTo(saida2);
        saida1.setId(null);
        assertThat(saida1).isNotEqualTo(saida2);
    }
}
