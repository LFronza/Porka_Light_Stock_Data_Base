package com.porkalite.sdb.web.rest;
import com.porkalite.sdb.domain.Lote;
import com.porkalite.sdb.repository.LoteRepository;
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
 * REST controller for managing Lote.
 */
@RestController
@RequestMapping("/api")
public class LoteResource {

    private final Logger log = LoggerFactory.getLogger(LoteResource.class);

    private static final String ENTITY_NAME = "lote";

    private final LoteRepository loteRepository;

    public LoteResource(LoteRepository loteRepository) {
        this.loteRepository = loteRepository;
    }

    /**
     * POST  /lotes : Create a new lote.
     *
     * @param lote the lote to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lote, or with status 400 (Bad Request) if the lote has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lotes")
    public ResponseEntity<Lote> createLote(@Valid @RequestBody Lote lote) throws URISyntaxException {
        log.debug("REST request to save Lote : {}", lote);
        if (lote.getId() != null) {
            throw new BadRequestAlertException("A new lote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Lote result = loteRepository.save(lote);
        return ResponseEntity.created(new URI("/api/lotes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lotes : Updates an existing lote.
     *
     * @param lote the lote to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lote,
     * or with status 400 (Bad Request) if the lote is not valid,
     * or with status 500 (Internal Server Error) if the lote couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lotes")
    public ResponseEntity<Lote> updateLote(@Valid @RequestBody Lote lote) throws URISyntaxException {
        log.debug("REST request to update Lote : {}", lote);
        if (lote.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Lote result = loteRepository.save(lote);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lote.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lotes : get all the lotes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lotes in body
     */
    @GetMapping("/lotes")
    public List<Lote> getAllLotes() {
        log.debug("REST request to get all Lotes");
        return loteRepository.findAll();
    }

    /**
     * GET  /lotes/:id : get the "id" lote.
     *
     * @param id the id of the lote to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lote, or with status 404 (Not Found)
     */
    @GetMapping("/lotes/{id}")
    public ResponseEntity<Lote> getLote(@PathVariable Long id) {
        log.debug("REST request to get Lote : {}", id);
        Optional<Lote> lote = loteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lote);
    }

    /**
     * DELETE  /lotes/:id : delete the "id" lote.
     *
     * @param id the id of the lote to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lotes/{id}")
    public ResponseEntity<Void> deleteLote(@PathVariable Long id) {
        log.debug("REST request to delete Lote : {}", id);
        loteRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
