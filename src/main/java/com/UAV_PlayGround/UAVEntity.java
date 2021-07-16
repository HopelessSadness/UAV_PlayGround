package com.UAV_PlayGround;

public class UAVEntity {
    private final String id;
    private final String name;

    public UAVEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
