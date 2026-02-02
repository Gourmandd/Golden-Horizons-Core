package net.gourmand.core.registry.category;

import earth.terrarium.pastel.registries.PastelItems;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.plant.fruit.Lifecycle;
import net.dries007.tfc.common.blocks.plant.fruit.StationaryBerryBushBlock;
import net.gourmand.core.registry.CoreBlockEntities;
import net.gourmand.core.registry.items.BushBlockItem;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Locale;

import static net.dries007.tfc.common.blocks.plant.fruit.Lifecycle.*;

public enum CoreStationaryBushes implements StringRepresentable {

    SAWBLADE_HOLLY(PastelItems.SAWBLADE_HOLLY_BERRY, DORMANT, DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING, DORMANT, DORMANT, DORMANT),
    ALOE(PastelItems.ALOE_LEAF, DORMANT, DORMANT, DORMANT, DORMANT, HEALTHY, HEALTHY, HEALTHY, HEALTHY, HEALTHY, FLOWERING, FLOWERING, FRUITING);

    private final String serializedName;
    private final DeferredHolder<Item, Item> product;
    private final Lifecycle[] stages;

    CoreStationaryBushes(DeferredHolder<Item, Item> product, Lifecycle... stages)
    {
        this.serializedName = name().toLowerCase(Locale.ROOT);
        this.product = product;
        this.stages = stages;
    }

    public Block create()
    {
        return new StationaryBerryBushBlock(ExtendedProperties.of(MapColor.PLANT).strength(0.6f).noOcclusion().randomTicks().sound(SoundType.SWEET_BERRY_BUSH).blockEntity(CoreBlockEntities.BERRY_BUSH).flammableLikeLeaves(), product, stages, CoreClimateRanges.STATIONARY_BUSHES.get(this));
    }

    public BlockItem createItem(Block block)
    {
        return new BushBlockItem(block, CoreClimateRanges.STATIONARY_BUSHES.get(this), stages);
    }

    @Override
    public String getSerializedName() {
        return serializedName;
    }
}