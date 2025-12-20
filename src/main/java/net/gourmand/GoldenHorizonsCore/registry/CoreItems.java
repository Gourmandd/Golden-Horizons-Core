package net.gourmand.GoldenHorizonsCore.registry;

import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.gourmand.GoldenHorizonsCore.GoldenHorizonsCore;
import net.gourmand.GoldenHorizonsCore.registry.category.CoreCrops;
import net.gourmand.GoldenHorizonsCore.registry.category.CoreMetals;
import net.gourmand.GoldenHorizonsCore.registry.items.CoreSeedItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

public class CoreItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(GoldenHorizonsCore.MODID);

    public static final Map<CoreMetals.MetalType, Map<Metal.ItemType, DeferredHolder<Item, Item>>> METAL_ITEMS = Helpers.mapOf(CoreMetals.MetalType.class, metal ->
            Helpers.mapOf(Metal.ItemType.class, type -> type.has(metal.getLikeMetal()), type ->
                    register("metal/" + type.name() + "/" + metal.name(), () -> type.create(metal))
            )
    );

    public static final Map<CoreCrops, DeferredHolder<Item, Item>> CROP_SEEDS = Helpers.mapOf(CoreCrops.class, crop ->
            register("seeds/" + crop.name(), () -> new CoreSeedItem(crop, CoreBlocks.CROPS.get(crop).get(), new Item.Properties()))
    );

    /* Much easier with kjs for now.
    public static final Map<Metals.Metal,DeferredHolder<Item, Item>> METAL_FLUID_BUCKETS = Helpers.mapOf(Metals.Metal.class, metal ->
            register("bucket/metal/" + metal.name(), () -> new BucketItem(CoreFluids.METALS.get(metal).source().get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)))
    );
    */

    private static DeferredHolder<Item, Item> register(String name, Supplier<Item> item)
    {
        return ITEMS.register(name.toLowerCase(Locale.ROOT), item);
    }
}
