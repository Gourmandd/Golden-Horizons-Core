package net.gourmand.core.datagen.book.guide.entries.ores;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.mojang.datafixers.util.Pair;
import net.gourmand.core.util.TextUtil;
import net.minecraft.world.item.Item;

public class OreEntry extends EntryProvider {

    public final String ID;
    public final BookIconModel ICON;

    public OreEntry(CategoryProviderBase parent, String id, Item icon) {
        super(parent);
        this.ID = id;
        this.ICON = BookIconModel.create(icon);
    }

    @Override
    protected void generatePages() {
        this.page("page1", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText())
        );

        this.pageTitle(entryName());

        this.pageText(
            """
            **This is bold**    \s 
            *This is italics*    \s
            ++This is underlined++
            """
        );
    }

    @Override
    protected String entryName() {
        return TextUtil.getName(ID);
    }

    @Override
    protected String entryDescription() {
        return "How to find: " + TextUtil.getName(ID);
    }

    @Override
    protected Pair<Integer, Integer> entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return ICON;
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
