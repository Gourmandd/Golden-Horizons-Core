package net.gourmand.GoldenHorizonsCore.registry.category;

import earth.terrarium.pastel.blocks.conditional.CloakedOreBlock;
import earth.terrarium.pastel.blocks.geology.AzuriteOreBlock;
import earth.terrarium.pastel.blocks.geology.ShimmerstoneOreBlock;
import net.dries007.tfc.util.registry.RegistryRock;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public enum CoreOres {

    SHIMMERSTONE(Type.NORMAL, PastelOreType.SHIMMERSTONE),
    AZURITE(Type.NORMAL, PastelOreType.AZURITE),
    STRATINE(Type.NORMAL, PastelOreType.STRATINE),
    PALTAERIA(Type.NORMAL, PastelOreType.PALTAERIA),
    MALACHITE(Type.NORMAL, PastelOreType.MALACHITE),
    GALENA(Type.GRADED),
    ANTHRACITE(Type.NORMAL_WITH_POWDER),
    BAUXITE(Type.NORMAL_WITH_POWDER);

    private final Type type;
    private final PastelOreType pastelOreType;

    CoreOres(Type type)
    {
        this.type = type;
        this.pastelOreType = PastelOreType.NONE;
    }

    CoreOres(Type type, PastelOreType pastelOreType)
    {
        this.type = type;
        this.pastelOreType = pastelOreType;
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
            case PastelOreType.SHIMMERSTONE ->
            {
                block = new ShimmerstoneOreBlock( UniformInt.of(2, 4), properties);
            }
            case PastelOreType.AZURITE ->
            {
                block = new AzuriteOreBlock(UniformInt.of(4, 7), properties);
            }
            case PastelOreType.STRATINE ->
            {
                block = new CloakedOreBlock(UniformInt.of(3, 5), properties);
            }
            case PastelOreType.PALTAERIA ->
            {
                block = new CloakedOreBlock(UniformInt.of(2, 4), properties);
            }
            case PastelOreType.MALACHITE ->
            {
                block = new CloakedOreBlock(UniformInt.of(7, 11), properties);
            }
            case PastelOreType.NONE ->
            {
                block = new Block(properties);
            }
            default -> {
                throw new AssertionError("pastelOreType of CoreOre did not match any PastelOreType enum entry");
            }
        }
        return block;
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
