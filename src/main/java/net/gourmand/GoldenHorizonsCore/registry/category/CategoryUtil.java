package net.gourmand.GoldenHorizonsCore.registry.category;

import java.util.ArrayList;

public class CategoryUtil {

    public static ArrayList<String> getCropNames(){
        ArrayList<String> list = new ArrayList<String>();

        for (CoreCrops crop : CoreCrops.values()){
            list.add(crop.getSerializedName());
        }

        return list;
    }

    public static ArrayList<String> getFruitTreeNames(){
        ArrayList<String> list = new ArrayList<String>();

        for (CoreFruitTrees tree : CoreFruitTrees.values()){
            list.add(tree.getSerializedName());
        }

        return list;
    }

    public static ArrayList<String> getStationaryBushNames(){
        ArrayList<String> list = new ArrayList<String>();

        for (CoreStationaryBushes bush : CoreStationaryBushes.values()){
            list.add(bush.getSerializedName());
        }

        return list;
    }

    public static ArrayList<String> getSpreadingBushNames(){
        ArrayList<String> list = new ArrayList<String>();

        for (CoreSpreadingBushes bush : CoreSpreadingBushes.values()){
            list.add(bush.getSerializedName());
        }

        return list;
    }

    public static ArrayList<String> getMetalNames(){
        ArrayList<String> list = new ArrayList<String>();

        for (CoreMetals.MetalType metal : CoreMetals.MetalType.values()){
            list.add(metal.getSerializedName());
        }

        return list;
    }
}
