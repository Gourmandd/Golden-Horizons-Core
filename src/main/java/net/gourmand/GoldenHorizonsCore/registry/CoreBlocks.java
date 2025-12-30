package net.gourmand.GoldenHorizonsCore.registry;

import net.dries007.tfc.common.blocks.*;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.rock.RockCategory;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.gourmand.GoldenHorizonsCore.GoldenHorizonsCore;
import net.gourmand.GoldenHorizonsCore.registry.blocks.CoreDecorationBlockHolder;
import net.gourmand.GoldenHorizonsCore.registry.category.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathType;
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

    public static final Map<Rock, Map<CoreOres, DeferredHolder<Block, Block>>> ORES = Helpers.mapOf(Rock.class, rock ->
            Helpers.mapOf(CoreOres.class, ore -> (!ore.isGraded() && ore.hasBlock()), ore ->
                    register(("ore/" + ore.name() + "/" + rock.name()), () -> ore.create(rock))
            )
    );

    // Even if no ores are registered as of now, it adds the opportunity to easily add them.
    public static final Map<Rock, Map<CoreOres, Map<CoreOres.Grade, DeferredHolder<Block, Block>>>> GRADED_ORES = Helpers.mapOf(Rock.class, rock ->
            Helpers.mapOf(CoreOres.class, CoreOres::isGraded, ore ->
                    Helpers.mapOf(CoreOres.Grade.class, grade ->
                            register(("ore/" + grade.name() + "_" + ore.name() + "/" + rock.name()), () -> ore.create(rock))
                    )
            )
    );

    public static final Map<CoreRocks, Map<CoreOres, DeferredHolder<Block, Block>>> CUSTOM_ROCK_ORES = Helpers.mapOf(CoreRocks.class, rock ->
            Helpers.mapOf(CoreOres.class, ore -> (!ore.isGraded() && ore.hasBlock()), ore ->
                    register(("ore/" + ore.name() + "/" + rock.name()), () -> ore.create(rock))
            )
    );

    // Even if no ores are registered as of now, it adds the opportunity to easily add them.
    public static final Map<CoreRocks, Map<CoreOres, Map<CoreOres.Grade, DeferredHolder<Block, Block>>>> CUSTOM_ROCK_GRADED_ORES = Helpers.mapOf(CoreRocks.class, rock ->
            Helpers.mapOf(CoreOres.class, CoreOres::isGraded, ore ->
                    Helpers.mapOf(CoreOres.Grade.class, grade ->
                            register(("ore/" + grade.name() + "_" + ore.name() + "/" + rock.name()), () -> ore.create(rock))
                    )
            )
    );

    public static final Map<CoreRocks, Map<Ore, DeferredHolder<Block, Block>>> CUSTOM_ROCK_TFC_ORES = Helpers.mapOf(CoreRocks.class, rock ->
            Helpers.mapOf(Ore.class, ore -> (!ore.isGraded() && ore.hasBlock()), ore ->
                    register(("ore/" + ore.name() + "/" + rock.name()), () -> ore.create(rock))
            )
    );

    public static final Map<CoreRocks, Map<Ore, Map<Ore.Grade, DeferredHolder<Block, Block>>>> CUSTOM_ROCK_TFC_GRADED_ORES = Helpers.mapOf(CoreRocks.class, rock ->
            Helpers.mapOf(Ore.class, Ore::isGraded, ore ->
                    Helpers.mapOf(Ore.Grade.class, grade ->
                            register(("ore/" + grade.name() + "_" + ore.name() + "/" + rock.name()), () -> ore.create(rock))
                    )
            )
    );

    public static final Map<CoreOres, DeferredHolder<Block, Block>> SMALL_ORES = Helpers.mapOf(CoreOres.class, CoreOres::isGraded, type ->
            register(("ore/small_" + type.name()), () -> GroundcoverBlock.looseOre(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).strength(0.05F, 0.0F).sound(SoundType.NETHER_ORE).noCollission().pushReaction(PushReaction.DESTROY)))
    );

    public static final Map<CoreRocks, Map<Rock.BlockType, DeferredHolder<Block, Block>>> ROCK_BLOCKS = Helpers.mapOf(CoreRocks.class, rock ->
            Helpers.mapOf(Rock.BlockType.class, rock::hasVariant, type ->
                    register(("rock/" + type.name() + "/" + rock.name()), () -> type.create(rock), rock.createItemProperties())
            )
    );

    public static final Map<CoreRocks, Map<Rock.BlockType, CoreDecorationBlockHolder>> ROCK_DECORATIONS = Helpers.mapOf(CoreRocks.class, rock ->
            Helpers.mapOf(Rock.BlockType.class, type -> (type.hasVariants() || type == Rock.BlockType.MOSSY_COBBLE || type == Rock.BlockType.MOSSY_BRICKS) && rock.hasVariant(type), type -> registerDecorations(
                    "rock/" + type.name() + "/" + rock.name(),
                    () -> type.createSlab(rock),
                    () -> type.createStairs(rock),
                    () -> type.createWall(rock),
                    rock.createItemProperties()
            ))
    );

    public static final Map<CoreRocks, DeferredHolder<Block, Block>> MAGMA_BLOCKS = Helpers.mapOf(CoreRocks.class, rock -> rock.category() == RockCategory.IGNEOUS_EXTRUSIVE || rock.category() == RockCategory.IGNEOUS_INTRUSIVE, rock ->
            register("rock/magma/" + rock.name(), () -> new TFCMagmaBlock(ExtendedProperties.of().pathType(PathType.LAVA).mapColor(MapColor.NETHER).requiresCorrectToolForDrops().lightLevel(s -> 6).randomTicks().strength(0.5F).isValidSpawn((state, level, pos, type) -> type.fireImmune()).hasPostProcess(CoreBlocks::always)), b -> new BlockItem(b, rock.createItemProperties()))
    );

    /* Much easier with kjs for now.
    public static final Map<Metals.Metal, DeferredHolder<Block, LiquidBlock>> METAL_FLUIDS = Helpers.mapOf(Metals.Metal.class, metal ->
            registerNoItem("fluid/metal/" + metal.name(), () -> new LiquidBlock(CoreFluids.METALS.get(metal).getSource(), BlockBehaviour.Properties.ofFullCopy(Blocks.LAVA).noLootTable()))
    );
    */

    public static boolean always(BlockState state, BlockGetter level, BlockPos pos)
    {
        return true;
    }


    private static <T1 extends SlabBlock, T2 extends StairBlock, T3 extends WallBlock> CoreDecorationBlockHolder registerDecorations(String baseName, Supplier<T1> slab, Supplier<T2> stair, Supplier<T3> wall, Item.Properties properties)
    {
        return new CoreDecorationBlockHolder(
                register(baseName + "_slab", slab, b -> new BlockItem(b, properties)),
                register(baseName + "_stairs", stair, b -> new BlockItem(b, properties)),
                register(baseName + "_wall", wall, b -> new BlockItem(b, properties))
        );
    }

    private static <T extends Block> DeferredHolder<Block, T> registerNoItem(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, (Function<T, ? extends BlockItem>) null);
    }

    private static <T extends Block> DeferredHolder<Block, T> register(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, new Item.Properties()));
    }

    private static <T extends Block> DeferredHolder<Block, T> register(String name, Supplier<T> blockSupplier, Item.Properties blockItemProperties)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, blockItemProperties));
    }

    private static <T extends Block> DeferredHolder<Block, T> register(String name, Supplier<T> blockSupplier, @Nullable Function<T, ? extends BlockItem> blockItemFactory)
    {
        return RegistrationHelpers.registerBlock(CoreBlocks.BLOCKS, CoreItems.ITEMS, name, blockSupplier, blockItemFactory);
    }
}
