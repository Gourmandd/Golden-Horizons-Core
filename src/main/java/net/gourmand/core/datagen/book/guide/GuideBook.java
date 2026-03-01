package net.gourmand.core.datagen.book.guide;

import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import net.gourmand.core.datagen.book.guide.categories.OresCategory;

import java.util.function.BiConsumer;

public class GuideBook extends SingleBookSubProvider {

    public static final String ID = "guide";

    public GuideBook(String modId, BiConsumer<String, String> defaultLang) {
        super(ID, modId, defaultLang);
    }

    @Override
    protected void registerDefaultMacros() {

    }

    @Override
    protected void generateCategories() {
        var oresCategory = this.add(new OresCategory(this).generate());
    }

    @Override
    protected String bookName() {
        return "Guide To This World";
    }

    @Override
    protected String bookTooltip() {
        return "Helping you, one page at a time!";
    }
}
