package de.sirvincelot24.mantle_reforged.client.book.action.protocol;

import de.sirvincelot24.mantle_reforged.client.screen.book.BookScreen;

public class ProtocolGoToPage extends ActionProtocol {
  private final boolean returner;

  public ProtocolGoToPage(boolean returner) {
    this.returner = returner;
  }

  @Override
  public void processCommand(BookScreen book, String param) {
    int pageNum = book.book.findPageNumber(param, book.advancementCache);

    if (pageNum >= 0) {
      book.openPage(pageNum, this.returner);
    }
  }
}
