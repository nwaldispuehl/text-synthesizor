package ch.retorte.textsynthesizor;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the {@link TextSynthesizor}.
 * 
 * @author nw
 */
public class TextSynthesizorTest {

  private TextSynthesizor textSynthesizor = spy(new TextSynthesizor());

  @Before
  public void setup() {
    doNothing().when(textSynthesizor).printUsageMessage();
    doNothing().when(textSynthesizor).quitProgram();
  }

  @Test
  public void shouldParseArguments_printUsageOnEmptyArgs() {
    // given
    String[] args = new String[0];
    textSynthesizor.createOptions();

    // when
    textSynthesizor.parseArguments(args);

    // then
    verify(textSynthesizor).printUsageMessage();
    verify(textSynthesizor).quitProgram();
  }

  @Test
  public void shouldParseArguments_readProperties() {
    // given
    String[] args = new String[] { "-n", "3", "-s", "40", "-t", "letter", "/abc/def" };
    textSynthesizor.createOptions();

    // when
    textSynthesizor.parseArguments(args);

    // then
    assertThat(textSynthesizor.getNGramSize(), is(3));
    assertThat(textSynthesizor.getOutputSize(), is(40));
    assertThat(textSynthesizor.getCustomTokenizerName(), is("letter"));
    assertThat(textSynthesizor.getInputFilePath(), is("/abc/def"));
  }

}
