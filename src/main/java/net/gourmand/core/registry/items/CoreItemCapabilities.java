package net.gourmand.core.registry.items;

import net.dries007.tfc.common.capabilities.ItemCapabilities;
import net.dries007.tfc.common.component.fluid.FluidContainerHandler;
import net.dries007.tfc.common.component.mold.IMold;
import net.dries007.tfc.common.component.mold.Mold;
import net.dries007.tfc.common.items.FluidContainerItem;
import net.dries007.tfc.common.items.MoldItem;
import net.gourmand.core.registry.CoreItems;
import net.gourmand.core.registry.category.CoreClay;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.jetbrains.annotations.Nullable;

public class CoreItemCapabilities {

    public static void register(RegisterCapabilitiesEvent event){


        for (CoreClay clay : CoreClay.values())
        {
            final Item vessel = CoreItems.CERAMICS.get(clay).get(CoreClay.ItemType.VESSEL).get();
            final Item jug = CoreItems.CERAMICS.get(clay).get(CoreClay.ItemType.JUG).get();

            event.registerItem(Capabilities.FluidHandler.ITEM, ItemCapabilities::forBucket, jug);

            event.registerItem(ItemCapabilities.MOLD, ItemCapabilities::forVessel, vessel);
            event.registerItem(ItemCapabilities.HEAT, ItemCapabilities::forVessel, vessel);
            event.registerItem(ItemCapabilities.FLUID, ItemCapabilities::forVessel, vessel);
            event.registerItem(ItemCapabilities.ITEM, ItemCapabilities::forVessel, vessel);
        }


        event.registerItem(ItemCapabilities.MOLD, CoreItemCapabilities::getMold,
                CoreItems.GLASS_MOLD.get(),
                CoreItems.GLASS_PANE_MOLD.get()
        );

        event.registerItem(ItemCapabilities.FLUID, CoreItemCapabilities::getMold,
                CoreItems.GLASS_MOLD.get(),
                CoreItems.GLASS_PANE_MOLD.get()
        );

        event.registerItem(ItemCapabilities.HEAT, CoreItemCapabilities::getMold,
                CoreItems.GLASS_MOLD.get(),
                CoreItems.GLASS_PANE_MOLD.get()
        );


        event.registerItem(Capabilities.FluidHandler.ITEM, ItemCapabilities::forBucket,
                CoreItems.WROUGHT_IRON_BUCKET.get()
        );
    }

    public static IMold getMold(ItemStack stack, @Nullable Void context)
    {
        return stack.getItem() instanceof MoldItem item ? new Mold(stack, item.containerInfo()) : null;
    }

    public static @Nullable FluidContainerHandler forBucket(ItemStack stack, @Nullable Void context)
    {
        return stack.getItem() instanceof FluidContainerItem item ? new FluidContainerHandler(stack, item.containerInfo()) : null;
    }
}
