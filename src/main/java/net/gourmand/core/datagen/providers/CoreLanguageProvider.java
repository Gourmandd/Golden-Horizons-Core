package net.gourmand.core.datagen.providers;

import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Metal;
import net.gourmand.core.AncientGroundCore;
import net.gourmand.core.registry.CoreBlocks;
import net.gourmand.core.registry.CoreItems;
import net.gourmand.core.registry.category.*;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.DyeColor;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.Locale;
import java.util.stream.Stream;

public class CoreLanguageProvider extends LanguageProvider {

    public CoreLanguageProvider(PackOutput output) {
        super(output, AncientGroundCore.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {

        Stream.of(CoreCrops.values()).forEach(crop -> {
            addItem(CoreItems.CROP_SEEDS.get(crop), getName(crop.name()) + " Seeds");
            addBlock(CoreBlocks.WILD_CROPS.get(crop), "Wild " + getName(crop.name()));
            addBlock(CoreBlocks.CROPS.get(crop), getName(crop.name()));
            addBlock(CoreBlocks.DEAD_CROPS.get(crop), "Dead " + getName(crop.name()));
        });

        Stream.of(CoreSpreadingBushes.values()).forEach(bush -> {
            addBlock(CoreBlocks.SPREADING_BUSHES.get(bush), getName(bush.name()) + " Bush");
        });

        Stream.of(CoreStationaryBushes.values()).forEach(bush -> {
            addBlock(CoreBlocks.STATIONARY_BUSHES.get(bush), getName(bush.name()) + " Bush");
        });

        Stream.of(CoreFruitTrees.values()).forEach(tree -> {
            addBlock(CoreBlocks.FRUIT_TREE_LEAVES.get(tree), getName(tree.name()) + " Leaves");
            addBlock(CoreBlocks.FRUIT_TREE_BRANCHES.get(tree), getName(tree.name()) + " Branch");
            addBlock(CoreBlocks.FRUIT_TREE_GROWING_BRANCHES.get(tree), getName(tree.name()) + " Growing Branch");
            addBlock(CoreBlocks.FRUIT_TREE_SAPLINGS.get(tree), getName(tree.name()) + " Sapling");
            addBlock(CoreBlocks.FRUIT_TREE_POTTED_SAPLINGS.get(tree), getName(tree.name()) + " Potted Sapling");
        });

        Stream.of(CoreClay.values()).forEach(clay -> {
            Stream.of(CoreClay.ItemType.values()).forEach(type -> {
                if (type.hasType(clay)){
                    if (type.getType() == CoreClay.ItemPartType.UNFIRED_MOLD){
                        addItem(CoreItems.CERAMICS.get(clay).get(type),  "Unfired " + getName(clay.name()) + " " + getName(type.name()) + " Mold");
                    } else {
                        addItem(CoreItems.CERAMICS.get(clay).get(type), getName(clay.name()) + " " + getName(type.name()));
                    }
                }
            });
            add("item." + AncientGroundCore.MODID + ".ceramic." + clay.getSerializedName() + ".jug.filled", "%s " + getName(clay.name()) + " Jug");
        });

        Stream.of(CoreRocks.values()).forEach(rock -> {
            Stream.of(Rock.BlockType.values()).forEach(type -> {
                if (rock.hasVariant(type)){
                    if (type.hasVariants()){
                        if (isRockTypePrefixed(type)){
                            addBlock(CoreBlocks.ROCK_DECORATIONS.get(rock).get(type).stair(), getName(type.name()) + " " + getName(rock.name()) + " Stairs");
                            addBlock(CoreBlocks.ROCK_DECORATIONS.get(rock).get(type).slab(), getName(type.name()) + " " + getName(rock.name()) + " Slab");
                            addBlock(CoreBlocks.ROCK_DECORATIONS.get(rock).get(type).wall(), getName(type.name())+ " " + getName(rock.name()) + " Wall");
                        } else {
                            addBlock(CoreBlocks.ROCK_DECORATIONS.get(rock).get(type).stair(), getName(rock.name()) + " " + getName(type.name()) + " Stairs");
                            addBlock(CoreBlocks.ROCK_DECORATIONS.get(rock).get(type).slab(), getName(rock.name()) + " " + getName(type.name()) + " Slab");
                            addBlock(CoreBlocks.ROCK_DECORATIONS.get(rock).get(type).wall(), getName(rock.name()) + " " + getName(type.name()) + " Wall");
                        }
                    }

                    if (isRockTypePrefixed(type)){
                        addBlock(CoreBlocks.ROCK_BLOCKS.get(rock).get(type),  getName(type.name()) + " " + getName(rock.name()));
                    } else {
                        addBlock(CoreBlocks.ROCK_BLOCKS.get(rock).get(type), getName(rock.name()) + " " + getName(type.name()));
                    }

                }
            });
            addItem(CoreItems.BRICKS.get(rock), getName(rock.name()) + "  Brick");
        });

        Stream.of(CorePastelWood.values()).forEach(wood -> {
            Stream.of(Wood.BlockType.values()).forEach(type -> {
                if (wood.hasBlockType(type)){
                    addBlock(CoreBlocks.DEEPER_DOWN_WOODS.get(wood).get(type), getName(wood.name()) + " " + getName(type.name()));
                }
            });
            addItem(CoreItems.LUMBER.get(wood), getName(wood.name()) + " Twig");
        });

        Stream.of(CoreMetals.MetalType.values()).forEach(metal -> {
            Stream.of(Metal.ItemType.values()).forEach(itemType -> {
                if (itemType.has(metal.getLikeMetal())){
                    addItem(CoreItems.METAL_ITEMS.get(metal).get(itemType), getName(metal.name()) + " " + getName(itemType.name()));
                }
            });

            Stream.of(Metal.BlockType.values()).forEach(blockType -> {
                if (blockType.has(metal.getLikeMetal())){
                    addBlock(CoreBlocks.METALS.get(metal).get(blockType), getName(metal.name()) + " " + getName(blockType.name()));
                }
            });

            add("metal." + AncientGroundCore.MODID + "." + metal.getSerializedName(), getName(metal.name()));
        });

        // TODO: find out if TFC still wants these as metal definitions don't exist anymore.
        Stream.of(DyeColor.values()).forEach(colour -> {
            add("metal." + AncientGroundCore.MODID + "." + colour.getSerializedName(), getName(colour.name()) + " Glass");
            add("fluid_type." + AncientGroundCore.MODID + "." + colour.getSerializedName(), "Molten " + getName(colour.name()) + " Glass");
        });
        add("metal." + AncientGroundCore.MODID + ".clear", "Clear Glass");
        add("fluid_type." + AncientGroundCore.MODID + ".clear", "Molten Clear Glass");

//        // Adds a translation with the given key and the given value.
//        add("translation.key.1", "Translation 1");
//
//        // Helpers are available for various common object types. Every helper has two variants: an add() variant
//        // for the object itself, and an addTypeHere() variant that accepts a supplier for the object.
//        // The different names for the supplier variants are required due to generic type erasure.
//        // All following examples assume the existence of the values as suppliers of the needed type.
//
//        // Adds a block translation.
//        add(MyBlocks.EXAMPLE_BLOCK.get(), "Example Block");
//        addBlock(MyBlocks.EXAMPLE_BLOCK, "Example Block");
//        // Adds an item translation.
//        add(MyItems.EXAMPLE_ITEM.get(), "Example Item");
//        addItem(MyItems.EXAMPLE_ITEM, "Example Item");
//        // Adds an item stack translation. This is mainly for items that have NBT-specific names.
//        add(MyItems.EXAMPLE_ITEM_STACK.get(), "Example Item");
//        addItemStack(MyItems.EXAMPLE_ITEM_STACK, "Example Item");
//        // Adds an entity type translation.
//        add(MyEntityTypes.EXAMPLE_ENTITY_TYPE.get(), "Example Entity");
//        addEntityType(MyEntityTypes.EXAMPLE_ENTITY_TYPE, "Example Entity");
//        // Adds an enchantment translation.
//        add(MyEnchantments.EXAMPLE_ENCHANTMENT.get(), "Example Enchantment");
//        addEnchantment(MyEnchantments.EXAMPLE_ENCHANTMENT, "Example Enchantment");
//        // Adds a mob effect translation.
//        add(MyMobEffects.EXAMPLE_MOB_EFFECT.get(), "Example Effect");
//        addEffect(MyMobEffects.EXAMPLE_MOB_EFFECT, "Example Effect");
    }

    private boolean isRockTypePrefixed(Rock.BlockType type){
        return type == Rock.BlockType.LOOSE || type == Rock.BlockType.MOSSY_LOOSE || type == Rock.BlockType.CHISELED || type == Rock.BlockType.HARDENED || type == Rock.BlockType.SMOOTH || type == Rock.BlockType.RAW;
    }

    private String getName(String string){
        //TODO: this could be made more sane?
        final String[] new_string = {""};
        final int[] count = {0};

        Stream.of(string.toLowerCase(Locale.ROOT).split("_")).forEach(str -> {
            if (count[0] == 0){
                new_string[0] = str.substring(0, 1).toUpperCase() + str.substring(1);
            } else {
                new_string[0] = new_string[0] + " " + str.substring(0, 1).toUpperCase() + str.substring(1);
            }
            count[0] = count[0] + 1;
        });

        return new_string[0];
    }
}
