package com.UAV_PlayGround;

public class PurchasedComponentPart extends UAVEntity {
    private final String partNumber;
    private final String provider;

    public PurchasedComponentPart (String id, String name, String partNumber, String provider){
        super(id, name);
        this.partNumber = partNumber;
        this.provider = provider;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public String getProvider() {
        return provider;
    }
}
