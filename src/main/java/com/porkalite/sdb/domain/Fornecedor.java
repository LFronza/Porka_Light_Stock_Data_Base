package com.porkalite.sdb.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Fornecedor.
 */
@Entity
@Table(name = "fornecedor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Fornecedor implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nm_fornecedor", nullable = false)
    private String nmFornecedor;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "cnpj")
    private Integer cnpj;

    @Column(name = "telefone")
    private Integer telefone;

    @OneToMany(mappedBy = "fornecedor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Lote> lotes = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNmFornecedor() {
        return nmFornecedor;
    }

    public Fornecedor nmFornecedor(String nmFornecedor) {
        this.nmFornecedor = nmFornecedor;
        return this;
    }

    public void setNmFornecedor(String nmFornecedor) {
        this.nmFornecedor = nmFornecedor;
    }

    public String getEndereco() {
        return endereco;
    }

    public Fornecedor endereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public Fornecedor cidade(String cidade) {
        this.cidade = cidade;
        return this;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Integer getCnpj() {
        return cnpj;
    }

    public Fornecedor cnpj(Integer cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public void setCnpj(Integer cnpj) {
        this.cnpj = cnpj;
    }

    public Integer getTelefone() {
        return telefone;
    }

    public Fornecedor telefone(Integer telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(Integer telefone) {
        this.telefone = telefone;
    }

    public Set<Lote> getLotes() {
        return lotes;
    }

    public Fornecedor lotes(Set<Lote> lotes) {
        this.lotes = lotes;
        return this;
    }

    public Fornecedor addLote(Lote lote) {
        this.lotes.add(lote);
        lote.setFornecedor(this);
        return this;
    }

    public Fornecedor removeLote(Lote lote) {
        this.lotes.remove(lote);
        lote.setFornecedor(null);
        return this;
    }

    public void setLotes(Set<Lote> lotes) {
        this.lotes = lotes;
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
        Fornecedor fornecedor = (Fornecedor) o;
        if (fornecedor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fornecedor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Fornecedor{" +
            "id=" + getId() +
            ", nmFornecedor='" + getNmFornecedor() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", cidade='" + getCidade() + "'" +
            ", cnpj=" + getCnpj() +
            ", telefone=" + getTelefone() +
            "}";
    }
}
