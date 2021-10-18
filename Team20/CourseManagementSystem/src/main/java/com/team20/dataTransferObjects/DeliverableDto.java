package com.team20.dataTransferObjects;

public class DeliverableDto {
    private String requirements;
    private String opendate;
    private String deadline;
    private int maxgrade;
    private String type;
    private int cid;

    public int getCid() {
        return this.cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getRequirements() {
        return this.requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getOpendate() {
        return this.opendate;
    }

    public void setOpendate(String opendate) {
        this.opendate = opendate;
    }

    public String getDeadline() {
        return this.deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getMaxgrade() {
        return this.maxgrade;
    }

    public void setMaxgrade(int maxgrade) {
        this.maxgrade = maxgrade;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
