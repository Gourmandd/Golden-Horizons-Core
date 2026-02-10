package net.gourmand.core.datagen.providers;


import earth.terrarium.pastel.registries.PastelBlockTags;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistryRock;
import net.gourmand.core.AncientGroundCore;
import net.gourmand.core.datagen.Accessors;
import net.gourmand.core.registry.CoreBlocks;
import net.gourmand.core.registry.category.CoreMetals;
import net.gourmand.core.registry.category.CoreOres;
import net.gourmand.core.registry.category.CoreRocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class BuiltinBlockTags extends TagsProvider<Block> implements Accessors
{
    private final ExistingFileHelper.IResourceType resourceType;

    public BuiltinBlockTags(GatherDataEvent event, CompletableFuture<HolderLookup.Provider> lookup)
    {
        super(event.getGenerator().getPackOutput(), Registries.BLOCK, lookup, AncientGroundCore.MODID, event.getExistingFileHelper());
        this.resourceType = new ExistingFileHelper.ResourceType(PackType.SERVER_DATA, ".json", Registries.tagsDirPath(registryKey));
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        final TagKey<Block> SHIMMERSTONE_ORES = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("pastel", "shimmerstone_ores"));
        // adds #c:ores to this tag to be able to isolate and get ore blocks.
        this.tag(TFCTags.Blocks.BREAKS_WHEN_ISOLATED).addTag(Tags.Blocks.ORES);

        add(CoreBlocks.FRUIT_TREE_LEAVES, List.of(
            BlockTags.LEAVES,
            BlockTags.MINEABLE_WITH_HOE,
            TFCTags.Blocks.MINEABLE_WITH_HOE,
            TFCTags.Blocks.MINEABLE_WITH_SCYTHE,
            TFCTags.Blocks.MINEABLE_WITH_KNIFE,
            TFCTags.Blocks.FRUIT_TREE_LEAVES)
        );

        add(CoreBlocks.FRUIT_TREE_SAPLINGS, List.of(
            BlockTags.MINEABLE_WITH_HOE,
            TFCTags.Blocks.MINEABLE_WITH_HOE,
            TFCTags.Blocks.MINEABLE_WITH_SCYTHE,
            TFCTags.Blocks.MINEABLE_WITH_KNIFE,
            TFCTags.Blocks.FRUIT_TREE_SAPLING)
        );

        add(CoreBlocks.FRUIT_TREE_GROWING_BRANCHES, List.of(
                BlockTags.MINEABLE_WITH_AXE,
                TFCTags.Blocks.FRUIT_TREE_BRANCH)
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
            if (!ore.hasBlock())
            {
                ResourceKey<Block> key = CoreBlocks.BASIC_ORES.get(ore).getKey();
                this.tag(Tags.Blocks.ORES).add(key);
                this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(key);
                this.tag(TFCTags.Blocks.PROSPECTABLE).add(key);
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

        Stream.of(CoreRocks.values()).forEach(rock -> {
            this.tag(PastelBlockTags.AZURITE_ORES).add(CoreBlocks.CUSTOM_ROCK_ORES.get(rock).get(CoreOres.AZURITE).getKey());
            this.tag(SHIMMERSTONE_ORES).add(CoreBlocks.CUSTOM_ROCK_ORES.get(rock).get(CoreOres.SHIMMERSTONE).getKey());
        });

        Stream.of(Rock.values()).forEach(rock -> {
            this.tag(PastelBlockTags.AZURITE_ORES).add(CoreBlocks.ORES.get(rock).get(CoreOres.AZURITE).getKey());
            this.tag(SHIMMERSTONE_ORES).add(CoreBlocks.ORES.get(rock).get(CoreOres.SHIMMERSTONE).getKey());
        });

        Stream.of(CoreMetals.MetalType.values()).forEach(metal -> {
            this.tag(BlockTags.SLABS).add(CoreBlocks.METALS.get(metal).get(Metal.BlockType.BLOCK_SLAB).getKey());
            this.tag(BlockTags.STAIRS).add(CoreBlocks.METALS.get(metal).get(Metal.BlockType.BLOCK_STAIRS).getKey());

            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(CoreBlocks.METALS.get(metal).get(Metal.BlockType.BLOCK).getKey());
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(CoreBlocks.METALS.get(metal).get(Metal.BlockType.BLOCK_SLAB).getKey());
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(CoreBlocks.METALS.get(metal).get(Metal.BlockType.BLOCK_STAIRS).getKey());
        });

        Stream.of(CoreRocks.values()).forEach(rock ->{

            Stream.of(Rock.BlockType.values()).forEach(type ->{
                if (rock.hasVariant(type)){
                    if (type.hasVariants()){
                        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(CoreBlocks.ROCK_DECORATIONS.get(rock).get(type).stair().getKey());
                        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(CoreBlocks.ROCK_DECORATIONS.get(rock).get(type).slab().getKey());
                        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(CoreBlocks.ROCK_DECORATIONS.get(rock).get(type).wall().getKey());
                        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(CoreBlocks.ROCK_BLOCKS.get(rock).get(type).getKey());
                    } else {
                        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(CoreBlocks.ROCK_BLOCKS.get(rock).get(type).getKey());
                    }
                }
            });

            this.tag(TFCTags.Blocks.CAN_COLLAPSE)
                    .add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.SPIKE).getKey())
                    .add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.HARDENED).getKey())
                    .add(CoreRocks.getRawRock(rock).builtInRegistryHolder().key());

            this.tag(Tags.Blocks.STONES)
                    .add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.HARDENED).getKey())
                    .add(CoreRocks.getRawRock(rock).builtInRegistryHolder().key());

            this.tag(TFCTags.Blocks.STONES_RAW).add(CoreRocks.getRawRock(rock).builtInRegistryHolder().key());
            this.tag(TFCTags.Blocks.BREAKS_WHEN_ISOLATED).add(CoreRocks.getRawRock(rock).builtInRegistryHolder().key());
            this.tag(TFCTags.Blocks.CAN_START_COLLAPSE).add(CoreRocks.getRawRock(rock).builtInRegistryHolder().key());
            this.tag(TFCTags.Blocks.CAN_TRIGGER_COLLAPSE)
                    .add(CoreRocks.getRawRock(rock).builtInRegistryHolder().key())
                    .add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.HARDENED).getKey());

            this.tag(TFCTags.Blocks.STONES_SPIKE).add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.SPIKE).getKey());
            this.tag(TFCTags.Blocks.STONES_HARDENED).add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.HARDENED).getKey());

            this.tag(TFCTags.Blocks.CAN_LANDSLIDE)
                    .add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.GRAVEL).getKey())
                    .add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.COBBLE).getKey())
                    .add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.MOSSY_COBBLE).getKey());

            this.tag(Tags.Blocks.COBBLESTONES_NORMAL).add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.COBBLE).getKey());
            this.tag(Tags.Blocks.COBBLESTONES_MOSSY).add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.MOSSY_COBBLE).getKey());

            this.tag(Tags.Blocks.COBBLESTONES)
                    .add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.COBBLE).getKey())
                    .add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.MOSSY_COBBLE).getKey());

            this.tag(BlockTags.STAIRS)
                    .add(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.COBBLE).stair().getKey())
                    .add(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.MOSSY_COBBLE).stair().getKey())
                    .add(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.MOSSY_BRICKS).stair().getKey());

            this.tag(BlockTags.SLABS)
                    .add(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.COBBLE).slab().getKey())
                    .add(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.MOSSY_COBBLE).slab().getKey())
                    .add(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.MOSSY_BRICKS).slab().getKey());

            this.tag(BlockTags.WALLS)
                    .add(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.COBBLE).wall().getKey())
                    .add(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.MOSSY_COBBLE).wall().getKey())
                    .add(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.MOSSY_BRICKS).wall().getKey());

            this.tag(BlockTags.STONE_BRICKS).add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.MOSSY_BRICKS).getKey());
            this.tag(TFCTags.Blocks.AQUEDUCTS).add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.AQUEDUCT).getKey());
            this.tag(Tags.Blocks.GRAVELS).add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.GRAVEL).getKey());
            this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.GRAVEL).getKey());

            this.tag(TFCTags.Blocks.STONES_LOOSE)
                    .add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE).getKey())
                    .add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.MOSSY_LOOSE).getKey());

            if (rock.hasVariants()){
                this.tag(BlockTags.STONE_BRICKS)
                        .add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.BRICKS).getKey())
                        .add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.CRACKED_BRICKS).getKey())
                        .add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.CHISELED).getKey());

                this.tag(TFCTags.Blocks.STONES_SMOOTH).add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.SMOOTH).getKey());

                this.tag(BlockTags.STAIRS)
                        .add(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.BRICKS).stair().getKey())
                        .add(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.CRACKED_BRICKS).stair().getKey())
                        .add(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.SMOOTH).stair().getKey());

                this.tag(BlockTags.SLABS)
                        .add(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.BRICKS).slab().getKey())
                        .add(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.CRACKED_BRICKS).slab().getKey())
                        .add(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.SMOOTH).slab().getKey());

                this.tag(BlockTags.WALLS)
                        .add(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.BRICKS).wall().getKey())
                        .add(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.CRACKED_BRICKS).wall().getKey())
                        .add(CoreBlocks.ROCK_DECORATIONS.get(rock).get(Rock.BlockType.SMOOTH).wall().getKey());

                this.tag(BlockTags.PRESSURE_PLATES).add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.PRESSURE_PLATE).getKey());
                this.tag(BlockTags.STONE_PRESSURE_PLATES).add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.PRESSURE_PLATE).getKey());
                this.tag(TFCTags.Blocks.STONES_PRESSURE_PLATES).add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.PRESSURE_PLATE).getKey());

                this.tag(BlockTags.STONE_BUTTONS).add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.BUTTON).getKey());
                this.tag(BlockTags.BUTTONS).add(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.BUTTON).getKey());
            }
        });
    }

    protected void add(Map<?, DeferredHolder<Block, Block>> map, List<TagKey<Block>> tags ){
        for (DeferredHolder<Block, Block> block : map.values()){
            for (TagKey<Block> tag : tags){
                this.tag(tag).add(block.getKey());
            };
        }
    };

    private <T1 extends RegistryRock, T2> void addOreTags(Map<T1, Map<T2, DeferredHolder<Block, Block>>> map, T2 ore, T1 rock){
        ResourceKey<Block> key = map.get(rock).get(ore).getKey();
        this.tag(Tags.Blocks.ORES).add(key);
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(key);
        this.tag(TFCTags.Blocks.PROSPECTABLE).add(key);
    };

    private <T1 extends RegistryRock, T2, T3 extends CoreOres.Grade> void addGradedOreTags(Map<T1, Map<T2, Map<T3, DeferredHolder<Block, Block>>>> map, T2 ore, T1 rock){

        for (CoreOres.Grade grade : CoreOres.Grade.values()){
            ResourceKey<Block> key = map.get(rock).get(ore).get(grade).getKey();
            this.tag(Tags.Blocks.ORES).add(key);
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(key);
            this.tag(TFCTags.Blocks.PROSPECTABLE).add(key);
        }
    };
}
