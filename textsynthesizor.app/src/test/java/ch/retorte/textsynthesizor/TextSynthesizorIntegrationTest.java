package ch.retorte.textsynthesizor;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import ch.retorte.textsynthesizor.builder.MarkovChainBuilder;
import ch.retorte.textsynthesizor.tokenizer.LetterTokenizer;
import ch.retorte.textsynthesizor.tokenizer.SentenceAwareWordTokenizer;
import ch.retorte.textsynthesizor.tokenizer.SimpleWordTokenizer;
import ch.retorte.textsynthesizor.tokenizer.Tokenizer;

/**
 * Set of test cases which test the actual work of all components together.
 * 
 * @author nw
 */
public class TextSynthesizorIntegrationTest {

  private Tokenizer letter = new LetterTokenizer();
  private Tokenizer word = new SimpleWordTokenizer();
  private Tokenizer sentence = new SentenceAwareWordTokenizer();

  private void testSynthesizorWith(Tokenizer tokenizer, int nGramSize, int outputSize, String input, String expectedOutput) {
    // given
    MarkovChainBuilder builder = new MarkovChainBuilder(tokenizer, nGramSize, input);

    // when
    String generatedText = builder.generateRandomTextOfSize(outputSize);

    // then
    assertThat(generatedText, is(expectedOutput));
  }

  @Test
  public void shouldGenerateSingleLetters() {
    testSynthesizorWith(letter, 0, 3, "a", "aaa");
    testSynthesizorWith(word, 0, 3, "a", "a a a");
  }

  @Test
  public void shouldGenerateSameInput() {
    testSynthesizorWith(word, 1, 6, "Good morning!", "Good morning! Good morning! Good morning!");
  }

  @Test
  public void shouldWorkWithEmptyInputString() {
    testSynthesizorWith(letter, 0, 1, "", "");
    testSynthesizorWith(word, 0, 1, "", "");
    testSynthesizorWith(sentence, 0, 1, "", "");
  }

}
