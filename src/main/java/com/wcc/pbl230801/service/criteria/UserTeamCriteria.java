package com.wcc.pbl230801.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.wcc.pbl230801.domain.UserTeam} entity. This class is used
 * in {@link com.wcc.pbl230801.web.rest.UserTeamResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-teams?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserTeamCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter uId;

    private LongFilter tId;

    private StringFilter lstMtnUsr;

    private ZonedDateTimeFilter lstMtnDt;

    private Boolean distinct;

    public UserTeamCriteria() {}

    public UserTeamCriteria(UserTeamCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uId = other.uId == null ? null : other.uId.copy();
        this.tId = other.tId == null ? null : other.tId.copy();
        this.lstMtnUsr = other.lstMtnUsr == null ? null : other.lstMtnUsr.copy();
        this.lstMtnDt = other.lstMtnDt == null ? null : other.lstMtnDt.copy();
        this.distinct = other.distinct;
    }

    @Override
    public UserTeamCriteria copy() {
        return new UserTeamCriteria(this);
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

    public LongFilter getuId() {
        return uId;
    }

    public LongFilter uId() {
        if (uId == null) {
            uId = new LongFilter();
        }
        return uId;
    }

    public void setuId(LongFilter uId) {
        this.uId = uId;
    }

    public LongFilter gettId() {
        return tId;
    }

    public LongFilter tId() {
        if (tId == null) {
            tId = new LongFilter();
        }
        return tId;
    }

    public void settId(LongFilter tId) {
        this.tId = tId;
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
        final UserTeamCriteria that = (UserTeamCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(uId, that.uId) &&
            Objects.equals(tId, that.tId) &&
            Objects.equals(lstMtnUsr, that.lstMtnUsr) &&
            Objects.equals(lstMtnDt, that.lstMtnDt) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uId, tId, lstMtnUsr, lstMtnDt, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserTeamCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (uId != null ? "uId=" + uId + ", " : "") +
            (tId != null ? "tId=" + tId + ", " : "") +
            (lstMtnUsr != null ? "lstMtnUsr=" + lstMtnUsr + ", " : "") +
            (lstMtnDt != null ? "lstMtnDt=" + lstMtnDt + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
