package net.gourmand.core.registry.items;

import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.common.items.PlantableInfo;
import net.dries007.tfc.util.climate.ClimateRange;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class BushBlockItem extends BlockItem implements PlantableInfo
{
    private final Supplier<ClimateRange> climateRange;
    private final List<Lifecycle> stages;

    public BushBlockItem(Block block, Supplier<ClimateRange> range, Lifecycle[] stages)
    {
        super(block, new Item.Properties());
        this.climateRange = range;
        this.stages = List.of(stages);
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

}