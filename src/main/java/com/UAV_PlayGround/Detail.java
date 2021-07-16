package com.UAV_PlayGround;

public class Detail extends UAVEntity {
    private final String applicability;
    private final String shopFloor;
    private final String material;

    public Detail(String id, String name, String applicability, String shopFloor, String material) {
        super(id, name);
        this.applicability = applicability;
        this.shopFloor = shopFloor;
        this.material = material;
    }

    public String getApplicability() {
        return applicability;
    }

    public String getShopFloor() {
        return shopFloor;
    }

    public String getMaterial() {
        return material;
    }
}
