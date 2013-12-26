package ch.retorte.textsynthesizor.tokenizer;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import ch.retorte.textsynthesizor.model.Token;

/**
 * Test cases for the {@link LetterTokenizer}.
 * 
 * @author nw
 */
public class LetterTokenizerTest {
  private Tokenizer tokenizer = new LetterTokenizer();

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
    String input = "a";

    // when
    List<Token> tokens = tokenizer.tokenize(input);

    // then
    assertThat(tokens.size(), is(1));
    assertThat(tokens.get(0).getContent(), is(input));
  }

  @Test
  public void shouldSplitLetters() {
    // given
    String input = "a b c";

    // when
    List<Token> tokens = tokenizer.tokenize(input);

    // then
    assertThat(tokens.size(), is(5));
    assertThat(tokens.get(0).getContent(), is("a"));
    assertThat(tokens.get(1).getContent(), is(" "));
    assertThat(tokens.get(2).getContent(), is("b"));
    assertThat(tokens.get(3).getContent(), is(" "));
    assertThat(tokens.get(4).getContent(), is("c"));
  }

  @Test
  public void shouldSplitDegeneratedLetters() {
    // given
    String input = "ab, c\nd";

    // when
    List<Token> tokens = tokenizer.tokenize(input);

    // then
    assertThat(tokens.size(), is(7));
    assertThat(tokens.get(0).getContent(), is("a"));
    assertThat(tokens.get(1).getContent(), is("b"));
    assertThat(tokens.get(2).getContent(), is(","));
    assertThat(tokens.get(3).getContent(), is(" "));
    assertThat(tokens.get(4).getContent(), is("c"));
    assertThat(tokens.get(5).getContent(), is("\n"));
    assertThat(tokens.get(6).getContent(), is("d"));
  }

  @Test
  public void shouldReturnDelimiter() {
    // when
    String delimiter = tokenizer.getDelimiter();

    // then
    assertThat(delimiter, is(""));
  }
}
