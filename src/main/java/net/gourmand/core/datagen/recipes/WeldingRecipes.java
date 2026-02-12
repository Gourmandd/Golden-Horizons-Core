package net.gourmand.core.datagen.recipes;

import net.dries007.tfc.common.recipes.WeldingRecipe;
import net.dries007.tfc.common.recipes.outputs.ItemStackProvider;
import net.gourmand.core.registry.CoreItems;
import net.gourmand.core.registry.category.CoreMetals;

import java.util.stream.Stream;
import static net.dries007.tfc.util.Metal.ItemType;
import static net.dries007.tfc.common.recipes.WeldingRecipe.Behavior.*;

public interface WeldingRecipes extends Recipes {

    default void weldingRecipes()
    {
        Stream.of(CoreMetals.MetalType.values()).forEach(metal -> {
            if (metal.getLikeMetal().defaultParts())
            {
                weld(metal, ItemType.INGOT, ItemType.INGOT, ItemType.DOUBLE_INGOT, IGNORE);
                weld(metal, ItemType.SHEET, ItemType.SHEET, ItemType.DOUBLE_SHEET, IGNORE);
            }
            if (metal.getLikeMetal().allParts())
            {
                weld(metal, ItemType.UNFINISHED_HELMET, ItemType.SHEET, ItemType.HELMET, COPY_BEST);
                weld(metal, ItemType.UNFINISHED_CHESTPLATE, ItemType.DOUBLE_SHEET, ItemType.CHESTPLATE, COPY_BEST);
                weld(metal, ItemType.UNFINISHED_GREAVES, ItemType.SHEET, ItemType.GREAVES, COPY_BEST);
                weld(metal, ItemType.UNFINISHED_BOOTS, ItemType.SHEET, ItemType.BOOTS, COPY_BEST);
                weld(metal, ItemType.KNIFE_BLADE, ItemType.KNIFE_BLADE, ItemType.SHEARS, COPY_WORST);
            }
        });
    }

    private void weld(CoreMetals.MetalType ingot1, CoreMetals.MetalType ingot2, CoreMetals.MetalType ingotOut)
    {
        add(new WeldingRecipe(
                ingredientOf(ingot1, ItemType.INGOT),
                ingredientOf(ingot2, ItemType.INGOT),
                ingotOut.tier() - 1,
                ItemStackProvider.of(CoreItems.METAL_ITEMS.get(ingotOut).get(ItemType.INGOT).get()),
                IGNORE
        ));
    }

    private void weld(CoreMetals.MetalType metal, ItemType input1, ItemType input2, ItemType output, WeldingRecipe.Behavior behavior)
    {
        add(new WeldingRecipe(
                ingredientOf(metal, input1),
                ingredientOf(metal, input2),
                metal.tier() - 1,
                ItemStackProvider.of(CoreItems.METAL_ITEMS.get(metal).get(output).get()),
                behavior
        ));
    }
}
