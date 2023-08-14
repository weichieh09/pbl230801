package com.wcc.pbl230801.pblService.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class EventZRespDTOC implements Serializable {

    private Long id;

    private String evntNm;

    private ZonedDateTime evntDt;

    private String venue;

    private ZonedDateTime eventBegTime;

    private ZonedDateTime eventEndTime;

    private String lstMtnUsr;

    private ZonedDateTime lstMtnDt;

    private String tId;

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

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }
}
