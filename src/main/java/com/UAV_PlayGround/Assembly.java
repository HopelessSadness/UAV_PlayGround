package com.UAV_PlayGround;

public class Assembly extends UAVEntity {
    private final String applicability;
    private final String composition;

    public Assembly(String id, String name, String applicability, String composition) {
        super(id, name);
        this.applicability = applicability;
        this.composition = composition;
    }

    public String getApplicability() {
        return applicability;
    }

    public String getComposition() {
        return composition;
    }
}
