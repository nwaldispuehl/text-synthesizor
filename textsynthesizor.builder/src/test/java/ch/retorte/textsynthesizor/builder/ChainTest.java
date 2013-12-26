package ch.retorte.textsynthesizor.builder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import ch.retorte.textsynthesizor.builder.Chain;
import ch.retorte.textsynthesizor.model.NGram;
import ch.retorte.textsynthesizor.model.NGramFactory;
import ch.retorte.textsynthesizor.model.Token;

/**
 * Test cases for the {@link Chain}.
 * 
 * @author nw
 */
public class ChainTest extends SynthesizorTestCommons {

  private NGramFactory nGramFactory = new NGramFactory();

  private Token tokenA;
  private Token tokenB;
  private Token emptyToken;
  private NGram nGram;

  @Before
  public void setup() {
    tokenA = createTokenWith("a");
    tokenB = createTokenWith("b");
    emptyToken = createTokenWith("");
    nGram = nGramFactory.createFrom(tokenA);
  }

  @Test
  public void shouldAddToken() {
    // given
    Chain chain = new Chain();

    // when
    chain.add(nGram, tokenB);
    Token nextToken = chain.getNextTokenFor(nGram);

    // then
    assertThat(nextToken, is(tokenB));
  }

  @Test
  public void shouldReturnEmptyTokenInCaseOfMissingToken() {
    // given
    Chain chain = new Chain();

    // when
    Token nextToken = chain.getNextTokenFor(nGram);

    // then
    assertThat(nextToken, is(emptyToken));
  }

}
