package net.gourmand.GoldenHorizonsCore.registry.blockentities;

import net.dries007.tfc.common.blockentities.BerryBushBlockEntity;
import net.dries007.tfc.common.blockentities.TickCounterBlockEntity;
import net.gourmand.GoldenHorizonsCore.registry.CoreBlockEntities;
import net.minecraft.core.BlockPos;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CoreBerryBushBlockEntity extends BerryBushBlockEntity {

    public static void reset(Level level, BlockPos pos)
    {
        level.getBlockEntity(pos, CoreBlockEntities.BERRY_BUSH.get()).ifPresent(TickCounterBlockEntity::resetCounter);
    }

    public static void resetPickedTick(Level level, BlockPos pos)
    {
        level.getBlockEntity(pos, CoreBlockEntities.BERRY_BUSH.get()).ifPresent(BerryBushBlockEntity::resetLastPickedCounter);
    }

    // Allows for large bushes without runaway spreading
    private int growthsRemaining = 24;

    public CoreBerryBushBlockEntity(BlockPos pos, BlockState state)
    {
        this(CoreBlockEntities.BERRY_BUSH.get(), pos, state);
    }

    public CoreBerryBushBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }
}
