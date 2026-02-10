package net.gourmand.core.registry.category;

import earth.terrarium.pastel.PastelCommon;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.devices.SluiceBlock;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.registry.RegistryWood;
import net.gourmand.core.registry.CoreBlockEntities;
import net.gourmand.core.registry.blockentities.CoreLoomBlockEntity;
import net.gourmand.core.registry.blockentities.CoreSluiceBlockEntity;
import net.gourmand.core.registry.blocks.CoreLoomBlock;
import net.gourmand.core.registry.blocks.CoreShelfBlock;
import net.gourmand.core.registry.blocks.CoreToolRackBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;

import java.util.Locale;
import java.util.function.Supplier;

public enum CorePastelWood implements RegistryWood {
    CHESTNUT_NOXWOOD(true, MapColor.CRIMSON_NYLIUM, MapColor.CRIMSON_NYLIUM),
    SLATE_NOXWOOD(true, MapColor.COLOR_GRAY, MapColor.COLOR_GRAY),
    EBONY_NOXWOOD(true, MapColor.TERRACOTTA_BLACK, MapColor.TERRACOTTA_BLACK),
    IVORY_NOXWOOD(true, MapColor.QUARTZ, MapColor.QUARTZ),
    WEEPING_GALA(false, MapColor.COLOR_BROWN, MapColor.COLOR_BROWN),
    WHITE(false, MapColor.SNOW, MapColor.SNOW),
    ORANGE(false, MapColor.COLOR_ORANGE, MapColor.COLOR_ORANGE),
    MAGENTA(false,  MapColor.COLOR_MAGENTA, MapColor.COLOR_MAGENTA ),
    LIGHT_BLUE(false,  MapColor.COLOR_LIGHT_BLUE, MapColor.COLOR_LIGHT_BLUE ),
    YELLOW(false,  MapColor.COLOR_YELLOW, MapColor.COLOR_YELLOW ),
    LIME(false,  MapColor.COLOR_LIGHT_GREEN, MapColor.COLOR_LIGHT_GREEN ),
    PINK(false, MapColor.COLOR_PINK, MapColor.COLOR_PINK ),
    GRAY(false,  MapColor.COLOR_GRAY, MapColor.COLOR_GRAY ),
    LIGHT_GRAY(false,  MapColor.COLOR_LIGHT_GRAY, MapColor.COLOR_LIGHT_GRAY ),
    CYAN(false,  MapColor.COLOR_CYAN, MapColor.COLOR_CYAN ),
    PURPLE(false,  MapColor.COLOR_PURPLE, MapColor.COLOR_PURPLE ),
    BLUE(false,  MapColor.COLOR_BLUE, MapColor.COLOR_BLUE ),
    BROWN(false,  MapColor.COLOR_BROWN, MapColor.COLOR_BROWN ),
    GREEN(false,  MapColor.COLOR_GREEN, MapColor.COLOR_GREEN ),
    RED(false,  MapColor.COLOR_RED, MapColor.COLOR_RED ),
    BLACK(false,  MapColor.COLOR_BLACK, MapColor.COLOR_BLACK );

    private final boolean isNoxfungi;
    private final String serializedName;
    private final MapColor woodColor;
    private final MapColor barkColor;
    private final BlockSetType blockSet;
    private final WoodType woodType;
    private final ResourceLocation planksTextures;

    CorePastelWood(boolean isNoxfungi, MapColor woodColor, MapColor barkColor){
        this.isNoxfungi = isNoxfungi;
        this.serializedName = name().toLowerCase(Locale.ROOT);
        this.woodColor = woodColor;
        this.barkColor = barkColor;
        this.blockSet = new BlockSetType(serializedName);
        this.woodType = new WoodType(Helpers.identifier(serializedName).toString(), blockSet);
        this.planksTextures = ResourceLocation.parse(PastelCommon.MOD_ID + ":block/" + serializedName + "_planks");
    }
    @Override
    public MapColor woodColor() {
        return woodColor;
    }

    public boolean isNoxfungi() { return isNoxfungi; }

    @Override
    public MapColor barkColor() {
        return barkColor;
    }

    public boolean hasBlockType(Wood.BlockType type){
        return switch (type) {
            case TWIG, SEWING_TABLE, SCRIBING_TABLE, VERTICAL_SUPPORT, HORIZONTAL_SUPPORT, LOOM, TOOL_RACK, SLUICE, SHELF -> true;
            default -> false;
        };
    }

    @Override
    public TreeGrower tree() {
        return null;
    }

    @Override
    public Supplier<Integer> ticksToGrow() {
        return () -> 0;
    }

    @Override
    public int autumnIndex() {
        return 0;
    }

    @Override
    public Supplier<Block> getBlock(Wood.BlockType blockType) {
        return null;
    }

    @Override
    public BlockSetType getBlockSet() {
        return blockSet;
    }

    @Override
    public WoodType getVanillaWoodType() {
        return woodType;
    }

    @Override
    public String getSerializedName() {
        return serializedName;
    }

    public ResourceLocation getPlanksTexture() {
        return planksTextures;
    }

    public static Supplier<Block> create(Wood.BlockType type, CorePastelWood wood) {

        switch (type){
            case TOOL_RACK -> {
                return () -> new CoreToolRackBlock(ExtendedProperties.of().sound(SoundType.WOOD).strength(2.0F).noOcclusion().blockEntity(CoreBlockEntities.TOOL_RACK));
            }
            case LOOM -> {
                return () -> new CoreLoomBlock(ExtendedProperties.of().sound(SoundType.WOOD).strength(2.5F).noOcclusion().flammableLikePlanks().blockEntity(CoreBlockEntities.LOOM).ticks(CoreLoomBlockEntity::tick), wood.getPlanksTexture());
            }
            case SLUICE -> {
                return () -> new SluiceBlock(ExtendedProperties.of().sound(SoundType.WOOD).strength(3F).noOcclusion().flammableLikeLogs().blockEntity(CoreBlockEntities.SLUICE).serverTicks(CoreSluiceBlockEntity::serverTick));
            }
            case SHELF -> {
                return () -> new CoreShelfBlock(ExtendedProperties.of().sound(SoundType.WOOD).noOcclusion().strength(2.5f).flammableLikePlanks().blockEntity(CoreBlockEntities.SHELF), false);
            }
            default -> {
                return type.create(wood);
            }

        }
    }
}
