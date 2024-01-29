package cs3500.hw01.ebooks;

/**
 * An EBookChunk is a larger piece of content within an EBook, like a paragraph
 * or a section, whose position in the document is fixed relative to other chunks.
 */
public interface EBookChunk {
  /**
   * Count the words in this EbookChunk, only complete, non-empty word counts.
   *
   * @return the relevant count
   */
  int countWords();

  /**
   * Checks if this EBookChunk contains the given word.
   * Only complete words are considered; for example, the document "hello" does not contain "ell".
   *
   * @param word the word to check for in this EBookChunk
   * @return true if the given word appears in this EBookChunk
   * @throws IllegalArgumentException if the given word is null,
   *         or contains spaces.
   */
  boolean contains(String word);

  /**
   * Formats the EBookChunk based on the given line width.
   * Each chunk should be separated by a blank line ("\n\n").
   *
   * @param lineWidth the desired line width for formatting
   * @return a formatted String representing the EBookChunk
   * @throws IllegalArgumentException if any word in the ebook is longer than lineWidth
   */
  String format(int lineWidth);
}


