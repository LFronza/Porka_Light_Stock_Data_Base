package com.porkalite.sdb.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Produto.
 */
@Entity
@Table(name = "produto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "cd_produto", unique = true)
    private Integer cdProduto;

    @NotNull
    @Column(name = "nm_produto", nullable = false)
    private String nmProduto;

    @Column(name = "cst_compra")
    private Float cstCompra;

    @Column(name = "cst_verder")
    private Float cstVerder;

    @Column(name = "dt_vencimento")
    private LocalDate dtVencimento;

    @OneToMany(mappedBy = "produto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Saida> saidas = new HashSet<>();
    @OneToMany(mappedBy = "produto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Lote> ltoes = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("produtos")
    private Apresentacao apresentacao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("produtos")
    private Grupo grupo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCdProduto() {
        return cdProduto;
    }

    public Produto cdProduto(Integer cdProduto) {
        this.cdProduto = cdProduto;
        return this;
    }

    public void setCdProduto(Integer cdProduto) {
        this.cdProduto = cdProduto;
    }

    public String getNmProduto() {
        return nmProduto;
    }

    public Produto nmProduto(String nmProduto) {
        this.nmProduto = nmProduto;
        return this;
    }

    public void setNmProduto(String nmProduto) {
        this.nmProduto = nmProduto;
    }

    public Float getCstCompra() {
        return cstCompra;
    }

    public Produto cstCompra(Float cstCompra) {
        this.cstCompra = cstCompra;
        return this;
    }

    public void setCstCompra(Float cstCompra) {
        this.cstCompra = cstCompra;
    }

    public Float getCstVerder() {
        return cstVerder;
    }

    public Produto cstVerder(Float cstVerder) {
        this.cstVerder = cstVerder;
        return this;
    }

    public void setCstVerder(Float cstVerder) {
        this.cstVerder = cstVerder;
    }

    public LocalDate getDtVencimento() {
        return dtVencimento;
    }

    public Produto dtVencimento(LocalDate dtVencimento) {
        this.dtVencimento = dtVencimento;
        return this;
    }

    public void setDtVencimento(LocalDate dtVencimento) {
        this.dtVencimento = dtVencimento;
    }

    public Set<Saida> getSaidas() {
        return saidas;
    }

    public Produto saidas(Set<Saida> saidas) {
        this.saidas = saidas;
        return this;
    }

    public Produto addSaida(Saida saida) {
        this.saidas.add(saida);
        saida.setProduto(this);
        return this;
    }

    public Produto removeSaida(Saida saida) {
        this.saidas.remove(saida);
        saida.setProduto(null);
        return this;
    }

    public void setSaidas(Set<Saida> saidas) {
        this.saidas = saidas;
    }

    public Set<Lote> getLtoes() {
        return ltoes;
    }

    public Produto ltoes(Set<Lote> lotes) {
        this.ltoes = lotes;
        return this;
    }

    public Produto addLtoe(Lote lote) {
        this.ltoes.add(lote);
        lote.setProduto(this);
        return this;
    }

    public Produto removeLtoe(Lote lote) {
        this.ltoes.remove(lote);
        lote.setProduto(null);
        return this;
    }

    public void setLtoes(Set<Lote> lotes) {
        this.ltoes = lotes;
    }

    public Apresentacao getApresentacao() {
        return apresentacao;
    }

    public Produto apresentacao(Apresentacao apresentacao) {
        this.apresentacao = apresentacao;
        return this;
    }

    public void setApresentacao(Apresentacao apresentacao) {
        this.apresentacao = apresentacao;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public Produto grupo(Grupo grupo) {
        this.grupo = grupo;
        return this;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
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
        Produto produto = (Produto) o;
        if (produto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), produto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Produto{" +
            "id=" + getId() +
            ", cdProduto=" + getCdProduto() +
            ", nmProduto='" + getNmProduto() + "'" +
            ", cstCompra=" + getCstCompra() +
            ", cstVerder=" + getCstVerder() +
            ", dtVencimento='" + getDtVencimento() + "'" +
            "}";
    }
}
