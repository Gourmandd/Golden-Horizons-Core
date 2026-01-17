package net.gourmand.GoldenHorizonsCore.registry;

import net.dries007.tfc.common.TFCTiers;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.common.items.MoldItem;
import net.dries007.tfc.common.items.ToolItem;
import net.dries007.tfc.config.TFCConfig;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.gourmand.GoldenHorizonsCore.GoldenHorizonsCore;
import net.gourmand.GoldenHorizonsCore.registry.category.*;
import net.gourmand.GoldenHorizonsCore.registry.category.CorePastelWood;
import net.gourmand.GoldenHorizonsCore.registry.items.BucketItem;
import net.gourmand.GoldenHorizonsCore.registry.items.CoreSeedItem;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
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

    public static final Map<CoreOres, DeferredHolder<Item, Item>> ORES = Helpers.mapOf(CoreOres.class, ore -> !(ore.isGraded() || ore.hasPastelOreType()), type ->
            register("ore/" + type.name())
    );
    public static final Map<CoreOres, Map<CoreOres.Grade, DeferredHolder<Item, Item>>> GRADED_ORES = Helpers.mapOf(CoreOres.class, CoreOres::isGraded, ore ->
            Helpers.mapOf(CoreOres.Grade.class, grade ->
                    register("ore/" + grade.name() + '_' + ore.name())
            )
    );

    public static final Map<CoreOres, DeferredHolder<Item, Item>> GEMS = Helpers.mapOf(CoreOres.class, CoreOres::isGem, ore ->
            register("gem/" + ore.name())
    );

    public static final Map<CoreRocks,  DeferredHolder<Item, Item>> BRICKS = Helpers.mapOf(CoreRocks.class,type ->
            register("brick/" + type.name(), type.createItemProperties())
    );


    public static final Map<CorePastelWood, DeferredHolder<Item, Item>> LUMBER = Helpers.mapOf(CorePastelWood.class, wood -> register("wood/lumber/" + wood.name()));

    public static final Map<CorePastelWood, DeferredHolder<Item, Item>> SUPPORTS = Helpers.mapOf(CorePastelWood.class, wood ->
            register("wood/support/" + wood.name(), () -> new StandingAndWallBlockItem(CoreBlocks.DEEPER_DOWN_WOODS.get(wood).get(Wood.BlockType.VERTICAL_SUPPORT).get(), CoreBlocks.DEEPER_DOWN_WOODS.get(wood).get(Wood.BlockType.HORIZONTAL_SUPPORT).get(), new Item.Properties(), Direction.DOWN))
    );

    public static final Map<CoreClay, Map<CoreClay.ItemType, DeferredHolder<Item, Item>>> CERAMICS = Helpers.mapOf(CoreClay.class, clay ->
            Helpers.mapOf(CoreClay.ItemType.class, type -> type.hasType(clay),type ->
                    register("ceramic/" + type.getName(clay), type.create(clay))
            )
    );

    /* Much easier with kjs for now.
    public static final Map<Metals.Metal,DeferredHolder<Item, Item>> METAL_FLUID_BUCKETS = Helpers.mapOf(Metals.Metal.class, metal ->
            register("bucket/metal/" + metal.name(), () -> new BucketItem(CoreFluids.METALS.get(metal).source().get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)))
    );
    */

    public static final DeferredHolder<Item, ToolItem> SNOW_SHOVEL = ITEMS.register("tool/snow_shovel", () -> new ToolItem(TFCTiers.STEEL, CoreTags.SNOW_SHOVEL_MINEABLE, new Item.Properties().durability(3200).stacksTo(0).setNoRepair()));
    public static final DeferredHolder<Item, Item> SNOW_SHOVEL_HEAD = ITEMS.register("tool/snow_shovel_head", CoreItems::basicItem);

    public static final DeferredHolder<Item, MoldItem> GLASS_MOLD = ITEMS.register("glass_mold", ()-> new MoldItem(() -> 800, CoreTags.GLASS_MOLD_ACCEPTABLE, new Item.Properties().stacksTo(1)));
    public static final DeferredHolder<Item, MoldItem> GLASS_PANE_MOLD = ITEMS.register("glass_pane_mold", ()-> new MoldItem(() -> 50, CoreTags.GLASS_MOLD_ACCEPTABLE, new Item.Properties().stacksTo(1)));

    public static final DeferredHolder<Item, Item> WROUGHT_IRON_BUCKET = register("metal/bucket/wrought_iron", () -> new BucketItem(new Item.Properties(), CoreTags.WROUGHT_IRON_BUCKET_ACCEPTABLE, true, TFCConfig.SERVER.metalBucketCanPlaceSources));

    private static Item basicItem(){
        return new Item(new Item.Properties());
    }


    private static DeferredHolder<Item, Item> register(String name)
    {
        return register(name, () -> new Item(new Item.Properties()));
    }

    private static DeferredHolder<Item, Item> register(String name, Item.Properties properties)
    {
        return ITEMS.register(name.toLowerCase(Locale.ROOT), () -> new Item(properties));
    }

    private static DeferredHolder<Item, Item> register(String name, Supplier<Item> item)
    {
        return ITEMS.register(name.toLowerCase(Locale.ROOT), item);
    }
}
