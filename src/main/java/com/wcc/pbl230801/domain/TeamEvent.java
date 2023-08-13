package com.wcc.pbl230801.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;

/**
 * A TeamEvent.
 */
@Entity
@Table(name = "team_event")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TeamEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "t_id")
    private Long tId;

    @Column(name = "e_id")
    private Long eId;

    @Column(name = "lst_mtn_usr")
    private String lstMtnUsr;

    @Column(name = "lst_mtn_dt")
    private ZonedDateTime lstMtnDt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TeamEvent id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long gettId() {
        return this.tId;
    }

    public TeamEvent tId(Long tId) {
        this.settId(tId);
        return this;
    }

    public void settId(Long tId) {
        this.tId = tId;
    }

    public Long geteId() {
        return this.eId;
    }

    public TeamEvent eId(Long eId) {
        this.seteId(eId);
        return this;
    }

    public void seteId(Long eId) {
        this.eId = eId;
    }

    public String getLstMtnUsr() {
        return this.lstMtnUsr;
    }

    public TeamEvent lstMtnUsr(String lstMtnUsr) {
        this.setLstMtnUsr(lstMtnUsr);
        return this;
    }

    public void setLstMtnUsr(String lstMtnUsr) {
        this.lstMtnUsr = lstMtnUsr;
    }

    public ZonedDateTime getLstMtnDt() {
        return this.lstMtnDt;
    }

    public TeamEvent lstMtnDt(ZonedDateTime lstMtnDt) {
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
        if (!(o instanceof TeamEvent)) {
            return false;
        }
        return id != null && id.equals(((TeamEvent) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TeamEvent{" +
            "id=" + getId() +
            ", tId=" + gettId() +
            ", eId=" + geteId() +
            ", lstMtnUsr='" + getLstMtnUsr() + "'" +
            ", lstMtnDt='" + getLstMtnDt() + "'" +
            "}";
    }
}
