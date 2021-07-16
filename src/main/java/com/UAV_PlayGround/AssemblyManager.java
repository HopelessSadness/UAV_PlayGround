package com.UAV_PlayGround;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class AssemblyManager implements AssemblyManagementProcessor {
    private static final String CSV_SEPARATOR = ",";
    private static final String CSV_PREFIX_FULL_ENTITY_LIST_SORTED_BY_ID = "fullEntityListSortedById_";

    private List<UAVEntity> inputDataList;

    public void generateFullEntityListSortedByIdInCSV(Object[] inputData, String UAVNumber)
            throws AssemblyManagementException {

        validateInputEntities(inputData);

        inputDataList = (List<UAVEntity>) (List<?>) Arrays.asList(inputData);
        Comparator<UAVEntity> entityIdComparator = Comparator.comparing(UAVEntity::getId);
        inputDataList.sort(entityIdComparator);

        StringBuffer sb = new StringBuffer();
        for (UAVEntity entity : inputDataList) {
            sb.append(entity.getId()).append(CSV_SEPARATOR);
            sb.append(entity.getName()).append(CSV_SEPARATOR);
            sb.append(entity.getClass().getSimpleName()).append("\n");
        }

        try {
            BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(
                                    String.format("%s%s.csv", CSV_PREFIX_FULL_ENTITY_LIST_SORTED_BY_ID, UAVNumber)),
                            StandardCharsets.UTF_8));
            bw.write(sb.toString());
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new AssemblyManagementException(".generateFullEntityListSortedByIdInCSV() falls with '" +
                    e.getMessage() + "'!");
        }
    }

    public void generateMaterialsByShopFlorJSON(Object[] inputData, String UAVNumber)
            throws AssemblyManagementException {

        Map<String, Map<String, Integer>> materialsByShopFlor = new HashMap<>();

        for (Object object : inputData) {
            if (isEntityTypeCorrect(object, "Detail")) {
                Detail currentDetail = (Detail) object;
                String currentShopFlor = currentDetail.getShopFloor();
                String currentMaterial = currentDetail.getMaterial();
                Map<String, Integer> materialsCount = new HashMap<>();

                if (materialsByShopFlor.containsKey(currentShopFlor)) {
                    materialsCount = materialsByShopFlor.get(currentShopFlor);

                    if (materialsCount.containsKey(currentMaterial)) {
                        materialsCount.put(currentMaterial, materialsCount.get(currentMaterial) + 1);
                    } else {
                        materialsCount.put(currentMaterial, 1);
                    }
                } else {
                    materialsCount.put(currentMaterial, 1);
                    materialsByShopFlor.put(currentShopFlor, materialsCount);
                }
            }
        }

        //.gson file generation will be here soon..
    }

    public void generatePurchasedComponentPartsByProviderJSON(Object[] inputData, String UAVNumber)
            throws AssemblyManagementException {

    }

    private void validateInputEntities(Object[] inputData)
            throws AssemblyManagementException {
        for (Object object : inputData) {
            if (object instanceof Assembly) continue;
            if (object instanceof Detail) continue;
            if (object instanceof PurchasedComponentPart) continue;

            String failedType = object.getClass().toString();
            throw new AssemblyManagementException(
                    "Input data is not valid!\n//falls with type == " + failedType);
        }
    }

    private boolean isEntityTypeCorrect(Object inputEntity, String entityTypeForValidation) {
        return entityTypeForValidation.equalsIgnoreCase(inputEntity.getClass().getSimpleName());
    }
}
