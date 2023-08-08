package com.wcc.pbl230801.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Prod.
 */
@Entity
@Table(name = "prod")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Prod implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 30)
    @Column(name = "prod_no", length = 30)
    private String prodNo;

    @Size(max = 100)
    @Column(name = "ch_name", length = 100)
    private String chName;

    @Column(name = "issu_dt")
    private LocalDate issuDt;

    @Column(name = "exp_dt")
    private LocalDate expDt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Prod id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProdNo() {
        return this.prodNo;
    }

    public Prod prodNo(String prodNo) {
        this.setProdNo(prodNo);
        return this;
    }

    public void setProdNo(String prodNo) {
        this.prodNo = prodNo;
    }

    public String getChName() {
        return this.chName;
    }

    public Prod chName(String chName) {
        this.setChName(chName);
        return this;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }

    public LocalDate getIssuDt() {
        return this.issuDt;
    }

    public Prod issuDt(LocalDate issuDt) {
        this.setIssuDt(issuDt);
        return this;
    }

    public void setIssuDt(LocalDate issuDt) {
        this.issuDt = issuDt;
    }

    public LocalDate getExpDt() {
        return this.expDt;
    }

    public Prod expDt(LocalDate expDt) {
        this.setExpDt(expDt);
        return this;
    }

    public void setExpDt(LocalDate expDt) {
        this.expDt = expDt;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Prod)) {
            return false;
        }
        return id != null && id.equals(((Prod) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Prod{" +
            "id=" + getId() +
            ", prodNo='" + getProdNo() + "'" +
            ", chName='" + getChName() + "'" +
            ", issuDt='" + getIssuDt() + "'" +
            ", expDt='" + getExpDt() + "'" +
            "}";
    }
}
