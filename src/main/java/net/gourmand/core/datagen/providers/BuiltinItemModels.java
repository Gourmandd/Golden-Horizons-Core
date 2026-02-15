package net.gourmand.core.datagen.providers;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.util.Metal;
import net.gourmand.core.AncientGroundCore;
import net.gourmand.core.registry.CoreBlocks;
import net.gourmand.core.registry.CoreItems;
import net.gourmand.core.registry.category.CoreMetals;
import net.gourmand.core.registry.category.CoreOres;
import net.gourmand.core.registry.category.CoreRocks;
import net.gourmand.core.util.TextureUtil;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.stream.Stream;

public class BuiltinItemModels extends ItemModelProvider {

    public BuiltinItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, AncientGroundCore.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        Stream.of(Rock.values()).forEach(rock -> {
            Stream.of(CoreOres.values()).forEach(ore -> {
                if (!ore.isGraded() && ore.hasBlock()){
                    simpleBlock(CoreBlocks.ORES.get(rock).get(ore));
                }

                Stream.of(CoreOres.Grade.values()).forEach(grade -> {
                    if (ore.isGraded()){
                        simpleBlock(CoreBlocks.GRADED_ORES.get(rock).get(ore).get(grade));
                    }
                });
            });

        });

        Stream.of(CoreRocks.values()).forEach(rock -> {
            Stream.of(CoreOres.values()).forEach(ore -> {
                if (!ore.isGraded() && ore.hasBlock()) {
                    simpleBlock(CoreBlocks.CUSTOM_ROCK_ORES.get(rock).get(ore));
                }

                Stream.of(CoreOres.Grade.values()).forEach(grade -> {
                    if (ore.isGraded()) {
                        simpleBlock(CoreBlocks.CUSTOM_ROCK_GRADED_ORES.get(rock).get(ore).get(grade));
                    }
                });
            });

            Stream.of(Ore.values()).forEach(ore -> {
                if (!ore.isGraded() && ore.hasBlock()) {
                    simpleBlock(CoreBlocks.CUSTOM_ROCK_TFC_ORES.get(rock).get(ore));
                }

                Stream.of(CoreOres.Grade.values()).forEach(grade -> {
                    if (ore.isGraded()) {
                        simpleBlock(CoreBlocks.CUSTOM_ROCK_TFC_GRADED_ORES.get(rock).get(ore).get(grade));
                    }
                });
            });
        });

        // rock blocks.
        Stream.of(CoreRocks.values()).forEach(rock -> {
            Stream.of(Rock.BlockType.values()).forEach(type -> {
                if (type.hasVariants() && rock.hasVariant(type)){
                    simpleBlock(CoreBlocks.ROCK_BLOCKS.get(rock).get(type));
                    simpleBlock(CoreBlocks.ROCK_DECORATIONS.get(rock).get(type).stair());
                    simpleBlock(CoreBlocks.ROCK_DECORATIONS.get(rock).get(type).slab());
                    wallInventory(getItemModelString(CoreBlocks.ROCK_DECORATIONS.get(rock).get(type).wall().getId()), TextureUtil.getRockTexture(rock, type));
                }
            });

            simpleBlock(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.HARDENED));
            simpleBlock(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.GRAVEL));
            simpleBlock(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.AQUEDUCT), ResourceLocation.parse(getBlockModelString(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.AQUEDUCT).getId()) + "/base"));
            simpleItem(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE).get().asItem(), getItemModelLocation(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE).getId()));
            mossyLooseItem(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.MOSSY_LOOSE), rock);
            simpleBlock(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.SPIKE), ResourceLocation.parse(getBlockModelString(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.SPIKE).getId()) + "_base"));

            if (rock.hasVariants()){
                simpleBlock(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.CHISELED));
                pressurePlate(getItemModelString(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.PRESSURE_PLATE).getId()), TextureUtil.getRockTexture(rock, Rock.BlockType.PRESSURE_PLATE));
                buttonInventory(getItemModelString(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.BUTTON).getId()), TextureUtil.getRockTexture(rock, Rock.BlockType.BUTTON));
            }

            simpleItem(CoreItems.BRICKS.get(rock).get(), getItemModelLocation(CoreItems.BRICKS.get(rock).getId()));
        });

        // metal items and blocks
        Stream.of(CoreMetals.MetalType.values()).forEach(metal -> {
            Stream.of(Metal.ItemType.values()).forEach(type -> {
                if (type.has(metal.getLikeMetal())){
                    simpleItem(CoreItems.METAL_ITEMS.get(metal).get(type).get(), getItemModelLocation(CoreItems.METAL_ITEMS.get(metal).get(type).getId()));
                }
            });
        });

        Stream.of(CoreMetals.MetalType.values()).forEach(metal -> {
            simpleBlock(CoreBlocks.METALS.get(metal).get(Metal.BlockType.BLOCK));
            simpleBlock(CoreBlocks.METALS.get(metal).get(Metal.BlockType.BLOCK_STAIRS));
            simpleBlock(CoreBlocks.METALS.get(metal).get(Metal.BlockType.BLOCK_SLAB));
        });
    }

    private void simpleBlock(DeferredHolder<Block, ? extends Block> block){
        withExistingParent(getItemModelString(block.getId()), getBlockModelLocation(block.getId()));
    }

    private void simpleBlock(DeferredHolder<Block, ? extends Block> block, ResourceLocation location){
        withExistingParent(getItemModelString(block.getId()), location);
    }

    private void simpleItem(Item item, ResourceLocation texture){
        this.getBuilder(getItemModelString(item)).parent(new ModelFile.UncheckedModelFile("item/generated")).texture("layer0", texture);
    }

    private void mossyLooseItem(DeferredHolder<Block, ? extends Block> block, CoreRocks rock){
        String textureBase = getItemModelString(block.getId()).replace("mossy_", ""); //stuff like this is why this datagen isn't my proudest work
        String textureMossy = (TerraFirmaCraft.MOD_ID + ":item/loose_rock/moss_" + rock.displayCategory().category().getSerializedName()).replace("sedimentary", "sedementary");
        this.getBuilder(getItemModelString(block.getId())).parent(new ModelFile.UncheckedModelFile("item/generated")).texture("layer0", textureBase).texture("layer1", textureMossy);
    }

    private String getItemModelString(ResourceLocation block){
        return block.getNamespace() + ":item/" + block.getPath();
    }

    private ResourceLocation getItemModelLocation(ResourceLocation block){
        return ResourceLocation.fromNamespaceAndPath(block.getNamespace(), "item/" + block.getPath());
    }

    private String getItemModelString(Item item){
        return item.toString().replace(":", ":item/");
    }

    private ResourceLocation getBlockModelLocation(ResourceLocation block){
        return ResourceLocation.fromNamespaceAndPath(block.getNamespace(), "block/" + block.getPath());
    }

    private String getBlockModelString(ResourceLocation block){
        return block.getNamespace() + ":block/" + block.getPath();
    }
}
