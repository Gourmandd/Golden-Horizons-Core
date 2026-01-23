package net.gourmand.GoldenHorizonsCore.registry.blocks;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.wood.ToolRackBlock;
import net.gourmand.GoldenHorizonsCore.registry.CoreBlockEntities;
import net.gourmand.GoldenHorizonsCore.registry.blockentities.CoreToolRackBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CoreToolRackBlock extends ToolRackBlock {

    public CoreToolRackBlock(ExtendedProperties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult)
    {
        CoreToolRackBlockEntity toolRack = level.getBlockEntity(pos, CoreBlockEntities.TOOL_RACK.get()).orElse(null);
        if (toolRack != null)
        {
            return toolRack.onRightClick(player, getSlotFromPos(state, hitResult.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ())));
        }
        return InteractionResult.PASS;
    }
}
