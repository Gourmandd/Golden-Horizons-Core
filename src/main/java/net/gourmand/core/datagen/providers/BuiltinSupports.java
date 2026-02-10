package net.gourmand.core.datagen.providers;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import net.gourmand.core.registry.CoreBlocks;
import net.gourmand.core.registry.category.CorePastelWood;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.recipes.ingredients.BlockIngredient;
import net.dries007.tfc.util.data.Support;

public class BuiltinSupports extends DataManagerProvider<Support>
{
    public BuiltinSupports(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup)
    {
        super(Support.MANAGER, output, lookup);
    }

    @Override
    protected void addData(HolderLookup.Provider provider)
    {
        add("horizontal_support_beam", new Support(BlockIngredient.of(
                Arrays.stream(CorePastelWood.values()).map(wood ->
                        CoreBlocks.DEEPER_DOWN_WOODS.get(wood).get(Wood.BlockType.HORIZONTAL_SUPPORT).get())),
                2, 2, 4
        ));
    }
}