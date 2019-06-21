package com.porkalite.sdb.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Saida.
 */
@Entity
@Table(name = "saida")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Saida implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "dt_saida", nullable = false)
    private LocalDate dtSaida;

    @NotNull
    @Column(name = "qt_saida", nullable = false)
    private Integer qtSaida;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("saidas")
    private Produto produto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDtSaida() {
        return dtSaida;
    }

    public Saida dtSaida(LocalDate dtSaida) {
        this.dtSaida = dtSaida;
        return this;
    }

    public void setDtSaida(LocalDate dtSaida) {
        this.dtSaida = dtSaida;
    }

    public Integer getQtSaida() {
        return qtSaida;
    }

    public Saida qtSaida(Integer qtSaida) {
        this.qtSaida = qtSaida;
        return this;
    }

    public void setQtSaida(Integer qtSaida) {
        this.qtSaida = qtSaida;
    }

    public Produto getProduto() {
        return produto;
    }

    public Saida produto(Produto produto) {
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
        Saida saida = (Saida) o;
        if (saida.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), saida.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Saida{" +
            "id=" + getId() +
            ", dtSaida='" + getDtSaida() + "'" +
            ", qtSaida=" + getQtSaida() +
            "}";
    }
}
