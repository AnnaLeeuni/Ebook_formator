package cs3500.hw01.ebooks;

import java.util.List;

/**
 * An EBookFlow is a contiguous chunk of text, like a paragraph, that can
 * line-wrap and flow to resize to fit the e-book reader.
 */
public interface EBookFlow {

  /**
   * Counts the number of complete, non-empty words in this flow.
   * If there are no words, or the flow is empty or only contains whitespace,
   * zero is returned.
   *
   * @return the count of relevant words
   */
  int countWords();

  /**
   * Checks if this flow contains the given word. Only complete words are considered;
   * for example, the document "hello" does not contain the word "ell".
   *
   * @param word the word to check for in this e-book flow
   * @return true if the given word appears in this e-book flow
   * @throws IllegalArgumentException if the given word is null or contains spaces
   */
  boolean contains(String word);


  /**
   * Converts this e-book flow into a List of Strings,
   * where each element in the list is one word from the e-book flow in order.
   * If the e-book flow is empty, returns an empty List.
   *
   * @return a List of Strings representing the words in the e-book flow
   */
  List<String> stringInToList();
}
