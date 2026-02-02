package net.gourmand.core.registry.category;

import net.gourmand.core.AncientGroundCore;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

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
}