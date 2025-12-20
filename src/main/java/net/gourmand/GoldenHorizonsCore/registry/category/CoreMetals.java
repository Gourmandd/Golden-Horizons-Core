/*
 * Licensed under the EUPL, Version 1.2.
 * You may obtain a copy of the Licence at:
 * https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 */

package net.gourmand.GoldenHorizonsCore.registry.category;

import java.util.Locale;
import java.util.Objects;

import net.dries007.tfc.common.TFCTiers;
import net.dries007.tfc.util.Metal;
import net.gourmand.GoldenHorizonsCore.registry.CoreBlocks;
import net.minecraft.core.Holder;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

import net.dries007.tfc.common.LevelTier;
import net.dries007.tfc.common.TFCArmorMaterials;
import net.dries007.tfc.util.registry.RegistryMetal;
import net.dries007.tfc.util.Metal.BlockType;


public class CoreMetals {

    public enum MetalType implements StringRepresentable, RegistryMetal {

        ALUMINIUM(0xFF486B72, MapColor.COLOR_LIGHT_BLUE, Rarity.UNCOMMON, -1),
        ALUMINIUM_BRONZE(0xFF486B72, MapColor.COLOR_LIGHT_BLUE, Rarity.UNCOMMON, -1),
        CAST_IRON_ALLOY(0xFF486B72, MapColor.COLOR_LIGHT_BLUE, Rarity.UNCOMMON, -1),
        HARDENED_STEEL(0xFF486B72, MapColor.COLOR_LIGHT_BLUE, Rarity.UNCOMMON, -1),
        NETHERSTEEL(0xFF486B72, MapColor.COLOR_LIGHT_BLUE, Rarity.UNCOMMON, -1),
        ELECTRUM(0xFF486B72, MapColor.COLOR_LIGHT_BLUE, Rarity.UNCOMMON, -1);

        // NETHERSTEEL(0xFF486B72, MapColor.COLOR_LIGHT_BLUE, Rarity.UNCOMMON, -1, TFCTiers.RED_STEEL, TFCArmorMaterials.RED_STEEL, PartType.EQUIPMENT),
        private final String serializedName;
        private final LevelTier toolTier;
        private final TFCArmorMaterials.Id armorMaterial;
        private final MapColor mapColor;
        private final Rarity rarity;
        private final int color;
        private final float weathering;
        private final PartType partType;

        MetalType(int color, MapColor mapColor, Rarity rarity, float weathering)
        {
            this(color, mapColor, rarity, weathering, TFCTiers.COPPER, TFCArmorMaterials.COPPER, PartType.BASE);
        }

        MetalType(int color, MapColor mapColor, Rarity rarity, float weathering, LevelTier toolTier, TFCArmorMaterials.Id armorTier, PartType partType)
        {
            this.serializedName = name().toLowerCase(Locale.ROOT);
            this.toolTier = toolTier;
            this.armorMaterial = armorTier;
            this.rarity = rarity;
            this.mapColor = mapColor;
            this.color = color;
            this.weathering = weathering;
            this.partType = partType;
        }

        @Override
        public @NotNull LevelTier toolTier() {
            return toolTier;
        }

        @Override
        public @NotNull Holder<ArmorMaterial> armorMaterial() {
            return armorMaterial.holder();
        }

        @Override
        public int armorDurability(ArmorItem.Type type)
        {
            Objects.requireNonNull(armorMaterial, "Tried to get invalid armorMaterial to get its durability");
            return switch (type)
            {
                case HELMET -> armorMaterial.headDamage();
                case BODY, CHESTPLATE -> armorMaterial.chestDamage();
                case LEGGINGS -> armorMaterial.legDamage();
                case BOOTS -> armorMaterial.feetDamage();
            };
        }

        @Override
        public Block getBlock(BlockType blockType) {
            return CoreBlocks.METALS.get(this).get(blockType).get();
        }

        @Override
        public MapColor mapColor() {
            return mapColor;
        }

        public int getColor()
        {
            return color;
        }

        @Override
        public Rarity rarity() {
            return rarity;
        }

        @Override
        public float weatheringResistance() {
            return weathering;
        }

        @Override
        public String getSerializedName() {
            return serializedName;
        }

        public PartType getPartType() {return partType;}

        public Metal getLikeMetal(){

            if (this.partType == null){
                return Metal.BISMUTH;
            }

            return switch (this.partType)
            {
                case BASE -> Metal.BISMUTH;
                case EQUIPMENT -> Metal.RED_STEEL;
                case BASE_OXIDIZED -> Metal.ROSE_GOLD;
                case EQUIPMENT_OXIDIZED -> Metal.COPPER;
                default -> Metal.BISMUTH;
            };
        }
    }

    public enum PartType {
        BASE, // ingot, sheet, doubles, rod, plated blocks.
        EQUIPMENT, // BASE + tools, armour, anvil, grate, bars, chain, trapdoor, lantern.
        BASE_OXIDIZED,
        EQUIPMENT_OXIDIZED;
    }
}
