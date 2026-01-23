package net.gourmand.GoldenHorizonsCore.registry.blocks;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.ShelfBlock;
import net.dries007.tfc.common.blocks.TFCBlocks;
import net.gourmand.GoldenHorizonsCore.registry.CoreBlockEntities;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;


import net.dries007.tfc.common.blockentities.PlacedItemBlockEntity;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.common.blocks.devices.PlacedItemBlock;

public class CoreShelfBlock extends ShelfBlock {

    public CoreShelfBlock(ExtendedProperties properties, boolean thick) {
        super(properties, thick);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        final boolean hitIsOnLowerHalf = hitResult.getLocation().y() - hitResult.getBlockPos().getY() < 0.5f;
        if (!hitIsOnLowerHalf && hitResult.getDirection() == Direction.UP)
        {
            final BlockPos above = pos.above();
            if (level.isEmptyBlock(above))
            {
                final BlockState toPlace = PlacedItemBlock.updateStateValues(level, pos, TFCBlocks.PLACED_ITEM.get().defaultBlockState());
                if (!PlacedItemBlock.isEmptyContents(toPlace))
                {
                    level.setBlockAndUpdate(above, toPlace);
                    level.getBlockEntity(above, TFCBlockEntities.PLACED_ITEM.get()).ifPresent(e -> e.insertItem(player, stack, hitResult));
                    return ItemInteractionResult.sidedSuccess(level.isClientSide);
                }
            }
        }

        if (hitIsOnLowerHalf)
        {
            final PlacedItemBlockEntity placedItem = level.getBlockEntity(pos, CoreBlockEntities.SHELF.get()).orElse(null);
            if (placedItem != null && placedItem.onRightClick(player, player.getItemInHand(hand), hitResult))
            {
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}
