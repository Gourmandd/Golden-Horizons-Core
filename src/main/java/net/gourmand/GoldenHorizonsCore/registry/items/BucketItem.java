package net.gourmand.GoldenHorizonsCore.registry.items;

import net.dries007.tfc.common.items.FluidContainerItem;
import net.dries007.tfc.config.TFCConfig;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Supplier;

public class BucketItem extends FluidContainerItem {

    public BucketItem(Properties properties, TagKey<Fluid> whitelist, boolean canPlaceLiquidsInWorld, Supplier<Boolean> canPlaceSourceBlocks) {
        super(properties, TFCConfig.SERVER.metalBucketCapacity, whitelist, canPlaceLiquidsInWorld, canPlaceSourceBlocks);
    }
}
