package net.gourmand.core.registry.blocks;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.crop.FloodedCropBlock;
import net.dries007.tfc.common.fluids.FluidProperty;
import net.dries007.tfc.util.climate.ClimateRange;
import net.gourmand.core.registry.CoreBlocks;
import net.gourmand.core.registry.CoreItems;
import net.gourmand.core.registry.category.CoreClimateRanges;
import net.gourmand.core.registry.category.CoreCrops;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.function.Supplier;

public abstract class CoreFloodedCropBlock extends FloodedCropBlock {

    public static final FluidProperty FLUID = TFCBlockStateProperties.FRESH_WATER;

    public static FloodedCropBlock create(ExtendedProperties properties, int stages, CoreCrops crop)
    {
        final IntegerProperty property = TFCBlockStateProperties.getAgeProperty(stages - 1);
        return new FloodedCropBlock(properties, stages - 1, CoreBlocks.DEAD_CROPS.get(crop), CoreItems.CROP_SEEDS.get(crop), crop.getNitrogen(), crop.getPhosphorous(), crop.getPotassium(), CoreClimateRanges.CROPS.get(crop))
        {
            @Override
            public IntegerProperty getAgeProperty()
            {
                return property;
            }
        };
    }

    protected CoreFloodedCropBlock(ExtendedProperties properties, int maxAge, Supplier<? extends Block> dead, Supplier<? extends Item> seeds, float nitrogen, float phosphorous, float potassium, Supplier<ClimateRange> climateRange)
    {
        super(properties, maxAge, dead, seeds, nitrogen, phosphorous, potassium, climateRange);
    }

}
