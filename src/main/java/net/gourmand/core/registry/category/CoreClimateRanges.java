package net.gourmand.core.registry.category;

import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.climate.ClimateRange;
import net.dries007.tfc.util.data.DataManager;
import net.gourmand.core.AncientGroundCore;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;
import java.util.Map;

public class CoreClimateRanges {

    public static final Map<CoreCrops, DataManager.Reference<ClimateRange>> CROPS = Helpers.mapOf(CoreCrops.class, crop -> register("crop/" + crop.getSerializedName()));

    public static final Map<CoreStationaryBushes, DataManager.Reference<ClimateRange>> STATIONARY_BUSHES = Helpers.mapOf(CoreStationaryBushes.class, bush -> register("plant/" + bush.name() + "_bush"));

    public static final Map<CoreSpreadingBushes, DataManager.Reference<ClimateRange>> SPREADING_BUSHES = Helpers.mapOf(CoreSpreadingBushes.class, bush -> register("plant/" + bush.name() + "_bush"));

    public static final Map<CoreFruitTrees, DataManager.Reference<ClimateRange>> FRUIT_TREES = Helpers.mapOf(CoreFruitTrees.class, tree -> register("plant/" + tree.name() + "_tree"));

    private static DataManager.Reference<ClimateRange> register(String name)
    {
        return ClimateRange.MANAGER.getReference(ResourceLocation.fromNamespaceAndPath(AncientGroundCore.MODID, name.toLowerCase(Locale.ROOT)));
    }
}
