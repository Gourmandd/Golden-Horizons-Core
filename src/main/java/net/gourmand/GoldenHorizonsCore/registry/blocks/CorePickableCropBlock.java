package net.gourmand.GoldenHorizonsCore.registry.blocks;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.crop.PickableCropBlock;
import net.dries007.tfc.util.climate.ClimateRange;
import net.gourmand.GoldenHorizonsCore.registry.CoreBlocks;
import net.gourmand.GoldenHorizonsCore.registry.CoreItems;
import net.gourmand.GoldenHorizonsCore.registry.category.CoreClimateRanges;
import net.gourmand.GoldenHorizonsCore.registry.category.CoreCrops;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public abstract class CorePickableCropBlock extends PickableCropBlock {

    public static CorePickableCropBlock create(ExtendedProperties properties, int stages, CoreCrops crop, @Nullable Supplier<Supplier<? extends Item>> fruit, Supplier<Supplier<? extends Item>> matureFruit)
    {
        final IntegerProperty property = TFCBlockStateProperties.getAgeProperty(stages - 1);
        return new CorePickableCropBlock(properties, stages - 1, CoreBlocks.DEAD_CROPS.get(crop), CoreItems.CROP_SEEDS.get(crop), crop.getNitrogen(), crop.getPhosphorous(), crop.getPotassium(), CoreClimateRanges.CROPS.get(crop), fruit, matureFruit)
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

    protected CorePickableCropBlock(ExtendedProperties properties, int maxAge, Supplier<? extends Block> dead, Supplier<? extends Item> seeds, float nitrogen, float phosphorous, float potassium, Supplier<ClimateRange> climateRange, @Nullable Supplier<Supplier<? extends Item>> fruit, Supplier<Supplier<? extends Item>> matureFruit)
    {
        super(properties, maxAge, dead, seeds, nitrogen, phosphorous, potassium, climateRange, fruit, matureFruit);
        this.fruit = fruit;
        this.matureFruit = matureFruit;
    }
}
