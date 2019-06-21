package com.porkalite.sdb.web.rest;
import com.porkalite.sdb.domain.Saida;
import com.porkalite.sdb.repository.SaidaRepository;
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
 * REST controller for managing Saida.
 */
@RestController
@RequestMapping("/api")
public class SaidaResource {

    private final Logger log = LoggerFactory.getLogger(SaidaResource.class);

    private static final String ENTITY_NAME = "saida";

    private final SaidaRepository saidaRepository;

    public SaidaResource(SaidaRepository saidaRepository) {
        this.saidaRepository = saidaRepository;
    }

    /**
     * POST  /saidas : Create a new saida.
     *
     * @param saida the saida to create
     * @return the ResponseEntity with status 201 (Created) and with body the new saida, or with status 400 (Bad Request) if the saida has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/saidas")
    public ResponseEntity<Saida> createSaida(@Valid @RequestBody Saida saida) throws URISyntaxException {
        log.debug("REST request to save Saida : {}", saida);
        if (saida.getId() != null) {
            throw new BadRequestAlertException("A new saida cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Saida result = saidaRepository.save(saida);
        return ResponseEntity.created(new URI("/api/saidas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /saidas : Updates an existing saida.
     *
     * @param saida the saida to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated saida,
     * or with status 400 (Bad Request) if the saida is not valid,
     * or with status 500 (Internal Server Error) if the saida couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/saidas")
    public ResponseEntity<Saida> updateSaida(@Valid @RequestBody Saida saida) throws URISyntaxException {
        log.debug("REST request to update Saida : {}", saida);
        if (saida.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Saida result = saidaRepository.save(saida);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, saida.getId().toString()))
            .body(result);
    }

    /**
     * GET  /saidas : get all the saidas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of saidas in body
     */
    @GetMapping("/saidas")
    public List<Saida> getAllSaidas() {
        log.debug("REST request to get all Saidas");
        return saidaRepository.findAll();
    }

    /**
     * GET  /saidas/:id : get the "id" saida.
     *
     * @param id the id of the saida to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the saida, or with status 404 (Not Found)
     */
    @GetMapping("/saidas/{id}")
    public ResponseEntity<Saida> getSaida(@PathVariable Long id) {
        log.debug("REST request to get Saida : {}", id);
        Optional<Saida> saida = saidaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(saida);
    }

    /**
     * DELETE  /saidas/:id : delete the "id" saida.
     *
     * @param id the id of the saida to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/saidas/{id}")
    public ResponseEntity<Void> deleteSaida(@PathVariable Long id) {
        log.debug("REST request to delete Saida : {}", id);
        saidaRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
