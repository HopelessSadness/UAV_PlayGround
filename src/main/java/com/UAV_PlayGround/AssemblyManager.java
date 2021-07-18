package com.UAV_PlayGround;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AssemblyManager implements AssemblyManagementProcessor {
    private static final Logger logger = Logger.getLogger(AssemblyManager.class.getName());
    private static final String LOG_FILE_NAME_PREFIX = "AssemblyManager ";
    private static final String CSV_SEPARATOR = ",";
    private static final String CSV_PREFIX_FULL_ENTITY_LIST_SORTED_BY_ID = "fullEntityListSortedById_";

    private List<UAVEntity> inputDataList;

    public void generateFullEntityListSortedByIdInCSV(Object[] inputData, String UAVNumber)
            throws AssemblyManagementException {

        String defaultPath;
        try {
            defaultPath = (new File(".")).getCanonicalPath();
            initLogFile(defaultPath, LOG_FILE_NAME_PREFIX);
        } catch (IOException e) {
            e.printStackTrace();
            throw new AssemblyManagementException(".generateFullEntityListSortedByIdInCSV() falls with '" +
                    e.getMessage() + "'!", logger);
        }

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
                    e.getMessage() + "'!", logger);
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

    private static void initLogFile(String path, String logFileNamePrefix) throws IOException {
        File file = new File(Paths.get(path).resolve("log").toString());
        if (file.mkdir()) {
            System.out.println(String.format("%s: logFile directory has been created..",
                    AssemblyManager.class.getName()));
        } else {
            System.out.println(String.format("%s: logFile directory already exist..",
                    AssemblyManager.class.getName()));
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("k-m-s(d.MM.yy)");
        String currentDate = dateFormat.format(new Date());

        FileHandler fh = new FileHandler(Paths.get(path).resolve("log/" +
                logFileNamePrefix + currentDate + ".txt").toString());
        fh.setFormatter(new SimpleFormatter());
        logger.addHandler(fh);
        logger.setLevel(Level.ALL);
    }

    private void validateInputEntities(Object[] inputData)
            throws AssemblyManagementException {
        for (Object object : inputData) {
            if (object instanceof Assembly) continue;
            if (object instanceof Detail) continue;
            if (object instanceof PurchasedComponentPart) continue;

            String failedType = object.getClass().toString();
            throw new AssemblyManagementException(
                    "Input data is not valid!\n//falls with type == " + failedType, logger);
        }
    }

    private boolean isEntityTypeCorrect(Object inputEntity, String entityTypeForValidation) {
        return entityTypeForValidation.equalsIgnoreCase(inputEntity.getClass().getSimpleName());
    }
}
