package net.gourmand.core.registry.category;

import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.gourmand.core.AncientGroundCore;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import java.util.Locale;
import java.util.Map;

public class CoreTags {

    private static TagKey<Block> createBlockTag(String name){
        return TagKey.create(
                Registries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(AncientGroundCore.MODID, name)
        );
    }

    private static TagKey<Fluid> createFluidTag(String name){
        return TagKey.create(
                Registries.FLUID,
                ResourceLocation.fromNamespaceAndPath(AncientGroundCore.MODID, name)
        );
    }

    public static final TagKey<Block> SNOW_SHOVEL_MINEABLE = createBlockTag( "snow_shovel_mineable");
    public static final TagKey<Fluid> GLASS_MOLD_ACCEPTABLE = createFluidTag( "molten_glass");
    public static final TagKey<Fluid> WROUGHT_IRON_BUCKET_ACCEPTABLE = createFluidTag( "usable_in_wrought_iron_bucket");

    public class Items {
        private static TagKey<Item> tag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
        }

        private static TagKey<Item> tag(String namespace, String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(namespace, name));
        }

        public static final Map<CoreMetals.MetalType, TagKey<Item>> METAL_INGOTS = Helpers.mapOf(CoreMetals.MetalType.class, metal ->
            tag("ingots/" + metal.getSerializedName())
        );

        public static final Map<CoreMetals.MetalType, TagKey<Item>> METAL_DOUBLE_INGOTS = Helpers.mapOf(CoreMetals.MetalType.class, metal ->
                tag("double_ingots/" + metal.getSerializedName())
        );

        public static final Map<CoreMetals.MetalType, TagKey<Item>> METAL_SHEETS = Helpers.mapOf(CoreMetals.MetalType.class, metal ->
                tag("sheets/" + metal.getSerializedName())
        );

        public static final Map<CoreMetals.MetalType, TagKey<Item>> METAL_DOUBLE_SHEETS = Helpers.mapOf(CoreMetals.MetalType.class, metal ->
                tag("double_sheets/" + metal.getSerializedName())
        );

        public static final Map<CoreClay, TagKey<Item>> CLAY_RECYCLING_5 = Helpers.mapOf(CoreClay.class, clay ->
                tag(AncientGroundCore.MODID ,"clay/" + clay.getSerializedName() + "/recycling_5")
        );

        public static final Map<CoreClay, TagKey<Item>> CLAY_RECYCLING_1 = Helpers.mapOf(CoreClay.class, clay ->
                tag(AncientGroundCore.MODID ,"clay/" + clay.getSerializedName() + "/recycling_1")
        );

        public static final Map<Metal.ItemType, TagKey<Item>> TOOL_HEADS = Helpers.mapOf(Metal.ItemType.class, tool -> (tool.hasMold() && tool != Metal.ItemType.INGOT), tool ->
                tag(AncientGroundCore.MODID ,"tool_heads/" + tool.name().toLowerCase(Locale.ROOT))
        );

        public static final TagKey<Item> UNFIRED_VESSELS = tag(AncientGroundCore.MODID ,"unfired_vessels");

        public static final TagKey<Item> CLAY_BALLS = tag("clay_balls");
    }
}