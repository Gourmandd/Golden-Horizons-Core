package net.gourmand.core.datagen.providers;

import net.dries007.tfc.common.blocks.ShelfBlock;
import net.dries007.tfc.common.blocks.devices.SluiceBlock;
import net.dries007.tfc.common.blocks.rock.*;
import net.dries007.tfc.common.blocks.wood.*;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistryRock;
import net.gourmand.core.AncientGroundCore;
import net.gourmand.core.registry.CoreBlocks;
import net.gourmand.core.registry.category.CoreMetals;
import net.gourmand.core.registry.category.CoreOres;
import net.gourmand.core.registry.category.CorePastelWood;
import net.gourmand.core.registry.category.CoreRocks;
import net.gourmand.core.util.TextureUtil;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Map;
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

        Stream.of(CoreOres.values()).forEach(ore -> {
            if (!ore.hasBlock()){
                cubeAll(CoreBlocks.BASIC_ORES.get(ore), ResourceLocation.fromNamespaceAndPath(AncientGroundCore.MODID, "block/ore/" + ore.getSerializedName() ));
            }
        });

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

                if (type.hasVariants() && rock.hasVariant(type) && generateMossyVariant(type, rock)){
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

        // metal blocks
        Stream.of(CoreMetals.MetalType.values()).forEach(metal -> {
            ResourceLocation texture = TextureUtil.getMetalBlockTexture(metal);
            cubeAll(CoreBlocks.METALS.get(metal).get(Metal.BlockType.BLOCK), texture);
            stairsBlock( CoreBlocks.METALS.get(metal).get(Metal.BlockType.BLOCK_STAIRS), texture);
            slabBlock(CoreBlocks.METALS.get(metal).get(Metal.BlockType.BLOCK_SLAB), texture, getBlockModelLocation(CoreBlocks.METALS.get(metal).get(Metal.BlockType.BLOCK).getId()));
        });

        // pastel wood types.
        Stream.of(CorePastelWood.values()).forEach(woodType -> {
            Map<Wood.BlockType, DeferredHolder<Block, Block>> map = CoreBlocks.DEEPER_DOWN_WOODS.get(woodType);
            supportBlock(map.get(Wood.BlockType.HORIZONTAL_SUPPORT), map.get(Wood.BlockType.VERTICAL_SUPPORT), woodType);
            twigBlock(map.get(Wood.BlockType.TWIG), woodType);
            scribingTableBlock(map.get(Wood.BlockType.SCRIBING_TABLE), woodType);
            sewingTableBlock(map.get(Wood.BlockType.SEWING_TABLE), woodType);
            loomBlock(map.get(Wood.BlockType.LOOM), woodType);
            shelfBlock(map.get(Wood.BlockType.SHELF), woodType);
            toolRackBlock(map.get(Wood.BlockType.TOOL_RACK), woodType);
            sluiceBlock(map.get(Wood.BlockType.SLUICE), woodType);
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

    private void stairsBlock(DeferredHolder<Block, ? extends Block> block, ResourceLocation texture){
        ModelFile stairs = this.models().stairs(getBlockModelString(block.getId()), texture, texture, texture);
        ModelFile stairsInner = this.models().stairsInner(getBlockModelString(block.getId()) + "_inner", texture, texture, texture);
        ModelFile stairsOuter = this.models().stairsOuter(getBlockModelString(block.getId()) + "_outer", texture, texture, texture);

        this.getVariantBuilder(block.get()).forAllStatesExcept((state) -> {
            Direction facing = state.getValue(StairBlock.FACING);
            Half half = state.getValue(StairBlock.HALF);
            StairsShape shape = state.getValue(StairBlock.SHAPE);
            int yRot = (int)facing.getClockWise().toYRot();
            if (shape == StairsShape.INNER_LEFT || shape == StairsShape.OUTER_LEFT) {
                yRot += 270;
            }

            if (shape != StairsShape.STRAIGHT && half == Half.TOP) {
                yRot += 90;
            }

            yRot %= 360;
            boolean uvlock = yRot != 0 || half == Half.TOP;
            return ConfiguredModel.builder().modelFile(shape == StairsShape.STRAIGHT ? stairs : (shape != StairsShape.INNER_LEFT && shape != StairsShape.INNER_RIGHT ? stairsOuter : stairsInner)).rotationX(half == Half.BOTTOM ? 0 : 180).rotationY(yRot).uvLock(uvlock).build();
        }, StairBlock.WATERLOGGED);
    }

    private void slabBlock(DeferredHolder<Block, ? extends Block> block, ResourceLocation texture, ResourceLocation doubleSlab){
        ModelFile slabBottom = this.models().slab(getBlockModelString(block.getId()), texture, texture, texture);
        ModelFile slabTop = this.models().slabTop(getBlockModelString(block.getId()) + "_top", texture, texture, texture);
        ModelFile slabDouble = this.models().getExistingFile(doubleSlab);

        this.getVariantBuilder(block.get())
                .partialState().with(SlabBlock.TYPE, SlabType.BOTTOM).addModels(new ConfiguredModel(slabBottom))
                .partialState().with(SlabBlock.TYPE, SlabType.TOP).addModels(new ConfiguredModel(slabTop))
                .partialState().with(SlabBlock.TYPE, SlabType.DOUBLE).addModels(new ConfiguredModel(slabDouble));
    }

    private void aqueductBlock(DeferredHolder<Block, Block> block, ResourceLocation texture){
        ModelFile modelBase = createModel(getBlockModelString(block.getId()) + "/base", aqueductBaseParent).texture("texture", texture).texture("particle", texture);
        ModelFile modelNorth = createModel(getBlockModelString(block.getId()) + "/north", aqueductNorthParent).texture("texture", texture).texture("particle", texture);
        ModelFile modelSouth = createModel(getBlockModelString(block.getId()) + "/south", aqueductSouthParent).texture("texture", texture).texture("particle", texture);
        ModelFile modelEast = createModel(getBlockModelString(block.getId()) + "/east", aqueductEastParent).texture("texture", texture).texture("particle", texture);
        ModelFile modelWest = createModel(getBlockModelString(block.getId()) + "/west", aqueductWestParent).texture("texture", texture).texture("particle", texture);

        MultiPartBlockStateBuilder builder = getMultipartBuilder(block.get());

        builder.part().modelFile(modelBase).addModel();
        builder.part().modelFile(modelNorth).addModel().condition(AqueductBlock.NORTH, false);
        builder.part().modelFile(modelSouth).addModel().condition(AqueductBlock.SOUTH, false);
        builder.part().modelFile(modelEast).addModel().condition(AqueductBlock.EAST, false);
        builder.part().modelFile(modelWest).addModel().condition(AqueductBlock.WEST, false);
    }

    private void rockSpikeBlock(DeferredHolder<Block, Block> block, ResourceLocation texture){
        ModelFile modelBase = createModel(getBlockModelString(block.getId()) + "_base", "tfc:block/rock/spike_base").texture("texture", texture).texture("particle", texture);
        ModelFile modelMiddle = createModel(getBlockModelString(block.getId()) + "_middle", "tfc:block/rock/spike_middle").texture("texture", texture).texture("particle", texture);
        ModelFile modelTip = createModel(getBlockModelString(block.getId()) + "_tip", "tfc:block/rock/spike_tip").texture("texture", texture).texture("particle", texture);

        VariantBlockStateBuilder builder = getVariantBuilder(block.get());

        builder
                .partialState().with(RockSpikeBlock.PART, RockSpikeBlock.Part.BASE).modelForState().modelFile(modelBase).addModel()
                .partialState().with(RockSpikeBlock.PART, RockSpikeBlock.Part.MIDDLE).modelForState().modelFile(modelMiddle).addModel()
                .partialState().with(RockSpikeBlock.PART, RockSpikeBlock.Part.TIP).modelForState().modelFile(modelTip).addModel();
    }

    private void looseRockBlock(DeferredHolder<Block, Block> block, ResourceLocation texture, CoreRocks rock){
        ModelFile model1 = createModel(getBlockModelString(block.getId()) + "_1", getLooseRockModelParent(rock, 1)).texture("texture", texture).texture("particle", texture);
        ModelFile model2 = createModel(getBlockModelString(block.getId()) + "_2", getLooseRockModelParent(rock, 2)).texture("texture", texture).texture("particle", texture);
        ModelFile model3 = createModel(getBlockModelString(block.getId()) + "_3", getLooseRockModelParent(rock, 3)).texture("texture", texture).texture("particle", texture);

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

    private void supportBlock(DeferredHolder<Block, Block> Hblock, DeferredHolder<Block, Block> Vblock, CorePastelWood wood){

        ResourceLocation textureTop = TextureUtil.getStrippedLogTopTexture(wood);
        ResourceLocation texture = TextureUtil.getStrippedLogTexture(wood);

        ModelFile modelHorizontal = createModel(getBlockModelString(Hblock.getId()) + "_horizontal", "tfc:block/wood/support/horizontal_vex").texture("top", textureTop).texture("texture", texture).texture("particle", texture);
        ModelFile modelConnection = createModel(getBlockModelString(Hblock.getId()).replace("_horizontal", "") + "_connection", "tfc:block/wood/support/connection_vex").texture("top", textureTop).texture("texture", texture).texture("particle", texture);
        ModelFile modelVertical = createModel(getBlockModelString(Vblock.getId()) + "_vertical", "tfc:block/wood/support/vertical_vex").texture("top", textureTop).texture("texture", texture).texture("particle", texture);

        // generated but reference never used?
        //ModelFile modelInventory = createModel(getBlockModelString(block.getId()) + "_inventory", aqueductNorthParent).texture("top", textureTop).texture("vertical", texture).texture("horizontal", texture).texture("particle", texture);

        // horizontal
        MultiPartBlockStateBuilder Hbuilder = getMultipartBuilder(Hblock.get());

        Hbuilder.part().modelFile(modelHorizontal).addModel();
        Hbuilder.part().modelFile(modelConnection).rotationY(270).addModel().condition(AqueductBlock.NORTH, true);
        Hbuilder.part().modelFile(modelConnection).rotationY(90).addModel().condition(AqueductBlock.SOUTH, true);
        Hbuilder.part().modelFile(modelConnection).addModel().condition(AqueductBlock.EAST, true);
        Hbuilder.part().modelFile(modelConnection).rotationY(180).addModel().condition(AqueductBlock.WEST, true);

        // vertical
        MultiPartBlockStateBuilder Vbuilder = getMultipartBuilder(Vblock.get());

        Vbuilder.part().modelFile(modelVertical).addModel();
        Vbuilder.part().modelFile(modelConnection).rotationY(270).addModel().condition(AqueductBlock.NORTH, true);
        Vbuilder.part().modelFile(modelConnection).rotationY(90).addModel().condition(AqueductBlock.SOUTH, true);
        Vbuilder.part().modelFile(modelConnection).addModel().condition(AqueductBlock.EAST, true);
        Vbuilder.part().modelFile(modelConnection).rotationY(180).addModel().condition(AqueductBlock.WEST, true);
    }

    private void twigBlock(DeferredHolder<Block, Block> block, CorePastelWood wood){

        ResourceLocation textureTop = TextureUtil.getLogTopTexture(wood);
        ResourceLocation texture = TextureUtil.getLogTexture(wood);

        ModelFile model = createModel(getBlockModelString(block.getId()), "tfc:block/groundcover/twig").texture("top", textureTop).texture("side", texture).texture("particle", texture);
        ModelFile model45 = createModel(getBlockModelString(block.getId()) + "_45", "tfc:block/groundcover/twig_45").texture("top", textureTop).texture("side", texture).texture("particle", texture);

        VariantBlockStateBuilder builder = this.getVariantBuilder(block.get());

        builder.partialState().modelForState()
                .modelFile(model).rotationY(0).nextModel()
                .modelFile(model).rotationY(90).nextModel()
                .modelFile(model).rotationY(180).nextModel()
                .modelFile(model).rotationY(270).nextModel()
                .modelFile(model45).rotationY(0).nextModel()
                .modelFile(model45).rotationY(90).nextModel()
                .modelFile(model45).rotationY(180).nextModel()
                .modelFile(model45).rotationY(270).addModel();
    }

    private void scribingTableBlock(DeferredHolder<Block, Block> block, CorePastelWood wood){

        ResourceLocation texturePlank = TextureUtil.getPlanksTexture(wood);
        ResourceLocation textureSmooth = TextureUtil.getStrippedLogTexture(wood);

        ModelFile model = createModel(getBlockModelString(block.getId()), "tfc:block/scribing_table").texture("sheet", textureSmooth).texture("planks", texturePlank).texture("particle", texturePlank);

        VariantBlockStateBuilder builder = this.getVariantBuilder(block.get());

        builder
                .partialState().with(ScribingTableBlock.FACING, Direction.NORTH).modelForState().modelFile(model).rotationY(0).addModel()
                .partialState().with(ScribingTableBlock.FACING, Direction.EAST).modelForState().modelFile(model).rotationY(90).addModel()
                .partialState().with(ScribingTableBlock.FACING, Direction.SOUTH).modelForState().modelFile(model).rotationY(180).addModel()
                .partialState().with(ScribingTableBlock.FACING, Direction.WEST).modelForState().modelFile(model).rotationY(270).addModel();
    }

    private void sewingTableBlock(DeferredHolder<Block, Block> block, CorePastelWood wood){

        ResourceLocation texturePlank = TextureUtil.getPlanksTexture(wood);
        ResourceLocation textureSmooth = TextureUtil.getStrippedLogTexture(wood);

        ModelFile model = createModel(getBlockModelString(block.getId()), "tfc:block/sewing_table").texture("0", textureSmooth).texture("1", texturePlank).texture("particle", texturePlank);

        VariantBlockStateBuilder builder = this.getVariantBuilder(block.get());

        builder
                .partialState().with(SewingTableBlock.FACING, Direction.NORTH).modelForState().modelFile(model).rotationY(0).addModel()
                .partialState().with(SewingTableBlock.FACING, Direction.EAST).modelForState().modelFile(model).rotationY(90).addModel()
                .partialState().with(SewingTableBlock.FACING, Direction.SOUTH).modelForState().modelFile(model).rotationY(180).addModel()
                .partialState().with(SewingTableBlock.FACING, Direction.WEST).modelForState().modelFile(model).rotationY(270).addModel();
    }

    private void shelfBlock(DeferredHolder<Block, Block> block, CorePastelWood wood){

        ResourceLocation texturePlank = TextureUtil.getPlanksTexture(wood);

        ModelFile model = createModel(getBlockModelString(block.getId()), "tfc:block/wood/shelf").texture("0", texturePlank).texture("particle", texturePlank);

        VariantBlockStateBuilder builder = this.getVariantBuilder(block.get());

        builder
                .partialState().with(ShelfBlock.FACING, Direction.NORTH).modelForState().modelFile(model).rotationY(0).addModel()
                .partialState().with(ShelfBlock.FACING, Direction.EAST).modelForState().modelFile(model).rotationY(90).addModel()
                .partialState().with(ShelfBlock.FACING, Direction.SOUTH).modelForState().modelFile(model).rotationY(180).addModel()
                .partialState().with(ShelfBlock.FACING, Direction.WEST).modelForState().modelFile(model).rotationY(270).addModel();
    }

    private void loomBlock(DeferredHolder<Block, Block> block, CorePastelWood wood){

        ResourceLocation texturePlank = TextureUtil.getPlanksTexture(wood);

        ModelFile model = createModel(getBlockModelString(block.getId()), "tfc:block/loom").texture("texture", texturePlank).texture("particle", texturePlank);

        VariantBlockStateBuilder builder = this.getVariantBuilder(block.get());

        builder
                .partialState().with(TFCLoomBlock.FACING, Direction.NORTH).modelForState().modelFile(model).rotationY(180).addModel()
                .partialState().with(TFCLoomBlock.FACING, Direction.EAST).modelForState().modelFile(model).rotationY(270).addModel()
                .partialState().with(TFCLoomBlock.FACING, Direction.SOUTH).modelForState().modelFile(model).rotationY(0).addModel()
                .partialState().with(TFCLoomBlock.FACING, Direction.WEST).modelForState().modelFile(model).rotationY(90).addModel();
    }

    private void toolRackBlock(DeferredHolder<Block, Block> block, CorePastelWood wood){

        ResourceLocation texturePlank = TextureUtil.getStrippedLogTexture(wood); // some of the plank textures don't look great, the stripped logs look a bit better.

        ModelFile model = createModel(getBlockModelString(block.getId()), "tfc:block/tool_rack").texture("texture", texturePlank).texture("particle", texturePlank);

        VariantBlockStateBuilder builder = this.getVariantBuilder(block.get());

        builder
                .partialState().with(ToolRackBlock.FACING, Direction.NORTH).modelForState().modelFile(model).rotationY(180).addModel()
                .partialState().with(ToolRackBlock.FACING, Direction.EAST).modelForState().modelFile(model).rotationY(270).addModel()
                .partialState().with(ToolRackBlock.FACING, Direction.SOUTH).modelForState().modelFile(model).rotationY(0).addModel()
                .partialState().with(ToolRackBlock.FACING, Direction.WEST).modelForState().modelFile(model).rotationY(90).addModel();
    }

    private void sluiceBlock(DeferredHolder<Block, Block> block, CorePastelWood wood){

        ResourceLocation texture = TextureUtil.getStrippedLogTexture(wood);

        ModelFile modelUpper = createModel(getBlockModelString(block.getId()) + "_upper", "tfc:block/sluice_upper").texture("texture", texture).texture("particle", texture);
        ModelFile modelLower = createModel(getBlockModelString(block.getId()) + "_lower", "tfc:block/sluice_lower").texture("texture", texture).texture("particle", texture);

        VariantBlockStateBuilder builder = this.getVariantBuilder(block.get());

        builder
                .partialState().with(SluiceBlock.FACING, Direction.NORTH).with(SluiceBlock.UPPER, true).modelForState().modelFile(modelUpper).rotationY(0).addModel()
                .partialState().with(SluiceBlock.FACING, Direction.EAST).with(SluiceBlock.UPPER, true).modelForState().modelFile(modelUpper).rotationY(90).addModel()
                .partialState().with(SluiceBlock.FACING, Direction.SOUTH).with(SluiceBlock.UPPER, true).modelForState().modelFile(modelUpper).rotationY(180).addModel()
                .partialState().with(SluiceBlock.FACING, Direction.WEST).with(SluiceBlock.UPPER, true).modelForState().modelFile(modelUpper).rotationY(270).addModel()
                .partialState().with(SluiceBlock.FACING, Direction.NORTH).with(SluiceBlock.UPPER, false).modelForState().modelFile(modelLower).rotationY(0).addModel()
                .partialState().with(SluiceBlock.FACING, Direction.EAST).with(SluiceBlock.UPPER, false).modelForState().modelFile(modelLower).rotationY(90).addModel()
                .partialState().with(SluiceBlock.FACING, Direction.SOUTH).with(SluiceBlock.UPPER, false).modelForState().modelFile(modelLower).rotationY(180).addModel()
                .partialState().with(SluiceBlock.FACING, Direction.WEST).with(SluiceBlock.UPPER, false).modelForState().modelFile(modelLower).rotationY(270).addModel();
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

    private ModelBuilder<BlockModelBuilder> createModel(String name, ResourceLocation parent){
        return this.models().withExistingParent(name, parent);
    }

    private ModelBuilder<BlockModelBuilder> createModel(String name, String parent){
        return this.models().withExistingParent(name, parent);
    }

    private boolean generateMossyVariant(Rock.BlockType type, CoreRocks rock){
        if (type == Rock.BlockType.MOSSY_BRICKS){
            switch (rock){
                case BLACKSLAG, BRECCIA, KOMATIITE, TRAVERTINE, PICRITE_BASALT, NEPHELINITE -> {return false;}
                default -> {return true;}
            }
        }
        if (type == Rock.BlockType.MOSSY_COBBLE){
            switch (rock){
                case BLACKSLAG, NEPHELINITE -> {return false;}
                default -> {return true;}
            }
        }
        return true;
    }
}
