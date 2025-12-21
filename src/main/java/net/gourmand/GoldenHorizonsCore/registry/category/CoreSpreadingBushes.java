package net.gourmand.GoldenHorizonsCore.registry.category;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.plant.fruit.SpreadingBushBlock;
import net.dries007.tfc.common.blocks.plant.fruit.SpreadingCaneBlock;
import net.dries007.tfc.common.items.TFCItems;
import net.gourmand.GoldenHorizonsCore.registry.CoreBlockEntities;
import net.gourmand.GoldenHorizonsCore.registry.CoreBlocks;
import net.gourmand.GoldenHorizonsCore.registry.items.BushBlockItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.neoforged.neoforge.registries.DeferredHolder;

import static net.dries007.tfc.common.blocks.plant.fruit.Lifecycle.*;

public enum CoreSpreadingBushes {

    TEST(TFCItems.ALFALFA.holder(), new Lifecycle[] {DORMANT, DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT}, 3);

    private final DeferredHolder<Item, Item> product;
    private final Lifecycle[] stages;
    private final int maxHeight;

    CoreSpreadingBushes(DeferredHolder<Item, Item> product, Lifecycle[] stages, int maxHeight)
    {
        this.product = product;
        this.stages = stages;
        this.maxHeight = maxHeight;
    }

    public Block createBush()
    {
        return new SpreadingBushBlock(ExtendedProperties.of(MapColor.PLANT).strength(0.6f).noOcclusion().randomTicks().sound(SoundType.SWEET_BERRY_BUSH).blockEntity(CoreBlockEntities.BERRY_BUSH).flammableLikeLeaves(), product, stages, CoreBlocks.SPREADING_CANES.get(this), maxHeight, CoreClimateRanges.SPREADING_BUSHES.get(this));
    }

    public Block createCane()
    {
        return new SpreadingCaneBlock(ExtendedProperties.of(MapColor.PLANT).strength(0.6f).noOcclusion().randomTicks().sound(SoundType.SWEET_BERRY_BUSH).blockEntity(CoreBlockEntities.BERRY_BUSH).flammableLikeLeaves(), product, stages, CoreBlocks.SPREADING_BUSHES.get(this), maxHeight, CoreClimateRanges.SPREADING_BUSHES.get(this));
    }

    public BlockItem createItem(Block block)
    {
        return new BushBlockItem(block, CoreClimateRanges.SPREADING_BUSHES.get(this), stages);
    }
}

