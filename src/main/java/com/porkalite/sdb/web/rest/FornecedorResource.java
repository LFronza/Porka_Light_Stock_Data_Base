package com.porkalite.sdb.web.rest;
import com.porkalite.sdb.domain.Fornecedor;
import com.porkalite.sdb.repository.FornecedorRepository;
import com.porkalite.sdb.web.rest.errors.BadRequestAlertException;
import com.porkalite.sdb.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Fornecedor.
 */
@RestController
@RequestMapping("/api")
public class FornecedorResource {

    private final Logger log = LoggerFactory.getLogger(FornecedorResource.class);

    private static final String ENTITY_NAME = "fornecedor";

    private final FornecedorRepository fornecedorRepository;

    public FornecedorResource(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    /**
     * POST  /fornecedors : Create a new fornecedor.
     *
     * @param fornecedor the fornecedor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fornecedor, or with status 400 (Bad Request) if the fornecedor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fornecedors")
    public ResponseEntity<Fornecedor> createFornecedor(@Valid @RequestBody Fornecedor fornecedor) throws URISyntaxException {
        log.debug("REST request to save Fornecedor : {}", fornecedor);
        if (fornecedor.getId() != null) {
            throw new BadRequestAlertException("A new fornecedor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Fornecedor result = fornecedorRepository.save(fornecedor);
        return ResponseEntity.created(new URI("/api/fornecedors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fornecedors : Updates an existing fornecedor.
     *
     * @param fornecedor the fornecedor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fornecedor,
     * or with status 400 (Bad Request) if the fornecedor is not valid,
     * or with status 500 (Internal Server Error) if the fornecedor couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fornecedors")
    public ResponseEntity<Fornecedor> updateFornecedor(@Valid @RequestBody Fornecedor fornecedor) throws URISyntaxException {
        log.debug("REST request to update Fornecedor : {}", fornecedor);
        if (fornecedor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Fornecedor result = fornecedorRepository.save(fornecedor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fornecedor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fornecedors : get all the fornecedors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fornecedors in body
     */
    @GetMapping("/fornecedors")
    public List<Fornecedor> getAllFornecedors() {
        log.debug("REST request to get all Fornecedors");
        return fornecedorRepository.findAll();
    }

    /**
     * GET  /fornecedors/:id : get the "id" fornecedor.
     *
     * @param id the id of the fornecedor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fornecedor, or with status 404 (Not Found)
     */
    @GetMapping("/fornecedors/{id}")
    public ResponseEntity<Fornecedor> getFornecedor(@PathVariable Long id) {
        log.debug("REST request to get Fornecedor : {}", id);
        Optional<Fornecedor> fornecedor = fornecedorRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fornecedor);
    }

    /**
     * DELETE  /fornecedors/:id : delete the "id" fornecedor.
     *
     * @param id the id of the fornecedor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fornecedors/{id}")
    public ResponseEntity<Void> deleteFornecedor(@PathVariable Long id) {
        log.debug("REST request to delete Fornecedor : {}", id);
        fornecedorRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
