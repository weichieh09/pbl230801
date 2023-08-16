package com.wcc.pbl230801.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;

/**
 * A Player.
 */
@Entity
@Table(name = "player")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "plyr_nm")
    private String plyrNm;

    @Column(name = "plyr_lvl")
    private Long plyrLvl;

    @Column(name = "lst_mtn_usr")
    private String lstMtnUsr;

    @Column(name = "lst_mtn_dt")
    private ZonedDateTime lstMtnDt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Player id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlyrNm() {
        return this.plyrNm;
    }

    public Player plyrNm(String plyrNm) {
        this.setPlyrNm(plyrNm);
        return this;
    }

    public void setPlyrNm(String plyrNm) {
        this.plyrNm = plyrNm;
    }

    public Long getPlyrLvl() {
        return this.plyrLvl;
    }

    public Player plyrLvl(Long plyrLvl) {
        this.setPlyrLvl(plyrLvl);
        return this;
    }

    public void setPlyrLvl(Long plyrLvl) {
        this.plyrLvl = plyrLvl;
    }

    public String getLstMtnUsr() {
        return this.lstMtnUsr;
    }

    public Player lstMtnUsr(String lstMtnUsr) {
        this.setLstMtnUsr(lstMtnUsr);
        return this;
    }

    public void setLstMtnUsr(String lstMtnUsr) {
        this.lstMtnUsr = lstMtnUsr;
    }

    public ZonedDateTime getLstMtnDt() {
        return this.lstMtnDt;
    }

    public Player lstMtnDt(ZonedDateTime lstMtnDt) {
        this.setLstMtnDt(lstMtnDt);
        return this;
    }

    public void setLstMtnDt(ZonedDateTime lstMtnDt) {
        this.lstMtnDt = lstMtnDt;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Player)) {
            return false;
        }
        return id != null && id.equals(((Player) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Player{" +
            "id=" + getId() +
            ", plyrNm='" + getPlyrNm() + "'" +
            ", plyrLvl=" + getPlyrLvl() +
            ", lstMtnUsr='" + getLstMtnUsr() + "'" +
            ", lstMtnDt='" + getLstMtnDt() + "'" +
            "}";
    }
}
