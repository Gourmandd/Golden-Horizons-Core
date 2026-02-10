package net.gourmand.core.datagen.providers;

import earth.terrarium.pastel.registries.PastelBlocks;
import net.dries007.tfc.common.blocks.TFCBlockStateProperties;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Metal;
import net.gourmand.core.registry.CoreBlocks;
import net.gourmand.core.registry.CoreItems;
import net.gourmand.core.registry.category.*;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class BuiltinBlockLootTables extends BlockLootSubProvider {

    public BuiltinBlockLootTables(HolderLookup.Provider lookupProvider) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider);
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        // The contents of our DeferredRegister.
        return CoreBlocks.BLOCKS.getEntries()
                .stream()
                // Cast to Block here, otherwise it will be a ? extends Block and Java will complain.
                .map(e -> (Block) e.value())
                .toList();
    }

    // methods to call from generate()
    private void addOreTable(Block oreBlock, Item oreItem){
        this.add(oreBlock, LootTableBuilders.createOreTable(oreBlock, oreItem));
    }

    private void addCropTable(CoreCrops crop){

        switch (crop.getCropType()){
            case SINGLE -> {
                this.add(CoreBlocks.CROPS.get(crop).get(), LootTableBuilders.createSingleCropTable(crop, getCropProduct(crop)));
                this.add(CoreBlocks.DEAD_CROPS.get(crop).get(), LootTableBuilders.createDeadSingleCropTable(crop, getCropProduct(crop)));
                this.add(CoreBlocks.WILD_CROPS.get(crop).get(), LootTableBuilders.createWildSingleCropTable(crop, getCropProduct(crop)));
            }
            case DOUBLE -> {
                this.add(CoreBlocks.CROPS.get(crop).get(), LootTableBuilders.createDoubleCropTable(crop, getCropProduct(crop)));
                this.add(CoreBlocks.DEAD_CROPS.get(crop).get(), LootTableBuilders.createDeadDoubleCropTable(crop, getCropProduct(crop)));
                this.add(CoreBlocks.WILD_CROPS.get(crop).get(), LootTableBuilders.createWildDoubleCropTable(crop, getCropProduct(crop)));
            }
            case SPREADING -> {
                this.add(CoreBlocks.CROPS.get(crop).get(), LootTableBuilders.createSpreadingCropTable(crop));
                this.add(CoreBlocks.DEAD_CROPS.get(crop).get(), LootTableBuilders.createDeadSingleCropTable(crop, getCropProduct(crop)));
                this.add(CoreBlocks.WILD_CROPS.get(crop).get(), LootTableBuilders.createWildSpreadingCropTable(crop));
            }
        }
    }

    private void addFruitTreeTable(CoreFruitTrees tree){

        this.dropPottedContents(CoreBlocks.FRUIT_TREE_POTTED_SAPLINGS.get(tree).get());
        this.add(CoreBlocks.FRUIT_TREE_BRANCHES.get(tree).get(), LootTableBuilders.createBranchTable(tree));
        this.add(CoreBlocks.FRUIT_TREE_GROWING_BRANCHES.get(tree).get(), LootTableBuilders.createGrowingBranchTable());
        this.add(CoreBlocks.FRUIT_TREE_LEAVES.get(tree).get(), createFruitTreeLeavesTable(tree));
        this.add(CoreBlocks.FRUIT_TREE_SAPLINGS.get(tree).get(), LootTableBuilders.createFruitTreeSaplingTable(tree));
    }

    private void addStationaryBushTable(CoreStationaryBushes bush){
        this.add(CoreBlocks.STATIONARY_BUSHES.get(bush).get(), LootTableBuilders.createStationaryBushTable(bush));
    }

    private void addSpreadingBushTable(CoreSpreadingBushes bush){
        this.add(CoreBlocks.SPREADING_BUSHES.get(bush).get(), LootTableBuilders.createSpreadingBushTable(bush));
        this.add(CoreBlocks.SPREADING_CANES.get(bush).get(), LootTableBuilders.createSpreadingBushCaneTable(bush));
    }

    private void addRockBlockTable(CoreRocks rock, Rock.BlockType type){
      switch (type){
          case LOOSE, MOSSY_LOOSE -> {
              this.add(CoreBlocks.ROCK_BLOCKS.get(rock).get(type).get(), LootTableBuilders.createLooseRockDropTable(CoreBlocks.ROCK_BLOCKS.get(rock).get(type).get()));
          }
          case SPIKE -> {
              this.add(CoreBlocks.ROCK_BLOCKS.get(rock).get(type).get(), LootTableBuilders.createRockDropTable(CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE).get(), 1, 2));
          }
          case RAW, HARDENED -> {
              this.add(CoreBlocks.ROCK_BLOCKS.get(rock).get(type).get(), LootTableBuilders.createRawRockDropTable(
                      CoreRocks.getRawRock(rock),
                      CoreBlocks.ROCK_BLOCKS.get(rock).get(Rock.BlockType.LOOSE).get()
              ));
          }
          default -> {
              this.dropSelf(CoreBlocks.ROCK_BLOCKS.get(rock).get(type).get());
          }
      }
    }

    // methods for getting items and other data
    public static Item getCropProduct(CoreCrops crop)
    {
        switch(crop){
            case COFFEE -> {
                return BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("rusticdelight", "coffee_beans"));
            }
            case COTTON -> {
                return BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("rusticdelight", "cotton_boll"));
            }
            case GLISTERING_MELON -> {
                return Objects.requireNonNull(BuiltInRegistries.BLOCK.get(PastelBlocks.GLISTERING_MELON)).asItem();
            }
            case AMARANTH -> {
                return PastelBlocks.AMARANTH_BUSHEL.asItem();
            }
            case null, default -> {
                throw new AssertionError("no product for this crop type");
            }
        }
    }

    @Override
    protected void generate() {

        generateOre();
        generateCrop();
        generateMetal();
        generateRock();
        generateWood();
    }

    private void generateOre(){
        CoreBlocks.BASIC_ORES.values().forEach( holder -> {
            this.dropSelf(holder.get());
        });

        CoreBlocks.SMALL_ORES.values().forEach( holder -> {
            this.dropSelf(holder.get());
        });

        CoreBlocks.ORES.values().forEach( map -> {
            Stream.of(CoreOres.values()).forEach(ore -> {
                if (!ore.isGraded() && ore.hasBlock() && !ore.hasPastelOreType()){
                    addOreTable(map.get(ore).get(), CoreItems.ORES.get(ore).get());
                }

                if (!ore.isGraded() && ore.hasBlock() && ore.hasPastelOreType()){
                    addOreTable(map.get(ore).get(),  ore.getPastelOre());
                }
            });
        });

        Stream.of(CoreOres.Grade.values()).forEach(grade -> {
            CoreBlocks.GRADED_ORES.values().forEach( map -> {
                Stream.of(CoreOres.values()).forEach(ore -> {
                    if (ore.isGraded()){
                        addOreTable(map.get(ore).get(grade).get(), CoreItems.GRADED_ORES.get(ore).get(grade).get());
                    }
                });
            });
        });

        CoreBlocks.CUSTOM_ROCK_ORES.values().forEach( map -> {
            Stream.of(CoreOres.values()).forEach(ore -> {
                if (!ore.isGraded() && ore.hasBlock() && !ore.hasPastelOreType()) {
                    addOreTable(map.get(ore).get(), CoreItems.ORES.get(ore).get());
                }

                if (!ore.isGraded() && ore.hasBlock() && ore.hasPastelOreType()) {
                    addOreTable(map.get(ore).get(), ore.getPastelOre());
                }
            });
        });

        Stream.of(CoreOres.Grade.values()).forEach(grade -> {
            CoreBlocks.CUSTOM_ROCK_GRADED_ORES.values().forEach( map -> {
                Stream.of(CoreOres.values()).forEach(ore -> {
                    if (ore.isGraded()) {
                        addOreTable(map.get(ore).get(grade).get(), CoreItems.GRADED_ORES.get(ore).get(grade).get());
                    }
                });
            });
        });

        CoreBlocks.CUSTOM_ROCK_TFC_ORES.values().forEach( map -> {
            Stream.of(Ore.values()).forEach(ore -> {
                if (!ore.isGraded() && ore.hasBlock()) {
                    addOreTable(map.get(ore).get(), TFCItems.ORES.get(ore).get());
                }
            });
        });

        Stream.of(CoreOres.Grade.values()).forEach(grade -> {
            CoreBlocks.CUSTOM_ROCK_TFC_GRADED_ORES.values().forEach( map -> {
                Stream.of(Ore.values()).forEach(ore -> {
                    if (ore.isGraded()) {
                        addOreTable(map.get(ore).get(grade).get(), TFCItems.GRADED_ORES.get(ore).get(CoreOres.getTFCgrade(grade)).get());
                    }
                });
            });
        });
    }

    private void generateCrop(){

        Stream.of(CoreCrops.values()).forEach(this::addCropTable);

        Stream.of(CoreFruitTrees.values()).forEach(this::addFruitTreeTable);

        Stream.of(CoreStationaryBushes.values()).forEach(this::addStationaryBushTable);

        Stream.of(CoreSpreadingBushes.values()).forEach(this::addSpreadingBushTable);
    }

    private void generateMetal(){

        Stream.of(CoreMetals.MetalType.values()).forEach(metal -> {
            Stream.of(Metal.BlockType.values()).forEach(type -> {
                if (type.has(metal.getLikeMetal())){
                    this.add(CoreBlocks.METALS.get(metal).get(type).get(), this.createOreDrop(CoreBlocks.METALS.get(metal).get(type).get(), CoreBlocks.METALS.get(metal).get(type).get().asItem()));
                }
            });
        });
    }

    private void generateRock(){

        CoreBlocks.MAGMA_BLOCKS.values().forEach( holder -> {
            this.dropSelf(holder.get());
        });

        Stream.of(CoreRocks.values()).forEach(rock -> {
            Stream.of(Rock.BlockType.values()).forEach(type -> {
                if (rock.hasVariant(type)){
                    addRockBlockTable(rock, type);
                }
                if ((type.hasVariants() || type == Rock.BlockType.MOSSY_COBBLE || type == Rock.BlockType.MOSSY_BRICKS) && rock.hasVariant(type)){
                    this.dropSelf(CoreBlocks.ROCK_DECORATIONS.get(rock).get(type).slab().get());
                    this.dropSelf(CoreBlocks.ROCK_DECORATIONS.get(rock).get(type).stair().get());
                    this.dropSelf(CoreBlocks.ROCK_DECORATIONS.get(rock).get(type).wall().get());
                }
            });
        });
    }

    private void generateWood(){

        Stream.of(CorePastelWood.values()).forEach(wood -> {
            Stream.of(Wood.BlockType.values()).forEach( type -> {
                if (wood.hasBlockType(type)){
                    if (type == Wood.BlockType.SLUICE){
                        this.add(CoreBlocks.DEEPER_DOWN_WOODS.get(wood).get(type).get() ,LootTableBuilders.createSluiceTable(CoreBlocks.DEEPER_DOWN_WOODS.get(wood).get(type).get()));
                    } else {
                        this.dropSelf(CoreBlocks.DEEPER_DOWN_WOODS.get(wood).get(type).get());
                    }
                }
            });
        });
    }

    private LootTable.Builder createFruitTreeLeavesTable(CoreFruitTrees tree){
        return this.createSilkTouchOrShearsDispatchTable(CoreBlocks.FRUIT_TREE_LEAVES.get(tree).get(),
                this.applyExplosionCondition(CoreBlocks.FRUIT_TREE_LEAVES.get(tree).get(), LootItem.lootTableItem(Items.STICK)
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(CoreBlocks.FRUIT_TREE_LEAVES.get(tree).get())
                            .setProperties(StatePropertiesPredicate.Builder.properties()
                                .hasProperty(TFCBlockStateProperties.LIFECYCLE, Lifecycle.FRUITING))
                        )
                )
        );

    }
}
