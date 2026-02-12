package net.gourmand.core.datagen.providers;

import net.gourmand.core.AncientGroundCore;
import net.gourmand.core.datagen.recipes.WeldingRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class BuiltinRecipes extends VanillaRecipeProvider implements WeldingRecipes {

    RecipeOutput output;
    HolderLookup.Provider lookup;

    public BuiltinRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        this.output = recipeOutput;

        weldingRecipes();
    }

    @Override
    public HolderLookup.Provider lookup() {
        return lookup;
    }

    @Override
    public void add(String prefix, String name, Recipe<?> recipe) {
        output.accept(ResourceLocation.fromNamespaceAndPath(AncientGroundCore.MODID, (prefix + "/" + name).toLowerCase(Locale.ROOT)), recipe, null);
    }
}
