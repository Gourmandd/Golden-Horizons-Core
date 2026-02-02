package net.gourmand.core.util;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.material.MapColor;

public interface RegistryClay extends StringRepresentable {

    public MapColor mapColor();

    public Rarity getRarity();

    public boolean hasReducedSet();
}
