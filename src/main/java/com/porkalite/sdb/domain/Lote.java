package com.porkalite.sdb.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "lote")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Lote implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "dt_entrada", nullable = false)
    private LocalDate dtEntrada;

    @Column(name = "qt_entrada")
    private Integer qtEntrada;

    @Column(name = "dt_vencimento")
    private LocalDate dtVencimento;

    @NotNull
    @Column(name = "nr_lote", nullable = false, unique = true)
    private Integer nrLote;

    @ManyToOne
    @JsonIgnoreProperties("lotes")
    private Fornecedor fornecedor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("ltoes")
    private Produto produto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDtEntrada() {
        return dtEntrada;
    }

    public Lote dtEntrada(LocalDate dtEntrada) {
        this.dtEntrada = dtEntrada;
        return this;
    }

    public void setDtEntrada(LocalDate dtEntrada) {
        this.dtEntrada = dtEntrada;
    }

    public Integer getQtEntrada() {
        return qtEntrada;
    }

    public Lote qtEntrada(Integer qtEntrada) {
        this.qtEntrada = qtEntrada;
        return this;
    }

    public void setQtEntrada(Integer qtEntrada) {
        this.qtEntrada = qtEntrada;
    }

    public LocalDate getDtVencimento() {
        return dtVencimento;
    }

    public Lote dtVencimento(LocalDate dtVencimento) {
        this.dtVencimento = dtVencimento;
        return this;
    }

    public void setDtVencimento(LocalDate dtVencimento) {
        this.dtVencimento = dtVencimento;
    }

    public Integer getNrLote() {
        return nrLote;
    }

    public Lote nrLote(Integer nrLote) {
        this.nrLote = nrLote;
        return this;
    }

    public void setNrLote(Integer nrLote) {
        this.nrLote = nrLote;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public Lote fornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
        return this;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Produto getProduto() {
        return produto;
    }

    public Lote produto(Produto produto) {
        this.produto = produto;
        return this;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lote lote = (Lote) o;
        if (lote.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lote.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Lote{" +
            "id=" + getId() +
            ", dtEntrada='" + getDtEntrada() + "'" +
            ", qtEntrada=" + getQtEntrada() +
            ", dtVencimento='" + getDtVencimento() + "'" +
            ", nrLote=" + getNrLote() +
            "}";
    }
}
