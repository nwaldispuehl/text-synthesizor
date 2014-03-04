package ch.retorte.textsynthesizor.tokenizer;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import ch.retorte.textsynthesizor.model.Token;

/**
 * Test cases for the {@link SentenceAwareWordTokenizer}.
 * 
 * @author nw
 */
public class SentenceAwareWordTokenizerTest {

  private Tokenizer tokenizer = new SentenceAwareWordTokenizer();

  @Test
  public void shouldReturnEmptyListOnEmptyInputString() {
    // given
    String input = "";

    // when
    List<Token> tokens = tokenizer.tokenize(input);

    // then
    assertTrue(tokens.isEmpty());
  }

  @Test
  public void shouldReturnSingleElement() {
    // given
    String input = "aToken";

    // when
    List<Token> tokens = tokenizer.tokenize(input);

    // then
    assertThat(tokens.size(), is(1));
    assertThat(tokens.get(0).getContent(), is(input));
  }

  @Test
  public void shouldSplitWords() {
    // given
    String input = "a b c";

    // when
    List<Token> tokens = tokenizer.tokenize(input);

    // then
    assertThat(tokens.size(), is(3));
    assertThat(tokens.get(0).getContent(), is("a"));
    assertThat(tokens.get(1).getContent(), is("b"));
    assertThat(tokens.get(2).getContent(), is("c"));
  }

  @Test
  public void shouldSplitDegeneratedWords() {
    // given
    String input = "a     b, c\nd";

    // when
    List<Token> tokens = tokenizer.tokenize(input);

    // then
    assertThat(tokens.size(), is(4));
    assertThat(tokens.get(0).getContent(), is("a"));
    assertThat(tokens.get(1).getContent(), is("b,"));
    assertThat(tokens.get(2).getContent(), is("c"));
    assertThat(tokens.get(3).getContent(), is("d"));
  }

  @Test
  public void shouldNotSplitConnectedWords() {
    // given
    String input = "a.b c,--d";

    // when
    List<Token> tokens = tokenizer.tokenize(input);

    // then
    assertThat(tokens.size(), is(2));
    assertThat(tokens.get(0).getContent(), is("a.b"));
    assertThat(tokens.get(1).getContent(), is("c,--d"));
  }

  @Test
  public void shouldMarkRightWordsAsFirst() {
    // given
    String input = "a b c. d e! f";

    // when
    List<Token> tokens = tokenizer.tokenize(input);

    // then
    assertThat(tokens.size(), is(6));
    assertThat(tokens.get(0).getContent(), is("a"));
    assertTrue(tokens.get(0).isFirst());

    assertThat(tokens.get(1).getContent(), is("b"));
    assertFalse(tokens.get(1).isFirst());

    assertThat(tokens.get(2).getContent(), is("c."));
    assertFalse(tokens.get(2).isFirst());

    assertThat(tokens.get(3).getContent(), is("d"));
    assertTrue(tokens.get(3).isFirst());

    assertThat(tokens.get(4).getContent(), is("e!"));
    assertFalse(tokens.get(4).isFirst());

    assertThat(tokens.get(5).getContent(), is("f"));
    assertTrue(tokens.get(5).isFirst());
  }

  @Test
  public void shouldReturnDelimiter() {
    // when
    String delimiter = tokenizer.getDelimiter();

    // then
    assertThat(delimiter, is(" "));
  }
}
