package com.wcc.pbl230801.pblService.dto;

import java.io.Serializable;

public class MatchZsReqDTOC implements Serializable {

    private String eId;
    private String tId;
    private String wScr;
    private String wPlyr1;
    private String wPlyr2;
    private String lScr;
    private String lPlyr1;
    private String lPlyr2;

    public String geteId() {
        return eId;
    }

    public void seteId(String eId) {
        this.eId = eId;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public String getwScr() {
        return wScr;
    }

    public void setwScr(String wScr) {
        this.wScr = wScr;
    }

    public String getwPlyr1() {
        return wPlyr1;
    }

    public void setwPlyr1(String wPlyr1) {
        this.wPlyr1 = wPlyr1;
    }

    public String getwPlyr2() {
        return wPlyr2;
    }

    public void setwPlyr2(String wPlyr2) {
        this.wPlyr2 = wPlyr2;
    }

    public String getlScr() {
        return lScr;
    }

    public void setlScr(String lScr) {
        this.lScr = lScr;
    }

    public String getlPlyr1() {
        return lPlyr1;
    }

    public void setlPlyr1(String lPlyr1) {
        this.lPlyr1 = lPlyr1;
    }

    public String getlPlyr2() {
        return lPlyr2;
    }

    public void setlPlyr2(String lPlyr2) {
        this.lPlyr2 = lPlyr2;
    }
}
