package com.porkalite.sdb.web.rest;
import com.porkalite.sdb.domain.Apresentacao;
import com.porkalite.sdb.repository.ApresentacaoRepository;
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
 * REST controller for managing Apresentacao.
 */
@RestController
@RequestMapping("/api")
public class ApresentacaoResource {

    private final Logger log = LoggerFactory.getLogger(ApresentacaoResource.class);

    private static final String ENTITY_NAME = "apresentacao";

    private final ApresentacaoRepository apresentacaoRepository;

    public ApresentacaoResource(ApresentacaoRepository apresentacaoRepository) {
        this.apresentacaoRepository = apresentacaoRepository;
    }

    /**
     * POST  /apresentacaos : Create a new apresentacao.
     *
     * @param apresentacao the apresentacao to create
     * @return the ResponseEntity with status 201 (Created) and with body the new apresentacao, or with status 400 (Bad Request) if the apresentacao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/apresentacaos")
    public ResponseEntity<Apresentacao> createApresentacao(@Valid @RequestBody Apresentacao apresentacao) throws URISyntaxException {
        log.debug("REST request to save Apresentacao : {}", apresentacao);
        if (apresentacao.getId() != null) {
            throw new BadRequestAlertException("A new apresentacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Apresentacao result = apresentacaoRepository.save(apresentacao);
        return ResponseEntity.created(new URI("/api/apresentacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /apresentacaos : Updates an existing apresentacao.
     *
     * @param apresentacao the apresentacao to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated apresentacao,
     * or with status 400 (Bad Request) if the apresentacao is not valid,
     * or with status 500 (Internal Server Error) if the apresentacao couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/apresentacaos")
    public ResponseEntity<Apresentacao> updateApresentacao(@Valid @RequestBody Apresentacao apresentacao) throws URISyntaxException {
        log.debug("REST request to update Apresentacao : {}", apresentacao);
        if (apresentacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Apresentacao result = apresentacaoRepository.save(apresentacao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, apresentacao.getId().toString()))
            .body(result);
    }

    /**
     * GET  /apresentacaos : get all the apresentacaos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of apresentacaos in body
     */
    @GetMapping("/apresentacaos")
    public List<Apresentacao> getAllApresentacaos() {
        log.debug("REST request to get all Apresentacaos");
        return apresentacaoRepository.findAll();
    }

    /**
     * GET  /apresentacaos/:id : get the "id" apresentacao.
     *
     * @param id the id of the apresentacao to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the apresentacao, or with status 404 (Not Found)
     */
    @GetMapping("/apresentacaos/{id}")
    public ResponseEntity<Apresentacao> getApresentacao(@PathVariable Long id) {
        log.debug("REST request to get Apresentacao : {}", id);
        Optional<Apresentacao> apresentacao = apresentacaoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(apresentacao);
    }

    /**
     * DELETE  /apresentacaos/:id : delete the "id" apresentacao.
     *
     * @param id the id of the apresentacao to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/apresentacaos/{id}")
    public ResponseEntity<Void> deleteApresentacao(@PathVariable Long id) {
        log.debug("REST request to delete Apresentacao : {}", id);
        apresentacaoRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
