package net.gourmand.GoldenHorizonsCore.registry.blockentities;

import net.dries007.tfc.common.blockentities.ToolRackBlockEntity;
import net.gourmand.GoldenHorizonsCore.registry.CoreBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CoreToolRackBlockEntity extends ToolRackBlockEntity {

    public CoreToolRackBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }


    @Override
    public BlockEntityType<?> getType() {
        return CoreBlockEntities.TOOL_RACK.get();
    }
}
