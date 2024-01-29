package cs3500.hw01.ebooks;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * A TextFlow represents a string consisting of words.
 */
public class TextFlow implements EBookFlow {
  private final String content;

  /**
   * Constructs a TextFlow with the given content.
   *
   * @param content the string of words representing the content of the TextFlow
   * @throws IllegalArgumentException if the content contains line breaks
   */

  public TextFlow(String content) {
    this.content = Objects.requireNonNull(content);
    if (content.contains("\n")) {
      throw new IllegalArgumentException("Text flows cannot contain line breaks");
    }
  }


  /**
   * Counts the number of complete, non-empty words in this TextFlow.
   *
   * @return the count of relevant words
   */
  public int countWords() {

    List<String> words = this.stringInToList();
    // Count non-empty words
    int count = 0;
    for (String word : words) {
      if (!word.isEmpty()) {
        count++;
      }
    }
    return count;
  }


  /**
   * Checks if this TextFlow contains the given word.
   * Only complete words are considered; e.g., "hello" does not contain "ell".
   *
   * @param word the word to check for in this TextFlow
   * @return true if the given word appears in this TextFlow
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

    if (!word.trim().isEmpty()) {
      List<String> wordsInContent = this.stringInToList();
      for (String contentWord : wordsInContent) {
        if (contentWord.equals(word)) {
          return true;
        }
      }
    }
    return false;
  }


  /**
   * converts a TextFlow in to a List of Strings,
   * where each word is a single item in the list.
   *
   * @return List of Strings as List of words
   *
   */
  public List<String> stringInToList() {
    String flow = this.toString().trim();

    if (flow.isEmpty()) {
      return List.of();
    } else {
      String[] wordsArray = flow.split("\\s+");
      return Arrays.asList(wordsArray);
    }
  }


  /**
   * Converts a TextFlow's content into a single String.
   *
   * @return A String of TextFlow's content
   *
   */
  @Override
  public String toString() {
    return content;
    // Return the content as the string representation
  }

}



