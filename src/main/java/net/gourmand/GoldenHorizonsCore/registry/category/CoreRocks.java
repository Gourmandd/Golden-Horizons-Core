package net.gourmand.GoldenHorizonsCore.registry.category;

import java.util.Locale;
import java.util.function.Supplier;

import net.dries007.tfc.common.blocks.rock.Rock;
import net.dries007.tfc.common.blocks.rock.RockDisplayCategory;
import net.gourmand.GoldenHorizonsCore.registry.CoreBlocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.MapColor;

import net.dries007.tfc.common.Lore;
import net.dries007.tfc.util.registry.RegistryRock;

public enum CoreRocks implements RegistryRock
{
    ARGILLITE(RockDisplayCategory.SEDIMENTARY, MapColor.STONE, false, false), //vanilla cobble
    NEPHELINITE(RockDisplayCategory.MAFIC_IGNEOUS_EXTRUSIVE, MapColor.DEEPSLATE, false, false), //deepslate
    BLACKSLAG(RockDisplayCategory.FELSIC_IGNEOUS_EXTRUSIVE, MapColor.TERRACOTTA_BLACK, false, false),
    PICRITE_BASALT(RockDisplayCategory.MAFIC_IGNEOUS_EXTRUSIVE, MapColor.TERRACOTTA_LIGHT_GRAY, false, true), // Pastel's basal marble
    TRAVERTINE(RockDisplayCategory.SEDIMENTARY, MapColor.TERRACOTTA_BROWN, false, true); // dripstone

    public static final CoreRocks[] VALUES = values();

    private final String serializedName;
    private final RockDisplayCategory category;
    private final MapColor color;
    private final boolean hasVariants;
    private final boolean hasCobble; //whether it has cobble from this mod, vanilla cobble can be used for argillite.

    CoreRocks(RockDisplayCategory category, MapColor color)
    {
        this.serializedName = name().toLowerCase(Locale.ROOT);
        this.category = category;
        this.color = color;
        this.hasVariants = true;
        this.hasCobble = true;
    }

    CoreRocks(RockDisplayCategory category, MapColor color, boolean hasVariants, boolean hasCobble)
    {
        this.serializedName = name().toLowerCase(Locale.ROOT);
        this.category = category;
        this.color = color;
        this.hasVariants = hasVariants;
        this.hasCobble = hasCobble;
    }

    public Item.Properties createItemProperties()
    {
        return new Item.Properties().component(Lore.TYPE, Lore.ROCK_DISPLAY_CATEGORIES.get(category));
    }

    @Override
    public RockDisplayCategory displayCategory()
    {
        return category;
    }

    @Override
    public MapColor color()
    {
        return color;
    }

    public boolean hasVariants()
    {
        return hasVariants;
    }

    public boolean hasCobble()
    {
        return hasCobble;
    }

    public boolean hasVariant(Rock.BlockType blockType){
        if (hasVariants){
            return true;
        }

        if (hasCobble && blockType == Rock.BlockType.COBBLE){
            return true;
        }

        switch (blockType){
            case RAW, BRICKS, SMOOTH, COBBLE, CRACKED_BRICKS, CHISELED, PRESSURE_PLATE, BUTTON  -> {
                return false;
            }
            case HARDENED, GRAVEL, SPIKE, MOSSY_BRICKS, MOSSY_COBBLE, MOSSY_LOOSE, LOOSE, AQUEDUCT -> {
                return true;
            }
        }
        return false;
    }

    @Override
    public Supplier<? extends Block> getBlock(Rock.BlockType type)
    {
        return CoreBlocks.ROCK_BLOCKS.get(this).get(type);
    }

    @Override
    public Supplier<? extends Block> getAnvil() {
        return null;
    }

    @Override
    public Supplier<? extends SlabBlock> getSlab(Rock.BlockType type)
    {
        return CoreBlocks.ROCK_DECORATIONS.get(this).get(type).slab();
    }

    @Override
    public Supplier<? extends StairBlock> getStair(Rock.BlockType type)
    {
        return CoreBlocks.ROCK_DECORATIONS.get(this).get(type).stair();
    }

    @Override
    public Supplier<? extends WallBlock> getWall(Rock.BlockType type)
    {
        return CoreBlocks.ROCK_DECORATIONS.get(this).get(type).wall();
    }

    @Override
    public String getSerializedName()
    {
        return serializedName;
    }
}