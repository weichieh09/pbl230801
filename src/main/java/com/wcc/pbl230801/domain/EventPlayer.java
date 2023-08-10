package com.wcc.pbl230801.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;

/**
 * A EventPlayer.
 */
@Entity
@Table(name = "event_player")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventPlayer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "e_id")
    private Long eId;

    @Column(name = "p_id")
    private Long pId;

    @Column(name = "chk_fg")
    private String chkFg;

    @Column(name = "lst_mtn_usr")
    private String lstMtnUsr;

    @Column(name = "lst_mtn_dt")
    private ZonedDateTime lstMtnDt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EventPlayer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long geteId() {
        return this.eId;
    }

    public EventPlayer eId(Long eId) {
        this.seteId(eId);
        return this;
    }

    public void seteId(Long eId) {
        this.eId = eId;
    }

    public Long getpId() {
        return this.pId;
    }

    public EventPlayer pId(Long pId) {
        this.setpId(pId);
        return this;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getChkFg() {
        return this.chkFg;
    }

    public EventPlayer chkFg(String chkFg) {
        this.setChkFg(chkFg);
        return this;
    }

    public void setChkFg(String chkFg) {
        this.chkFg = chkFg;
    }

    public String getLstMtnUsr() {
        return this.lstMtnUsr;
    }

    public EventPlayer lstMtnUsr(String lstMtnUsr) {
        this.setLstMtnUsr(lstMtnUsr);
        return this;
    }

    public void setLstMtnUsr(String lstMtnUsr) {
        this.lstMtnUsr = lstMtnUsr;
    }

    public ZonedDateTime getLstMtnDt() {
        return this.lstMtnDt;
    }

    public EventPlayer lstMtnDt(ZonedDateTime lstMtnDt) {
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
        if (!(o instanceof EventPlayer)) {
            return false;
        }
        return id != null && id.equals(((EventPlayer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventPlayer{" +
            "id=" + getId() +
            ", eId=" + geteId() +
            ", pId=" + getpId() +
            ", chkFg='" + getChkFg() + "'" +
            ", lstMtnUsr='" + getLstMtnUsr() + "'" +
            ", lstMtnDt='" + getLstMtnDt() + "'" +
            "}";
    }
}
