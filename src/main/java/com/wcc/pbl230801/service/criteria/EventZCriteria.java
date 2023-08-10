package com.wcc.pbl230801.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.wcc.pbl230801.domain.EventZ} entity. This class is used
 * in {@link com.wcc.pbl230801.web.rest.EventZResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /event-zs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventZCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter evntNm;

    private ZonedDateTimeFilter evntDt;

    private StringFilter venue;

    private ZonedDateTimeFilter eventBegTime;

    private ZonedDateTimeFilter eventEndTime;

    private StringFilter lstMtnUsr;

    private ZonedDateTimeFilter lstMtnDt;

    private Boolean distinct;

    public EventZCriteria() {}

    public EventZCriteria(EventZCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.evntNm = other.evntNm == null ? null : other.evntNm.copy();
        this.evntDt = other.evntDt == null ? null : other.evntDt.copy();
        this.venue = other.venue == null ? null : other.venue.copy();
        this.eventBegTime = other.eventBegTime == null ? null : other.eventBegTime.copy();
        this.eventEndTime = other.eventEndTime == null ? null : other.eventEndTime.copy();
        this.lstMtnUsr = other.lstMtnUsr == null ? null : other.lstMtnUsr.copy();
        this.lstMtnDt = other.lstMtnDt == null ? null : other.lstMtnDt.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EventZCriteria copy() {
        return new EventZCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEvntNm() {
        return evntNm;
    }

    public StringFilter evntNm() {
        if (evntNm == null) {
            evntNm = new StringFilter();
        }
        return evntNm;
    }

    public void setEvntNm(StringFilter evntNm) {
        this.evntNm = evntNm;
    }

    public ZonedDateTimeFilter getEvntDt() {
        return evntDt;
    }

    public ZonedDateTimeFilter evntDt() {
        if (evntDt == null) {
            evntDt = new ZonedDateTimeFilter();
        }
        return evntDt;
    }

    public void setEvntDt(ZonedDateTimeFilter evntDt) {
        this.evntDt = evntDt;
    }

    public StringFilter getVenue() {
        return venue;
    }

    public StringFilter venue() {
        if (venue == null) {
            venue = new StringFilter();
        }
        return venue;
    }

    public void setVenue(StringFilter venue) {
        this.venue = venue;
    }

    public ZonedDateTimeFilter getEventBegTime() {
        return eventBegTime;
    }

    public ZonedDateTimeFilter eventBegTime() {
        if (eventBegTime == null) {
            eventBegTime = new ZonedDateTimeFilter();
        }
        return eventBegTime;
    }

    public void setEventBegTime(ZonedDateTimeFilter eventBegTime) {
        this.eventBegTime = eventBegTime;
    }

    public ZonedDateTimeFilter getEventEndTime() {
        return eventEndTime;
    }

    public ZonedDateTimeFilter eventEndTime() {
        if (eventEndTime == null) {
            eventEndTime = new ZonedDateTimeFilter();
        }
        return eventEndTime;
    }

    public void setEventEndTime(ZonedDateTimeFilter eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public StringFilter getLstMtnUsr() {
        return lstMtnUsr;
    }

    public StringFilter lstMtnUsr() {
        if (lstMtnUsr == null) {
            lstMtnUsr = new StringFilter();
        }
        return lstMtnUsr;
    }

    public void setLstMtnUsr(StringFilter lstMtnUsr) {
        this.lstMtnUsr = lstMtnUsr;
    }

    public ZonedDateTimeFilter getLstMtnDt() {
        return lstMtnDt;
    }

    public ZonedDateTimeFilter lstMtnDt() {
        if (lstMtnDt == null) {
            lstMtnDt = new ZonedDateTimeFilter();
        }
        return lstMtnDt;
    }

    public void setLstMtnDt(ZonedDateTimeFilter lstMtnDt) {
        this.lstMtnDt = lstMtnDt;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EventZCriteria that = (EventZCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(evntNm, that.evntNm) &&
            Objects.equals(evntDt, that.evntDt) &&
            Objects.equals(venue, that.venue) &&
            Objects.equals(eventBegTime, that.eventBegTime) &&
            Objects.equals(eventEndTime, that.eventEndTime) &&
            Objects.equals(lstMtnUsr, that.lstMtnUsr) &&
            Objects.equals(lstMtnDt, that.lstMtnDt) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, evntNm, evntDt, venue, eventBegTime, eventEndTime, lstMtnUsr, lstMtnDt, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventZCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (evntNm != null ? "evntNm=" + evntNm + ", " : "") +
            (evntDt != null ? "evntDt=" + evntDt + ", " : "") +
            (venue != null ? "venue=" + venue + ", " : "") +
            (eventBegTime != null ? "eventBegTime=" + eventBegTime + ", " : "") +
            (eventEndTime != null ? "eventEndTime=" + eventEndTime + ", " : "") +
            (lstMtnUsr != null ? "lstMtnUsr=" + lstMtnUsr + ", " : "") +
            (lstMtnDt != null ? "lstMtnDt=" + lstMtnDt + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
