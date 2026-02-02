package net.gourmand.core.registry.blocks;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.crop.DoubleCropBlock;
import net.dries007.tfc.util.climate.ClimateRange;
import net.gourmand.core.registry.CoreBlocks;
import net.gourmand.core.registry.CoreItems;
import net.gourmand.core.registry.category.CoreClimateRanges;
import net.gourmand.core.registry.category.CoreCrops;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.function.Supplier;

public abstract class CoreDoubleCropBlock extends DoubleCropBlock {

    public static final EnumProperty<Part> PART = TFCBlockStateProperties.DOUBLE_CROP_PART;

    public static CoreDoubleCropBlock create(ExtendedProperties properties, int singleStages, int doubleStages, CoreCrops crop)
    {
        final IntegerProperty property = TFCBlockStateProperties.getAgeProperty(singleStages + doubleStages - 1);
        return new CoreDoubleCropBlock(properties, singleStages - 1, singleStages + doubleStages - 1, CoreBlocks.DEAD_CROPS.get(crop), CoreItems.CROP_SEEDS.get(crop), crop.getNitrogen(), crop.getPhosphorous(), crop.getPotassium(), CoreClimateRanges.CROPS.get(crop))
        {
            @Override
            public IntegerProperty getAgeProperty()
            {
                return property;
            }
        };
    }

    protected final int maxSingleAge;
    protected final float maxSingleGrowth;

    protected CoreDoubleCropBlock(ExtendedProperties properties, int maxSingleAge, int maxAge, Supplier<? extends Block> dead, Supplier<? extends Item> seeds, float nitrogen, float phosporous, float potassium, Supplier<ClimateRange> climateRange)
    {
        super(properties, maxSingleAge, maxAge, dead, seeds, nitrogen, phosporous, potassium, climateRange);

        this.maxSingleAge = maxSingleAge;
        this.maxSingleGrowth = (float) maxSingleAge / maxAge;
        registerDefaultState(defaultBlockState().setValue(PART, Part.BOTTOM));
    }
}
