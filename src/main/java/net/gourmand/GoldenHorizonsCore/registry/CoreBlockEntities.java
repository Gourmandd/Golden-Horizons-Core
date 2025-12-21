package net.gourmand.GoldenHorizonsCore.registry;

import net.dries007.tfc.common.blockentities.BerryBushBlockEntity;
import net.dries007.tfc.common.blockentities.TFCBlockEntities;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.gourmand.GoldenHorizonsCore.GoldenHorizonsCore;
import net.gourmand.GoldenHorizonsCore.registry.blockentities.CoreBerryBushBlockEntity;
import net.gourmand.GoldenHorizonsCore.registry.blockentities.CoreCropBlockEntity;
import net.gourmand.GoldenHorizonsCore.registry.blockentities.CoreTickingPlantBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class CoreBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, GoldenHorizonsCore.MODID);

    public static final TFCBlockEntities.Id<CoreCropBlockEntity> CROP = register("crop", CoreCropBlockEntity::new, CoreBlocks.CROPS.values().stream());

    public static final TFCBlockEntities.Id<BerryBushBlockEntity> BERRY_BUSH = register("berry_bush", CoreBerryBushBlockEntity::new, Stream.of(
            CoreBlocks.SPREADING_BUSHES.values(),
            CoreBlocks.SPREADING_CANES.values(),
            CoreBlocks.STATIONARY_BUSHES.values(),
            CoreBlocks.FRUIT_TREE_LEAVES.values()
    ).flatMap(Collection::stream));

    public static final TFCBlockEntities.Id<CoreTickingPlantBlockEntity> TICK_COUNTING_PLANT = register("tick_counting_branch", CoreTickingPlantBlockEntity::new, Stream.of(
                    CoreBlocks.FRUIT_TREE_SAPLINGS.values().stream(),
                    CoreBlocks.FRUIT_TREE_GROWING_BRANCHES.values().stream()
            ).flatMap(e -> e)
    );

    private static <T extends BlockEntity> TFCBlockEntities.Id<T> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Supplier<? extends Block> block)
    {
        return new TFCBlockEntities.Id<>(RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, block));
    }

    private static <T extends BlockEntity> TFCBlockEntities.Id<T> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Stream<? extends Supplier<? extends Block>> blocks)
    {
        return new TFCBlockEntities.Id<>(RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, blocks));
    }
}
