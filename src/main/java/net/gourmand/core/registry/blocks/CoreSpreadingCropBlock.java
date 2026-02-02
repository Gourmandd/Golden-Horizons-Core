package net.gourmand.core.registry.blocks;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.crop.SpreadingCropBlock;
import net.dries007.tfc.util.climate.ClimateRange;
import net.gourmand.core.registry.CoreBlocks;
import net.gourmand.core.registry.CoreItems;
import net.gourmand.core.registry.category.CoreClimateRanges;
import net.gourmand.core.registry.category.CoreCrops;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.function.Supplier;

public abstract class CoreSpreadingCropBlock extends SpreadingCropBlock {

    public static CoreSpreadingCropBlock create(ExtendedProperties properties, int stages, CoreCrops crop, Supplier<Supplier<? extends Block>> fruit)
    {
        final IntegerProperty property = TFCBlockStateProperties.getAgeProperty(stages - 1);
        return new CoreSpreadingCropBlock(properties, stages - 1, CoreBlocks.DEAD_CROPS.get(crop), CoreItems.CROP_SEEDS.get(crop), crop.getNitrogen(), crop.getPhosphorous(), crop.getPotassium(), CoreClimateRanges.CROPS.get(crop), fruit)
        {
            @Override
            public IntegerProperty getAgeProperty()
            {
                return property;
            }
        };
    }

    private final Supplier<Supplier<? extends Block>> fruit;

    protected CoreSpreadingCropBlock(ExtendedProperties properties, int maxAge, Supplier<? extends Block> dead, Supplier<? extends Item> seeds, float nitrogen, float phosphorous, float potassium, Supplier<ClimateRange> climateRange, Supplier<Supplier<? extends Block>> fruit)
    {
        super(properties, maxAge, dead, seeds, nitrogen, phosphorous, potassium, climateRange, fruit);
        registerDefaultState(getStateDefinition().any().setValue(NORTH, false).setValue(WEST, false).setValue(EAST, false).setValue(SOUTH, false).setValue(getAgeProperty(), 0));
        this.fruit = fruit;
    }
}
