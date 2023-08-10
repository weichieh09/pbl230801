package com.wcc.pbl230801.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.wcc.pbl230801.domain.EventZ} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventZDTO implements Serializable {

    private Long id;

    private String evntNm;

    private ZonedDateTime evntDt;

    private String venue;

    private ZonedDateTime eventBegTime;

    private ZonedDateTime eventEndTime;

    private String lstMtnUsr;

    private ZonedDateTime lstMtnDt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ZonedDateTime getEventBegTime() {
        return eventBegTime;
    }

    public void setEventBegTime(ZonedDateTime eventBegTime) {
        this.eventBegTime = eventBegTime;
    }

    public ZonedDateTime getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(ZonedDateTime eventEndTime) {
        this.eventEndTime = eventEndTime;
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
        if (!(o instanceof EventZDTO)) {
            return false;
        }

        EventZDTO eventZDTO = (EventZDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eventZDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventZDTO{" +
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
