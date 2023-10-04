package de.sirvincelot24.mantle_reforged.client.book.data.content;

import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import de.sirvincelot24.mantle_reforged.Mantle;
import de.sirvincelot24.mantle_reforged.client.book.data.BookData;
import de.sirvincelot24.mantle_reforged.client.book.data.element.TextData;
import de.sirvincelot24.mantle_reforged.client.screen.book.BookScreen;
import de.sirvincelot24.mantle_reforged.client.screen.book.element.BookElement;
import de.sirvincelot24.mantle_reforged.client.screen.book.element.TextElement;

import java.util.ArrayList;

public class ContentText extends PageContent {
  public static final ResourceLocation ID = Mantle.getResource("text");

  @Getter
  public String title = null;
  public TextData[] text;

  @Override
  public void build(BookData book, ArrayList<BookElement> list, boolean rightSide) {
    int y;
    if (this.title == null || this.title.isEmpty()) {
      y = 0;
    } else {
      this.addTitle(list, this.title);
      y = getTitleHeight();
    }
    if (this.text != null && this.text.length > 0) {
      list.add(new TextElement(0, y, BookScreen.PAGE_WIDTH, BookScreen.PAGE_HEIGHT - y, this.text));
    }
  }
}
