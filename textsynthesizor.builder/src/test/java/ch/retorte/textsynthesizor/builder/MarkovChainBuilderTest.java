package ch.retorte.textsynthesizor.builder;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import ch.retorte.textsynthesizor.builder.MarkovChainBuilder;
import ch.retorte.textsynthesizor.model.Token;
import ch.retorte.textsynthesizor.tokenizer.Tokenizer;

/**
 * Test cases for the {@link MarkovChainBuilder}.
 * 
 * @author nw
 */
public class MarkovChainBuilderTest extends SynthesizorTestCommons {

  private Tokenizer tokenizer = mock(Tokenizer.class);
  private MarkovChainBuilder builder;

  @Before
  public void setup() {
    builder = spy(new MarkovChainBuilder(tokenizer));
    when(tokenizer.getDelimiter()).thenReturn("");
  }

  @Test
  public void shouldCallTokenizer() {
    // given
    String input = "myInput";

    // when
    builder.generateTokensOf(input);

    // then
    verify(tokenizer).tokenize(input);
  }

  @Test
  public void shouldBuildProbabilityTable() {
    // given
    int n = 1;
    List<Token> tokenList = newArrayList();
    tokenList.add(createTokenWith("a", true));
    tokenList.add(createTokenWith("b"));
    tokenList.add(createTokenWith("c"));
    when(builder.getTokens()).thenReturn(tokenList);

    // when
    builder.buildProbabilityTableWith(n);

    // then
    verify(builder, times(tokenList.size())).addToChain(Mockito.anyListOf(Token.class), Mockito.any(Token.class));
  }

  @Test
  public void shouldPrepareTokenList() {
    // given
    int n = 1;
    List<Token> tokenList = newArrayList();
    tokenList.add(createTokenWith("a", true));
    tokenList.add(createTokenWith("b"));
    tokenList.add(createTokenWith("c"));

    // when
    List<Token> preparedTokenList = builder.prepareTokenList(tokenList, n);

    // then
    assertThat(preparedTokenList.size(), is(4));
    assertThat(preparedTokenList.get(0).getContent(), is(""));
  }

  @Test
  public void shouldGenerateStringOfLength() {
    // given
    builder.initializeBufferWithSize(1);
    Token createdToken = createTokenWith("abc");
    when(builder.extractNextToken()).thenReturn(createdToken);
    when(builder.isChainEmpty()).thenReturn(false);

    // when
    builder.generateStringOfLength(1);

    // then
    verify(builder).extractNextToken();
  }

}
