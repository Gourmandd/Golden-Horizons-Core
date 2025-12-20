package net.gourmand.GoldenHorizonsCore.registry.category;

import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.climate.ClimateRange;
import net.dries007.tfc.util.data.DataManager;
import net.gourmand.GoldenHorizonsCore.GoldenHorizonsCore;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;
import java.util.Map;

public class CoreClimateRanges {

    public static final Map<CoreCrops, DataManager.Reference<ClimateRange>> CROPS = Helpers.mapOf(CoreCrops.class, crop -> register("crop/" + crop.getSerializedName()));

    private static DataManager.Reference<ClimateRange> register(String name)
    {
        return ClimateRange.MANAGER.getReference(ResourceLocation.fromNamespaceAndPath(GoldenHorizonsCore.MODID, name.toLowerCase(Locale.ROOT)));
    }
}
