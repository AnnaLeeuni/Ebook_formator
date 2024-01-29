package cs3500.hw01.ebooks;

import java.util.List;
import java.util.ArrayList;

/**
 * A simple representation of an e-book, that can contain an arbitrary
 * vertical collection of horizontally-wrapped text content.
 */
public final class EBook {
  private final List<EBookChunk> chunks;

  /**
   * Constructs an e-book with the given list of e-book chunks.
   *
   * @param chunks the list of e-book chunks to include in the e-book
   * @throws IllegalArgumentException if the chunks list is null or contains null elements
   */
  public EBook(List<EBookChunk> chunks) {
    validateChunks(chunks);
    this.chunks = new ArrayList<>(chunks);
  }

  private static void validateChunks(List<EBookChunk> chunks) {
    if (chunks == null) {
      throw new IllegalArgumentException("Chunk list cannot be null");
    }
    if (chunks.stream().anyMatch(c -> c == null)) {
      throw new IllegalArgumentException("Chunk list cannot contain null chunk");
    }
  }

  /**
   * Counts the number of complete, non-empty words in this e-book.
   * Includes the section titles.
   *
   * @return the count of relevant words
   */
  public int countWords() {
    int ans = 0;
    for (EBookChunk chunk : this.chunks) {
      ans += chunk.countWords();
    }
    return ans;
  }

  /**
   * Checks if this e-book contains the given word.
   * Only complete words are considered; for example, the document "hello" does not contain "ell".
   * The word in the e-book title is also counted.
   *
   * @param word the word to check for in this e-book
   * @return true if the given word appears in this e-book
   * @throws IllegalArgumentException if the given word is null,
   *                                  or contains spaces.
   */
  public boolean contains(String word) {
    return this.chunks.stream().anyMatch(c -> c.contains(word));
  }


  /**
   * Line-wraps the e-book based on a given line width.
   * Each chunk is separated by a blank line ("\n\n").
   *
   * @param lineWidth the desired line width for formatting
   * @return a String with the e-book line-wrapped nicely
   * @throws IllegalArgumentException if any word in the ebook is longer than lineWidth
   */

  public String format(int lineWidth) {
    if (lineWidth <= 0) {
      throw new IllegalArgumentException();
    }


    StringBuilder formattedEBook = new StringBuilder();

    for (EBookChunk chunk : this.chunks) {
      formattedEBook.append(chunk.format(lineWidth));
      formattedEBook.append("\n\n");
    }

    return formattedEBook.toString().trim(); // Remove trailing whitespace
  }

}
