package net.gourmand.core.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.data.FluidHeat;

public interface Accessors
{

    default Item itemOf(ResourceLocation name)
    {
        assert BuiltInRegistries.ITEM.containsKey(name) : "No item '" + name + "'";
        return BuiltInRegistries.ITEM.get(name);
    }

    default String nameOf(ItemLike item)
    {
        assert item.asItem() != Items.AIR : "Should never get name of Items.AIR";
        assert item.asItem() != Items.BARRIER : "Should never get name of Items.BARRIER";
        return BuiltInRegistries.ITEM.getKey(item.asItem()).getPath();
    }

    default int units(Metal.ItemType type)
    {
        return switch (type)
        {
            case ROD -> 50;
            default -> 100;
            case DOUBLE_INGOT, SHEET, FISH_HOOK, FISHING_ROD, SWORD, SWORD_BLADE, MACE, MACE_HEAD, SHEARS, UNFINISHED_BOOTS -> 200;
            case DOUBLE_SHEET, TUYERE, UNFINISHED_HELMET, UNFINISHED_CHESTPLATE, UNFINISHED_GREAVES, SHIELD, BOOTS -> 400;
            case HELMET, GREAVES -> 600;
            case CHESTPLATE -> 800;
            case HORSE_ARMOR -> 1200;
        };
    }

    default int units(Metal.BlockType type)
    {
        return switch (type)
        {
            case ANVIL -> 1400;
            case BLOCK, EXPOSED_BLOCK, WEATHERED_BLOCK, OXIDIZED_BLOCK, LAMP, GRATE, EXPOSED_GRATE, WEATHERED_GRATE, OXIDIZED_GRATE -> 100;
            case BLOCK_SLAB, EXPOSED_BLOCK_SLAB, WEATHERED_BLOCK_SLAB, OXIDIZED_BLOCK_SLAB -> 50;
            case BLOCK_STAIRS, EXPOSED_BLOCK_STAIRS, WEATHERED_BLOCK_STAIRS, OXIDIZED_BLOCK_STAIRS -> 75;
            case BARS -> 25;
            case CHAIN -> 6;
            case TRAPDOOR -> 200;
        };
    }

    default float temperatureOf(Metal metal)
    {
        return FluidHeat.MANAGER.getOrThrow(Helpers.identifier(metal.getSerializedName())).meltTemperature();
    }
}