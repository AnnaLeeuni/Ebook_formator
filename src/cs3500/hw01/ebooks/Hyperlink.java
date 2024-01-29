package cs3500.hw01.ebooks;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A Hyperlink contains a flow of content to show,
 * with an associated link that lead people to a specified destination on a website.
 */
public class Hyperlink implements EBookFlow {
  private final TextFlow content;
  private final String linkDestination;

  /**
   * Constructs a Hyperlink with the provided content and link destination.
   *
   * @param content the TextFlow representing the content to be shown
   * @param linkDestination the destination link on a website
   */
  public Hyperlink(TextFlow content, String linkDestination) {
    this.content = Objects.requireNonNull(content);
    this.linkDestination = Objects.requireNonNull(linkDestination);
  }


  /**
   * Checks if the provided hyperlink is exactly the same as this one.
   * They must have the same TextFlow content and the same link destination.
   *
   * @param other the Hyperlink to compare with
   * @return true if the hyperlinks have the same content and link destination
   */
  public boolean exact(Hyperlink other) {
    return this.getContent().toString().equals(other.getContent().toString())
            && this.getLink().equals(other.getLink());

  }


  /**
   * Counts the number of complete, non-empty words in this hyperlink's content.
   *
   * @return the count of relevant words
   */
  @Override
  public int countWords() {
    return content.countWords();
  }

  /**
   * Checks if this hyperlink's content contains the given word.
   * Only complete words are considered; e.g., "hello" does not contain "ell".
   *
   * @param word the word to check for in this hyperlink's content
   * @return true if the given word appears in this hyperlink's content
   * @throws IllegalArgumentException if the given word is null or contains spaces
   */
  @Override
  public boolean contains(String word) {
    return content.contains(word);
  }

  /**
   * Converts this hyperlink's content TextFlow into a List of Strings,
   * where each element in the list is one word from the TextFlow placed in order.
   * If the TextFlow is empty, returns an empty List.
   *
   * @return a List of Strings representing the words in the hyperlink's content
   */
  public List<String> stringInToList() {
    String[] wordsArray = this.toString().split("\\s+");
    return Arrays.asList(wordsArray);
  }


  /**
   * Gets the content of the hyperlink.
   *
   * @return the TextFlow content of this hyperlink
   */
  public TextFlow getContent() {
    return this.content;
  }

  /**
   * Gets the linkDestination of the hyperlink.
   *
   * @return the linkDestination of this hyperlink
   */
  public String getLink() {
    return linkDestination;
  }

  /**
   * Returns the content as the string representation.
   *
   * @return String content of the hyperlink's content
   */
  @Override
  public String toString() {
    return content.toString();

  }
}