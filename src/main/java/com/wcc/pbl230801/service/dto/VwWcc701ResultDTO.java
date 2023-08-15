package com.wcc.pbl230801.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.wcc.pbl230801.domain.VwWcc701Result} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VwWcc701ResultDTO implements Serializable {

    private Long id;

    private Long eId;

    private Long tId;

    private String evntNm;

    private ZonedDateTime evntDt;

    private String venue;

    private Long mId;

    private ZonedDateTime mtchEndTime;

    private Long wPlyr1Id;

    private String wPlyr1Lvl;

    private String wPlyr1Nm;

    private Long wPlyr2Id;

    private String wPlyr2Lvl;

    private String wPlyr2Nm;

    private String vs;

    private Long lPlyr1Id;

    private String lPlyr1Lvl;

    private String lPlyr1Nm;

    private Long lPlyr2Id;

    private String lPlyr2Lvl;

    private String lPlyr2Nm;

    private String wScr;

    private String lScr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long geteId() {
        return eId;
    }

    public void seteId(Long eId) {
        this.eId = eId;
    }

    public Long gettId() {
        return tId;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public String getEvntNm() {
        return evntNm;
    }

    public void setEvntNm(String evntNm) {
        this.evntNm = evntNm;
    }

    public ZonedDateTime getEvntDt() {
        return evntDt;
    }

    public void setEvntDt(ZonedDateTime evntDt) {
        this.evntDt = evntDt;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    public ZonedDateTime getMtchEndTime() {
        return mtchEndTime;
    }

    public void setMtchEndTime(ZonedDateTime mtchEndTime) {
        this.mtchEndTime = mtchEndTime;
    }

    public Long getwPlyr1Id() {
        return wPlyr1Id;
    }

    public void setwPlyr1Id(Long wPlyr1Id) {
        this.wPlyr1Id = wPlyr1Id;
    }

    public String getwPlyr1Lvl() {
        return wPlyr1Lvl;
    }

    public void setwPlyr1Lvl(String wPlyr1Lvl) {
        this.wPlyr1Lvl = wPlyr1Lvl;
    }

    public String getwPlyr1Nm() {
        return wPlyr1Nm;
    }

    public void setwPlyr1Nm(String wPlyr1Nm) {
        this.wPlyr1Nm = wPlyr1Nm;
    }

    public Long getwPlyr2Id() {
        return wPlyr2Id;
    }

    public void setwPlyr2Id(Long wPlyr2Id) {
        this.wPlyr2Id = wPlyr2Id;
    }

    public String getwPlyr2Lvl() {
        return wPlyr2Lvl;
    }

    public void setwPlyr2Lvl(String wPlyr2Lvl) {
        this.wPlyr2Lvl = wPlyr2Lvl;
    }

    public String getwPlyr2Nm() {
        return wPlyr2Nm;
    }

    public void setwPlyr2Nm(String wPlyr2Nm) {
        this.wPlyr2Nm = wPlyr2Nm;
    }

    public String getVs() {
        return vs;
    }

    public void setVs(String vs) {
        this.vs = vs;
    }

    public Long getlPlyr1Id() {
        return lPlyr1Id;
    }

    public void setlPlyr1Id(Long lPlyr1Id) {
        this.lPlyr1Id = lPlyr1Id;
    }

    public String getlPlyr1Lvl() {
        return lPlyr1Lvl;
    }

    public void setlPlyr1Lvl(String lPlyr1Lvl) {
        this.lPlyr1Lvl = lPlyr1Lvl;
    }

    public String getlPlyr1Nm() {
        return lPlyr1Nm;
    }

    public void setlPlyr1Nm(String lPlyr1Nm) {
        this.lPlyr1Nm = lPlyr1Nm;
    }

    public Long getlPlyr2Id() {
        return lPlyr2Id;
    }

    public void setlPlyr2Id(Long lPlyr2Id) {
        this.lPlyr2Id = lPlyr2Id;
    }

    public String getlPlyr2Lvl() {
        return lPlyr2Lvl;
    }

    public void setlPlyr2Lvl(String lPlyr2Lvl) {
        this.lPlyr2Lvl = lPlyr2Lvl;
    }

    public String getlPlyr2Nm() {
        return lPlyr2Nm;
    }

    public void setlPlyr2Nm(String lPlyr2Nm) {
        this.lPlyr2Nm = lPlyr2Nm;
    }

    public String getwScr() {
        return wScr;
    }

    public void setwScr(String wScr) {
        this.wScr = wScr;
    }

    public String getlScr() {
        return lScr;
    }

    public void setlScr(String lScr) {
        this.lScr = lScr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VwWcc701ResultDTO)) {
            return false;
        }

        VwWcc701ResultDTO vwWcc701ResultDTO = (VwWcc701ResultDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vwWcc701ResultDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VwWcc701ResultDTO{" +
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
