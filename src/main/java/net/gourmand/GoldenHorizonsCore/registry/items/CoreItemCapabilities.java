package net.gourmand.GoldenHorizonsCore.registry.items;

import net.dries007.tfc.common.capabilities.ItemCapabilities;
import net.dries007.tfc.common.component.mold.IMold;
import net.dries007.tfc.common.component.mold.Mold;
import net.dries007.tfc.common.items.MoldItem;
import net.gourmand.GoldenHorizonsCore.registry.CoreItems;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.jetbrains.annotations.Nullable;

public class CoreItemCapabilities {

    public static void register(RegisterCapabilitiesEvent event){


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
    }

    public static IMold getMold(ItemStack stack, @Nullable Void context)
    {
        return stack.getItem() instanceof MoldItem item ? new Mold(stack, item.containerInfo()) : null;
    }
}
