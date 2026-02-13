package net.gourmand.core.util;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.util.registry.RegistryRock;
import net.gourmand.core.AncientGroundCore;
import net.gourmand.core.registry.category.CoreOres;
import net.gourmand.core.registry.category.CoreRocks;
import java.util.Locale;

public class TextureUtil {

    //by converting the rock to a string we can determine which Enum it belongs to...
    public static String getRawRockTexture(RegistryRock rock){

        for (Rock rockType : Rock.values()){
            if (rockType.getSerializedName().equals(rock.getSerializedName())){
                return getRawRockTexture(rockType);
            }
        }

        for (CoreRocks rockType : CoreRocks.values()){
            if (rockType.getSerializedName().equals(rock.getSerializedName())){
                return getRawRockTexture(rockType);
            }
        }

        throw new IllegalArgumentException("RockType: " + rock.getSerializedName() + " not found");
    }

    private static String getRawRockTexture(CoreRocks rock){
        if (rock.hasVariants()){
            return (AncientGroundCore.MODID + ":block/rock/raw/" + rock.getSerializedName());
        } else {
            switch (rock){
                case ARGILLITE -> {
                    return "minecraft:block/stone";
                }
                case NEPHELINITE -> {
                    return "minecraft:block/deepslate";
                }
                case TRAVERTINE -> {
                    return "minecraft:block/dripstone_block";
                }
                case BRECCIA -> {
                    return "caupona:block/felsic_tuff";
                }
                case KOMATIITE -> {
                    return "create:block/palettes/stone_types/scoria";
                }
                case BLACKSLAG -> {
                    return "pastel:block/blackslag";
                }
                case PICRITE_BASALT -> {
                    return "pastel:block/basal_marble";
                }
                case null, default -> throw new AssertionError("Invalid Rock to get texture for");
            }
        }
    }

    private static String getRawRockTexture(Rock rock){
        return (TerraFirmaCraft.MOD_ID + ":block/rock/raw/" + rock.getSerializedName());
    }

    public static String getOreTexture(CoreOres ore){
        return (AncientGroundCore.MODID + ":block/ore/" + ore.getSerializedName());
    }

    public static String getOreTexture(Ore ore){
        return (TerraFirmaCraft.MOD_ID + ":block/ore/" + ore.name().toLowerCase(Locale.ROOT));
    }

    public static String getOreTexture(CoreOres ore, CoreOres.Grade grade){
        switch (grade){
            case RICH -> {
                return AncientGroundCore.MODID + ":block/ore/rich/" + ore.getSerializedName();
            }
            case NORMAL -> {
                return AncientGroundCore.MODID + ":block/ore/normal_" + ore.getSerializedName();
            }
            case POOR -> {
                return AncientGroundCore.MODID + ":block/ore/poor/" + ore.getSerializedName();
            }
            case null -> throw new AssertionError("Invalid grade");
        }
    }

    public static String getOreTexture(Ore ore, CoreOres.Grade grade){
        switch (grade){
            case RICH -> {
                return TerraFirmaCraft.MOD_ID + ":block/ore/rich/" + ore.name().toLowerCase(Locale.ROOT);
            }
            case NORMAL -> {
                return TerraFirmaCraft.MOD_ID + ":block/ore/normal_" + ore.name().toLowerCase(Locale.ROOT);
            }
            case POOR -> {
                return TerraFirmaCraft.MOD_ID + ":block/ore/poor/" + ore.name().toLowerCase(Locale.ROOT);
            }
            case null -> throw new AssertionError("Invalid grade");
        }
    }
}
