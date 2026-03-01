

package net.gourmand.core.datagen;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import com.klikli_dev.modonomicon.api.datagen.LanguageProviderCache;
import com.klikli_dev.modonomicon.api.datagen.NeoBookProvider;
import net.gourmand.core.AncientGroundCore;
import net.gourmand.core.datagen.book.guide.GuideBook;
import net.gourmand.core.datagen.providers.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;



public final class DataEntryPoint
{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        final PackOutput output = event.getGenerator().getPackOutput();

        final var lookup = add(event, new DatapackBuiltinEntriesProvider(
                event.getGenerator().getPackOutput(), event.getLookupProvider(),
                new RegistrySetBuilder()
                , Set.of(AncientGroundCore.MODID, "minecraft"))).getRegistryProvider();

        add(event, new BuiltinRecipes(output, lookup));

        add(event, new BuiltinClimateRanges(output, lookup));
        add(event, new BuiltinBlockTags(event, lookup));
        add(event, new BuiltinItemTags(event, lookup));
        add(event, new BuiltinSupports(output, lookup));
        add(event, new BuiltinItemHeats(output, lookup));
        add(event, new BuiltinBlockStates(output, event.getExistingFileHelper()));
        add(event, new BuiltinItemModels(output, event.getExistingFileHelper()));

        addLoot(lookup, output, event, BuiltinBlockLootTables::new, LootContextParamSets.BLOCK);

        // book and lang.
        var enUsCache = new LanguageProviderCache("en_us");

        add(event, NeoBookProvider.of(event, new GuideBook(AncientGroundCore.MODID, enUsCache)));
        add(event, new CoreLanguageProvider(output, enUsCache));
    }

    private static <T extends DataProvider> T add(GatherDataEvent event, T provider)
    {
        return event.getGenerator().addProvider(true, provider);
    }

    private static void addLoot(CompletableFuture<HolderLookup.Provider> lookup, PackOutput output, GatherDataEvent event, Function<HolderLookup.Provider, LootTableSubProvider> provider, LootContextParamSet set )
    {
        add(event,
                new LootTableProvider(
                        output,
                        Collections.emptySet(),
                        List.of(
                                new LootTableProvider.SubProviderEntry(provider, set)
                        ),
                        lookup
                )
        );
    }
}