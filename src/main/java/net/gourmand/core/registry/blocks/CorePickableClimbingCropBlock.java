package net.gourmand.core.registry.blocks;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.crop.PickableClimbingCropBlock;
import net.dries007.tfc.util.climate.ClimateRange;
import net.gourmand.core.registry.CoreBlocks;
import net.gourmand.core.registry.CoreItems;
import net.gourmand.core.registry.category.CoreClimateRanges;
import net.gourmand.core.registry.category.CoreCrops;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public abstract class CorePickableClimbingCropBlock extends PickableClimbingCropBlock {
    public static CorePickableClimbingCropBlock create(ExtendedProperties properties, int singleStages, int doubleStages, CoreCrops crop, @Nullable Supplier<Supplier<? extends Item>> fruit, Supplier<Supplier<? extends Item>> matureFruit)
    {
        final IntegerProperty property = TFCBlockStateProperties.getAgeProperty(singleStages + doubleStages - 1);
        return new CorePickableClimbingCropBlock(properties, singleStages - 1, singleStages + doubleStages - 1, CoreBlocks.DEAD_CROPS.get(crop), CoreItems.CROP_SEEDS.get(crop), crop.getNitrogen(), crop.getPhosphorous(), crop.getPotassium(), CoreClimateRanges.CROPS.get(crop), fruit, matureFruit)
        {
            @Override
            public IntegerProperty getAgeProperty()
            {
                return property;
            }
        };
    }

    private final @Nullable Supplier<Supplier<? extends Item>> fruit;
    private final Supplier<Supplier<? extends Item>> matureFruit;

    protected CorePickableClimbingCropBlock(ExtendedProperties properties, int maxSingleAge, int maxAge, Supplier<? extends Block> dead, Supplier<? extends Item> seeds, float nitrogen, float phosphorous, float potassium, Supplier<ClimateRange> climateRange, @Nullable Supplier<Supplier<? extends Item>> fruit, Supplier<Supplier<? extends Item>> matureFruit)
    {
        super(properties, maxSingleAge, maxAge, dead, seeds, nitrogen, phosphorous, potassium, climateRange, fruit, matureFruit);
        this.fruit = fruit;
        this.matureFruit = matureFruit;
    }
}
