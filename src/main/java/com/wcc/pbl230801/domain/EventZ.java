package com.wcc.pbl230801.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;

/**
 * A EventZ.
 */
@Entity
@Table(name = "event_z")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventZ implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "evnt_nm")
    private String evntNm;

    @Column(name = "evnt_dt")
    private ZonedDateTime evntDt;

    @Column(name = "venue")
    private String venue;

    @Column(name = "event_beg_time")
    private ZonedDateTime eventBegTime;

    @Column(name = "event_end_time")
    private ZonedDateTime eventEndTime;

    @Column(name = "lst_mtn_usr")
    private String lstMtnUsr;

    @Column(name = "lst_mtn_dt")
    private ZonedDateTime lstMtnDt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EventZ id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvntNm() {
        return this.evntNm;
    }

    public EventZ evntNm(String evntNm) {
        this.setEvntNm(evntNm);
        return this;
    }

    public void setEvntNm(String evntNm) {
        this.evntNm = evntNm;
    }

    public ZonedDateTime getEvntDt() {
        return this.evntDt;
    }

    public EventZ evntDt(ZonedDateTime evntDt) {
        this.setEvntDt(evntDt);
        return this;
    }

    public void setEvntDt(ZonedDateTime evntDt) {
        this.evntDt = evntDt;
    }

    public String getVenue() {
        return this.venue;
    }

    public EventZ venue(String venue) {
        this.setVenue(venue);
        return this;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public ZonedDateTime getEventBegTime() {
        return this.eventBegTime;
    }

    public EventZ eventBegTime(ZonedDateTime eventBegTime) {
        this.setEventBegTime(eventBegTime);
        return this;
    }

    public void setEventBegTime(ZonedDateTime eventBegTime) {
        this.eventBegTime = eventBegTime;
    }

    public ZonedDateTime getEventEndTime() {
        return this.eventEndTime;
    }

    public EventZ eventEndTime(ZonedDateTime eventEndTime) {
        this.setEventEndTime(eventEndTime);
        return this;
    }

    public void setEventEndTime(ZonedDateTime eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public String getLstMtnUsr() {
        return this.lstMtnUsr;
    }

    public EventZ lstMtnUsr(String lstMtnUsr) {
        this.setLstMtnUsr(lstMtnUsr);
        return this;
    }

    public void setLstMtnUsr(String lstMtnUsr) {
        this.lstMtnUsr = lstMtnUsr;
    }

    public ZonedDateTime getLstMtnDt() {
        return this.lstMtnDt;
    }

    public EventZ lstMtnDt(ZonedDateTime lstMtnDt) {
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
        if (!(o instanceof EventZ)) {
            return false;
        }
        return id != null && id.equals(((EventZ) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventZ{" +
            "id=" + getId() +
            ", evntNm='" + getEvntNm() + "'" +
            ", evntDt='" + getEvntDt() + "'" +
            ", venue='" + getVenue() + "'" +
            ", eventBegTime='" + getEventBegTime() + "'" +
            ", eventEndTime='" + getEventEndTime() + "'" +
            ", lstMtnUsr='" + getLstMtnUsr() + "'" +
            ", lstMtnDt='" + getLstMtnDt() + "'" +
            "}";
    }
}
