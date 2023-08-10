package com.wcc.pbl230801.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.wcc.pbl230801.domain.MatchZ} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatchZDTO implements Serializable {

    private Long id;

    private Long eId;

    private ZonedDateTime mtchEndTime;

    private String wPlyr1;

    private String wPlyr2;

    private String wScr;

    private String lPlyr1;

    private String lPlyr2;

    private String lScr;

    private String lstMtnUsr;

    private ZonedDateTime lstMtnDt;

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

    public ZonedDateTime getMtchEndTime() {
        return mtchEndTime;
    }

    public void setMtchEndTime(ZonedDateTime mtchEndTime) {
        this.mtchEndTime = mtchEndTime;
    }

    public String getwPlyr1() {
        return wPlyr1;
    }

    public void setwPlyr1(String wPlyr1) {
        this.wPlyr1 = wPlyr1;
    }

    public String getwPlyr2() {
        return wPlyr2;
    }

    public void setwPlyr2(String wPlyr2) {
        this.wPlyr2 = wPlyr2;
    }

    public String getwScr() {
        return wScr;
    }

    public void setwScr(String wScr) {
        this.wScr = wScr;
    }

    public String getlPlyr1() {
        return lPlyr1;
    }

    public void setlPlyr1(String lPlyr1) {
        this.lPlyr1 = lPlyr1;
    }

    public String getlPlyr2() {
        return lPlyr2;
    }

    public void setlPlyr2(String lPlyr2) {
        this.lPlyr2 = lPlyr2;
    }

    public String getlScr() {
        return lScr;
    }

    public void setlScr(String lScr) {
        this.lScr = lScr;
    }

    public String getLstMtnUsr() {
        return lstMtnUsr;
    }

    public void setLstMtnUsr(String lstMtnUsr) {
        this.lstMtnUsr = lstMtnUsr;
    }

    public ZonedDateTime getLstMtnDt() {
        return lstMtnDt;
    }

    public void setLstMtnDt(ZonedDateTime lstMtnDt) {
        this.lstMtnDt = lstMtnDt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MatchZDTO)) {
            return false;
        }

        MatchZDTO matchZDTO = (MatchZDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, matchZDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchZDTO{" +
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
