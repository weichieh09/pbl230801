package com.wcc.pbl230801.pblService.dto;

import java.io.Serializable;

public class EventZReqDTOC implements Serializable {

    private String eId;
    private String evntNm;
    private String evntDt;
    private String venue;
    private String eventBegTime;
    private String eventEndTime;

    public String geteId() {
        return eId;
    }

    public void seteId(String eId) {
        this.eId = eId;
    }

    public String getEvntNm() {
        return evntNm;
    }

    public void setEvntNm(String evntNm) {
        this.evntNm = evntNm;
    }

    public String getEvntDt() {
        return evntDt;
    }

    public void setEvntDt(String evntDt) {
        this.evntDt = evntDt;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getEventBegTime() {
        return eventBegTime;
    }

    public void setEventBegTime(String eventBegTime) {
        this.eventBegTime = eventBegTime;
    }

    public String getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(String eventEndTime) {
        this.eventEndTime = eventEndTime;
    }
}
