package net.gourmand.core.datagen.providers;

import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.gourmand.core.AncientGroundCore;
import net.gourmand.core.registry.CoreBlocks;
import net.gourmand.core.registry.category.CoreOres;
import net.gourmand.core.registry.category.CoreRocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
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
                    simpleOre(CoreBlocks.ORES.get(rock).get(ore));
                }

                Stream.of(CoreOres.Grade.values()).forEach(grade -> {
                    if (ore.isGraded()){
                        simpleOre(CoreBlocks.GRADED_ORES.get(rock).get(ore).get(grade));
                    }
                });
            });

        });

        Stream.of(CoreRocks.values()).forEach(rock -> {
            Stream.of(CoreOres.values()).forEach(ore -> {
                if (!ore.isGraded() && ore.hasBlock()) {
                    simpleOre(CoreBlocks.CUSTOM_ROCK_ORES.get(rock).get(ore));
                }

                Stream.of(CoreOres.Grade.values()).forEach(grade -> {
                    if (ore.isGraded()) {
                        simpleOre(CoreBlocks.CUSTOM_ROCK_GRADED_ORES.get(rock).get(ore).get(grade));
                    }
                });
            });

            Stream.of(Ore.values()).forEach(ore -> {
                if (!ore.isGraded() && ore.hasBlock()) {
                    simpleOre(CoreBlocks.CUSTOM_ROCK_TFC_ORES.get(rock).get(ore));
                }

                Stream.of(CoreOres.Grade.values()).forEach(grade -> {
                    if (ore.isGraded()) {
                        simpleOre(CoreBlocks.CUSTOM_ROCK_TFC_GRADED_ORES.get(rock).get(ore).get(grade));
                    }
                });
            });
        });

    }

    private void simpleOre(DeferredHolder<Block, Block> block){
        withExistingParent(block.getId().getNamespace() + ":item/" + block.getId().getPath(), block.getId().getNamespace() + ":block/" + block.getId().getPath());
    }
}
