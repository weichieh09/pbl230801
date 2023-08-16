package com.wcc.pbl230801.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.wcc.pbl230801.domain.Player} entity. This class is used
 * in {@link com.wcc.pbl230801.web.rest.PlayerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /players?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlayerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter plyrNm;

    private LongFilter plyrLvl;

    private StringFilter lstMtnUsr;

    private ZonedDateTimeFilter lstMtnDt;

    private Boolean distinct;

    public PlayerCriteria() {}

    public PlayerCriteria(PlayerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.plyrNm = other.plyrNm == null ? null : other.plyrNm.copy();
        this.plyrLvl = other.plyrLvl == null ? null : other.plyrLvl.copy();
        this.lstMtnUsr = other.lstMtnUsr == null ? null : other.lstMtnUsr.copy();
        this.lstMtnDt = other.lstMtnDt == null ? null : other.lstMtnDt.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PlayerCriteria copy() {
        return new PlayerCriteria(this);
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

    public StringFilter getPlyrNm() {
        return plyrNm;
    }

    public StringFilter plyrNm() {
        if (plyrNm == null) {
            plyrNm = new StringFilter();
        }
        return plyrNm;
    }

    public void setPlyrNm(StringFilter plyrNm) {
        this.plyrNm = plyrNm;
    }

    public LongFilter getPlyrLvl() {
        return plyrLvl;
    }

    public LongFilter plyrLvl() {
        if (plyrLvl == null) {
            plyrLvl = new LongFilter();
        }
        return plyrLvl;
    }

    public void setPlyrLvl(LongFilter plyrLvl) {
        this.plyrLvl = plyrLvl;
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
        final PlayerCriteria that = (PlayerCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(plyrNm, that.plyrNm) &&
            Objects.equals(plyrLvl, that.plyrLvl) &&
            Objects.equals(lstMtnUsr, that.lstMtnUsr) &&
            Objects.equals(lstMtnDt, that.lstMtnDt) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, plyrNm, plyrLvl, lstMtnUsr, lstMtnDt, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlayerCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (plyrNm != null ? "plyrNm=" + plyrNm + ", " : "") +
            (plyrLvl != null ? "plyrLvl=" + plyrLvl + ", " : "") +
            (lstMtnUsr != null ? "lstMtnUsr=" + lstMtnUsr + ", " : "") +
            (lstMtnDt != null ? "lstMtnDt=" + lstMtnDt + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
