package net.gourmand.GoldenHorizonsCore.registry.blockentities;

import net.dries007.tfc.common.blockentities.CropBlockEntity;
import net.gourmand.GoldenHorizonsCore.registry.CoreBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CoreCropBlockEntity extends CropBlockEntity {

    public CoreCropBlockEntity(BlockPos pos, BlockState state)
    {
        this(CoreBlockEntities.CROP.get(), pos, state);
    }

    public CoreCropBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

}
