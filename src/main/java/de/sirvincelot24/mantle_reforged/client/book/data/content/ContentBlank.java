package de.sirvincelot24.mantle_reforged.client.book.data.content;

import net.minecraft.resources.ResourceLocation;
import de.sirvincelot24.mantle_reforged.Mantle;
import de.sirvincelot24.mantle_reforged.client.book.data.BookData;
import de.sirvincelot24.mantle_reforged.client.screen.book.element.BookElement;

import java.util.ArrayList;

public class ContentBlank extends PageContent {
  public static final ResourceLocation ID = Mantle.getResource("blank");

  @Override
  public void build(BookData book, ArrayList<BookElement> list, boolean rightSide) {
  }
}
