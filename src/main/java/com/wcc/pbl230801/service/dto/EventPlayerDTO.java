package com.wcc.pbl230801.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.wcc.pbl230801.domain.EventPlayer} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventPlayerDTO implements Serializable {

    private Long id;

    private Long eId;

    private Long pId;

    private String chkFg;

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

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getChkFg() {
        return chkFg;
    }

    public void setChkFg(String chkFg) {
        this.chkFg = chkFg;
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
        if (!(o instanceof EventPlayerDTO)) {
            return false;
        }

        EventPlayerDTO eventPlayerDTO = (EventPlayerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eventPlayerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventPlayerDTO{" +
            "id=" + getId() +
            ", eId=" + geteId() +
            ", pId=" + getpId() +
            ", chkFg='" + getChkFg() + "'" +
            ", lstMtnUsr='" + getLstMtnUsr() + "'" +
            ", lstMtnDt='" + getLstMtnDt() + "'" +
            "}";
    }
}
