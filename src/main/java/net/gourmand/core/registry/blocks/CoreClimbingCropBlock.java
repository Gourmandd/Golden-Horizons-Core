package net.gourmand.core.registry.blocks;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.crop.ClimbingCropBlock;
import net.dries007.tfc.util.climate.ClimateRange;
import net.gourmand.core.registry.CoreBlocks;
import net.gourmand.core.registry.CoreItems;
import net.gourmand.core.registry.category.CoreClimateRanges;
import net.gourmand.core.registry.category.CoreCrops;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.function.Supplier;

public abstract class CoreClimbingCropBlock extends ClimbingCropBlock {

    public static final BooleanProperty STICK = TFCBlockStateProperties.STICK;

    public static CoreClimbingCropBlock create(ExtendedProperties properties, int singleStages, int doubleStages, CoreCrops crop)
    {
        final IntegerProperty property = TFCBlockStateProperties.getAgeProperty(singleStages + doubleStages - 1);
        return new CoreClimbingCropBlock(properties, singleStages - 1, singleStages + doubleStages - 1, CoreBlocks.DEAD_CROPS.get(crop), CoreItems.CROP_SEEDS.get(crop), crop.getNitrogen(), crop.getPhosphorous(), crop.getPotassium(), CoreClimateRanges.CROPS.get(crop))
        {
            @Override
            public IntegerProperty getAgeProperty()
            {
                return property;
            }
        };
    }

    protected CoreClimbingCropBlock(ExtendedProperties properties, int maxSingleAge, int maxAge, Supplier<? extends Block> dead, Supplier<? extends Item> seeds, float nitrogen, float phosphorous, float potassium, Supplier<ClimateRange> climateRange)
    {
        super(properties, maxSingleAge, maxAge, dead, seeds, nitrogen, phosphorous, potassium, climateRange);
        registerDefaultState(getStateDefinition().any().setValue(STICK, false).setValue(PART, Part.BOTTOM));
    }
}
