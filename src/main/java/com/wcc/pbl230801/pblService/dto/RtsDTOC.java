package com.wcc.pbl230801.pblService.dto;

import java.io.Serializable;

public class RtsDTOC implements Serializable {

    private String pId;
    private String plyrLvl;
    private String plyrNm;
    private String totWins;
    private String mtchEndTime;

    public String getPlyrNm() {
        return plyrNm;
    }

    public void setPlyrNm(String plyrNm) {
        this.plyrNm = plyrNm;
    }

    public String getTotWins() {
        return totWins;
    }

    public void setTotWins(String totWins) {
        this.totWins = totWins;
    }

    public String getPlyrLvl() {
        return plyrLvl;
    }

    public void setPlyrLvl(String plyrLvl) {
        this.plyrLvl = plyrLvl;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getMtchEndTime() {
        return mtchEndTime;
    }

    public void setMtchEndTime(String mtchEndTime) {
        this.mtchEndTime = mtchEndTime;
    }
}
