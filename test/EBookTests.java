import cs3500.hw01.ebooks.TextFlow;
import cs3500.hw01.ebooks.Paragraph;
import cs3500.hw01.ebooks.EBookFlow;
import cs3500.hw01.ebooks.Section;
import cs3500.hw01.ebooks.EBook;
import cs3500.hw01.ebooks.EBookChunk;
import cs3500.hw01.ebooks.Hyperlink;

import org.junit.Assert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import org.junit.Test;


import java.util.List;
import java.util.ArrayList;


/**
 * The rest of the tests and examples.
 */
public class EBookTests {

  // test countWords in TextFlow
  @Test
  public void exampleCountsTextFlow() {
    TextFlow flow1 = new TextFlow("Two words  ");
    TextFlow flow2 = new TextFlow("   Two   words");

    int count = flow1.countWords();
    Assert.assertEquals(count, 2);

    Paragraph paragraph = new Paragraph(List.of(flow1, flow2));
    int countParagraph = paragraph.countWords();
    Assert.assertEquals(countParagraph, 4);

  }

  //Tests countWords in Paragraph
  @Test
  public void testParagraphCountsWords() {
    // Create a paragraph with three TextFlows
    List<EBookFlow> flows = new ArrayList<>();
    flows.add(new TextFlow("   This is a   sentence "));
    flows.add(new TextFlow("  Another   sentence here   "));
    flows.add(new TextFlow("  One more sentence    "));

    Paragraph paragraph = new Paragraph(flows);

    // Total words in the paragraph should be the sum of words in each TextFlow
    int expectedTotalWords = 10;

    // Ensure the countWords() method in Paragraph returns the correct word count
    Assert.assertEquals(expectedTotalWords, paragraph.countWords());
  }


  //Tests countWords with Title that has random spaces
  @Test
  public void exampleCountsWordsTitle() {
    Paragraph paragraph = new Paragraph(List.of(
            new TextFlow("ood"),
            new TextFlow("ood")
    ));

    EBook book = new EBook(
            List.of(new Section("   Object   orientated design   ", List.of(paragraph))));

    Assert.assertEquals(book.countWords(), 5);
  }


  // Test contains in hyperLink
  @Test
  public void exampleHyperlinkContains() {

    TextFlow studentHub = new TextFlow("Here is StudentHub");
    String hubLink = "northeastern.sharepoint.com/sites/StudentHub";

    EBookFlow hyperlink = new Hyperlink(studentHub, hubLink);

    Assert.assertTrue(hyperlink.contains("Here"));
    Assert.assertFalse(hyperlink.contains("northeastern"));
    Assert.assertFalse(hyperlink.contains("apple"));
  }

  // Test countWords in hyperLink
  @Test
  public void exampleHyperlinkCountWords() {

    TextFlow studentHub = new TextFlow("Here is StudentHub");
    String hubLink = "northeastern.sharepoint.com/sites/StudentHub";

    EBookFlow hyperlinkHub = new Hyperlink(studentHub, hubLink);
    Assert.assertEquals(3, hyperlinkHub.countWords());
  }


  // and more tests for the edge cases in hyperLink
  @Test
  public void exampleEBookContainsHyperlink() {
    String hubLink = "northeastern.sharepoint.com/sites/StudentHub";

    EBook book = new EBook(
            List.of(new Section("School in Boston",
                    List.of(new Paragraph(List.of(
                            new TextFlow("Northeastern"),
                            new Hyperlink(new TextFlow("studentHub website"),
                                    hubLink)
                    ))))));

    // repeat every chaff from examplarEBookTest:
    Assert.assertTrue(book.contains("studentHub"));
    Assert.assertTrue(book.contains("Boston"));
    Assert.assertFalse(book.contains("Google"));
    Assert.assertTrue(book.contains("Northeastern"));
    Assert.assertFalse(book.contains("east"));
    Assert.assertFalse(book.contains("sites"));
    assertThrows(RuntimeException.class, () -> book.contains("studentHub website"));
    assertThrows(RuntimeException.class, () -> book.contains("in Boston"));
    assertThrows(IllegalArgumentException.class, () -> book.contains(" "));
    Assert.assertFalse(book.contains("Bos"));

  }

  // tests the getContent in HyperLink, Paragraph and Section
  @Test
  public void exampleTestGetContent() {

    List<EBookChunk> paragraph = List.of(new Paragraph(List.of(
            new TextFlow("Northeastern"))));
    Section school = new Section("School in Boston", paragraph);
    Assert.assertEquals(school.getContents(), paragraph);

    List<EBookFlow> flows = List.of(new TextFlow("This is the first flow."));
    Paragraph paragraph2 = new Paragraph(flows);
    Assert.assertEquals(flows, paragraph2.getContents());

    TextFlow flow = new TextFlow("Google");
    Hyperlink hyperLink = new Hyperlink(flow,
            "https://www.google.com/maps");
    Assert.assertEquals(flow, hyperLink.getContent());
  }

  // tests the toString method in TextFlow and HyperLink
  @Test
  public void exampleToString() {
    TextFlow flow = new TextFlow("A flow");
    Assert.assertEquals("A flow", flow.toString());

    Hyperlink hyperLink = new Hyperlink(flow, "www.flow");
    Assert.assertEquals("A flow", hyperLink.toString());
  }



  // tests getTitle in Section
  @Test
  public void exampleTestGetTitle() {

    List<EBookChunk> paragraph = List.of(new Paragraph(List.of(
            new TextFlow("Northeastern"))));
    Section school = new Section("School in Boston", paragraph);
    Assert.assertEquals("School in Boston", school.getTitle());

  }

  // tests getLink in hyperLiink
  @Test
  public void exampleGetLink() {
    Hyperlink link = new Hyperlink(new TextFlow("Google Map"),
            "https://www.google.com/maps");
    Assert.assertEquals("https://www.google.com/maps", link.getLink());
  }

  // tests exactLink in Section
  @Test
  public void exampleExactLink() {
    Hyperlink link1 = new Hyperlink(new TextFlow("Google Map"),
            "https://www.google.com/maps");

    Hyperlink link2 = new Hyperlink(new TextFlow("Google Map"),
            "https://www.google.com/maps");

    Assert.assertTrue(link1.exact(link2));
  }

  //Tests StringInToList for TextFlow and HyperLink
  @Test
  public void exampleStringInToList() {
    TextFlow flow = new TextFlow("A flow");
    Hyperlink link = new Hyperlink(new TextFlow("Google Map"),
            "https://www.google.com/maps");

    Assert.assertEquals(List.of("A", "flow"), flow.stringInToList());
    Assert.assertEquals(List.of("Google", "Map"), link.stringInToList());
  }


  //Tests the method formatTitle in Section
  @Test
  public void exampleFormatTitle() {
    String titleWithExtraSpaces = "  This   is  a     title ";
    String expectedFormattedTitle = "This is a title";
    List<EBookChunk> paragraph = List.of(new Paragraph(List.of(
            new TextFlow("Northeastern"))));

    Section section = new Section(titleWithExtraSpaces, paragraph);
    String formattedTitle = section.formatTitle(titleWithExtraSpaces);

    Assert.assertEquals(expectedFormattedTitle, formattedTitle);
  }

  // Tests with emptyString in Title
  @Test
  public void testFormatTitleWithEmptyString() {
    String emptyTitle = "";
    String expectedFormattedTitle = "";
    List<EBookChunk> paragraph = List.of(new Paragraph(List.of(
            new TextFlow("Northeastern"))));

    Section section = new Section(emptyTitle, paragraph);
    String formattedTitle = section.formatTitle(emptyTitle);

    assertEquals(expectedFormattedTitle, formattedTitle);
  }




  // test format in Paragraph
  @Test
  public void exampleFormatParagraph() {
    Paragraph paragraph = new Paragraph(List.of(new TextFlow(
            "Object orientated design will be hard to get an A"),
            new TextFlow("There will be nine assignments")
    ));

    EBook book = new EBook(List.of(paragraph));

    // Format the paragraph
    String formattedParagraph = paragraph.format(20);

    // Expected result with line breaks at appropriate positions
    String expected = "Object orientated\ndesign will be hard\nto get an A There\n"
            + "will be nine\nassignments";

    // Assert that the formatted paragraph matches the expected result
    Assert.assertEquals(expected, formattedParagraph);
  }


  // test format in section
  @Test
  public void exampleFormatSection() {
    List<EBookChunk> paragraph = List.of(new Paragraph(List.of(new TextFlow(
            "Object orientated design will be hard to get an A"),
            new TextFlow("There will be nine assignments")
    )));

    Section section = new Section("Object orientated design", paragraph);
    EBook book = new EBook(List.of(section));

    // Format the paragraph
    String formattedSection = section.format(20);

    // Expected result with line breaks at appropriate positions
    String expected = "Object orientated\ndesign\nObject orientated\ndesign will be "
            + "hard\nto get an A There\n"
            + "will be nine\nassignments";

    // Assert that the formatted paragraph matches the expected result
    Assert.assertEquals(expected, formattedSection);
  }

  // tests format when there is two sub-sections in a existing section
  @Test
  public void exampleFormat2subSectionInASection() {
    List<EBookChunk> paragraph = List.of(new Paragraph(List.of(new TextFlow(
            "Object orientated design will be hard to get an A"),
            new TextFlow("There will be nine assignments")
    )));

    Section sectionSub1 = new Section("Object orientated design", paragraph);
    Section sectionSub2 = new Section("Object orientated design", paragraph);

    Section sectionAll = new Section("sectionAll", (List.of(sectionSub1, sectionSub2)));
    EBook book = new EBook(List.of(sectionAll));

    // Format the paragraph
    String formattedSection = sectionAll.format(20);

    // Expected result with line breaks at appropriate positions
    String expected = "sectionAll\nObject orientated\ndesign\nObject orientated\ndesign will be "
            + "hard\nto get an A There\n"
            + "will be nine\nassignments\n\nObject orientated\ndesign\nObject orientated\ndesign "
            + "will be hard\nto get an A There\nwill be nine\nassignments";

    // Assert that the formatted paragraph matches the expected result
    Assert.assertEquals(expected, formattedSection);

  }

  //tests format for ebook
  @Test
  public void exampleFormat() {
    List<EBookChunk> paragraph = List.of(new Paragraph(List.of(new TextFlow(
            "Object orientated design will be hard to get an A"),
            new TextFlow("There will be nine assignments")
    )));

    Section section = new Section("Object orientated design", paragraph);
    EBook book = new EBook(List.of(section));

    String expected = "Object orientated\ndesign\nObject orientated\ndesign will be hard\nto get an"
            + " A There\nwill be nine\nassignments";
    String formattedBook = book.format(20);
    Assert.assertEquals(expected, formattedBook);

  }

  // test when lineWidth is invalid
  @Test
  public void exampleFormatLineWidthException() {
    List<EBookChunk> paragraph = List.of(new Paragraph(List.of(new TextFlow(
            "Object orientated design will be hard to get an A"),
            new TextFlow("There will be nine assignments")
    )));

    Section section = new Section("Object orientated design", paragraph);
    EBook book = new EBook(List.of(section));

    String expected = "Object orientated\ndesign\nObject orientated\ndesign will be hard\nto get "
            + "an A There\n"
            + "will be nine\nassignments";

    assertThrows(IllegalArgumentException.class, () -> book.format(-3));
    assertThrows(IllegalArgumentException.class, () -> book.format(4));
    assertThrows(IllegalArgumentException.class, () -> book.format(0));
  }

  // test is Multi Chunk Line Break works just fine
  @Test
  public void exampleMultiChunkLineBreakCorrectInBook() {
    Paragraph paragraph = new Paragraph(List.of(
            new TextFlow("Object orientated design will be hard to get an A"),
            new TextFlow("There will be nine assignments")));


    EBook book = new EBook(List.of(paragraph, paragraph));

    // Format the paragraph
    String formattedBook = book.format(20);

    // Expected result with line breaks at appropriate positions
    String expected = "Object orientated\ndesign will be hard\nto get an A There\n"
            + "will be nine\nassignments\n\nObject orientated\ndesign will be hard\n"
            + "to get an A There\nwill be nine\nassignments";

    // Assert that the formatted paragraph matches the expected result
    Assert.assertEquals(expected, formattedBook);
  }

  // test when there is a subsection title right after a section title
  @Test
  public void exampleSubSectionTitle() {
    Paragraph paragraph = new Paragraph(List.of(
            new TextFlow("Object orientated design will be hard to get an A"),
            new TextFlow("There will be nine assignments")));


    EBook book = new EBook(List.of(new Section("Class to take",
            List.of(new Section("ood",
                    (List.of(paragraph, paragraph)))))));

    // Format the paragraph
    String formattedBook = book.format(20);

    // Expected result with line breaks at appropriate positions
    String expected = "Class to take\nood\nObject orientated\ndesign will be hard\nto get an "
            + "A There\n"
            + "will be nine\nassignments\n\nObject orientated\ndesign will be hard\n"
            + "to get an A There\nwill be nine\nassignments";

    // Assert that the formatted paragraph matches the expected result
    Assert.assertEquals(expected, formattedBook);

  }


  // test if there is a blank line between two section
  @Test
  public void exampleBlankLineBetweenSection() {
    Paragraph paragraph = new Paragraph(List.of(
            new TextFlow("Object orientated design will be hard to get an A"),
            new TextFlow("There will be nine assignments")));


    EBook book = new EBook(List.of(new Section("ood", (List.of(paragraph, paragraph))),
            new Section("ood", List.of(paragraph, paragraph))));

    // Format the paragraph
    String formattedBook = book.format(20);

    // Expected result with line breaks at appropriate positions
    String expected = "ood\nObject orientated\ndesign will be hard\nto get an A There\n"
            + "will be nine\nassignments\n\nObject orientated\ndesign will be hard\n"
            + "to get an A There\nwill be nine\nassignments\n\n"
            +  "ood\nObject orientated\ndesign will be hard\nto get an A There\n"
            + "will be nine\nassignments\n\nObject orientated\ndesign will be hard\n"
            + "to get an A There\nwill be nine\nassignments";

    // Assert that the formatted paragraph matches the expected result
    Assert.assertEquals(expected, formattedBook);

  }


  // test if format works just fine if Section has no content
  @Test
  public void exampleSectionWithNoContent() {

    EBook book = new EBook(List.of(new Section("ood", (List.of()))));
    // Format the paragraph
    String formattedBook = book.format(20);

    // Expected result with line breaks at appropriate positions
    String expected = "ood";

    // Assert that the formatted paragraph matches the expected result
    Assert.assertEquals(expected, formattedBook);

  }

  // test mix of combination of ebook with section, sub-section and paragraph
  @Test
  public void exampleMix() {
    Paragraph paragraph2Flow = new Paragraph(List.of(
            new TextFlow("This is the first flow"),
            new TextFlow("This is the second flow")));

    Paragraph paragraph1Flow = new Paragraph(List.of(
            new TextFlow("This is the first flow")));

    TextFlow flows = new TextFlow("This is the first flow");


    EBook book = new EBook(List.of(
            new Paragraph(List.of(flows)),
            new Section("Section 1", (List.of(paragraph2Flow))),
            new Section("Section 2.1", List.of(
                    new Section("Section 2.2", List.of(paragraph1Flow))))));

    // Format the paragraph
    String formattedBook = book.format(20);

    // Expected result with line breaks at appropriate positions
    String expected = "This is the first\n"
            + "flow\n\nSection 1\nThis is the first\nflow This is the\nsecond flow\n\n"
            + "Section 2.1\nSection 2.2\nThis is the first\nflow";

    // Assert that the formatted paragraph matches the expected result
    Assert.assertEquals(expected, formattedBook);

  }


  // tests if empty or whitespaces textFlow works for stringInToList
  @Test
  public void exampleMixEmpty() {

    TextFlow flows = new TextFlow("");
    String flow = flows.toString();

    TextFlow flows2 = new TextFlow("   ");
    String flow2 = flows.toString().trim();

    boolean yes = flow.isEmpty();
    Assert.assertTrue(yes);

    boolean yes2 = flow2.isEmpty();
    Assert.assertTrue(yes);


    boolean yup = flows.stringInToList().isEmpty();
    Assert.assertTrue(yup);
    Assert.assertEquals(0, flows.stringInToList().size());
    Assert.assertEquals(0, flows2.stringInToList().size());

  }


  // test of empty title or empty flow still works in format
  @Test
  public void exampleMix2() {
    Paragraph paragraph2Flow = new Paragraph(List.of(
            new TextFlow("This is the first flow"),
            new TextFlow("")));

    Paragraph paragraph1Flow = new Paragraph(List.of(
            new TextFlow("")));

    TextFlow flows = new TextFlow("     ");


    EBook book = new EBook(List.of(
            new Paragraph(List.of(flows)),
            new Section("    ", (List.of(paragraph2Flow))),
            new Section("Section 2", List.of(
                    new Section("Section 2.1", List.of(paragraph2Flow))))));

    String formattedBook = book.format(20);

    // Expected result with line breaks at appropriate positions
    String expected = "This is the first\nflow\n\nSection 2\nSection 2.1\nThis is the first\nflow";

    // Assert that the formatted paragraph matches the expected result
    Assert.assertEquals(expected, formattedBook);

    Assert.assertEquals(14, book.countWords());

  }

  // test if a empty book can be formatted
  @Test
  public void exampleEmptyBookEmptyTextFlow() {
    TextFlow flows = new TextFlow("     ");
    EBook book = new EBook(List.of(new Paragraph(List.of(flows))));
    String formattedBook = book.format(20);


    // Expected result with line breaks at appropriate positions
    String expected = "";

    Assert.assertEquals(expected, formattedBook);
  }


  // tests when there is only single textflow in the book, other being empty
  @Test
  public void exampleEmptyBookEmptyTitle() {
    TextFlow flows = new TextFlow("    apple ");
    EBook book = new EBook(List.of(new Section("     ",
            List.of(new Paragraph(List.of(flows))))));
    String formattedBook = book.format(20);


    // Expected result with line breaks at appropriate positions
    String expected = "apple";

    Assert.assertEquals(expected, formattedBook);

  }

  // test if format works fine when spaces in random location
  @Test
  public void exampleEmptyBook2LineTitle() {
    TextFlow flows = new TextFlow("    apple ");
    EBook book = new EBook(List.of(new Section("   apple  apple     apple  apple  apple   ",
            List.of(new Paragraph(List.of(flows))))));
    String formattedBook = book.format(20);


    // Expected result with line breaks at appropriate positions
    String expected = "apple apple apple\napple apple\napple";

    Assert.assertEquals(expected, formattedBook);

  }

  // tests countWords if there are random spaces in ebook
  @Test
  public void exampleEmptyBook2LineTitleCount() {
    TextFlow flows = new TextFlow("apple");
    EBook book = new EBook(List.of(new Section("  apple  apple ",
            List.of(new Paragraph(List.of(flows))))));
    String formattedBook = book.format(20);


    Assert.assertEquals(3, book.countWords());
    System.out.println(book.countWords());
  }
}