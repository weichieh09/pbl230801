package com.wcc.pbl230801.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;

/**
 * A MatchZ.
 */
@Entity
@Table(name = "match_z")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatchZ implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "e_id")
    private Long eId;

    @Column(name = "mtch_end_time")
    private ZonedDateTime mtchEndTime;

    @Column(name = "w_plyr_1")
    private String wPlyr1;

    @Column(name = "w_plyr_2")
    private String wPlyr2;

    @Column(name = "w_scr")
    private String wScr;

    @Column(name = "l_plyr_1")
    private String lPlyr1;

    @Column(name = "l_plyr_2")
    private String lPlyr2;

    @Column(name = "l_scr")
    private String lScr;

    @Column(name = "lst_mtn_usr")
    private String lstMtnUsr;

    @Column(name = "lst_mtn_dt")
    private ZonedDateTime lstMtnDt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MatchZ id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long geteId() {
        return this.eId;
    }

    public MatchZ eId(Long eId) {
        this.seteId(eId);
        return this;
    }

    public void seteId(Long eId) {
        this.eId = eId;
    }

    public ZonedDateTime getMtchEndTime() {
        return this.mtchEndTime;
    }

    public MatchZ mtchEndTime(ZonedDateTime mtchEndTime) {
        this.setMtchEndTime(mtchEndTime);
        return this;
    }

    public void setMtchEndTime(ZonedDateTime mtchEndTime) {
        this.mtchEndTime = mtchEndTime;
    }

    public String getwPlyr1() {
        return this.wPlyr1;
    }

    public MatchZ wPlyr1(String wPlyr1) {
        this.setwPlyr1(wPlyr1);
        return this;
    }

    public void setwPlyr1(String wPlyr1) {
        this.wPlyr1 = wPlyr1;
    }

    public String getwPlyr2() {
        return this.wPlyr2;
    }

    public MatchZ wPlyr2(String wPlyr2) {
        this.setwPlyr2(wPlyr2);
        return this;
    }

    public void setwPlyr2(String wPlyr2) {
        this.wPlyr2 = wPlyr2;
    }

    public String getwScr() {
        return this.wScr;
    }

    public MatchZ wScr(String wScr) {
        this.setwScr(wScr);
        return this;
    }

    public void setwScr(String wScr) {
        this.wScr = wScr;
    }

    public String getlPlyr1() {
        return this.lPlyr1;
    }

    public MatchZ lPlyr1(String lPlyr1) {
        this.setlPlyr1(lPlyr1);
        return this;
    }

    public void setlPlyr1(String lPlyr1) {
        this.lPlyr1 = lPlyr1;
    }

    public String getlPlyr2() {
        return this.lPlyr2;
    }

    public MatchZ lPlyr2(String lPlyr2) {
        this.setlPlyr2(lPlyr2);
        return this;
    }

    public void setlPlyr2(String lPlyr2) {
        this.lPlyr2 = lPlyr2;
    }

    public String getlScr() {
        return this.lScr;
    }

    public MatchZ lScr(String lScr) {
        this.setlScr(lScr);
        return this;
    }

    public void setlScr(String lScr) {
        this.lScr = lScr;
    }

    public String getLstMtnUsr() {
        return this.lstMtnUsr;
    }

    public MatchZ lstMtnUsr(String lstMtnUsr) {
        this.setLstMtnUsr(lstMtnUsr);
        return this;
    }

    public void setLstMtnUsr(String lstMtnUsr) {
        this.lstMtnUsr = lstMtnUsr;
    }

    public ZonedDateTime getLstMtnDt() {
        return this.lstMtnDt;
    }

    public MatchZ lstMtnDt(ZonedDateTime lstMtnDt) {
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
        if (!(o instanceof MatchZ)) {
            return false;
        }
        return id != null && id.equals(((MatchZ) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchZ{" +
            "id=" + getId() +
            ", eId=" + geteId() +
            ", mtchEndTime='" + getMtchEndTime() + "'" +
            ", wPlyr1='" + getwPlyr1() + "'" +
            ", wPlyr2='" + getwPlyr2() + "'" +
            ", wScr='" + getwScr() + "'" +
            ", lPlyr1='" + getlPlyr1() + "'" +
            ", lPlyr2='" + getlPlyr2() + "'" +
            ", lScr='" + getlScr() + "'" +
            ", lstMtnUsr='" + getLstMtnUsr() + "'" +
            ", lstMtnDt='" + getLstMtnDt() + "'" +
            "}";
    }
}
