package net.gourmand.GoldenHorizonsCore.registry.blocks;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.crop.DefaultCropBlock;
import net.dries007.tfc.util.climate.ClimateRange;
import net.gourmand.GoldenHorizonsCore.registry.CoreBlocks;
import net.gourmand.GoldenHorizonsCore.registry.CoreItems;
import net.gourmand.GoldenHorizonsCore.registry.category.CoreClimateRanges;
import net.gourmand.GoldenHorizonsCore.registry.category.CoreCrops;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public abstract class CoreDefaultCropBlock extends DefaultCropBlock {

    public static CoreDefaultCropBlock create(ExtendedProperties properties, int stages, CoreCrops crop) {
        final IntegerProperty property = TFCBlockStateProperties.getAgeProperty(stages - 1);
        return new CoreDefaultCropBlock(properties, stages - 1, CoreBlocks.DEAD_CROPS.get(crop), CoreItems.CROP_SEEDS.get(crop), crop.getNitrogen(), crop.getPhosphorous(), crop.getPotassium(), CoreClimateRanges.CROPS.get(crop)) {
            public @NotNull IntegerProperty getAgeProperty() {
                return property;
            }
        };
    }

    protected CoreDefaultCropBlock(ExtendedProperties properties, int maxAge, Supplier<? extends Block> dead, Supplier<? extends Item> seeds, float nitrogen, float phosphorous, float potassium, Supplier<ClimateRange> climateRange) {
        super(properties, maxAge, dead, seeds, nitrogen, phosphorous, potassium, climateRange);
    }
}