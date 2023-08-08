package com.wcc.pbl230801.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.wcc.pbl230801.domain.Prod} entity. This class is used
 * in {@link com.wcc.pbl230801.web.rest.ProdResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /prods?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProdCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter prodNo;

    private StringFilter chName;

    private LocalDateFilter issuDt;

    private LocalDateFilter expDt;

    private Boolean distinct;

    public ProdCriteria() {}

    public ProdCriteria(ProdCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.prodNo = other.prodNo == null ? null : other.prodNo.copy();
        this.chName = other.chName == null ? null : other.chName.copy();
        this.issuDt = other.issuDt == null ? null : other.issuDt.copy();
        this.expDt = other.expDt == null ? null : other.expDt.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProdCriteria copy() {
        return new ProdCriteria(this);
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

    public StringFilter getProdNo() {
        return prodNo;
    }

    public StringFilter prodNo() {
        if (prodNo == null) {
            prodNo = new StringFilter();
        }
        return prodNo;
    }

    public void setProdNo(StringFilter prodNo) {
        this.prodNo = prodNo;
    }

    public StringFilter getChName() {
        return chName;
    }

    public StringFilter chName() {
        if (chName == null) {
            chName = new StringFilter();
        }
        return chName;
    }

    public void setChName(StringFilter chName) {
        this.chName = chName;
    }

    public LocalDateFilter getIssuDt() {
        return issuDt;
    }

    public LocalDateFilter issuDt() {
        if (issuDt == null) {
            issuDt = new LocalDateFilter();
        }
        return issuDt;
    }

    public void setIssuDt(LocalDateFilter issuDt) {
        this.issuDt = issuDt;
    }

    public LocalDateFilter getExpDt() {
        return expDt;
    }

    public LocalDateFilter expDt() {
        if (expDt == null) {
            expDt = new LocalDateFilter();
        }
        return expDt;
    }

    public void setExpDt(LocalDateFilter expDt) {
        this.expDt = expDt;
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
        final ProdCriteria that = (ProdCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(prodNo, that.prodNo) &&
            Objects.equals(chName, that.chName) &&
            Objects.equals(issuDt, that.issuDt) &&
            Objects.equals(expDt, that.expDt) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, prodNo, chName, issuDt, expDt, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProdCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (prodNo != null ? "prodNo=" + prodNo + ", " : "") +
            (chName != null ? "chName=" + chName + ", " : "") +
            (issuDt != null ? "issuDt=" + issuDt + ", " : "") +
            (expDt != null ? "expDt=" + expDt + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
