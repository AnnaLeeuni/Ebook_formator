import cs3500.hw01.ebooks.TextFlow;
import cs3500.hw01.ebooks.Paragraph;
import cs3500.hw01.ebooks.Section;
import cs3500.hw01.ebooks.EBook;

import org.junit.Assert;

import static org.junit.Assert.assertThrows;

import org.junit.Test;

import java.util.List;


/**
 * Tests the method contains and counts for examplar.
 */
public class ExamplarEBooks {

  // test contains if the word is incomplete
  @Test
  public void exampleContains() {

    EBook book = new EBook(
            List.of(new Paragraph(
                    List.of(new TextFlow("apple"),
                            new TextFlow("apple banana")))));

    boolean yes = book.contains("ban");
    Assert.assertFalse(yes);
  }

  // tests of word is a blank string
  @Test
  public void exampleContainBlank() {
    EBook book = new EBook(
            List.of(new Paragraph(
                    List.of(new TextFlow("apple"),
                            new TextFlow("apple banana")))));

    assertThrows(IllegalArgumentException.class, () -> book.contains(" "));

  }

  // tests a working count with title
  @Test
  public void exampleCountWithTitle() {
    EBook book = new EBook(
            List.of(new Section("Many Fruits",
                    List.of(new Paragraph(
                            List.of(new TextFlow("apple"),
                                    new TextFlow("apple banana")))))));

    int count = book.countWords();
    Assert.assertEquals(5, count);
  }


  // tests if contains try to find more than one word
  @Test
  public void exampleContainsMore() {
    EBook book = new EBook(
            List.of(new Section("Many Fruits",
                    List.of(new Paragraph(
                            List.of(new TextFlow("apple"),
                                    new TextFlow("apple banana")))))));


    assertThrows(RuntimeException.class, () -> book.contains("Many Fruits"));
  }

  // tests contains if the word is incomplete in title
  @Test
  public void exampleContainsLess() {
    EBook book = new EBook(
            List.of(new Section("Many Fruits",
                    List.of(new Paragraph(
                            List.of(new TextFlow("apple"),
                                    new TextFlow("apple banana")))))));


    boolean no = book.contains("any");
    Assert.assertFalse(no);
  }


}