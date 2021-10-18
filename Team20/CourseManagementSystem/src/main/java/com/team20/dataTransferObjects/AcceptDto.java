package com.team20.dataTransferObjects;

public class AcceptDto{
    private boolean accepted;
    private int uid;

    public boolean isAccepted() {
        return this.accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public int getUid() {
        return this.uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

}
