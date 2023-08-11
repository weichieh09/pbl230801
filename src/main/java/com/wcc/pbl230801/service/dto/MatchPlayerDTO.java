package com.wcc.pbl230801.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.wcc.pbl230801.domain.MatchPlayer} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MatchPlayerDTO implements Serializable {

    private Long id;

    private Long mId;

    private Long pId;

    private Long eId;

    private ZonedDateTime mtchEndTime;

    private String score;

    private String winFg;

    private String lstMtnUsr;

    private ZonedDateTime lstMtnDt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getWinFg() {
        return winFg;
    }

    public void setWinFg(String winFg) {
        this.winFg = winFg;
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
        if (!(o instanceof MatchPlayerDTO)) {
            return false;
        }

        MatchPlayerDTO matchPlayerDTO = (MatchPlayerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, matchPlayerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MatchPlayerDTO{" +
            "id=" + getId() +
            ", mId=" + getmId() +
            ", pId=" + getpId() +
            ", eId=" + geteId() +
            ", mtchEndTime='" + getMtchEndTime() + "'" +
            ", score='" + getScore() + "'" +
            ", winFg='" + getWinFg() + "'" +
            ", lstMtnUsr='" + getLstMtnUsr() + "'" +
            ", lstMtnDt='" + getLstMtnDt() + "'" +
            "}";
    }
}
