import com.UAV_PlayGround.*;

public class Main {
    public static void main(String[] args) throws AssemblyManagementException {
        Object[] inputArrrrrrrrrrrrrrrrrrr = new Object[]{
                new Assembly("1b", "lol", "lol", "lol"),
                new Assembly("1a", "lol", "lol", "lol"),
                new Assembly("2", "lol", "lol", "lol"),
                new Assembly("33", "lol", "lol", "lol"),
                new Assembly("44", "lol", "lol", "lol"),
                new Detail("542", "lol", "lol", "lol", "lol"),
                new Detail("54", "lol", "lol", "lol", "lol"),
                new Detail("55", "lol", "lol", "lol1", "lol"),
                new Detail("534", "lol", "lol", "lol1", "loldetal"),
                new Detail("55234", "lol", "lol", "lol0", "lol")
        };

        AssemblyManager assemblyManager = new AssemblyManager();
        assemblyManager.generateFullEntityListSortedByIdInCSV(inputArrrrrrrrrrrrrrrrrrr, "yyy-La-la_BPLA");
        assemblyManager.generateMaterialsByShopFlorJSON(inputArrrrrrrrrrrrrrrrrrr, "yyy-La-la_BPLA");
    }
}
