package net.gourmand.core.datagen.providers;

import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.util.registry.RegistryRock;
import net.gourmand.core.AncientGroundCore;
import net.gourmand.core.registry.CoreBlocks;
import net.gourmand.core.registry.category.CoreOres;
import net.gourmand.core.registry.category.CoreRocks;
import net.gourmand.core.util.TextureUtil;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.stream.Stream;

public class BuiltinBlockStates extends BlockStateProvider {

    private final static ResourceLocation oreParent = ResourceLocation.fromNamespaceAndPath("tfc", "block/ore");

    public BuiltinBlockStates(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, AncientGroundCore.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        Stream.of(Rock.values()).forEach(rock -> {
            Stream.of(CoreOres.values()).forEach(ore -> {
                if (!ore.isGraded() && ore.hasBlock()){
                    simpleOre(CoreBlocks.ORES.get(rock).get(ore), rock, ore);
                }

                Stream.of(CoreOres.Grade.values()).forEach(grade -> {
                    if (ore.isGraded()){
                        simpleOre(CoreBlocks.GRADED_ORES.get(rock).get(ore).get(grade), rock, ore, grade);
                    }
                });
            });

        });

        Stream.of(CoreRocks.values()).forEach(rock -> {
            Stream.of(CoreOres.values()).forEach(ore -> {
                if (!ore.isGraded() && ore.hasBlock()) {
                    simpleOre(CoreBlocks.CUSTOM_ROCK_ORES.get(rock).get(ore), rock, ore);
                }

                Stream.of(CoreOres.Grade.values()).forEach(grade -> {
                    if (ore.isGraded()) {
                        simpleOre(CoreBlocks.CUSTOM_ROCK_GRADED_ORES.get(rock).get(ore).get(grade), rock, ore, grade);
                    }
                });
            });

            Stream.of(Ore.values()).forEach(ore -> {
                if (!ore.isGraded() && ore.hasBlock()) {
                    simpleOre(CoreBlocks.CUSTOM_ROCK_TFC_ORES.get(rock).get(ore), rock, ore);
                }

                Stream.of(CoreOres.Grade.values()).forEach(grade -> {
                    if (ore.isGraded()) {
                        simpleOre(CoreBlocks.CUSTOM_ROCK_TFC_GRADED_ORES.get(rock).get(ore).get(grade), rock, ore, grade);
                    }
                });
            });
        });
    }



    private void simpleOre(DeferredHolder<Block, Block> block, RegistryRock rock, CoreOres ore){
        String allTexture = TextureUtil.getRawRockTexture(rock);
        String oreTexture = TextureUtil.getOreTexture(ore);
        simpleBlock(block.get(), ConfiguredModel.builder().modelFile(getOreModel(block.getId().getPath(), allTexture, oreTexture)).buildLast());
    }

    private void simpleOre(DeferredHolder<Block, Block> block, RegistryRock rock, CoreOres ore, CoreOres.Grade grade){
        String allTexture = TextureUtil.getRawRockTexture(rock);
        String oreTexture = TextureUtil.getOreTexture(ore, grade);
        simpleBlock(block.get(), ConfiguredModel.builder().modelFile(getOreModel(block.getId().getPath(), allTexture, oreTexture)).buildLast());
    }

    private void simpleOre(DeferredHolder<Block, Block> block, RegistryRock rock, Ore ore){
        String allTexture = TextureUtil.getRawRockTexture(rock);
        String oreTexture = TextureUtil.getOreTexture(ore);
        simpleBlock(block.get(), ConfiguredModel.builder().modelFile(getOreModel(block.getId().getPath(), allTexture, oreTexture)).buildLast());
    }

    private void simpleOre(DeferredHolder<Block, Block> block, RegistryRock rock, Ore ore, CoreOres.Grade grade){
        String allTexture = TextureUtil.getRawRockTexture(rock);
        String oreTexture = TextureUtil.getOreTexture(ore, grade);
        simpleBlock(block.get(), ConfiguredModel.builder().modelFile(getOreModel(block.getId().getPath(), allTexture, oreTexture)).buildLast());
    }

    private BlockModelBuilder getOreModel(String name, String allTexture, String oreTexture){
        return models().withExistingParent("block/" + name, oreParent).texture("all", allTexture).texture("overlay", oreTexture);
    }
}
