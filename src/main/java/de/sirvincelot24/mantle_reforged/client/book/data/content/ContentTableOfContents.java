package de.sirvincelot24.mantle_reforged.client.book.data.content;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import de.sirvincelot24.mantle_reforged.client.book.data.BookData;
import de.sirvincelot24.mantle_reforged.client.book.data.element.TextData;
import de.sirvincelot24.mantle_reforged.client.screen.book.BookScreen;
import de.sirvincelot24.mantle_reforged.client.screen.book.element.BookElement;
import de.sirvincelot24.mantle_reforged.client.screen.book.element.TextElement;

import java.util.ArrayList;

public class ContentTableOfContents extends PageContent {

  @Getter
  public String title;
  public TextData[] data;

  public ContentTableOfContents(String title, TextData... contents) {
    this.title = title;
    this.data = contents;
  }

  @Override
  public void build(BookData book, ArrayList<BookElement> list, boolean rightSide) {
    int y = 0;

    if (this.title != null && !this.title.trim().isEmpty()) {
      this.addTitle(list, this.title);
      y += getTitleHeight();
    }

    for (int i = 0; i < this.data.length; i++) {
      TextData text = this.data[i];
      list.add(new TextElement(0, y + i * (int) (Minecraft.getInstance().font.lineHeight * text.scale), BookScreen.PAGE_WIDTH, Minecraft.getInstance().font.lineHeight, text));
    }
  }
}
