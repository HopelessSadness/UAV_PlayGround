package com.UAV_PlayGround;

public interface AssemblyManagementProcessor {
    void generateFullEntityListSortedByIdInCSV (Object[] inputData, String UAVNumber)
            throws AssemblyManagementException;

    void generateMaterialsByShopFlorJSON (Object[] inputData, String UAVNumber)
            throws AssemblyManagementException;

    void generatePurchasedComponentPartsByProviderJSON (Object[] inputData, String UAVNumber)
            throws AssemblyManagementException;
}
