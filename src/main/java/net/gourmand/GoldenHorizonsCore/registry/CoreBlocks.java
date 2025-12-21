package net.gourmand.GoldenHorizonsCore.registry;

import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.gourmand.GoldenHorizonsCore.GoldenHorizonsCore;
import net.gourmand.GoldenHorizonsCore.registry.category.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class CoreBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(GoldenHorizonsCore.MODID);

    public static final Map<CoreMetals.MetalType, Map<Metal.BlockType, DeferredHolder<Block, Block>>> METALS = Helpers.mapOf(CoreMetals.MetalType.class, metal ->
            Helpers.mapOf(Metal.BlockType.class, type -> type.has(metal.getLikeMetal()), type ->
                    register(type.createName(metal), type.create(metal), type.createBlockItem(new Item.Properties()))
            )
    );

    public static final Map<CoreCrops, DeferredHolder<Block, Block> > CROPS = Helpers.mapOf(CoreCrops.class, crop ->
            registerNoItem("crop/" + crop.name(),  crop::create)
    );

    public static final Map<CoreCrops, DeferredHolder<Block, Block>> DEAD_CROPS = Helpers.mapOf(CoreCrops.class, crop ->
            registerNoItem("dead_crop/" + crop.name(), crop::createDead)
    );

    public static final Map<CoreCrops, DeferredHolder<Block, Block>> WILD_CROPS = Helpers.mapOf(CoreCrops.class, crop ->
            register("wild_crop/" + crop.name(), crop::createWild)
    );

    public static final Map<CoreStationaryBushes, DeferredHolder<Block, Block>> STATIONARY_BUSHES = Helpers.mapOf(CoreStationaryBushes.class, bush ->
            register("plant/" + bush.name() + "_bush", bush::create, bush::createItem)
    );

    public static final Map<CoreSpreadingBushes, DeferredHolder<Block, Block>> SPREADING_CANES = Helpers.mapOf(CoreSpreadingBushes.class, bush ->
            registerNoItem("plant/" + bush.name() + "_bush_cane", bush::createCane)
    );

    public static final Map<CoreSpreadingBushes, DeferredHolder<Block, Block>> SPREADING_BUSHES = Helpers.mapOf(CoreSpreadingBushes.class, bush ->
            register("plant/" + bush.name() + "_bush", bush::createBush, bush::createItem)
    );

    public static final Map<CoreFruitTrees, DeferredHolder<Block, Block>> FRUIT_TREE_LEAVES = Helpers.mapOf(CoreFruitTrees.class, tree ->
            register("plant/" + tree.name() + "_leaves", tree::createLeaves)
    );

    public static final Map<CoreFruitTrees, DeferredHolder<Block, Block>> FRUIT_TREE_BRANCHES = Helpers.mapOf(CoreFruitTrees.class, tree ->
            registerNoItem("plant/" + tree.name() + "_branch", tree::createBranch)
    );

    public static final Map<CoreFruitTrees, DeferredHolder<Block, Block>> FRUIT_TREE_GROWING_BRANCHES = Helpers.mapOf(CoreFruitTrees.class, tree ->
            registerNoItem("plant/" + tree.name() + "_growing_branch", tree::createGrowingBranch)
    );

    public static final Map<CoreFruitTrees, DeferredHolder<Block, Block>> FRUIT_TREE_SAPLINGS = Helpers.mapOf(CoreFruitTrees.class, tree ->
            register("plant/" + tree.name() + "_sapling", tree::createSapling, tree::createSaplingItem)
    );

    public static final Map<CoreFruitTrees, DeferredHolder<Block, Block>> FRUIT_TREE_POTTED_SAPLINGS = Helpers.mapOf(CoreFruitTrees.class, tree ->
            registerNoItem("plant/potted/" + tree.name() + "_sapling", tree::createPottedSapling)
    );

    /* Much easier with kjs for now.
    public static final Map<Metals.Metal, DeferredHolder<Block, LiquidBlock>> METAL_FLUIDS = Helpers.mapOf(Metals.Metal.class, metal ->
            registerNoItem("fluid/metal/" + metal.name(), () -> new LiquidBlock(CoreFluids.METALS.get(metal).getSource(), BlockBehaviour.Properties.ofFullCopy(Blocks.LAVA).noLootTable()))
    );
    */

    private static <T extends Block> DeferredHolder<Block, T> registerNoItem(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, (Function<T, ? extends BlockItem>) null);
    }

    private static <T extends Block> DeferredHolder<Block, T> register(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, new Item.Properties()));
    }

    private static <T extends Block> DeferredHolder<Block, T> register(String name, Supplier<T> blockSupplier, @Nullable Function<T, ? extends BlockItem> blockItemFactory)
    {
        return RegistrationHelpers.registerBlock(CoreBlocks.BLOCKS, CoreItems.ITEMS, name, blockSupplier, blockItemFactory);
    }
}
