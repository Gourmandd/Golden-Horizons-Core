package net.gourmand.core.registry.category;

import earth.terrarium.pastel.blocks.conditional.CloakedOreBlock;
import earth.terrarium.pastel.blocks.geology.AzuriteOreBlock;
import earth.terrarium.pastel.blocks.geology.ShimmerstoneOreBlock;
import earth.terrarium.pastel.registries.PastelItems;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.util.registry.RegistryRock;
import net.gourmand.core.util.RegistryOre;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.Locale;

public enum CoreOres implements RegistryOre {

    SHIMMERSTONE(Type.NORMAL, PastelOreType.SHIMMERSTONE),
    AZURITE(Type.NORMAL, PastelOreType.AZURITE),
    STRATINE(Type.NORMAL, PastelOreType.STRATINE),
    PALTAERIA(Type.NORMAL, PastelOreType.PALTAERIA),
    MALACHITE(Type.NORMAL, PastelOreType.MALACHITE),
    GALENA(Type.GRADED),
    ANTHRACITE(Type.ITEM_ONLY),
    BAUXITE(Type.ITEM_ONLY);

    private final Type type;
    private final PastelOreType pastelOreType;
    private final String serializedName;

    CoreOres(Type type)
    {
        this.type = type;
        this.pastelOreType = PastelOreType.NONE;
        this.serializedName = name().toLowerCase(Locale.ROOT);
    }

    CoreOres(Type type, PastelOreType pastelOreType)
    {
        this.type = type;
        this.pastelOreType = pastelOreType;
        this.serializedName = name().toLowerCase(Locale.ROOT);
    }

    public boolean isGraded()
    {
        return type == Type.GRADED;
    }

    public boolean isGem()
    {
        return type == Type.GEM;
    }

    public boolean hasPowder()
    {
        return type != Type.NORMAL && type != Type.ITEM_ONLY;
    }

    public boolean hasBlock()
    {
        return type != Type.ITEM_ONLY;
    }

    public boolean hasPastelOreType()
    {
        return this.pastelOreType != PastelOreType.NONE;
    }

    public Block create(RegistryRock rock)
    {
        final BlockBehaviour.Properties properties = Block.Properties.of().mapColor(MapColor.STONE).sound(SoundType.STONE).strength(rock.category().hardness(6.5f), 10).requiresCorrectToolForDrops();
        final Block block;

        switch (pastelOreType){
            case PastelOreType.SHIMMERSTONE -> block = new ShimmerstoneOreBlock( UniformInt.of(2, 4), properties);
            case PastelOreType.AZURITE -> block = new AzuriteOreBlock(UniformInt.of(4, 7), properties);
            case PastelOreType.STRATINE -> block = new CloakedOreBlock(UniformInt.of(3, 5), properties);
            case PastelOreType.PALTAERIA -> block = new CloakedOreBlock(UniformInt.of(2, 4), properties);
            case PastelOreType.MALACHITE -> block = new CloakedOreBlock(UniformInt.of(7, 11), properties);
            case PastelOreType.NONE -> block = new Block(properties);
            default -> throw new AssertionError("pastelOreType of CoreOre did not match any PastelOreType enum entry");
        }
        return block;
    }

    public Item getPastelOre(){

        final Item item;

        switch (pastelOreType){
            case PastelOreType.SHIMMERSTONE -> item = PastelItems.SHIMMERSTONE_GEM.asItem();
            case PastelOreType.AZURITE -> item = PastelItems.RAW_AZURITE.asItem();
            case PastelOreType.STRATINE -> item = PastelItems.STRATINE_FRAGMENTS.asItem();
            case PastelOreType.PALTAERIA -> item = PastelItems.PALTAERIA_FRAGMENTS.asItem();
            case PastelOreType.MALACHITE -> item = PastelItems.RAW_MALACHITE.asItem();
            case PastelOreType.NONE -> throw new AssertionError("pastelOre of CoreOre is NONE, only use the method if you are sure pastelOreType isnt NONE");
            default -> throw new AssertionError("pastelOre of CoreOre did not match any PastelOreType enum entry");
        }
        return item;
    }

    public static Ore.Grade getTFCgrade(Grade grade){
        switch (grade){
            case POOR -> {
                return Ore.Grade.POOR;
            }
            case NORMAL ->{
                return Ore.Grade.NORMAL;
            }
            case RICH -> {
                return Ore.Grade.RICH;
            }
            default -> {
                return Ore.Grade.NORMAL;
            }
        }
    }

    @Override
    public String getSerializedName() {
        return serializedName;
    }

    public enum Grade
    {
        POOR, NORMAL, RICH
    }

    enum Type
    {
        GRADED, NORMAL, NORMAL_WITH_POWDER, GEM, ITEM_ONLY
    }

    enum PastelOreType
    {
        NONE, SHIMMERSTONE, STRATINE, AZURITE, PALTAERIA, MALACHITE
    }
}
