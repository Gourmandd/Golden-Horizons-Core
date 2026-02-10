package net.gourmand.core.datagen.providers;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.items.*;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistryRock;
import net.gourmand.core.AncientGroundCore;
import net.gourmand.core.datagen.Accessors;
import net.gourmand.core.registry.CoreBlocks;
import net.gourmand.core.registry.CoreItems;
import net.gourmand.core.registry.category.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class BuiltinItemTags extends TagsProvider<Item> implements Accessors
{
    private final ExistingFileHelper.IResourceType resourceType;

    final TagKey<Item> SHIMMERSTONE_ORES = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("pastel", "shimmerstone_ores"));
    final TagKey<Item> AZURITE_ORES = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("pastel", "azurite_ores"));

    public BuiltinItemTags(GatherDataEvent event, CompletableFuture<HolderLookup.Provider> lookup)
    {
        super(event.getGenerator().getPackOutput(), Registries.ITEM, lookup, AncientGroundCore.MODID, event.getExistingFileHelper());
        this.resourceType = new ExistingFileHelper.ResourceType(PackType.SERVER_DATA, ".json", Registries.tagsDirPath(registryKey));
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        final TagKey<Item> UPRIGHT_ON_BELT = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("create", "upright_on_belt"));

        add(CoreBlocks.FRUIT_TREE_LEAVES, List.of(
                ItemTags.LEAVES)
        );

        add(CoreBlocks.FRUIT_TREE_SAPLINGS, List.of(
                ItemTags.SAPLINGS)
        );

        Stream.of(CoreRocks.values()).forEach(rock -> {
            Stream.of(Ore.values()).forEach(ore -> {
                if (ore.hasBlock())
                {
                    if (ore.isGraded())
                    {
                        addGradedOreTags(CoreBlocks.CUSTOM_ROCK_TFC_GRADED_ORES, ore, rock);
                    }
                    else
                    {
                        addOreTags(CoreBlocks.CUSTOM_ROCK_TFC_ORES, ore, rock);
                    }
                }
            });
        });

        Stream.of(CoreOres.values()).forEach(ore -> {
            // if it's an ore such as bituminous coal
            if (!ore.hasBlock())
            {
                this.tag(Tags.Items.ORES).add(getKey(CoreBlocks.BASIC_ORES.get(ore)));
            }
            else
            {
                Stream.of(CoreRocks.values()).forEach(rock -> {
                    if (ore.isGraded())
                    {
                        addGradedOreTags(CoreBlocks.CUSTOM_ROCK_GRADED_ORES, ore, rock);
                    }
                    else
                    {
                        addOreTags(CoreBlocks.CUSTOM_ROCK_ORES, ore, rock);
                    }
                });
                Stream.of(Rock.values()).forEach(rock -> {
                    if (ore.isGraded())
                    {
                        addGradedOreTags(CoreBlocks.GRADED_ORES, ore, rock);
                    }
                    else
                    {
                        addOreTags(CoreBlocks.ORES, ore, rock);
                    }
                });
            }
        });

        Stream.of(Rock.values()).forEach(rock -> {
            this.tag(AZURITE_ORES).add(getKey(CoreBlocks.ORES.get(rock).get(CoreOres.AZURITE)));
            this.tag(SHIMMERSTONE_ORES).add(getKey(CoreBlocks.ORES.get(rock).get(CoreOres.SHIMMERSTONE)));
        });

        Stream.of(CoreRocks.values()).forEach(rock -> {
            this.tag(AZURITE_ORES).add(getKey(CoreBlocks.CUSTOM_ROCK_ORES.get(rock).get(CoreOres.AZURITE)));
            this.tag(SHIMMERSTONE_ORES).add(getKey(CoreBlocks.CUSTOM_ROCK_ORES.get(rock).get(CoreOres.SHIMMERSTONE)));
        });

        Stream.of(CoreMetals.MetalType.values()).forEach(metal -> {
            this.tag(Tags.Items.INGOTS).add(CoreItems.METAL_ITEMS.get(metal).get(Metal.ItemType.INGOT).getKey());
            this.tag(TFCTags.Items.DOUBLE_INGOTS).add(CoreItems.METAL_ITEMS.get(metal).get(Metal.ItemType.DOUBLE_INGOT).getKey());
            this.tag(TFCTags.Items.SHEETS).add(CoreItems.METAL_ITEMS.get(metal).get(Metal.ItemType.SHEET).getKey());
            this.tag(TFCTags.Items.DOUBLE_SHEETS).add(CoreItems.METAL_ITEMS.get(metal).get(Metal.ItemType.DOUBLE_SHEET).getKey());
            this.tag(Tags.Items.RODS).add(CoreItems.METAL_ITEMS.get(metal).get(Metal.ItemType.ROD).getKey());

            this.tag(CoreTags.Items.METAL_INGOTS.get(metal)).add(CoreItems.METAL_ITEMS.get(metal).get(Metal.ItemType.INGOT).getKey());
            this.tag(CoreTags.Items.METAL_DOUBLE_INGOTS.get(metal)).add(CoreItems.METAL_ITEMS.get(metal).get(Metal.ItemType.DOUBLE_INGOT).getKey());
            this.tag(CoreTags.Items.METAL_SHEETS.get(metal)).add(CoreItems.METAL_ITEMS.get(metal).get(Metal.ItemType.SHEET).getKey());
            this.tag(CoreTags.Items.METAL_DOUBLE_SHEETS.get(metal)).add(CoreItems.METAL_ITEMS.get(metal).get(Metal.ItemType.DOUBLE_SHEET).getKey());
        });

        Stream.of(CoreClay.values()).forEach(clay -> {
            Stream.of(CoreClay.ItemType.values()).forEach(type -> {
                if (type.getType() == CoreClay.ItemPartType.UNFIRED_MOLD && type != CoreClay.ItemType.INGOT){
                    this.tag(TFCTags.Items.UNFIRED_MOLDS).add(CoreItems.CERAMICS.get(clay).get(type).getKey());
                    this.tag(CoreTags.Items.CLAY_RECYCLING_5.get(clay)).add(CoreItems.CERAMICS.get(clay).get(type).getKey());
                }
            });

            this.tag(TFCTags.Items.VESSELS).add(CoreItems.CERAMICS.get(clay).get(CoreClay.ItemType.VESSEL).getKey());
            this.tag(CoreTags.Items.UNFIRED_VESSELS).add(CoreItems.CERAMICS.get(clay).get(CoreClay.ItemType.UNFIRED_VESSEL).getKey());
            this.tag(TFCTags.Items.UNFIRED_VESSELS).add(CoreItems.CERAMICS.get(clay).get(CoreClay.ItemType.UNFIRED_VESSEL).getKey());
            this.tag(TFCTags.Items.FLUID_ITEM_INGREDIENT_EMPTY_CONTAINERS).add(CoreItems.CERAMICS.get(clay).get(CoreClay.ItemType.JUG).getKey());
            this.tag(UPRIGHT_ON_BELT).add(CoreItems.CERAMICS.get(clay).get(CoreClay.ItemType.JUG).getKey());


            this.tag(CoreTags.Items.CLAY_RECYCLING_5.get(clay)).add(
                    CoreItems.CERAMICS.get(clay).get(CoreClay.ItemType.UNFIRED_VESSEL).getKey(),
                    CoreItems.CERAMICS.get(clay).get(CoreClay.ItemType.UNFIRED_JUG).getKey(),
                    CoreItems.CERAMICS.get(clay).get(CoreClay.ItemType.UNFIRED_POT).getKey(),
                    CoreItems.CERAMICS.get(clay).get(CoreClay.ItemType.UNFIRED_SPINDLE_HEAD).getKey(),
                    CoreItems.CERAMICS.get(clay).get(CoreClay.ItemType.UNFIRED_PAN).getKey(),
                    CoreItems.CERAMICS.get(clay).get(CoreClay.ItemType.UNFIRED_BLOWPIPE).getKey()
            );

            this.tag(CoreTags.Items.CLAY_RECYCLING_1.get(clay)).add(
                    CoreItems.CERAMICS.get(clay).get(CoreClay.ItemType.UNFIRED_BRICK).getKey(),
                    CoreItems.CERAMICS.get(clay).get(CoreClay.ItemType.UNFIRED_BOWL).getKey(),
                    CoreItems.CERAMICS.get(clay).get(CoreClay.ItemType.UNFIRED_FLOWER_POT).getKey(),
                    CoreItems.CERAMICS.get(clay).get(CoreClay.ItemType.INGOT).getKey()
            );

            this.tag(TFCTags.Items.UNFIRED_POTTERY).add(
                    CoreItems.CERAMICS.get(clay).get(CoreClay.ItemType.UNFIRED_BRICK).getKey(),
                    CoreItems.CERAMICS.get(clay).get(CoreClay.ItemType.UNFIRED_BOWL).getKey(),
                    CoreItems.CERAMICS.get(clay).get(CoreClay.ItemType.UNFIRED_FLOWER_POT).getKey(),
                    CoreItems.CERAMICS.get(clay).get(CoreClay.ItemType.UNFIRED_VESSEL).getKey(),
                    CoreItems.CERAMICS.get(clay).get(CoreClay.ItemType.UNFIRED_JUG).getKey(),
                    CoreItems.CERAMICS.get(clay).get(CoreClay.ItemType.UNFIRED_POT).getKey(),
                    CoreItems.CERAMICS.get(clay).get(CoreClay.ItemType.UNFIRED_SPINDLE_HEAD).getKey(),
                    CoreItems.CERAMICS.get(clay).get(CoreClay.ItemType.UNFIRED_PAN).getKey(),
                    CoreItems.CERAMICS.get(clay).get(CoreClay.ItemType.UNFIRED_BLOWPIPE).getKey()
            );

            if (!clay.hasReducedSet()){
                this.tag(CoreTags.Items.CLAY_BALLS).add(
                        CoreItems.CERAMICS.get(clay).get(CoreClay.ItemType.CLAY_BALL).getKey()
                );
            }
        });

        CategoryUtil.getTFCToolMetals().forEach(metal -> {
            CategoryUtil.getTFCToolHeads().forEach(tool -> {
                this.tag(CoreTags.Items.TOOL_HEADS.get(tool)).add(
                        TFCItems.METAL_ITEMS.get(metal).get(tool).holder().getKey()
                );
            });
        });

        Stream.of(CoreRocks.values()).forEach(rock ->{

            this.tag(Tags.Items.COBBLESTONES_NORMAL).add(getKey(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.COBBLE)));
            this.tag(Tags.Items.COBBLESTONES_MOSSY).add(getKey(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.MOSSY_COBBLE)));

            this.tag(TFCTags.Items.STONES_HARDENED).add(getKey(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.HARDENED)));

            this.tag(Tags.Items.STONES).add(getKey(CoreRocks.getRawRock(rock)));

            this.tag(Tags.Items.COBBLESTONES)
                    .add(getKey(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.COBBLE)))
                    .add(getKey(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.MOSSY_COBBLE)));

            this.tag(ItemTags.STAIRS)
                    .add(getKey(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.COBBLE).stair()))
                    .add(getKey(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.MOSSY_COBBLE).stair()))
                    .add(getKey(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.MOSSY_BRICKS).stair()));

            this.tag(ItemTags.SLABS)
                    .add(getKey(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.COBBLE).slab()))
                    .add(getKey(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.MOSSY_COBBLE).slab()))
                    .add(getKey(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.MOSSY_BRICKS).slab()));

            this.tag(ItemTags.WALLS)
                    .add(getKey(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.COBBLE).wall()))
                    .add(getKey(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.MOSSY_COBBLE).wall()))
                    .add(getKey(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.MOSSY_BRICKS).wall()));

            this.tag(ItemTags.STONE_BRICKS).add(getKey(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.MOSSY_BRICKS)));
            this.tag(TFCTags.Items.AQUEDUCTS).add(getKey(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.AQUEDUCT)));
            this.tag(Tags.Items.GRAVELS).add(getKey(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.GRAVEL)));

            this.tag(TFCTags.Items.STONES_LOOSE)
                    .add(getKey(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE)))
                    .add(getKey(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.MOSSY_LOOSE)));

            if (rock.hasVariants()){

                this.tag(ItemTags.STONE_BRICKS)
                        .add(getKey(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.BRICKS)))
                        .add(getKey(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.CRACKED_BRICKS)))
                        .add(getKey(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.CHISELED)));

                this.tag(TFCTags.Items.STONES_SMOOTH).add(getKey(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.SMOOTH)));

                this.tag(ItemTags.STAIRS)
                        .add(getKey(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.BRICKS).stair()))
                        .add(getKey(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.CRACKED_BRICKS).stair()))
                        .add(getKey(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.SMOOTH).stair()));

                this.tag(ItemTags.SLABS)
                        .add(getKey(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.BRICKS).slab()))
                        .add(getKey(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.CRACKED_BRICKS).slab()))
                        .add(getKey(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.SMOOTH).slab()));

                this.tag(ItemTags.WALLS)
                        .add(getKey(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.BRICKS).wall()))
                        .add(getKey(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.CRACKED_BRICKS).wall()))
                        .add(getKey(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.SMOOTH).wall()));

                this.tag(TFCTags.Items.STONES_PRESSURE_PLATES).add(getKey(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.PRESSURE_PLATE)));

                this.tag(ItemTags.STONE_BUTTONS).add(getKey(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.BUTTON)));
                this.tag(ItemTags.BUTTONS).add(getKey(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.BUTTON)));
            }
        });
    }

    protected void add(Map<?, DeferredHolder<Block, Block>> map, List<TagKey<Item>> tags ){
        for (DeferredHolder<Block, ?> block : map.values()){
            for (TagKey<Item> tag : tags){
                this.tag(tag).add(getKey(block));
            };
        }
    };

    protected ResourceKey<Item> getKey(DeferredHolder<Block, ? extends Block> block ){
        return block.get().asItem().builtInRegistryHolder().key();
    };

    protected ResourceKey<Item> getKey(Block block){
        return block.asItem().builtInRegistryHolder().key();
    };

    private <T1 extends RegistryRock, T2> void addOreTags(Map<T1, Map<T2, DeferredHolder<Block, Block>>> map, T2 ore, T1 rock){
        DeferredHolder<Block, Block> block = map.get(rock).get(ore);
        this.tag(Tags.Items.ORES).add(getKey(block));
    };

    private <T1 extends RegistryRock, T2, T3 extends CoreOres.Grade> void addGradedOreTags(Map<T1, Map<T2, Map<T3, DeferredHolder<Block, Block>>>> map, T2 ore, T1 rock){

        for (CoreOres.Grade grade : CoreOres.Grade.values()){
            DeferredHolder<Block, Block> block = map.get(rock).get(ore).get(grade);
            this.tag(Tags.Items.ORES).add(getKey(block));
        }
    };
}
