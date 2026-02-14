package net.gourmand.core.datagen.providers;

import net.dries007.tfc.common.blocks.rock.*;
import net.dries007.tfc.util.registry.RegistryRock;
import net.gourmand.core.AncientGroundCore;
import net.gourmand.core.registry.CoreBlocks;
import net.gourmand.core.registry.category.CoreOres;
import net.gourmand.core.registry.category.CoreRocks;
import net.gourmand.core.util.TextureUtil;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.stream.Stream;

public class BuiltinBlockStates extends BlockStateProvider {

    private final static ResourceLocation oreParent = ResourceLocation.fromNamespaceAndPath("tfc", "block/ore");
    private final static ResourceLocation aqueductBaseParent = ResourceLocation.fromNamespaceAndPath("tfc", "block/aqueduct/base");
    private final static ResourceLocation aqueductNorthParent = ResourceLocation.fromNamespaceAndPath("tfc", "block/aqueduct/north");
    private final static ResourceLocation aqueductSouthParent = ResourceLocation.fromNamespaceAndPath("tfc", "block/aqueduct/south");
    private final static ResourceLocation aqueductEastParent = ResourceLocation.fromNamespaceAndPath("tfc", "block/aqueduct/east");
    private final static ResourceLocation aqueductWestParent = ResourceLocation.fromNamespaceAndPath("tfc", "block/aqueduct/west");

    public BuiltinBlockStates(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, AncientGroundCore.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        // ores.
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

        // rock blocks.
        Stream.of(CoreRocks.values()).forEach(rock -> {
            Stream.of(Rock.BlockType.values()).forEach(type -> {

                if (type.hasVariants() && rock.hasVariant(type)){
                    ResourceLocation texture = TextureUtil.getRockTexture(rock, type);
                    cubeAll(CoreBlocks.ROCK_BLOCKS.get(rock).get(type), texture);
                    stairsBlock(CoreBlocks.ROCK_DECORATIONS.get(rock).get(type).stair(), texture);
                    slabBlock(CoreBlocks.ROCK_DECORATIONS.get(rock).get(type).slab(), texture, getBlockModelLocation(CoreBlocks.ROCK_BLOCKS.get(rock).get(type).getId()));
                    wallBlock(CoreBlocks.ROCK_DECORATIONS.get(rock).get(type).wall(), getBlockModelString(CoreBlocks.ROCK_BLOCKS.get(rock).get(type).getId()), texture);
                }


            });

            cubeAll(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.GRAVEL), TextureUtil.getRockTexture(rock ,Rock.BlockType.GRAVEL));
            cubeAll(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.HARDENED), TextureUtil.getRockTexture(rock ,Rock.BlockType.HARDENED));
            aqueductBlock(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.AQUEDUCT), TextureUtil.getRockTexture(rock, Rock.BlockType.AQUEDUCT));
            looseRockBlock(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE), TextureUtil.getRockTexture(rock, Rock.BlockType.LOOSE), rock);
            looseRockBlock(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.MOSSY_LOOSE), TextureUtil.getRockTexture(rock, Rock.BlockType.MOSSY_LOOSE), rock);
            rockSpikeBlock(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.SPIKE),  TextureUtil.getRockTexture(rock, Rock.BlockType.SPIKE));

            if (rock.hasVariants()){
                cubeAll(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.CHISELED), TextureUtil.getRockTexture(rock ,Rock.BlockType.CHISELED));
                pressurePlateBlock(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.PRESSURE_PLATE), TextureUtil.getRockTexture(rock, Rock.BlockType.PRESSURE_PLATE));
                buttonBlock(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.BUTTON), TextureUtil.getRockTexture(rock, Rock.BlockType.BUTTON));
            }
        });
    }



    private void simpleOre(DeferredHolder<Block, Block> block, RegistryRock rock, CoreOres ore){
        String allTexture = TextureUtil.getRawRockTexture(rock);
        String oreTexture = TextureUtil.getOreTexture(ore);
        simpleBlock(block.get(), ConfiguredModel.builder().modelFile(createOreModel(block.getId().getPath(), allTexture, oreTexture)).buildLast());
    }

    private void simpleOre(DeferredHolder<Block, Block> block, RegistryRock rock, CoreOres ore, CoreOres.Grade grade){
        String allTexture = TextureUtil.getRawRockTexture(rock);
        String oreTexture = TextureUtil.getOreTexture(ore, grade);
        simpleBlock(block.get(), ConfiguredModel.builder().modelFile(createOreModel(block.getId().getPath(), allTexture, oreTexture)).buildLast());
    }

    private void simpleOre(DeferredHolder<Block, Block> block, RegistryRock rock, Ore ore){
        String allTexture = TextureUtil.getRawRockTexture(rock);
        String oreTexture = TextureUtil.getOreTexture(ore);
        simpleBlock(block.get(), ConfiguredModel.builder().modelFile(createOreModel(block.getId().getPath(), allTexture, oreTexture)).buildLast());
    }

    private void simpleOre(DeferredHolder<Block, Block> block, RegistryRock rock, Ore ore, CoreOres.Grade grade){
        String allTexture = TextureUtil.getRawRockTexture(rock);
        String oreTexture = TextureUtil.getOreTexture(ore, grade);
        simpleBlock(block.get(), ConfiguredModel.builder().modelFile(createOreModel(block.getId().getPath(), allTexture, oreTexture)).buildLast());
    }

    private BlockModelBuilder createOreModel(String name, String allTexture, String oreTexture){
        return models().withExistingParent("block/" + name, oreParent).texture("all", allTexture).texture("overlay", oreTexture);
    }

    private void cubeAll(DeferredHolder<Block, Block> block, ResourceLocation texture){
        simpleBlock(block.get(), this.models().cubeAll(block.getId().getNamespace() + ":block/" + block.getId().getPath(), texture));
    }

    private void stairsBlock(DeferredHolder<Block, ? extends StairBlock> block, ResourceLocation texture){
        ModelFile stairs = this.models().stairs(getBlockModelString(block.getId()), texture, texture, texture);
        ModelFile stairsInner = this.models().stairsInner(getBlockModelString(block.getId()) + "_inner", texture, texture, texture);
        ModelFile stairsOuter = this.models().stairsOuter(getBlockModelString(block.getId()) + "_outer", texture, texture, texture);
        stairsBlock(block.get(), stairs, stairsInner, stairsOuter);
    }

    private void slabBlock(DeferredHolder<Block, ? extends SlabBlock> block, ResourceLocation texture, ResourceLocation doubleSlab){
        ModelFile slabBottom = this.models().slab(getBlockModelString(block.getId()), texture, texture, texture);
        ModelFile slabTop = this.models().slabTop(getBlockModelString(block.getId()) + "_top", texture, texture, texture);
        ModelFile slabDouble = this.models().getExistingFile(doubleSlab);
        this.slabBlock(block.get(), slabBottom, slabTop, slabDouble);
    }

    private void aqueductBlock(DeferredHolder<Block, Block> block, ResourceLocation texture){
        ModelFile modelBase = this.models().withExistingParent(getBlockModelString(block.getId()) + "/base", aqueductBaseParent).texture("texture", texture).texture("particle", texture);
        ModelFile modelNorth = this.models().withExistingParent(getBlockModelString(block.getId()) + "/north", aqueductNorthParent).texture("texture", texture).texture("particle", texture);
        ModelFile modelSouth = this.models().withExistingParent(getBlockModelString(block.getId()) + "/south", aqueductSouthParent).texture("texture", texture).texture("particle", texture);
        ModelFile modelEast = this.models().withExistingParent(getBlockModelString(block.getId()) + "/east", aqueductEastParent).texture("texture", texture).texture("particle", texture);
        ModelFile modelWest = this.models().withExistingParent(getBlockModelString(block.getId()) + "/west", aqueductWestParent).texture("texture", texture).texture("particle", texture);

        MultiPartBlockStateBuilder builder = getMultipartBuilder(block.get());

        builder.part().modelFile(modelBase).addModel();
        builder.part().modelFile(modelNorth).addModel().condition(AqueductBlock.NORTH, false);
        builder.part().modelFile(modelSouth).addModel().condition(AqueductBlock.SOUTH, false);
        builder.part().modelFile(modelEast).addModel().condition(AqueductBlock.EAST, false);
        builder.part().modelFile(modelWest).addModel().condition(AqueductBlock.WEST, false);
    }

    private void rockSpikeBlock(DeferredHolder<Block, Block> block, ResourceLocation texture){
        ModelFile modelBase = this.models().withExistingParent(getBlockModelString(block.getId()) + "_base", "tfc:block/rock/spike_base").texture("texture", texture).texture("particle", texture);
        ModelFile modelMiddle = this.models().withExistingParent(getBlockModelString(block.getId()) + "_middle", "tfc:block/rock/spike_middle").texture("texture", texture).texture("particle", texture);
        ModelFile modelTip = this.models().withExistingParent(getBlockModelString(block.getId()) + "_tip", "tfc:block/rock/spike_tip").texture("texture", texture).texture("particle", texture);

        VariantBlockStateBuilder builder = getVariantBuilder(block.get());

        builder
                .partialState().with(RockSpikeBlock.PART, RockSpikeBlock.Part.BASE).modelForState().modelFile(modelBase).addModel()
                .partialState().with(RockSpikeBlock.PART, RockSpikeBlock.Part.MIDDLE).modelForState().modelFile(modelMiddle).addModel()
                .partialState().with(RockSpikeBlock.PART, RockSpikeBlock.Part.TIP).modelForState().modelFile(modelTip).addModel();
    }

    private void looseRockBlock(DeferredHolder<Block, Block> block, ResourceLocation texture, CoreRocks rock){
        ModelFile model1 = this.models().withExistingParent(getBlockModelString(block.getId()) + "_1", getLooseRockModelParent(rock, 1)).texture("texture", texture).texture("particle", texture);
        ModelFile model2 = this.models().withExistingParent(getBlockModelString(block.getId()) + "_2", getLooseRockModelParent(rock, 2)).texture("texture", texture).texture("particle", texture);
        ModelFile model3 = this.models().withExistingParent(getBlockModelString(block.getId()) + "_3", getLooseRockModelParent(rock, 3)).texture("texture", texture).texture("particle", texture);

        VariantBlockStateBuilder builder = getVariantBuilder(block.get());

            builder
                    .partialState().with(LooseRockBlock.COUNT, 1).modelForState()
                            .modelFile(model1).rotationY(0).nextModel()
                            .modelFile(model1).rotationY(90).nextModel()
                            .modelFile(model1).rotationY(180).nextModel()
                            .modelFile(model1).rotationY(270).addModel()
                    .partialState().with(LooseRockBlock.COUNT, 2).modelForState()
                            .modelFile(model2).rotationY(0).nextModel()
                            .modelFile(model2).rotationY(90).nextModel()
                            .modelFile(model2).rotationY(180).nextModel()
                            .modelFile(model2).rotationY(270).addModel()
                    .partialState().with(LooseRockBlock.COUNT, 3).modelForState()
                            .modelFile(model3).rotationY(0).nextModel()
                            .modelFile(model3).rotationY(90).nextModel()
                            .modelFile(model3).rotationY(180).nextModel()
                            .modelFile(model3).rotationY(270).addModel();
    }

    private void pressurePlateBlock(DeferredHolder<Block, Block> block, ResourceLocation texture){
        ModelFile pressurePlate = this.models().pressurePlate(getBlockModelString(block.getId()), texture);
        ModelFile pressurePlateDown = this.models().pressurePlateDown(getBlockModelString(block.getId()) + "_down", texture);

        this.getVariantBuilder(block.get()).partialState()
                .with(PressurePlateBlock.POWERED, true).addModels(new ConfiguredModel(pressurePlateDown)).partialState()
                .with(PressurePlateBlock.POWERED, false).addModels(new ConfiguredModel(pressurePlate));
    }

    private void buttonBlock(DeferredHolder<Block, Block> block, ResourceLocation texture){
        ModelFile button = this.models().button(getBlockModelString(block.getId()), texture);
        ModelFile buttonPressed = this.models().buttonPressed(getBlockModelString(block.getId()) + "_pressed", texture);

        this.getVariantBuilder(block.get()).forAllStates((state) -> {
            Direction facing = state.getValue(ButtonBlock.FACING);
            AttachFace face = state.getValue(ButtonBlock.FACE);
            boolean powered = state.getValue(ButtonBlock.POWERED);
            return ConfiguredModel.builder()
                    .modelFile(powered ? buttonPressed : button)
                    .rotationX(face == AttachFace.FLOOR ? 0 : (face == AttachFace.WALL ? 90 : 180))
                    .rotationY((int) (face == AttachFace.CEILING ? facing : facing.getOpposite()).toYRot())
                    .uvLock(face == AttachFace.WALL).build();
        });
    }

    private void wallBlock(DeferredHolder<Block, ? extends WallBlock> block, String baseName, ResourceLocation texture){
        this.wallBlock(block.get(), baseName, texture);
    }

    private String getBlockModelString(ResourceLocation block){
        return block.getNamespace() + ":block/" + block.getPath();
    }

    private String getLooseRockModelParent(CoreRocks rock, int count){
        RockCategory category = rock.displayCategory().category();

        switch (category){
            case METAMORPHIC -> {
                return "tfc:block/rock/loose_metamorphic_" + count;
            }
            case SEDIMENTARY -> {
                return "tfc:block/rock/loose_sedimentary_" + count;
            }
            case IGNEOUS_EXTRUSIVE -> {
                return "tfc:block/rock/loose_igneous_extrusive_" + count;
            }
            case IGNEOUS_INTRUSIVE -> {
                return "tfc:block/rock/loose_igneous_intrusive_" + count;
            }
            default -> throw new AssertionError("No category found for rock: " + rock.getSerializedName());
        }

    }

    private ResourceLocation getBlockModelLocation(ResourceLocation block){
        return ResourceLocation.fromNamespaceAndPath(block.getNamespace(), "block/" + block.getPath());
    }
}
