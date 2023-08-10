package com.wcc.pbl230801.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.wcc.pbl230801.domain.EventPlayer} entity. This class is used
 * in {@link com.wcc.pbl230801.web.rest.EventPlayerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /event-players?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventPlayerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter eId;

    private LongFilter pId;

    private StringFilter chkFg;

    private StringFilter lstMtnUsr;

    private ZonedDateTimeFilter lstMtnDt;

    private Boolean distinct;

    public EventPlayerCriteria() {}

    public EventPlayerCriteria(EventPlayerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.eId = other.eId == null ? null : other.eId.copy();
        this.pId = other.pId == null ? null : other.pId.copy();
        this.chkFg = other.chkFg == null ? null : other.chkFg.copy();
        this.lstMtnUsr = other.lstMtnUsr == null ? null : other.lstMtnUsr.copy();
        this.lstMtnDt = other.lstMtnDt == null ? null : other.lstMtnDt.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EventPlayerCriteria copy() {
        return new EventPlayerCriteria(this);
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

    public LongFilter geteId() {
        return eId;
    }

    public LongFilter eId() {
        if (eId == null) {
            eId = new LongFilter();
        }
        return eId;
    }

    public void seteId(LongFilter eId) {
        this.eId = eId;
    }

    public LongFilter getpId() {
        return pId;
    }

    public LongFilter pId() {
        if (pId == null) {
            pId = new LongFilter();
        }
        return pId;
    }

    public void setpId(LongFilter pId) {
        this.pId = pId;
    }

    public StringFilter getChkFg() {
        return chkFg;
    }

    public StringFilter chkFg() {
        if (chkFg == null) {
            chkFg = new StringFilter();
        }
        return chkFg;
    }

    public void setChkFg(StringFilter chkFg) {
        this.chkFg = chkFg;
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
        final EventPlayerCriteria that = (EventPlayerCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(eId, that.eId) &&
            Objects.equals(pId, that.pId) &&
            Objects.equals(chkFg, that.chkFg) &&
            Objects.equals(lstMtnUsr, that.lstMtnUsr) &&
            Objects.equals(lstMtnDt, that.lstMtnDt) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eId, pId, chkFg, lstMtnUsr, lstMtnDt, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventPlayerCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (eId != null ? "eId=" + eId + ", " : "") +
            (pId != null ? "pId=" + pId + ", " : "") +
            (chkFg != null ? "chkFg=" + chkFg + ", " : "") +
            (lstMtnUsr != null ? "lstMtnUsr=" + lstMtnUsr + ", " : "") +
            (lstMtnDt != null ? "lstMtnDt=" + lstMtnDt + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
