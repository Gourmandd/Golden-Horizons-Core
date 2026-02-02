package net.gourmand.core.datagen.providers;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import net.dries007.tfc.util.climate.ClimateRange;
import net.dries007.tfc.util.data.DataManager;
import static net.gourmand.core.registry.category.CoreFruitTrees.*;
import static net.gourmand.core.registry.category.CoreClimateRanges.*;
import static net.gourmand.core.registry.category.CoreSpreadingBushes.*;
import static net.gourmand.core.registry.category.CoreStationaryBushes.*;
import static net.gourmand.core.registry.category.CoreCrops.*;

public class BuiltinClimateRanges extends DataManagerProvider<ClimateRange>
{
    public BuiltinClimateRanges(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup)
    {
        super(ClimateRange.MANAGER, output, lookup);
    }

    @Override
    protected void addData(HolderLookup.Provider provider)
    {
        add(FRUIT_TREES, WALNUT, b -> b.hydration(10, 60).temperature(-14.8f, 16.6f));
        add(FRUIT_TREES, WOLFBERRY, b -> b.hydration(35, 80).temperature(8.8f, 36.5f));
        add(SPREADING_BUSHES, TEST, b -> b.hydration(0, 100).temperature(9.8f, 41.2f));
        add(STATIONARY_BUSHES, ALOE, b -> b.hydration(17, 100).temperature(-11.8f, 6.2f));
        add(STATIONARY_BUSHES, SAWBLADE_HOLLY, b -> b.hydration(17, 100).temperature(-11.8f, 6.2f));
        add(CROPS, COFFEE, b -> b.hydration(14, 75).temperature(11f, 32f));
        add(CROPS, COTTON, b -> b.hydration(15, 62).temperature(-1f, 28f));
        add(CROPS, GLISTERING_MELON, b -> b.hydration(35, 100).temperature(5.0f, 31f));
        add(CROPS, AMARANTH, b -> b.hydration(8, 70).temperature(5f, 27f));
    }

    private <T> void add(Map<T, DataManager.Reference<ClimateRange>> map, T value, UnaryOperator<ClimateRange.Builder> builder)
    {
        add(map.get(value), builder.apply(new ClimateRange.Builder()).build());
    }
}