package net.gourmand.GoldenHorizonsCore.registry.category;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.common.blocks.plant.fruit.FruitTreeBranchBlock;
import net.dries007.tfc.common.blocks.plant.fruit.FruitTreeLeavesBlock;
import net.dries007.tfc.common.blocks.plant.fruit.FruitTreeSaplingBlock;
import net.dries007.tfc.common.blocks.plant.fruit.GrowingFruitTreeBranchBlock;
import net.dries007.tfc.util.calendar.ICalendar;
import net.gourmand.GoldenHorizonsCore.registry.CoreBlockEntities;
import net.gourmand.GoldenHorizonsCore.registry.CoreBlocks;
import net.gourmand.GoldenHorizonsCore.registry.items.FruitTreeSaplingItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import static net.dries007.tfc.common.blocks.plant.fruit.Lifecycle.*;

import java.awt.*;
import java.util.Locale;

public enum CoreFruitTrees implements StringRepresentable {

    WOLFBERRY(ResourceLocation.fromNamespaceAndPath("caupona", "wolfberries"), 8, new Lifecycle[] {HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY}, new Color(251, 135, 255).getRGB());

    private final ResourceLocation product_location;
    private final Item product;
    private final Lifecycle[] stages;
    private final String serializedName;
    private final int defaultTicksToGrow;
    private final int floweringLeavesColor;

    CoreFruitTrees(ResourceLocation product_location, int daysToGrow, Lifecycle[] stages, int floweringLeavesColor)
    {
        this.product_location = product_location;
        this.product = null;
        this.stages = stages;
        this.serializedName = name().toLowerCase(Locale.ROOT);
        this.defaultTicksToGrow = daysToGrow * ICalendar.CALENDAR_TICKS_IN_DAY;
        this.floweringLeavesColor = floweringLeavesColor;
    }

    CoreFruitTrees(Item product, int daysToGrow, Lifecycle[] stages, int floweringLeavesColor)
    {
        this.product_location = null;
        this.product = product;
        this.stages = stages;
        this.serializedName = name().toLowerCase(Locale.ROOT);
        this.defaultTicksToGrow = daysToGrow * ICalendar.CALENDAR_TICKS_IN_DAY;
        this.floweringLeavesColor = floweringLeavesColor;
    }

    public Block createSapling()
    {
        return new FruitTreeSaplingBlock(
                ExtendedProperties.of(MapColor.PLANT)
                        .noCollission()
                        .randomTicks()
                        .strength(0)
                        .sound(SoundType.GRASS)
                        .blockEntity(CoreBlockEntities.TICK_COUNTING_PLANT)
                        .flammableLikeLeaves(),
                CoreBlocks.FRUIT_TREE_GROWING_BRANCHES.get(this),
                () -> 192000,
                CoreClimateRanges.FRUIT_TREES.get(this),
                stages);
    }

    public Block createPottedSapling()
    {
        return new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, CoreBlocks.FRUIT_TREE_SAPLINGS.get(this), BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_ACACIA_SAPLING));
    }

    public Block createLeaves()
    {
        if (product_location == null){
            return new FruitTreeLeavesBlock(ExtendedProperties.of().mapColor(FruitTreeLeavesBlock::getMapColor).strength(0.5F).sound(SoundType.GRASS).randomTicks().noOcclusion().blockEntity(CoreBlockEntities.BERRY_BUSH).flammableLikeLeaves(), () -> product, stages, CoreClimateRanges.FRUIT_TREES.get(this), floweringLeavesColor);
        } else {
            return new FruitTreeLeavesBlock(ExtendedProperties.of().mapColor(FruitTreeLeavesBlock::getMapColor).strength(0.5F).sound(SoundType.GRASS).randomTicks().noOcclusion().blockEntity(CoreBlockEntities.BERRY_BUSH).flammableLikeLeaves(), () -> BuiltInRegistries.ITEM.get(product_location), stages, CoreClimateRanges.FRUIT_TREES.get(this), floweringLeavesColor);
        }
    }

    public Block createBranch()
    {
        return new FruitTreeBranchBlock(ExtendedProperties.of(MapColor.WOOD).sound(SoundType.SCAFFOLDING).randomTicks().strength(1.0f).pushReaction(PushReaction.DESTROY).flammableLikeLogs(), CoreClimateRanges.FRUIT_TREES.get(this));
    }

    public Block createGrowingBranch()
    {
        return new GrowingFruitTreeBranchBlock(ExtendedProperties.of(MapColor.WOOD).sound(SoundType.SCAFFOLDING).randomTicks().strength(1.0f).pushReaction(PushReaction.DESTROY).blockEntity(CoreBlockEntities.TICK_COUNTING_PLANT).flammableLikeLogs().cloneEmpty(), CoreBlocks.FRUIT_TREE_BRANCHES.get(this), CoreBlocks.FRUIT_TREE_LEAVES.get(this), CoreClimateRanges.FRUIT_TREES.get(this));
    }

    public BlockItem createSaplingItem(Block block)
    {
        return new FruitTreeSaplingItem(
                block,
                CoreClimateRanges.FRUIT_TREES.get(this),
                stages
        );
    }

    public int defaultTicksToGrow()
    {
        return defaultTicksToGrow;
    }

    @Override
    public String getSerializedName()
    {
        return serializedName;
    }
}