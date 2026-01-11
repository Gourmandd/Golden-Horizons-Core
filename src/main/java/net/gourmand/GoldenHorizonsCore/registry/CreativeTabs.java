package net.gourmand.GoldenHorizonsCore.registry;

import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.util.Metal;
import net.gourmand.GoldenHorizonsCore.GoldenHorizonsCore;
import net.gourmand.GoldenHorizonsCore.registry.blocks.CoreDecorationBlockHolder;
import net.gourmand.GoldenHorizonsCore.registry.category.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Map;

public class CreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GoldenHorizonsCore.MODID);


    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> METAL = CREATIVE_TABS.register("metal", () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group.metal." + GoldenHorizonsCore.MODID)) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> CoreItems.METAL_ITEMS.get(CoreMetals.MetalType.ALUMINIUM).get(Metal.ItemType.INGOT).get().getDefaultInstance())
            .displayItems(CreativeTabs::fillMetal).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> NATURE = CREATIVE_TABS.register("nature", () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group.nature." + GoldenHorizonsCore.MODID)) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> CoreItems.CROP_SEEDS.get(CoreCrops.AMARANTH).get().getDefaultInstance())
            .displayItems(CreativeTabs::fillNature).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ORES = CREATIVE_TABS.register("ores", () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group.ores." + GoldenHorizonsCore.MODID)) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> CoreBlocks.ORES.get(Rock.ANDESITE).get(CoreOres.AZURITE).get().asItem().getDefaultInstance())
            .displayItems(CreativeTabs::fillOres).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ROCKS = CREATIVE_TABS.register("rocks", () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group.rocks." + GoldenHorizonsCore.MODID)) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> CoreBlocks.ROCK_BLOCKS.get(CoreRocks.NEPHELINITE).get(Rock.BlockType.HARDENED).get().asItem().getDefaultInstance())
            .displayItems(CreativeTabs::fillRocks).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TOOLS = CREATIVE_TABS.register("tools", () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group.tools." + GoldenHorizonsCore.MODID)) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> CoreItems.SNOW_SHOVEL.get().getDefaultInstance())
            .displayItems(CreativeTabs::fillTools).build());

    private static void fillNature(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out)
    {
        for (CoreCrops crop : CoreCrops.values())
        {
            accept(out, CoreBlocks.WILD_CROPS, crop);
            accept(out, CoreItems.CROP_SEEDS, crop);
        }

        for (CoreSpreadingBushes bush : CoreSpreadingBushes.values())
        {
            accept(out, CoreBlocks.SPREADING_BUSHES, bush);
        }

        for (CoreStationaryBushes bush : CoreStationaryBushes.values())
        {
            accept(out, CoreBlocks.STATIONARY_BUSHES, bush);
        }

        for (CoreFruitTrees tree : CoreFruitTrees.values())
        {
            accept(out, CoreBlocks.FRUIT_TREE_SAPLINGS, tree);
            accept(out, CoreBlocks.FRUIT_TREE_LEAVES, tree);
        }
    }

    private static void fillMetal(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out)
    {
        for(CoreMetals.MetalType metal : CoreMetals.MetalType.values())
        {
            for (Metal.ItemType type : Metal.ItemType.values())
            {
                accept(out, CoreItems.METAL_ITEMS, metal, type);
            }

            for (Metal.BlockType type : Metal.BlockType.values())
            {
                accept(out, CoreBlocks.METALS, metal, type);
            }
        }
    }

    private static void fillOres(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out)
    {
        for (CoreOres ore : CoreOres.values())
        {
            if (ore.isGraded())
            {
                accept(out, CoreBlocks.SMALL_ORES, ore);
                accept(out, CoreItems.GRADED_ORES, ore, CoreOres.Grade.POOR);
                accept(out, CoreItems.GRADED_ORES, ore, CoreOres.Grade.NORMAL);
                accept(out, CoreItems.GRADED_ORES, ore, CoreOres.Grade.RICH);
            }
        }

        for (CoreOres ore : CoreOres.values())
        {
            if (!(ore.isGraded() || ore.hasPastelOreType())) accept(out, CoreItems.ORES, ore);
        }

        for (CoreOres ore : CoreOres.values())
        {
            if (ore.isGraded())
            {
                CoreBlocks.GRADED_ORES.values().forEach(map -> {
                        accept(out, map, ore, CoreOres.Grade.POOR);
                        accept(out, map, ore, CoreOres.Grade.NORMAL);
                        accept(out, map, ore, CoreOres.Grade.RICH);
                    }
                );

                CoreBlocks.CUSTOM_ROCK_GRADED_ORES.values().forEach(map -> {
                            accept(out, map, ore, CoreOres.Grade.POOR);
                            accept(out, map, ore, CoreOres.Grade.NORMAL);
                            accept(out, map, ore, CoreOres.Grade.RICH);
                        }
                );
            }
            else
            {
                CoreBlocks.ORES.values().forEach(map -> accept(out, map, ore));
                CoreBlocks.CUSTOM_ROCK_ORES.values().forEach(map -> accept(out, map, ore));
            }
        }

        for (Ore ore : Ore.values())
        {
            if (ore.isGraded())
            {
                CoreBlocks.CUSTOM_ROCK_TFC_GRADED_ORES.values().forEach(map -> {
                            accept(out, map, ore, Ore.Grade.POOR);
                            accept(out, map, ore, Ore.Grade.NORMAL);
                            accept(out, map, ore, Ore.Grade.RICH);
                        }
                );
            }
            else
            {
                CoreBlocks.CUSTOM_ROCK_TFC_ORES.values().forEach(map -> accept(out, map, ore));
            }
        }
    }

    private static void fillRocks(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out)
    {
        for (CoreRocks rock : CoreRocks.VALUES)
        {
            for (Rock.BlockType type : new Rock.BlockType[] {
                    Rock.BlockType.HARDENED,
                    Rock.BlockType.RAW,
                    Rock.BlockType.PRESSURE_PLATE,
                    Rock.BlockType.BUTTON,
                    Rock.BlockType.SPIKE,
                    Rock.BlockType.COBBLE,
                    Rock.BlockType.MOSSY_COBBLE,
                    Rock.BlockType.BRICKS,
                    Rock.BlockType.CRACKED_BRICKS,
                    Rock.BlockType.MOSSY_BRICKS,
                    Rock.BlockType.SMOOTH,
                    Rock.BlockType.CHISELED,
                    Rock.BlockType.AQUEDUCT,
                    Rock.BlockType.GRAVEL,
                    Rock.BlockType.LOOSE,
                    Rock.BlockType.MOSSY_LOOSE,
            })
            {
                if (rock.hasVariant(type))
                {
                    accept(out, CoreBlocks.ROCK_BLOCKS, rock, type);
                }

                if (type.hasVariants() && rock.hasVariant(type))
                {
                    accept(out, CoreBlocks.ROCK_DECORATIONS.get(rock).get(type));
                }
            }
            accept(out, CoreItems.BRICKS, rock);
        }
    }

    private static void fillTools(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out)
    {
        out.accept(CoreItems.SNOW_SHOVEL.get());
        out.accept(CoreItems.SNOW_SHOVEL_HEAD.get());

        out.accept(CoreItems.GLASS_MOLD.get());
        out.accept(CoreItems.GLASS_PANE_MOLD.get());
    }


    private static <R extends DeferredHolder<?, ?>, K1, K2> void accept(CreativeModeTab.Output out, Map<K1, Map<K2, R>> map, K1 key1, K2 key2)
    {
        if (map.containsKey(key1))
        {
            accept(out, map.get(key1), key2);
        }
    }

    private static void accept(CreativeModeTab.Output out, CoreDecorationBlockHolder decoration)
    {
        out.accept(decoration.stair().get());
        out.accept(decoration.slab().get());
        out.accept(decoration.wall().get());
    }

    private static <R extends DeferredHolder<?, ?>, K> void accept(CreativeModeTab.Output out, Map<K, R> map, K key)
    {
        if (map.containsKey(key)) {
            out.accept((ItemLike) map.get(key));
        }
    }
}
