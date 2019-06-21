package com.porkalite.sdb.web.rest;

import com.porkalite.sdb.PorkaLightStockDataBaseApp;

import com.porkalite.sdb.domain.Apresentacao;
import com.porkalite.sdb.repository.ApresentacaoRepository;
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
import java.util.List;


import static com.porkalite.sdb.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ApresentacaoResource REST controller.
 *
 * @see ApresentacaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PorkaLightStockDataBaseApp.class)
public class ApresentacaoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private ApresentacaoRepository apresentacaoRepository;

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

    private MockMvc restApresentacaoMockMvc;

    private Apresentacao apresentacao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApresentacaoResource apresentacaoResource = new ApresentacaoResource(apresentacaoRepository);
        this.restApresentacaoMockMvc = MockMvcBuilders.standaloneSetup(apresentacaoResource)
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
    public static Apresentacao createEntity(EntityManager em) {
        Apresentacao apresentacao = new Apresentacao()
            .nome(DEFAULT_NOME);
        return apresentacao;
    }

    @Before
    public void initTest() {
        apresentacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createApresentacao() throws Exception {
        int databaseSizeBeforeCreate = apresentacaoRepository.findAll().size();

        // Create the Apresentacao
        restApresentacaoMockMvc.perform(post("/api/apresentacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apresentacao)))
            .andExpect(status().isCreated());

        // Validate the Apresentacao in the database
        List<Apresentacao> apresentacaoList = apresentacaoRepository.findAll();
        assertThat(apresentacaoList).hasSize(databaseSizeBeforeCreate + 1);
        Apresentacao testApresentacao = apresentacaoList.get(apresentacaoList.size() - 1);
        assertThat(testApresentacao.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createApresentacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apresentacaoRepository.findAll().size();

        // Create the Apresentacao with an existing ID
        apresentacao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApresentacaoMockMvc.perform(post("/api/apresentacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apresentacao)))
            .andExpect(status().isBadRequest());

        // Validate the Apresentacao in the database
        List<Apresentacao> apresentacaoList = apresentacaoRepository.findAll();
        assertThat(apresentacaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = apresentacaoRepository.findAll().size();
        // set the field null
        apresentacao.setNome(null);

        // Create the Apresentacao, which fails.

        restApresentacaoMockMvc.perform(post("/api/apresentacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apresentacao)))
            .andExpect(status().isBadRequest());

        List<Apresentacao> apresentacaoList = apresentacaoRepository.findAll();
        assertThat(apresentacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApresentacaos() throws Exception {
        // Initialize the database
        apresentacaoRepository.saveAndFlush(apresentacao);

        // Get all the apresentacaoList
        restApresentacaoMockMvc.perform(get("/api/apresentacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apresentacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));
    }
    
    @Test
    @Transactional
    public void getApresentacao() throws Exception {
        // Initialize the database
        apresentacaoRepository.saveAndFlush(apresentacao);

        // Get the apresentacao
        restApresentacaoMockMvc.perform(get("/api/apresentacaos/{id}", apresentacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(apresentacao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApresentacao() throws Exception {
        // Get the apresentacao
        restApresentacaoMockMvc.perform(get("/api/apresentacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApresentacao() throws Exception {
        // Initialize the database
        apresentacaoRepository.saveAndFlush(apresentacao);

        int databaseSizeBeforeUpdate = apresentacaoRepository.findAll().size();

        // Update the apresentacao
        Apresentacao updatedApresentacao = apresentacaoRepository.findById(apresentacao.getId()).get();
        // Disconnect from session so that the updates on updatedApresentacao are not directly saved in db
        em.detach(updatedApresentacao);
        updatedApresentacao
            .nome(UPDATED_NOME);

        restApresentacaoMockMvc.perform(put("/api/apresentacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedApresentacao)))
            .andExpect(status().isOk());

        // Validate the Apresentacao in the database
        List<Apresentacao> apresentacaoList = apresentacaoRepository.findAll();
        assertThat(apresentacaoList).hasSize(databaseSizeBeforeUpdate);
        Apresentacao testApresentacao = apresentacaoList.get(apresentacaoList.size() - 1);
        assertThat(testApresentacao.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingApresentacao() throws Exception {
        int databaseSizeBeforeUpdate = apresentacaoRepository.findAll().size();

        // Create the Apresentacao

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApresentacaoMockMvc.perform(put("/api/apresentacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(apresentacao)))
            .andExpect(status().isBadRequest());

        // Validate the Apresentacao in the database
        List<Apresentacao> apresentacaoList = apresentacaoRepository.findAll();
        assertThat(apresentacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApresentacao() throws Exception {
        // Initialize the database
        apresentacaoRepository.saveAndFlush(apresentacao);

        int databaseSizeBeforeDelete = apresentacaoRepository.findAll().size();

        // Delete the apresentacao
        restApresentacaoMockMvc.perform(delete("/api/apresentacaos/{id}", apresentacao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Apresentacao> apresentacaoList = apresentacaoRepository.findAll();
        assertThat(apresentacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Apresentacao.class);
        Apresentacao apresentacao1 = new Apresentacao();
        apresentacao1.setId(1L);
        Apresentacao apresentacao2 = new Apresentacao();
        apresentacao2.setId(apresentacao1.getId());
        assertThat(apresentacao1).isEqualTo(apresentacao2);
        apresentacao2.setId(2L);
        assertThat(apresentacao1).isNotEqualTo(apresentacao2);
        apresentacao1.setId(null);
        assertThat(apresentacao1).isNotEqualTo(apresentacao2);
    }
}
