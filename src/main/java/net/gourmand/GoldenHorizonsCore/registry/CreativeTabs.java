package net.gourmand.GoldenHorizonsCore.registry;

import net.dries007.tfc.util.Metal;
import net.gourmand.GoldenHorizonsCore.GoldenHorizonsCore;
import net.gourmand.GoldenHorizonsCore.registry.category.CoreMetals;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Map;

public class CreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GoldenHorizonsCore.MODID);


    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> METAL = CREATIVE_TABS.register("metal", () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group." + GoldenHorizonsCore.MODID)) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> CoreItems.METAL_ITEMS.get(CoreMetals.MetalType.ALUMINIUM).get(Metal.ItemType.INGOT).get().getDefaultInstance())
            .displayItems(CreativeTabs::fillMetal).build());

    private static void fillMetal(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output out)
    {
        for(CoreMetals.MetalType metal : CoreMetals.MetalType.values())
        {
            for (Metal.ItemType type : Metal.ItemType.values())
            {
                accept(out, CoreItems.METAL_ITEMS, metal, type);
            }

            for (Metal.BlockType type : Metal.BlockType.values())
            {
                accept(out, CoreBlocks.METALS, metal, type);
            }
        }
    }

    private static <R extends DeferredHolder<?, ?>, K1, K2> void accept(CreativeModeTab.Output out, Map<K1, Map<K2, R>> map, K1 key1, K2 key2)
    {
        if (map.containsKey(key1))
        {
            accept(out, map.get(key1), key2);
        }
    }

    private static <R extends DeferredHolder<?, ?>, K> void accept(CreativeModeTab.Output out, Map<K, R> map, K key)
    {
        if (map.containsKey(key))
        {
            out.accept((ItemLike) map.get(key));
        }
    }
}
