package cs3500.hw01.ebooks;

import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

/**
 * A Section of an e-book consists of a section title, followed by
 * a sequence of e-book chunks (which could be paragraphs, sub-sections, etc.)
 */
public class Section implements EBookChunk {
  private final String title;
  private final List<EBookChunk> contents;


  /**
   * Constructs a Section with the given title and contents.
   *
   * @param title the title of the section
   * @param contents the list of e-book chunks representing the contents of the section
   * @throws IllegalArgumentException if the title contains line breaks or if contents is null
   */
  public Section(String title, List<EBookChunk> contents) {
    this.title = formatTitle(Objects.requireNonNull(title));

    if (title.contains("\n")) {
      throw new IllegalArgumentException("Titles cannot contain line breaks");
    }
    this.contents = new ArrayList<>(Objects.requireNonNull(contents));
  }

  /**
   * trim any leading, in between or trailing space of the title String.
   *
   * @param title the title string of this section
   * @return a revised, clean title String
   */
  public String formatTitle(String title) {
    // Trim the content to remove extra spaces at the beginning and end
    String trimmedContent = title.trim();
    // Replace multiple spaces with a single space
    return trimmedContent.replaceAll("\\s+", " ");
  }

  /**
   * Gets the title of the section.
   *
   * @return the title of the section
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * Gets the content list of e-book chunks in this section.
   *
   * @return the list of e-book chunks as contents
   */
  public List<EBookChunk> getContents() {
    return this.contents;
  }


  /**
   * Counts the number of complete, non-empty words in this Section.
   *
   * @return the count of relevant words
   */
  @Override
  public int countWords() {
    String[] titleWords = this.title.split("\\s+");
    int titleWordCount = 0;
    for (String word : titleWords) {
      if (!word.isEmpty()) {
        titleWordCount++;
      }
    }

    int contentWordCount = this.contents.stream().mapToInt(EBookChunk::countWords).sum();
    return titleWordCount + contentWordCount;
  }

  /**
   * Checks if this section contains the given word.
   * Only complete words are considered; for example, the document "hello" does not contain "ell".
   * The word in the title is also counted.
   *
   * @param word the word to check for in this section
   * @return true if the given word appears in this section
   * @throws IllegalArgumentException if the given word is null,
   *                                  or contains spaces.
   */
  public boolean contains(String word) {

    if (word == null) {
      throw new IllegalArgumentException("Invalid word");
    }
    if (word.contains(" ")) {
      throw new IllegalArgumentException();
    }

    TextFlow theTitle = new TextFlow(this.title);

    return theTitle.contains(word) || this.contents.stream().anyMatch(c -> c.contains(word));
  }

  /**
   * Line-wraps the section to fit within a given line width.
   * The title is separated from its content with a line break.
   *
   * @param lineWidth the desired line width for formatting
   * @return a String with the section line-wrapped nicely
   * @throws IllegalArgumentException if any word in the section is longer than the line width
   */
  public String format(int lineWidth) {
    StringBuilder formattedSection = new StringBuilder();

    Paragraph title = new Paragraph(List.of(new TextFlow(this.title)));
    // Format the section title as a paragraph
    formattedSection.append(title.format(lineWidth));
    formattedSection.append("\n"); // Separate the title from content with a single newline

    for (EBookChunk chunk : this.getContents()) {
      formattedSection.append(chunk.format(lineWidth));
      formattedSection.append("\n\n");
    }
    return formattedSection.toString().trim(); // Remove trailing whitespace
  }
}
