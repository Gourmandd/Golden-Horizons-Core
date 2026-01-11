package net.gourmand.GoldenHorizonsCore.client;

import net.dries007.tfc.client.IGhostBlockHandler;
import net.dries007.tfc.client.TFCColors;
import net.dries007.tfc.client.model.ContainedFluidModel;
import net.gourmand.GoldenHorizonsCore.registry.CoreBlocks;
import net.gourmand.GoldenHorizonsCore.registry.CoreItems;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.util.function.Predicate;

public class ClientEventHandler {

    public static void init(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(ClientEventHandler::registerColorHandlerBlocks);
        modEventBus.addListener(ClientEventHandler::clientSetup);
        modEventBus.addListener(ClientEventHandler::registerColorHandlerItems);
    }

    @SuppressWarnings("deprecation")
    public static void clientSetup(FMLClientSetupEvent event){

        final RenderType solid = RenderType.solid();
        final RenderType cutout = RenderType.cutout();
        final RenderType cutoutMipped = RenderType.cutoutMipped();
        final RenderType translucent = RenderType.translucent();
        final Predicate<RenderType> ghostBlock = rt -> rt == cutoutMipped || rt == Sheets.translucentCullBlockSheet();

        CoreBlocks.METALS.values().forEach(map -> map.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout)));

        CoreBlocks.CROPS.values().forEach(reg -> {
            if (reg.get() instanceof IGhostBlockHandler)
            {
                ItemBlockRenderTypes.setRenderLayer(reg.get(), ghostBlock);
            }
            else
            {
                ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout);
            }
        });
        CoreBlocks.DEAD_CROPS.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout));
        CoreBlocks.WILD_CROPS.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout));

        CoreBlocks.SPREADING_BUSHES.values().forEach(bush -> ItemBlockRenderTypes.setRenderLayer(bush.get(), cutoutMipped));
        CoreBlocks.SPREADING_CANES.values().forEach(bush -> ItemBlockRenderTypes.setRenderLayer(bush.get(), cutoutMipped));
        CoreBlocks.STATIONARY_BUSHES.values().forEach(bush -> ItemBlockRenderTypes.setRenderLayer(bush.get(), cutoutMipped));
        CoreBlocks.FRUIT_TREE_LEAVES.values().forEach(leaves -> ItemBlockRenderTypes.setRenderLayer(leaves.get(), cutoutMipped));
        CoreBlocks.FRUIT_TREE_SAPLINGS.values().forEach(leaves -> ItemBlockRenderTypes.setRenderLayer(leaves.get(), cutout));
        CoreBlocks.FRUIT_TREE_POTTED_SAPLINGS.values().forEach(leaves -> ItemBlockRenderTypes.setRenderLayer(leaves.get(), cutout));

        CoreBlocks.ORES.values().forEach(map -> map.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout)));
        CoreBlocks.GRADED_ORES.values().forEach(map -> map.values().forEach(inner -> inner.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout))));

        CoreBlocks.CUSTOM_ROCK_ORES.values().forEach(map -> map.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout)));
        CoreBlocks.CUSTOM_ROCK_GRADED_ORES.values().forEach(map -> map.values().forEach(inner -> inner.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout))));

        CoreBlocks.CUSTOM_ROCK_TFC_ORES.values().forEach(map -> map.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout)));
        CoreBlocks.CUSTOM_ROCK_TFC_GRADED_ORES.values().forEach(map -> map.values().forEach(inner -> inner.values().forEach(reg -> ItemBlockRenderTypes.setRenderLayer(reg.get(), cutout))));

    }

    public static void registerColorHandlerBlocks(RegisterColorHandlersEvent.Block event){

        final BlockColor grassColor = (state, level, pos, tintIndex) -> TFCColors.getGrassColor(pos, tintIndex);

        CoreBlocks.WILD_CROPS.forEach((crop, reg) -> event.register(grassColor, reg.get()));
    }

    private static void registerColorHandlerItems(RegisterColorHandlersEvent.Item event) {
        event.register(ContainedFluidModel.COLOR, CoreItems.GLASS_MOLD.get(), CoreItems.GLASS_PANE_MOLD.get());
    }
}
