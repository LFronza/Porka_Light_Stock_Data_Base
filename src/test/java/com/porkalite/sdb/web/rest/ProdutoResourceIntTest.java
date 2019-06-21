package com.porkalite.sdb.web.rest;

import com.porkalite.sdb.PorkaLightStockDataBaseApp;

import com.porkalite.sdb.domain.Produto;
import com.porkalite.sdb.domain.Grupo;
import com.porkalite.sdb.repository.ProdutoRepository;
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
 * Test class for the ProdutoResource REST controller.
 *
 * @see ProdutoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PorkaLightStockDataBaseApp.class)
public class ProdutoResourceIntTest {

    private static final Integer DEFAULT_CD_PRODUTO = 1;
    private static final Integer UPDATED_CD_PRODUTO = 2;

    private static final String DEFAULT_NM_PRODUTO = "AAAAAAAAAA";
    private static final String UPDATED_NM_PRODUTO = "BBBBBBBBBB";

    private static final Float DEFAULT_CST_COMPRA = 1F;
    private static final Float UPDATED_CST_COMPRA = 2F;

    private static final Float DEFAULT_CST_VERDER = 1F;
    private static final Float UPDATED_CST_VERDER = 2F;

    private static final LocalDate DEFAULT_DT_VENCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_VENCIMENTO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ProdutoRepository produtoRepository;

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

    private MockMvc restProdutoMockMvc;

    private Produto produto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProdutoResource produtoResource = new ProdutoResource(produtoRepository);
        this.restProdutoMockMvc = MockMvcBuilders.standaloneSetup(produtoResource)
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
    public static Produto createEntity(EntityManager em) {
        Produto produto = new Produto()
            .cdProduto(DEFAULT_CD_PRODUTO)
            .nmProduto(DEFAULT_NM_PRODUTO)
            .cstCompra(DEFAULT_CST_COMPRA)
            .cstVerder(DEFAULT_CST_VERDER)
            .dtVencimento(DEFAULT_DT_VENCIMENTO);
        // Add required entity
        Grupo grupo = GrupoResourceIntTest.createEntity(em);
        em.persist(grupo);
        em.flush();
        produto.setGrupo(grupo);
        return produto;
    }

    @Before
    public void initTest() {
        produto = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduto() throws Exception {
        int databaseSizeBeforeCreate = produtoRepository.findAll().size();

        // Create the Produto
        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produto)))
            .andExpect(status().isCreated());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeCreate + 1);
        Produto testProduto = produtoList.get(produtoList.size() - 1);
        assertThat(testProduto.getCdProduto()).isEqualTo(DEFAULT_CD_PRODUTO);
        assertThat(testProduto.getNmProduto()).isEqualTo(DEFAULT_NM_PRODUTO);
        assertThat(testProduto.getCstCompra()).isEqualTo(DEFAULT_CST_COMPRA);
        assertThat(testProduto.getCstVerder()).isEqualTo(DEFAULT_CST_VERDER);
        assertThat(testProduto.getDtVencimento()).isEqualTo(DEFAULT_DT_VENCIMENTO);
    }

    @Test
    @Transactional
    public void createProdutoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produtoRepository.findAll().size();

        // Create the Produto with an existing ID
        produto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produto)))
            .andExpect(status().isBadRequest());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNmProdutoIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtoRepository.findAll().size();
        // set the field null
        produto.setNmProduto(null);

        // Create the Produto, which fails.

        restProdutoMockMvc.perform(post("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produto)))
            .andExpect(status().isBadRequest());

        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProdutos() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get all the produtoList
        restProdutoMockMvc.perform(get("/api/produtos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produto.getId().intValue())))
            .andExpect(jsonPath("$.[*].cdProduto").value(hasItem(DEFAULT_CD_PRODUTO)))
            .andExpect(jsonPath("$.[*].nmProduto").value(hasItem(DEFAULT_NM_PRODUTO.toString())))
            .andExpect(jsonPath("$.[*].cstCompra").value(hasItem(DEFAULT_CST_COMPRA.doubleValue())))
            .andExpect(jsonPath("$.[*].cstVerder").value(hasItem(DEFAULT_CST_VERDER.doubleValue())))
            .andExpect(jsonPath("$.[*].dtVencimento").value(hasItem(DEFAULT_DT_VENCIMENTO.toString())));
    }
    
    @Test
    @Transactional
    public void getProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        // Get the produto
        restProdutoMockMvc.perform(get("/api/produtos/{id}", produto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(produto.getId().intValue()))
            .andExpect(jsonPath("$.cdProduto").value(DEFAULT_CD_PRODUTO))
            .andExpect(jsonPath("$.nmProduto").value(DEFAULT_NM_PRODUTO.toString()))
            .andExpect(jsonPath("$.cstCompra").value(DEFAULT_CST_COMPRA.doubleValue()))
            .andExpect(jsonPath("$.cstVerder").value(DEFAULT_CST_VERDER.doubleValue()))
            .andExpect(jsonPath("$.dtVencimento").value(DEFAULT_DT_VENCIMENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProduto() throws Exception {
        // Get the produto
        restProdutoMockMvc.perform(get("/api/produtos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        int databaseSizeBeforeUpdate = produtoRepository.findAll().size();

        // Update the produto
        Produto updatedProduto = produtoRepository.findById(produto.getId()).get();
        // Disconnect from session so that the updates on updatedProduto are not directly saved in db
        em.detach(updatedProduto);
        updatedProduto
            .cdProduto(UPDATED_CD_PRODUTO)
            .nmProduto(UPDATED_NM_PRODUTO)
            .cstCompra(UPDATED_CST_COMPRA)
            .cstVerder(UPDATED_CST_VERDER)
            .dtVencimento(UPDATED_DT_VENCIMENTO);

        restProdutoMockMvc.perform(put("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProduto)))
            .andExpect(status().isOk());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeUpdate);
        Produto testProduto = produtoList.get(produtoList.size() - 1);
        assertThat(testProduto.getCdProduto()).isEqualTo(UPDATED_CD_PRODUTO);
        assertThat(testProduto.getNmProduto()).isEqualTo(UPDATED_NM_PRODUTO);
        assertThat(testProduto.getCstCompra()).isEqualTo(UPDATED_CST_COMPRA);
        assertThat(testProduto.getCstVerder()).isEqualTo(UPDATED_CST_VERDER);
        assertThat(testProduto.getDtVencimento()).isEqualTo(UPDATED_DT_VENCIMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingProduto() throws Exception {
        int databaseSizeBeforeUpdate = produtoRepository.findAll().size();

        // Create the Produto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProdutoMockMvc.perform(put("/api/produtos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produto)))
            .andExpect(status().isBadRequest());

        // Validate the Produto in the database
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProduto() throws Exception {
        // Initialize the database
        produtoRepository.saveAndFlush(produto);

        int databaseSizeBeforeDelete = produtoRepository.findAll().size();

        // Delete the produto
        restProdutoMockMvc.perform(delete("/api/produtos/{id}", produto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Produto> produtoList = produtoRepository.findAll();
        assertThat(produtoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Produto.class);
        Produto produto1 = new Produto();
        produto1.setId(1L);
        Produto produto2 = new Produto();
        produto2.setId(produto1.getId());
        assertThat(produto1).isEqualTo(produto2);
        produto2.setId(2L);
        assertThat(produto1).isNotEqualTo(produto2);
        produto1.setId(null);
        assertThat(produto1).isNotEqualTo(produto2);
    }
}
