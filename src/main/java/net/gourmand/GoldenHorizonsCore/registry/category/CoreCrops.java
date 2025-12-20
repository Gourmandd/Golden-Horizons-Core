package net.gourmand.GoldenHorizonsCore.registry.category;

import earth.terrarium.pastel.registries.PastelBlocks;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.common.blocks.crop.*;
import net.dries007.tfc.util.climate.ClimateRange;
import net.gourmand.GoldenHorizonsCore.registry.CoreBlockEntities;
import net.gourmand.GoldenHorizonsCore.registry.blockentities.CoreCropBlockEntity;
import net.gourmand.GoldenHorizonsCore.registry.blocks.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;

public enum CoreCrops implements StringRepresentable {

    GLISTERING_MELON(0.75f, -0.2f, -0.2f, 7, () -> () -> BuiltInRegistries.BLOCK.get(PastelBlocks.GLISTERING_MELON)),
    COTTON(0.75f, -0.2f, -0.2f, 3),
    COFFEE(0.75f, -0.2f, -0.2f, 5),
    AMARANTH(0.75f, -0.2f, -0.2f, 2, 3, false);

    CoreCrops(float nitrogen, float phosphorus, float potassium, int singleBlockStages)
    {
        this(nitrogen, phosphorus, potassium, self -> CoreDefaultCropBlock.create(crop(), singleBlockStages, self), self -> new DeadCropBlock(dead(), self.getClimateRange()), self -> new WildCropBlock(dead().randomTicks()));
    }

    CoreCrops(float nitrogen, float phosphorus, float potassium, int spreadingSingleBlockStages, Supplier<Supplier<? extends Block>> fruit)
    {
        this(nitrogen, phosphorus, potassium, self -> CoreSpreadingCropBlock.create(crop(), spreadingSingleBlockStages, self, fruit), self -> new DeadCropBlock(dead(), self.getClimateRange()), self -> new WildSpreadingCropBlock(dead().randomTicks(), fruit));
    }

    CoreCrops(float nitrogen, float phosphorus, float potassium, int spreadingSingleBlockStages, @Nullable Supplier<Supplier<? extends Item>> fruit1, Supplier<Supplier<? extends Item>> fruit2)
    {
        this(nitrogen, phosphorus, potassium, self -> CorePickableCropBlock.create(crop(), spreadingSingleBlockStages, self, fruit1, fruit2), self -> new DeadCropBlock(dead(), self.getClimateRange()), self -> new WildCropBlock(dead().randomTicks()));
    }

    CoreCrops(float nitrogen, float phosphorus, float potassium, int floodedSingleBlockStages, boolean flooded)
    {
        this(nitrogen, phosphorus, potassium, self -> CoreFloodedCropBlock.create(crop(), floodedSingleBlockStages, self), self -> new FloodedDeadCropBlock(dead(), self.getClimateRange()), self -> new FloodedWildCropBlock(dead().randomTicks()));
        assert flooded;
    }

    CoreCrops(float nitrogen, float phosphorus, float potassium, int doubleBlockBottomStages, int doubleBlockTopStages, boolean requiresStick, @Nullable Supplier<Supplier<? extends Item>> fruit1, Supplier<Supplier<? extends Item>> fruit2)
    {
        this(nitrogen, phosphorus, potassium,
                requiresStick ?
                        self -> CorePickableClimbingCropBlock.create(doubleCrop(), doubleBlockBottomStages, doubleBlockTopStages, self, fruit1, fruit2) :
                        self -> CoreDoubleCropBlock.create(doubleCrop(), doubleBlockBottomStages, doubleBlockTopStages, self),
                requiresStick ?
                        self -> new DeadClimbingCropBlock(dead(), self.getClimateRange()) :
                        self -> new DeadDoubleCropBlock(dead(), self.getClimateRange()),
                self -> new WildDoubleCropBlock(dead().randomTicks())
        );
    }

    CoreCrops(float nitrogen, float phosphorus, float potassium, int doubleBlockBottomStages, int doubleBlockTopStages, boolean requiresStick)
    {
        this(nitrogen, phosphorus, potassium,
                requiresStick ?
                        self -> CoreClimbingCropBlock.create(doubleCrop(), doubleBlockBottomStages, doubleBlockTopStages, self) :
                        self -> CoreDoubleCropBlock.create(doubleCrop(), doubleBlockBottomStages, doubleBlockTopStages, self),
                requiresStick ?
                        self -> new DeadClimbingCropBlock(dead(), self.getClimateRange()) :
                        self -> new DeadDoubleCropBlock(dead(), self.getClimateRange()),
                self -> new WildDoubleCropBlock(dead().randomTicks())
        );
    }

    CoreCrops(float nitrogen, float phosphorus, float potassium, Function<CoreCrops, Block> factory, Function<CoreCrops, Block> deadFactory, Function<CoreCrops, Block> wildFactory)
    {
        this.serializedName = name().toLowerCase(Locale.ROOT);
        this.nitrogen = nitrogen;
        this.phosphorous = phosphorus;
        this.potassium = potassium;
        this.factory = () -> factory.apply(this);
        this.deadFactory = () -> deadFactory.apply(this);
        this.wildFactory = () -> wildFactory.apply(this);
    }

    private final String serializedName;
    private final float nitrogen;
    private final float phosphorous;
    private final float potassium;
    private final Supplier<Block> factory;
    private final Supplier<Block> deadFactory;
    private final Supplier<Block> wildFactory;

    private static ExtendedProperties doubleCrop()
    {
        return dead().blockEntity(CoreBlockEntities.CROP).serverTicks(CoreCropBlockEntity::serverTickBottomPartOnly);
    }

    private static ExtendedProperties crop()
    {
        return dead().blockEntity(CoreBlockEntities.CROP).serverTicks(CoreCropBlockEntity::serverTick);
    }

    private static ExtendedProperties dead()
    {
        return ExtendedProperties.of(MapColor.PLANT).noCollission().randomTicks().strength(0.4F).sound(SoundType.CROP).flammable(60, 30).pushReaction(PushReaction.DESTROY);
    }

    @Override
    public String getSerializedName()
    {
        return serializedName;
    }

    public Block create()
    {
        return factory.get();
    }

    public Block createDead()
    {
        return deadFactory.get();
    }

    public Block createWild()
    {
        return wildFactory.get();
    }

    public float getNitrogen()
    {
        return this.nitrogen;
    }

    public float getPhosphorous()
    {
        return this.phosphorous;
    }

    public float getPotassium()
    {
        return this.potassium;
    }

    public Supplier<ClimateRange> getClimateRange()
    {
        return CoreClimateRanges.CROPS.get(this);
    }
}

