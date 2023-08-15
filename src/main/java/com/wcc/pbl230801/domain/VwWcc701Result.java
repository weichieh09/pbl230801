package com.wcc.pbl230801.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;

/**
 * A VwWcc701Result.
 */
@Entity
@Table(name = "vw_wcc_701_result")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VwWcc701Result implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "e_id")
    private Long eId;

    @Column(name = "t_id")
    private Long tId;

    @Column(name = "evnt_nm")
    private String evntNm;

    @Column(name = "evnt_dt")
    private ZonedDateTime evntDt;

    @Column(name = "venue")
    private String venue;

    @Column(name = "m_id")
    private Long mId;

    @Column(name = "mtch_end_time")
    private ZonedDateTime mtchEndTime;

    @Column(name = "w_plyr_1_id")
    private Long wPlyr1Id;

    @Column(name = "w_plyr_1_lvl")
    private String wPlyr1Lvl;

    @Column(name = "w_plyr_1_nm")
    private String wPlyr1Nm;

    @Column(name = "w_plyr_2_id")
    private Long wPlyr2Id;

    @Column(name = "w_plyr_2_lvl")
    private String wPlyr2Lvl;

    @Column(name = "w_plyr_2_nm")
    private String wPlyr2Nm;

    @Column(name = "vs")
    private String vs;

    @Column(name = "l_plyr_1_id")
    private Long lPlyr1Id;

    @Column(name = "l_plyr_1_lvl")
    private String lPlyr1Lvl;

    @Column(name = "l_plyr_1_nm")
    private String lPlyr1Nm;

    @Column(name = "l_plyr_2_id")
    private Long lPlyr2Id;

    @Column(name = "l_plyr_2_lvl")
    private String lPlyr2Lvl;

    @Column(name = "l_plyr_2_nm")
    private String lPlyr2Nm;

    @Column(name = "w_scr")
    private String wScr;

    @Column(name = "l_scr")
    private String lScr;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public VwWcc701Result id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long geteId() {
        return this.eId;
    }

    public VwWcc701Result eId(Long eId) {
        this.seteId(eId);
        return this;
    }

    public void seteId(Long eId) {
        this.eId = eId;
    }

    public Long gettId() {
        return this.tId;
    }

    public VwWcc701Result tId(Long tId) {
        this.settId(tId);
        return this;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public String getEvntNm() {
        return this.evntNm;
    }

    public VwWcc701Result evntNm(String evntNm) {
        this.setEvntNm(evntNm);
        return this;
    }

    public void setEvntNm(String evntNm) {
        this.evntNm = evntNm;
    }

    public ZonedDateTime getEvntDt() {
        return this.evntDt;
    }

    public VwWcc701Result evntDt(ZonedDateTime evntDt) {
        this.setEvntDt(evntDt);
        return this;
    }

    public void setEvntDt(ZonedDateTime evntDt) {
        this.evntDt = evntDt;
    }

    public String getVenue() {
        return this.venue;
    }

    public VwWcc701Result venue(String venue) {
        this.setVenue(venue);
        return this;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Long getmId() {
        return this.mId;
    }

    public VwWcc701Result mId(Long mId) {
        this.setmId(mId);
        return this;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    public ZonedDateTime getMtchEndTime() {
        return this.mtchEndTime;
    }

    public VwWcc701Result mtchEndTime(ZonedDateTime mtchEndTime) {
        this.setMtchEndTime(mtchEndTime);
        return this;
    }

    public void setMtchEndTime(ZonedDateTime mtchEndTime) {
        this.mtchEndTime = mtchEndTime;
    }

    public Long getwPlyr1Id() {
        return this.wPlyr1Id;
    }

    public VwWcc701Result wPlyr1Id(Long wPlyr1Id) {
        this.setwPlyr1Id(wPlyr1Id);
        return this;
    }

    public void setwPlyr1Id(Long wPlyr1Id) {
        this.wPlyr1Id = wPlyr1Id;
    }

    public String getwPlyr1Lvl() {
        return this.wPlyr1Lvl;
    }

    public VwWcc701Result wPlyr1Lvl(String wPlyr1Lvl) {
        this.setwPlyr1Lvl(wPlyr1Lvl);
        return this;
    }

    public void setwPlyr1Lvl(String wPlyr1Lvl) {
        this.wPlyr1Lvl = wPlyr1Lvl;
    }

    public String getwPlyr1Nm() {
        return this.wPlyr1Nm;
    }

    public VwWcc701Result wPlyr1Nm(String wPlyr1Nm) {
        this.setwPlyr1Nm(wPlyr1Nm);
        return this;
    }

    public void setwPlyr1Nm(String wPlyr1Nm) {
        this.wPlyr1Nm = wPlyr1Nm;
    }

    public Long getwPlyr2Id() {
        return this.wPlyr2Id;
    }

    public VwWcc701Result wPlyr2Id(Long wPlyr2Id) {
        this.setwPlyr2Id(wPlyr2Id);
        return this;
    }

    public void setwPlyr2Id(Long wPlyr2Id) {
        this.wPlyr2Id = wPlyr2Id;
    }

    public String getwPlyr2Lvl() {
        return this.wPlyr2Lvl;
    }

    public VwWcc701Result wPlyr2Lvl(String wPlyr2Lvl) {
        this.setwPlyr2Lvl(wPlyr2Lvl);
        return this;
    }

    public void setwPlyr2Lvl(String wPlyr2Lvl) {
        this.wPlyr2Lvl = wPlyr2Lvl;
    }

    public String getwPlyr2Nm() {
        return this.wPlyr2Nm;
    }

    public VwWcc701Result wPlyr2Nm(String wPlyr2Nm) {
        this.setwPlyr2Nm(wPlyr2Nm);
        return this;
    }

    public void setwPlyr2Nm(String wPlyr2Nm) {
        this.wPlyr2Nm = wPlyr2Nm;
    }

    public String getVs() {
        return this.vs;
    }

    public VwWcc701Result vs(String vs) {
        this.setVs(vs);
        return this;
    }

    public void setVs(String vs) {
        this.vs = vs;
    }

    public Long getlPlyr1Id() {
        return this.lPlyr1Id;
    }

    public VwWcc701Result lPlyr1Id(Long lPlyr1Id) {
        this.setlPlyr1Id(lPlyr1Id);
        return this;
    }

    public void setlPlyr1Id(Long lPlyr1Id) {
        this.lPlyr1Id = lPlyr1Id;
    }

    public String getlPlyr1Lvl() {
        return this.lPlyr1Lvl;
    }

    public VwWcc701Result lPlyr1Lvl(String lPlyr1Lvl) {
        this.setlPlyr1Lvl(lPlyr1Lvl);
        return this;
    }

    public void setlPlyr1Lvl(String lPlyr1Lvl) {
        this.lPlyr1Lvl = lPlyr1Lvl;
    }

    public String getlPlyr1Nm() {
        return this.lPlyr1Nm;
    }

    public VwWcc701Result lPlyr1Nm(String lPlyr1Nm) {
        this.setlPlyr1Nm(lPlyr1Nm);
        return this;
    }

    public void setlPlyr1Nm(String lPlyr1Nm) {
        this.lPlyr1Nm = lPlyr1Nm;
    }

    public Long getlPlyr2Id() {
        return this.lPlyr2Id;
    }

    public VwWcc701Result lPlyr2Id(Long lPlyr2Id) {
        this.setlPlyr2Id(lPlyr2Id);
        return this;
    }

    public void setlPlyr2Id(Long lPlyr2Id) {
        this.lPlyr2Id = lPlyr2Id;
    }

    public String getlPlyr2Lvl() {
        return this.lPlyr2Lvl;
    }

    public VwWcc701Result lPlyr2Lvl(String lPlyr2Lvl) {
        this.setlPlyr2Lvl(lPlyr2Lvl);
        return this;
    }

    public void setlPlyr2Lvl(String lPlyr2Lvl) {
        this.lPlyr2Lvl = lPlyr2Lvl;
    }

    public String getlPlyr2Nm() {
        return this.lPlyr2Nm;
    }

    public VwWcc701Result lPlyr2Nm(String lPlyr2Nm) {
        this.setlPlyr2Nm(lPlyr2Nm);
        return this;
    }

    public void setlPlyr2Nm(String lPlyr2Nm) {
        this.lPlyr2Nm = lPlyr2Nm;
    }

    public String getwScr() {
        return this.wScr;
    }

    public VwWcc701Result wScr(String wScr) {
        this.setwScr(wScr);
        return this;
    }

    public void setwScr(String wScr) {
        this.wScr = wScr;
    }

    public String getlScr() {
        return this.lScr;
    }

    public VwWcc701Result lScr(String lScr) {
        this.setlScr(lScr);
        return this;
    }

    public void setlScr(String lScr) {
        this.lScr = lScr;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VwWcc701Result)) {
            return false;
        }
        return id != null && id.equals(((VwWcc701Result) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VwWcc701Result{" +
            "id=" + getId() +
            ", eId=" + geteId() +
            ", tId=" + gettId() +
            ", evntNm='" + getEvntNm() + "'" +
            ", evntDt='" + getEvntDt() + "'" +
            ", venue='" + getVenue() + "'" +
            ", mId=" + getmId() +
            ", mtchEndTime='" + getMtchEndTime() + "'" +
            ", wPlyr1Id=" + getwPlyr1Id() +
            ", wPlyr1Lvl='" + getwPlyr1Lvl() + "'" +
            ", wPlyr1Nm='" + getwPlyr1Nm() + "'" +
            ", wPlyr2Id=" + getwPlyr2Id() +
            ", wPlyr2Lvl='" + getwPlyr2Lvl() + "'" +
            ", wPlyr2Nm='" + getwPlyr2Nm() + "'" +
            ", vs='" + getVs() + "'" +
            ", lPlyr1Id=" + getlPlyr1Id() +
            ", lPlyr1Lvl='" + getlPlyr1Lvl() + "'" +
            ", lPlyr1Nm='" + getlPlyr1Nm() + "'" +
            ", lPlyr2Id=" + getlPlyr2Id() +
            ", lPlyr2Lvl='" + getlPlyr2Lvl() + "'" +
            ", lPlyr2Nm='" + getlPlyr2Nm() + "'" +
            ", wScr='" + getwScr() + "'" +
            ", lScr='" + getlScr() + "'" +
            "}";
    }
}
