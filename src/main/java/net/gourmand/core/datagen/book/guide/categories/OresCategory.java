package net.gourmand.core.datagen.book.guide.categories;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import net.dries007.tfc.common.blocks.rock.Ore;
import net.dries007.tfc.common.items.TFCItems;
import net.gourmand.core.datagen.book.guide.entries.ores.OreEntry;
import net.gourmand.core.registry.CoreItems;
import net.gourmand.core.registry.category.CoreOres;
import net.minecraft.resources.ResourceLocation;

public class OresCategory extends CategoryProvider {

    public static final String ID = "ores";

    public OresCategory(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected String[] generateEntryMap() {
        return new String[]{
                "a__m__dei",
                "b__k__fgh",
                "c__l___j_",
                "_________",
                "nopqrstuv"
        };
    }

    @Override
    protected void generateEntries() {
        // copper
        var nativeCopperEntry = this.add(new OreEntry(this, "native_copper", TFCItems.GRADED_ORES.get(Ore.NATIVE_COPPER).get(Ore.Grade.RICH).get()).generate('a'));
        var malachiteEntry = this.add(new OreEntry(this, "malachite", TFCItems.GRADED_ORES.get(Ore.MALACHITE).get(Ore.Grade.RICH).get()).generate('b'));
        var tetrahedriteEntry = this.add(new OreEntry(this, "tetrahedrite", TFCItems.GRADED_ORES.get(Ore.TETRAHEDRITE).get(Ore.Grade.RICH).get()).generate('c'));

        // misc metals
        var nativeGoldEntry = this.add(new OreEntry(this, "native_gold", TFCItems.GRADED_ORES.get(Ore.NATIVE_GOLD).get(Ore.Grade.RICH).get()).generate('d'));
        var nativeSilverEntry = this.add(new OreEntry(this, "native_silver", TFCItems.GRADED_ORES.get(Ore.NATIVE_SILVER).get(Ore.Grade.RICH).get()).generate('e'));
        var cassiteriteEntry = this.add(new OreEntry(this, "cassiterite", TFCItems.GRADED_ORES.get(Ore.CASSITERITE).get(Ore.Grade.RICH).get()).generate('f'));
        var bismuthiniteEntry = this.add(new OreEntry(this, "bismuthinite", TFCItems.GRADED_ORES.get(Ore.BISMUTHINITE).get(Ore.Grade.RICH).get()).generate('g'));
        var garnieriteEntry = this.add(new OreEntry(this, "garnierite", TFCItems.GRADED_ORES.get(Ore.GARNIERITE).get(Ore.Grade.RICH).get()).generate('h'));
        var galenaEntry = this.add(new OreEntry(this, "galena", CoreItems.GRADED_ORES.get(CoreOres.GALENA).get(CoreOres.Grade.RICH).get()).generate('i'));
        var sphaleriteEntry = this.add(new OreEntry(this, "sphalerite", TFCItems.GRADED_ORES.get(Ore.SPHALERITE).get(Ore.Grade.RICH).get()).generate('j'));

        // iron
        var magnetiteEntry = this.add(new OreEntry(this, "magnetite", TFCItems.GRADED_ORES.get(Ore.MAGNETITE).get(Ore.Grade.RICH).get()).generate('k'));
        var limoniteEntry = this.add(new OreEntry(this, "limonite", TFCItems.GRADED_ORES.get(Ore.LIMONITE).get(Ore.Grade.RICH).get()).generate('l'));
        var hematiteEntry = this.add(new OreEntry(this, "hematite", TFCItems.GRADED_ORES.get(Ore.HEMATITE).get(Ore.Grade.RICH).get()).generate('m'));

        // gems
        var amethystEntry = this.add(new OreEntry(this, "amethyst", TFCItems.GEMS.get(Ore.AMETHYST).get()).generate('n'));
        var diamondEntry = this.add(new OreEntry(this, "diamond", TFCItems.GEMS.get(Ore.DIAMOND).get()).generate('o'));
        var emeraldEntry = this.add(new OreEntry(this, "emerald", TFCItems.GEMS.get(Ore.EMERALD).get()).generate('p'));
        var lapisLazuliEntry = this.add(new OreEntry(this, "lapis_lazuli", TFCItems.GEMS.get(Ore.LAPIS_LAZULI).get()).generate('q'));
        var opalEntry = this.add(new OreEntry(this, "opal", TFCItems.GEMS.get(Ore.OPAL).get()).generate('r'));
        var pyriteEntry = this.add(new OreEntry(this, "pyrite", TFCItems.GEMS.get(Ore.PYRITE).get()).generate('s'));
        var rubyEntry = this.add(new OreEntry(this, "ruby", TFCItems.GEMS.get(Ore.RUBY).get()).generate('t'));
        var sapphireEntry = this.add(new OreEntry(this, "sapphire", TFCItems.GEMS.get(Ore.SAPPHIRE).get()).generate('u'));
        var topazEntry = this.add(new OreEntry(this, "topaz", TFCItems.GEMS.get(Ore.TOPAZ).get()).generate('v'));

    }

    @Override
    protected String categoryName() {
        return "Ores";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(TFCItems.GRADED_ORES.get(Ore.NATIVE_COPPER).get(Ore.Grade.RICH));
    }

    @Override
    public String categoryId() {
        return ID;
    }

    @Override
    protected BookCategoryModel additionalSetup(BookCategoryModel category) {
        category.withBackground(ResourceLocation.parse("pastel:textures/gui/modonomicon/category_background.png"));
        return super.additionalSetup(category);
    }
}
