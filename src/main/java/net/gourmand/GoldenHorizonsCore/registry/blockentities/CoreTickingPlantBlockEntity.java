package net.gourmand.GoldenHorizonsCore.registry.blockentities;

import net.dries007.tfc.common.blockentities.TickCounterBlockEntity;
import net.dries007.tfc.common.blockentities.TickingPlantBlockEntity;
import net.gourmand.GoldenHorizonsCore.registry.CoreBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CoreTickingPlantBlockEntity extends TickingPlantBlockEntity {

    public static void reset(Level level, BlockPos pos)
    {
        level.getBlockEntity(pos, CoreBlockEntities.TICK_COUNTING_PLANT.get()).ifPresent(TickCounterBlockEntity::resetCounter);
    }

    public static void addTicks(Level level, BlockPos pos, long ticks)
    {
        level.getBlockEntity(pos, CoreBlockEntities.TICK_COUNTING_PLANT.get()).ifPresent(entity -> entity.increaseCounter(ticks));
    }

    public static void setStemPos(Level level, BlockPos pos, BlockPos stemPos)
    {
        level.getBlockEntity(pos, CoreBlockEntities.TICK_COUNTING_PLANT.get()).ifPresent(entity -> entity.setStemPos(stemPos));
    }

    protected BlockPos stemPos;
    protected long lastPickedTick = Integer.MIN_VALUE;

    public CoreTickingPlantBlockEntity(BlockPos pos, BlockState state)
    {
        super(CoreBlockEntities.TICK_COUNTING_PLANT.get(), pos, state);
    }

}
