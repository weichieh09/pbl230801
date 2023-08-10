package com.wcc.pbl230801.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.wcc.pbl230801.domain.VwEventResult} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VwEventResultDTO implements Serializable {

    private Long id;

    private Long eId;

    private Long pId;

    private Long mId;

    private String winFg;

    private String plyrNm;

    private String plyrLvl;

    private ZonedDateTime mtchEndTime;

    private String totMatchs;

    private String totWins;

    private String acmlWins;

    private String chkFg;

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

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    public String getWinFg() {
        return winFg;
    }

    public void setWinFg(String winFg) {
        this.winFg = winFg;
    }

    public String getPlyrNm() {
        return plyrNm;
    }

    public void setPlyrNm(String plyrNm) {
        this.plyrNm = plyrNm;
    }

    public String getPlyrLvl() {
        return plyrLvl;
    }

    public void setPlyrLvl(String plyrLvl) {
        this.plyrLvl = plyrLvl;
    }

    public ZonedDateTime getMtchEndTime() {
        return mtchEndTime;
    }

    public void setMtchEndTime(ZonedDateTime mtchEndTime) {
        this.mtchEndTime = mtchEndTime;
    }

    public String getTotMatchs() {
        return totMatchs;
    }

    public void setTotMatchs(String totMatchs) {
        this.totMatchs = totMatchs;
    }

    public String getTotWins() {
        return totWins;
    }

    public void setTotWins(String totWins) {
        this.totWins = totWins;
    }

    public String getAcmlWins() {
        return acmlWins;
    }

    public void setAcmlWins(String acmlWins) {
        this.acmlWins = acmlWins;
    }

    public String getChkFg() {
        return chkFg;
    }

    public void setChkFg(String chkFg) {
        this.chkFg = chkFg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VwEventResultDTO)) {
            return false;
        }

        VwEventResultDTO vwEventResultDTO = (VwEventResultDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vwEventResultDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VwEventResultDTO{" +
            "id=" + getId() +
            ", eId=" + geteId() +
            ", pId=" + getpId() +
            ", mId=" + getmId() +
            ", winFg='" + getWinFg() + "'" +
            ", plyrNm='" + getPlyrNm() + "'" +
            ", plyrLvl='" + getPlyrLvl() + "'" +
            ", mtchEndTime='" + getMtchEndTime() + "'" +
            ", totMatchs='" + getTotMatchs() + "'" +
            ", totWins='" + getTotWins() + "'" +
            ", acmlWins='" + getAcmlWins() + "'" +
            ", chkFg='" + getChkFg() + "'" +
            "}";
    }
}
