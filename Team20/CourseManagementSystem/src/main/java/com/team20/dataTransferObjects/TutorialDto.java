package com.team20.dataTransferObjects;

public class TutorialDto {
    private int cid;
    private int cap;
    private String prof;
    private String time;

    public int getCid() {
        return this.cid;
    }

    public void setCid(int c)
    {
        this.cid = c;
    }

    public int getCap() {
        return this.cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public String getProf() {
        return this.prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
