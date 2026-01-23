package net.gourmand.GoldenHorizonsCore.registry.blocks;

import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.wood.TFCLoomBlock;
import net.gourmand.GoldenHorizonsCore.registry.CoreBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CoreLoomBlock extends TFCLoomBlock {


    public CoreLoomBlock(ExtendedProperties properties, ResourceLocation textureLocation) {
        super(properties, textureLocation);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        if (!level.getFluidState(pos).isEmpty())
        {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        return level.getBlockEntity(pos, CoreBlockEntities.LOOM.get())
                .map(loom -> loom.onRightClick(player))
                .orElse(ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION);
    }
}
