package de.sirvincelot24.mantle_reforged.client.book.transformer;

import de.sirvincelot24.mantle_reforged.client.book.data.BookData;
import de.sirvincelot24.mantle_reforged.client.book.data.PageData;
import de.sirvincelot24.mantle_reforged.client.book.data.SectionData;
import de.sirvincelot24.mantle_reforged.client.book.data.content.ContentListing;

import javax.annotation.Nullable;

/**
 * Transformer to create a simple list of elements
 */
public class ContentListingSectionTransformer extends SectionTransformer {
  private final Boolean largeTitle;
  private final Boolean centerTitle;
  public ContentListingSectionTransformer(String sectionName, @Nullable Boolean largeTitle, @Nullable Boolean centerTitle) {
    super(sectionName);
    this.largeTitle = largeTitle;
    this.centerTitle = centerTitle;
  }
  public ContentListingSectionTransformer(String sectionName) {
    this(sectionName, null, null);
  }

  @Override
  public void transform(BookData book, SectionData data) {
    ContentListing listing = new ContentListing();
    listing.setLargeTitle(largeTitle);
    listing.setCenterTitle(centerTitle);
    listing.title = book.translate(sectionName);
    String subtextKey = sectionName + ".subtext";
    if (book.strings.containsKey(subtextKey)) {
      listing.subText = book.translate(subtextKey);
    }

    PageData listingPage = new PageData(true);
    listingPage.name = sectionName;
    listingPage.source = data.source;
    listingPage.parent = data;
    listingPage.content = listing;

    data.pages.removeIf(sectionPage -> !processPage(book, listing, sectionPage));
    if (listing.hasEntries()) {
      listingPage.load();
      data.pages.add(0, listingPage);
    }
  }

  /**
   * Builds the listing
   * @return true if the page should be removed
   */
  protected boolean processPage(BookData book, ContentListing listing, PageData page) {
    if (!IndexTransformer.isPageHidden(page) && !page.name.equals("hidden")) {
      listing.addEntry(page.getTitle(), page);
    }
    return true;
  }
}
