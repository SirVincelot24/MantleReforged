package de.sirvincelot24.mantle_reforged.client.book.data.content;

import de.sirvincelot24.mantle_reforged.client.book.data.BookData;
import de.sirvincelot24.mantle_reforged.client.book.data.SectionData;
import de.sirvincelot24.mantle_reforged.client.screen.book.BookScreen;
import de.sirvincelot24.mantle_reforged.client.screen.book.element.BookElement;
import de.sirvincelot24.mantle_reforged.client.screen.book.element.SelectionElement;

import java.util.ArrayList;

public class ContentSectionList extends PageContent {

  protected ArrayList<SectionData> sections = new ArrayList<>();

  public boolean addSection(SectionData data) {
    return this.sections.size() < 12 && this.sections.add(data);
  }

  @Override
  public void build(BookData book, ArrayList<BookElement> list, boolean rightSide) {
    int columns = book.appearance.drawFourColumnIndex ? 4 : 3;
    int width = (SelectionElement.WIDTH + 5) * columns - 5;
    int height = (SelectionElement.HEIGHT + 5) * 3 - 5;

    int ox = (BookScreen.PAGE_WIDTH - width) / 2;
    int oy = (BookScreen.PAGE_HEIGHT - height) / 2 - 5;

    int sectionRange = Math.min(columns * 3, this.sections.size());
    for (int i = 0; i < sectionRange; i++) {
      int ix = i % columns;
      int iy = i / columns;

      int x = ox + ix * (SelectionElement.WIDTH + 5);
      int y = oy + iy * (SelectionElement.HEIGHT + 5);

      list.add(new SelectionElement(x, y, this.sections.get(i)));
    }
  }
}
