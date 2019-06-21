package com.porkalite.sdb.web.rest;

import com.porkalite.sdb.PorkaLightStockDataBaseApp;

import com.porkalite.sdb.domain.Fornecedor;
import com.porkalite.sdb.repository.FornecedorRepository;
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
 * Test class for the FornecedorResource REST controller.
 *
 * @see FornecedorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PorkaLightStockDataBaseApp.class)
public class FornecedorResourceIntTest {

    private static final String DEFAULT_NM_FORNECEDOR = "AAAAAAAAAA";
    private static final String UPDATED_NM_FORNECEDOR = "BBBBBBBBBB";

    private static final String DEFAULT_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO = "BBBBBBBBBB";

    private static final String DEFAULT_CIDADE = "AAAAAAAAAA";
    private static final String UPDATED_CIDADE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CNPJ = 1;
    private static final Integer UPDATED_CNPJ = 2;

    private static final Integer DEFAULT_TELEFONE = 1;
    private static final Integer UPDATED_TELEFONE = 2;

    @Autowired
    private FornecedorRepository fornecedorRepository;

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

    private MockMvc restFornecedorMockMvc;

    private Fornecedor fornecedor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FornecedorResource fornecedorResource = new FornecedorResource(fornecedorRepository);
        this.restFornecedorMockMvc = MockMvcBuilders.standaloneSetup(fornecedorResource)
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
    public static Fornecedor createEntity(EntityManager em) {
        Fornecedor fornecedor = new Fornecedor()
            .nmFornecedor(DEFAULT_NM_FORNECEDOR)
            .endereco(DEFAULT_ENDERECO)
            .cidade(DEFAULT_CIDADE)
            .cnpj(DEFAULT_CNPJ)
            .telefone(DEFAULT_TELEFONE);
        return fornecedor;
    }

    @Before
    public void initTest() {
        fornecedor = createEntity(em);
    }

    @Test
    @Transactional
    public void createFornecedor() throws Exception {
        int databaseSizeBeforeCreate = fornecedorRepository.findAll().size();

        // Create the Fornecedor
        restFornecedorMockMvc.perform(post("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedor)))
            .andExpect(status().isCreated());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeCreate + 1);
        Fornecedor testFornecedor = fornecedorList.get(fornecedorList.size() - 1);
        assertThat(testFornecedor.getNmFornecedor()).isEqualTo(DEFAULT_NM_FORNECEDOR);
        assertThat(testFornecedor.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testFornecedor.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testFornecedor.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testFornecedor.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
    }

    @Test
    @Transactional
    public void createFornecedorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fornecedorRepository.findAll().size();

        // Create the Fornecedor with an existing ID
        fornecedor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFornecedorMockMvc.perform(post("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedor)))
            .andExpect(status().isBadRequest());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNmFornecedorIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorRepository.findAll().size();
        // set the field null
        fornecedor.setNmFornecedor(null);

        // Create the Fornecedor, which fails.

        restFornecedorMockMvc.perform(post("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedor)))
            .andExpect(status().isBadRequest());

        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFornecedors() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get all the fornecedorList
        restFornecedorMockMvc.perform(get("/api/fornecedors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fornecedor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nmFornecedor").value(hasItem(DEFAULT_NM_FORNECEDOR.toString())))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO.toString())))
            .andExpect(jsonPath("$.[*].cidade").value(hasItem(DEFAULT_CIDADE.toString())))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)));
    }
    
    @Test
    @Transactional
    public void getFornecedor() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        // Get the fornecedor
        restFornecedorMockMvc.perform(get("/api/fornecedors/{id}", fornecedor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fornecedor.getId().intValue()))
            .andExpect(jsonPath("$.nmFornecedor").value(DEFAULT_NM_FORNECEDOR.toString()))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO.toString()))
            .andExpect(jsonPath("$.cidade").value(DEFAULT_CIDADE.toString()))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE));
    }

    @Test
    @Transactional
    public void getNonExistingFornecedor() throws Exception {
        // Get the fornecedor
        restFornecedorMockMvc.perform(get("/api/fornecedors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFornecedor() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();

        // Update the fornecedor
        Fornecedor updatedFornecedor = fornecedorRepository.findById(fornecedor.getId()).get();
        // Disconnect from session so that the updates on updatedFornecedor are not directly saved in db
        em.detach(updatedFornecedor);
        updatedFornecedor
            .nmFornecedor(UPDATED_NM_FORNECEDOR)
            .endereco(UPDATED_ENDERECO)
            .cidade(UPDATED_CIDADE)
            .cnpj(UPDATED_CNPJ)
            .telefone(UPDATED_TELEFONE);

        restFornecedorMockMvc.perform(put("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFornecedor)))
            .andExpect(status().isOk());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
        Fornecedor testFornecedor = fornecedorList.get(fornecedorList.size() - 1);
        assertThat(testFornecedor.getNmFornecedor()).isEqualTo(UPDATED_NM_FORNECEDOR);
        assertThat(testFornecedor.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testFornecedor.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testFornecedor.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testFornecedor.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    public void updateNonExistingFornecedor() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorRepository.findAll().size();

        // Create the Fornecedor

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFornecedorMockMvc.perform(put("/api/fornecedors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fornecedor)))
            .andExpect(status().isBadRequest());

        // Validate the Fornecedor in the database
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFornecedor() throws Exception {
        // Initialize the database
        fornecedorRepository.saveAndFlush(fornecedor);

        int databaseSizeBeforeDelete = fornecedorRepository.findAll().size();

        // Delete the fornecedor
        restFornecedorMockMvc.perform(delete("/api/fornecedors/{id}", fornecedor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        assertThat(fornecedorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fornecedor.class);
        Fornecedor fornecedor1 = new Fornecedor();
        fornecedor1.setId(1L);
        Fornecedor fornecedor2 = new Fornecedor();
        fornecedor2.setId(fornecedor1.getId());
        assertThat(fornecedor1).isEqualTo(fornecedor2);
        fornecedor2.setId(2L);
        assertThat(fornecedor1).isNotEqualTo(fornecedor2);
        fornecedor1.setId(null);
        assertThat(fornecedor1).isNotEqualTo(fornecedor2);
    }
}
