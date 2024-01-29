package cs3500.hw01.ebooks;

import java.util.List;
import java.util.ArrayList;

/**
 * A Paragraph of an e-book consists of a chunk of plain text.
 */
public class Paragraph implements EBookChunk {
  private final List<EBookFlow> contents;

  /**
   * Constructs a Paragraph with the given contents.
   *
   * @param contents the list of EBookFlow instances representing the contents of the paragraph
   * @throws IllegalArgumentException if the contents list is null or contains null elements
   */

  public Paragraph(List<EBookFlow> contents) {
    validateContents(contents);
    this.contents = new ArrayList<>(contents);
  }


  private static void validateContents(List<EBookFlow> content) {
    if (content == null) {
      throw new IllegalArgumentException("Contentlist cannot be null");
    }
    if (content.stream().anyMatch(c -> c == null)) {
      throw new IllegalArgumentException("Content list cannot contain null content");
    }
  }

  /**
   * Gets the content of the Paragraph.
   *
   @return the list of EBookFlow instances representing the content of this paragraph
   */
  public List<EBookFlow> getContents() {
    return this.contents;
  }


  /**
   * Counts the number of complete, non-empty words in this paragraph.
   *
   * @return the count of relevant words
   */
  public int countWords() {

    return this.contents.stream().mapToInt(EBookFlow::countWords).sum();
  }

  /**
   * Checks if this e-book paragraph contains the given word.
   * Only complete words are considered; for example, the document "hello" does not contain "ell".
   * The word in the e-book title is also counted.
   *
   * @param word the word to check for in this e-book paragraph
   * @return true if the given word appears in this e-book paragraph
   * @throws IllegalArgumentException if the given word is null or contains spaces
   */
  public boolean contains(String word) {
    if (word == null) {
      throw new IllegalArgumentException("Invalid word");
    }
    if (word.contains(" ")) {
      throw new IllegalArgumentException();
    }
    return this.contents.stream().anyMatch(c -> c.contains(word));
  }


  /**
   * Line-wraps the paragraph to fit within a given line width.
   * Words are separated by '\n' into the next line or only whitespace in between.
   *
   * @param lineWidth the desired line width for formatting
   * @return a String with the paragraph line-wrapped nicely
   * @throws IllegalArgumentException if any word in the e-book is longer than the line width
   *                                  or if lineWidth is equals/smaller than zero
   */
  public String format(int lineWidth) {

    if (lineWidth <= 0) {
      throw new IllegalArgumentException();
    }

    List<EBookFlow> flows = this.getContents();
    List<String> allWords = new ArrayList<>();

    // Collect all words from all TextFlows into a single list
    for (EBookFlow flow : flows) {
      allWords.addAll(flow.stringInToList());
    }

    StringBuilder formattedParagraph = new StringBuilder();
    int currentLineWidth = 0;

    // start formatting
    for (String word : allWords) {
      if (word.length() > lineWidth) {
        throw new IllegalArgumentException("Word exceeds the line width: " + word);
      }
      // Check if adding the next word would exceed the lineWidth
      if (currentLineWidth + word.length() + 1 <= lineWidth) {
        if (currentLineWidth > 0) {
          formattedParagraph.append(" "); // Add space between words
          currentLineWidth++;
        }
        formattedParagraph.append(word);
        currentLineWidth += word.length();
      } else {
        formattedParagraph.append("\n").append(word); // Start a new line
        currentLineWidth = word.length();
      }
    }
    if (formattedParagraph.toString().trim().isEmpty()) {
      return "";
    } else {
      return formattedParagraph.toString().trim(); // Remove trailing whitespace
    }

  }

}
