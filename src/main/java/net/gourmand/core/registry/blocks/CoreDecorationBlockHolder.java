package net.gourmand.core.registry.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.neoforged.neoforge.registries.DeferredHolder;

public record CoreDecorationBlockHolder(
        DeferredHolder<Block, ? extends SlabBlock> slab,
        DeferredHolder<Block, ? extends StairBlock> stair,
        DeferredHolder<Block, ? extends WallBlock> wall
) {}
