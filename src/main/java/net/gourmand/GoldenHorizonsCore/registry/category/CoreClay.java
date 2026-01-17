package net.gourmand.GoldenHorizonsCore.registry.category;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.items.JugItem;
import net.dries007.tfc.common.items.VesselItem;
import net.dries007.tfc.config.TFCConfig;
import net.gourmand.GoldenHorizonsCore.util.RegistryClay;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;

public enum CoreClay implements RegistryClay {
    YIXING(MapColor.COLOR_PURPLE),
    YELLOWWARE(MapColor.TERRACOTTA_YELLOW),
    PORCELAIN(MapColor.SNOW),
    KAOLINITE(MapColor.TERRACOTTA_MAGENTA, true),
    EARTHENWARE(MapColor.TERRACOTTA_ORANGE);

    private final MapColor mapColor;
    private final boolean reducedSet;
    private final Rarity rarity;
    private final String serializedName;

    CoreClay(MapColor mapColor){
        this(mapColor, false);
    }

    CoreClay(MapColor mapColor, boolean reducedSet){
      this(mapColor, reducedSet, Rarity.COMMON);
    }

    CoreClay(MapColor mapColor, boolean reducedSet, Rarity rarity){
        this.serializedName = name().toLowerCase(Locale.ROOT);
        this.mapColor = mapColor;
        this.reducedSet = reducedSet;
        this.rarity = rarity;
    }

    @Override
    public MapColor mapColor()
    {
        return mapColor;
    }

    @Override
    public Rarity getRarity() {
        return rarity;
    }

    @Override
    public @NotNull String getSerializedName()
    {
        return serializedName;
    }

    @Override
    public boolean hasReducedSet() {
        return reducedSet;
    }

    public enum ItemType {
        PICKAXE_HEAD(ItemPartType.UNFIRED_MOLD),
        PROPICK_HEAD(ItemPartType.UNFIRED_MOLD),
        AXE_HEAD(ItemPartType.UNFIRED_MOLD),
        SHOVEL_HEAD(ItemPartType.UNFIRED_MOLD),
        HOE_HEAD(ItemPartType.UNFIRED_MOLD),
        CHISEL_HEAD(ItemPartType.UNFIRED_MOLD),
        HAMMER_HEAD(ItemPartType.UNFIRED_MOLD),
        SAW_BLADE(ItemPartType.UNFIRED_MOLD),
        JAVELIN_HEAD(ItemPartType.UNFIRED_MOLD),
        SWORD_BLADE(ItemPartType.UNFIRED_MOLD),
        MACE_HEAD(ItemPartType.UNFIRED_MOLD),
        KNIFE_BLADE(ItemPartType.UNFIRED_MOLD),
        SCYTHE_BLADE(ItemPartType.UNFIRED_MOLD),
        BELL(ItemPartType.UNFIRED_MOLD),
        INGOT(ItemPartType.UNFIRED_MOLD),

        CLAY_BALL(ItemPartType.REDUCED_ITEM),

        UNFIRED_POT(),
        UNFIRED_VESSEL(),
        UNFIRED_JUG(),
        UNFIRED_SPINDLE_HEAD(),
        UNFIRED_BRICK(),
        BRICK(),
        UNFIRED_BLOWPIPE(),
        UNFIRED_BOWL(),
        UNFIRED_FLOWER_POT(),
        UNFIRED_PAN(),

        JUG(ItemPartType.ITEM, clayType -> new JugItem(new Item.Properties().rarity(clayType.getRarity()).stacksTo(1), TFCConfig.SERVER.jugCapacity, TFCTags.Fluids.USABLE_IN_JUG)),
        VESSEL(ItemPartType.ITEM, clayType -> new VesselItem(new Item.Properties().rarity(clayType.getRarity()).stacksTo(1)));

        private final ItemPartType type;
        private final Function<RegistryClay, Item> itemFactory;
        private final String serializedName;

        ItemType()
        {
            this(ItemPartType.ITEM);
        }

        ItemType(ItemPartType type)
        {
            this(type, clayType -> new Item(new Item.Properties().rarity(clayType.getRarity())));
        }

        ItemType(ItemPartType type, Function<RegistryClay, Item> itemFactory)
        {
            this.type = type;
            this.itemFactory = itemFactory;
            this.serializedName = name().toLowerCase(Locale.ROOT);
        }

        public String getSerializedName()
        {
            return serializedName;
        }

        public String getName(RegistryClay clay)
        {
            if (type == ItemPartType.UNFIRED_MOLD){
                return clay.getSerializedName() + "/unfired_" + getSerializedName() + "_mold";
            } else {
                return clay.getSerializedName() + "/" + getSerializedName();
            }
        }

        public boolean hasType(RegistryClay clay)
        {
            return !(clay.hasReducedSet() && getType() == ItemPartType.REDUCED_ITEM);
        }

        public Supplier<Item> create(RegistryClay clay)
        {
            return () -> itemFactory.apply(clay);
        }

        public ItemPartType getType() {
            return type;
        }
    }

    public enum ItemPartType
    {
        // reduced item means that clay types with reducedSet: true won't have this item.
        UNFIRED_MOLD, ITEM, REDUCED_ITEM;
    }
}
