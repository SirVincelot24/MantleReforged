package de.sirvincelot24.mantle_reforged.client.book.data.content;

import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import de.sirvincelot24.mantle_reforged.Mantle;
import de.sirvincelot24.mantle_reforged.client.book.data.BookData;
import de.sirvincelot24.mantle_reforged.client.book.data.element.ImageData;
import de.sirvincelot24.mantle_reforged.client.book.data.element.TextData;
import de.sirvincelot24.mantle_reforged.client.screen.book.BookScreen;
import de.sirvincelot24.mantle_reforged.client.screen.book.element.BookElement;
import de.sirvincelot24.mantle_reforged.client.screen.book.element.ImageElement;
import de.sirvincelot24.mantle_reforged.client.screen.book.element.TextElement;

import java.util.ArrayList;

public class ContentTextRightImage extends PageContent {
  public static final ResourceLocation ID = Mantle.getResource("text_right_image");

  @Getter
  public String title;
  public TextData[] text1;
  public TextData[] text2;
  public ImageData image;

  @Override
  public void build(BookData book, ArrayList<BookElement> list, boolean rightSide) {
    int y = getTitleHeight();

    if (this.title == null || this.title.isEmpty()) {
      y = 0;
    } else {
      this.addTitle(list, this.title);
    }

    if (this.text1 != null && this.text1.length > 0) {
      list.add(new TextElement(0, y, BookScreen.PAGE_WIDTH - 55, 50, this.text1));
    }

    if (this.image != null && this.image.location != null) {
      list.add(new ImageElement(BookScreen.PAGE_WIDTH - 50, y, 50, 50, this.image));
    } else {
      list.add(new ImageElement(BookScreen.PAGE_WIDTH - 50, y, 50, 50, ImageData.MISSING));
    }

    if (this.text2 != null && this.text2.length > 0) {
      list.add(new TextElement(0, y + 55, BookScreen.PAGE_WIDTH, BookScreen.PAGE_HEIGHT - 55 - y, this.text2));
    }
  }
}
