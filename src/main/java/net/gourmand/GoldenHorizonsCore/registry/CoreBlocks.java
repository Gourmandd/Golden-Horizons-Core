package net.gourmand.GoldenHorizonsCore.registry;

import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.Metal;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.gourmand.GoldenHorizonsCore.GoldenHorizonsCore;
import net.gourmand.GoldenHorizonsCore.registry.category.CoreMetals;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class CoreBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(GoldenHorizonsCore.MODID);

    public static final Map<CoreMetals.MetalType, Map<Metal.BlockType, DeferredHolder<Block, Block>>> METALS = Helpers.mapOf(CoreMetals.MetalType.class, metal ->
            Helpers.mapOf(Metal.BlockType.class, type -> type.has(metal.getLikeMetal()), type ->
                    register(type.createName(metal), type.create(metal), type.createBlockItem(new Item.Properties()))
            )
    );

    /* Much easier with kjs for now.
    public static final Map<Metals.Metal, DeferredHolder<Block, LiquidBlock>> METAL_FLUIDS = Helpers.mapOf(Metals.Metal.class, metal ->
            registerNoItem("fluid/metal/" + metal.name(), () -> new LiquidBlock(CoreFluids.METALS.get(metal).getSource(), BlockBehaviour.Properties.ofFullCopy(Blocks.LAVA).noLootTable()))
    );
    */

    private static <T extends Block> DeferredHolder<Block, T> registerNoItem(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, (Function<T, ? extends BlockItem>) null);
    }

    private static <T extends Block> DeferredHolder<Block, T> register(String name, Supplier<T> blockSupplier)
    {
        return register(name, blockSupplier, block -> new BlockItem(block, new Item.Properties()));
    }

    private static <T extends Block> DeferredHolder<Block, T> register(String name, Supplier<T> blockSupplier, @Nullable Function<T, ? extends BlockItem> blockItemFactory)
    {
        return RegistrationHelpers.registerBlock(CoreBlocks.BLOCKS, CoreItems.ITEMS, name, blockSupplier, blockItemFactory);
    }
}
