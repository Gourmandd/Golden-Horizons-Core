package net.gourmand.GoldenHorizonsCore.registry.items;

import net.dries007.tfc.common.items.PlantableInfo;
import net.dries007.tfc.util.climate.ClimateRange;
import net.gourmand.GoldenHorizonsCore.registry.category.CoreCrops;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class CoreSeedItem extends ItemNameBlockItem implements PlantableInfo {
    private final PlantableInfo.PlantNutrients nutrients;
    private final Supplier<ClimateRange> climateRange;

    public CoreSeedItem(CoreCrops crop, Block block, Item.Properties properties) {
        super(block, properties);
        this.nutrients = new PlantableInfo.PlantNutrients(crop.getNitrogen(), crop.getPhosphorous(), crop.getPotassium());
        this.climateRange = crop.getClimateRange();
    }

    @Nullable
    public PlantableInfo.@Nullable PlantNutrients getNutrientsInfo() {
        return this.nutrients;
    }

    public @Nullable ClimateRange getClimateRangeInfo() {
        return (ClimateRange)this.climateRange.get();
    }
}