package net.gourmand.GoldenHorizonsCore.registry.blockentities;

import net.dries007.tfc.common.blockentities.LoomBlockEntity;
import net.gourmand.GoldenHorizonsCore.registry.CoreBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CoreLoomBlockEntity extends LoomBlockEntity {

    public CoreLoomBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return CoreBlockEntities.LOOM.get();
    }
}
