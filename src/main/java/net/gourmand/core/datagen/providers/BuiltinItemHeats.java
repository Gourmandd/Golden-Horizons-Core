package net.gourmand.core.datagen.providers;

import net.dries007.tfc.common.component.heat.HeatCapability;
import net.dries007.tfc.common.component.heat.HeatDefinition;
import net.dries007.tfc.util.Metal;
import net.gourmand.core.datagen.Accessors;
import net.gourmand.core.registry.CoreBlocks;
import net.gourmand.core.registry.CoreItems;
import net.gourmand.core.registry.category.CategoryUtil;
import net.gourmand.core.registry.category.CoreClay;
import net.gourmand.core.registry.category.CoreMetals;
import net.gourmand.core.registry.category.CoreOres;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class BuiltinItemHeats extends DataManagerProvider<HeatDefinition> implements Accessors {

    private final CompletableFuture<?> lookup;

    public BuiltinItemHeats(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(HeatCapability.MANAGER, output, lookup);
        this.lookup = lookup;
    }

    @Override
    protected void addData(HolderLookup.Provider provider) {

        Stream.of(CoreMetals.MetalType.values()).forEach(type -> {
            add(CoreItems.METAL_ITEMS.get(type).get(Metal.ItemType.INGOT).get(), CategoryUtil.HeatCapacities.INGOT);
            add(CoreItems.METAL_ITEMS.get(type).get(Metal.ItemType.DOUBLE_INGOT).get(), CategoryUtil.HeatCapacities.DOUBLE_INGOT);
            add(CoreItems.METAL_ITEMS.get(type).get(Metal.ItemType.SHEET).get(), CategoryUtil.HeatCapacities.SHEET);
            add(CoreItems.METAL_ITEMS.get(type).get(Metal.ItemType.DOUBLE_SHEET).get(), CategoryUtil.HeatCapacities.DOUBLE_SHEET);
            add(CoreItems.METAL_ITEMS.get(type).get(Metal.ItemType.ROD).get(), CategoryUtil.HeatCapacities.ROD);

            add(CoreBlocks.METALS.get(type).get(Metal.BlockType.BLOCK).get(), CategoryUtil.HeatCapacities.INGOT);
            add(CoreBlocks.METALS.get(type).get(Metal.BlockType.BLOCK_SLAB).get(), CategoryUtil.HeatCapacities.INGOT);
            add(CoreBlocks.METALS.get(type).get(Metal.BlockType.BLOCK_STAIRS).get(), CategoryUtil.HeatCapacities.INGOT);
        });

        Stream.of(CoreClay.values()).forEach(type -> {
            add(CoreItems.CERAMICS.get(type).get(CoreClay.ItemType.UNFIRED_BRICK).get(), 0.4f);
            add(CoreItems.CERAMICS.get(type).get(CoreClay.ItemType.UNFIRED_FLOWER_POT).get(), 0.6f);
            add(CoreItems.CERAMICS.get(type).get(CoreClay.ItemType.UNFIRED_JUG).get(), 0.8f);
            add(CoreItems.CERAMICS.get(type).get(CoreClay.ItemType.UNFIRED_BOWL).get(), 0.4f);
            add(CoreItems.CERAMICS.get(type).get(CoreClay.ItemType.UNFIRED_POT).get(), 0.8f);
            add(CoreItems.CERAMICS.get(type).get(CoreClay.ItemType.UNFIRED_SPINDLE_HEAD).get(), 0.8f);
            add(CoreItems.CERAMICS.get(type).get(CoreClay.ItemType.UNFIRED_PAN).get(), 0.8f);
            add(CoreItems.CERAMICS.get(type).get(CoreClay.ItemType.UNFIRED_BLOWPIPE).get(), 0.6f);
        });

        Stream.of(CoreOres.values()).forEach(ore -> {
            if (ore.isGraded()){
                Stream.of(CoreOres.Grade.values()).forEach(grade -> {
                    add(CoreItems.GRADED_ORES.get(ore).get(grade).get(), 2.857143f, 138.0f, 184.0f);
                });
                add(CoreBlocks.SMALL_ORES.get(ore).get(), 2.857143f, 138.0f, 184.0f);
            }
        });
    }

    private void add(ItemLike item, float heatCapacity)
    {
        add(Ingredient.of(item), heatCapacity);
    }

    private void add(ItemLike item, float heatCapacity, float forgingTemperature, float weldingTemperature)
    {
        add(Ingredient.of(item), heatCapacity, forgingTemperature,  weldingTemperature);
    }

    private void add(Ingredient item, float heatCapacity)
    {
        add(nameOf(item), new HeatDefinition(item, heatCapacity, 0f, 0f));
    }

    private void add(Ingredient item, float heatCapacity, float forgingTemperature, float weldingTemperature)
    {
        add(nameOf(item), new HeatDefinition(item, heatCapacity, forgingTemperature, weldingTemperature));
    }



}
