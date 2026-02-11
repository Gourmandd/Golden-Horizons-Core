package net.gourmand.core.registry.category;

import net.dries007.tfc.util.Metal;

import java.util.ArrayList;
import java.util.Locale;

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

    public static ArrayList<String> getAllOreNames(){
        ArrayList<String> list = new ArrayList<String>();

        for (CoreOres ore : CoreOres.values()){
            list.add(ore.name().toLowerCase(Locale.ROOT));
        }

        return list;
    }

    public static ArrayList<String> getNonGradedOreNames(){
        ArrayList<String> list = new ArrayList<String>();

        for (CoreOres ore : CoreOres.values()){
            if (!ore.isGraded()){
                list.add(ore.name().toLowerCase(Locale.ROOT));
            }
        }

        return list;
    }

    public static ArrayList<String> getGradedOreNames(){
        ArrayList<String> list = new ArrayList<String>();

        for (CoreOres ore : CoreOres.values()){
            if (ore.isGraded()){
                list.add(ore.name().toLowerCase(Locale.ROOT));
            }
        }

        return list;
    }

    public static ArrayList<String> GetGemOreNames(){
        ArrayList<String> list = new ArrayList<String>();

        for (CoreOres ore : CoreOres.values()){
            if (ore.isGem()){
                list.add(ore.name().toLowerCase(Locale.ROOT));
            }
        }

        return list;
    }

    public static ArrayList<String> getPastelOreNames(){
        ArrayList<String> list = new ArrayList<String>();

        for (CoreOres ore : CoreOres.values()){
            if (ore.hasPastelOreType()){
                list.add(ore.name().toLowerCase(Locale.ROOT));
            }
        }

        return list;
    }

    public static ArrayList<String> getAllRockNames(){
        ArrayList<String> list = new ArrayList<String>();

        for (CoreRocks rock : CoreRocks.values()){
            list.add(rock.name().toLowerCase(Locale.ROOT));
        }

        return list;
    }

    public static ArrayList<String> getAllVariantRockNames(){
        ArrayList<String> list = new ArrayList<String>();

        for (CoreRocks rock : CoreRocks.values()){
            if (rock.hasVariants()){
                list.add(rock.name().toLowerCase(Locale.ROOT));
            }
        }

        return list;
    }

    public static ArrayList<String> getPastelWoodNames(){
        ArrayList<String> list = new ArrayList<String>();

        for (CorePastelWood wood : CorePastelWood.values()){
            list.add(wood.getSerializedName());
        }

        return list;
    }

    public static ArrayList<String> getClayNames(){
        ArrayList<String> list = new ArrayList<String>();

        for (CoreClay clay : CoreClay.values()){
            list.add(clay.getSerializedName());
        }

        return list;
    }

    public static ArrayList<String> getClayItemTypeNames(){
        ArrayList<String> list = new ArrayList<String>();

        for (CoreClay.ItemType type : CoreClay.ItemType.values()){
            list.add(type.getSerializedName());
        }

        return list;
    }

    public static ArrayList<String> getClayMoldNames(){
        ArrayList<String> list = new ArrayList<String>();

        for (CoreClay.ItemType type : CoreClay.ItemType.values()){
            if (type.getType() == CoreClay.ItemPartType.UNFIRED_MOLD){
                list.add(type.getSerializedName());
            }
        }

        return list;
    }

    public static Metal.ItemType[] getTFCTools(){
        return new Metal.ItemType[] {
            Metal.ItemType.PICKAXE,
                    Metal.ItemType.PROPICK,
                    Metal.ItemType.AXE,
                    Metal.ItemType.SHOVEL,
                    Metal.ItemType.HOE,
                    Metal.ItemType.CHISEL,
                    Metal.ItemType.HAMMER,
                    Metal.ItemType.SAW,
                    Metal.ItemType.JAVELIN,
                    Metal.ItemType.SWORD,
                    Metal.ItemType.MACE,
                    Metal.ItemType.KNIFE,
                    Metal.ItemType.SCYTHE
        };
    }
    public static ArrayList<Metal.ItemType> getTFCToolHeads(){
        ArrayList<Metal.ItemType> list = new ArrayList<Metal.ItemType>();

        for (Metal.ItemType type : Metal.ItemType.values()){
            if (type.hasMold() && type != Metal.ItemType.INGOT){
                list.add(type);
            }
        }

        return list;
    }


    public static ArrayList<Metal> getTFCToolMetals(){
        ArrayList<Metal> list = new ArrayList<Metal>();

        for (Metal metal : Metal.values()){
            if (Metal.ItemType.PICKAXE.has(metal)){
                list.add(metal);
            }
        }

        return list;
    }

    public static class HeatCapacities {
        public final static float DOUBLE_SHEET = 11.428572f;
        public final static float SHEET = 5.714286f;
        public final static float DOUBLE_INGOT = 5.714286f;
        public final static float INGOT = 2.857143f;
        public final static float ROD = 1.4285715f;
        public final static float ANVIL = 40.0f;
    }
}
