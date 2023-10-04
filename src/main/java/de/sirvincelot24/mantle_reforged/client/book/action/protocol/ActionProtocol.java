package de.sirvincelot24.mantle_reforged.client.book.action.protocol;

import de.sirvincelot24.mantle_reforged.client.screen.book.BookScreen;

public abstract class ActionProtocol {
  public abstract void processCommand(BookScreen book, String param);
}
