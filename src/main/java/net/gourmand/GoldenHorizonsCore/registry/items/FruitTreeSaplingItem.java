package net.gourmand.GoldenHorizonsCore.registry.items;

import net.dries007.tfc.common.blocks.plant.fruit.FruitTreeSaplingBlock;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.common.items.PlantableInfo;
import net.dries007.tfc.util.climate.ClimateRange;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

public class FruitTreeSaplingItem extends BlockItem implements PlantableInfo
{
    private final Supplier<ClimateRange> climateRange;
    private final List<Lifecycle> stages;
    private final LongSupplier ticksToGrow;

    public FruitTreeSaplingItem(Block block, Supplier<ClimateRange> climateRange, Lifecycle[] lifecycle)
    {
        super(block, new Item.Properties());
        this.climateRange = climateRange;
        this.stages = List.of(lifecycle);
        // Ideally, this ctor would just take FruitTreeSaplingBlock instead of Block, but too much stuff downcasts to Block before this gets called
        if (block instanceof FruitTreeSaplingBlock sapling)
        {
            this.ticksToGrow = sapling::getTicksToGrow;
        }
        else
        {
            this.ticksToGrow = () -> -1;
        }
    }


    @Override
    public @Nullable ClimateRange getClimateRangeInfo()
    {
        return climateRange.get();
    }

    @Override
    public @Nullable List<Lifecycle> getLifecycleInfo()
    {
        return stages;
    }

    @Override
    public int getGrowthTimeInfo()
    {
        return (int) ticksToGrow.getAsLong();
    }
}