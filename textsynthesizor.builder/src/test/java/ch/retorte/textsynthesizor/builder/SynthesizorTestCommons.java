package ch.retorte.textsynthesizor.builder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import ch.retorte.textsynthesizor.model.Token;

/**
 * Tools needed in more than one test class.
 * 
 * @author nw
 */
public class SynthesizorTestCommons {

  protected Token createTokenWith(String content) {
    return createTokenWith(content, false);
  }

  protected Token createTokenWith(String content, boolean isFirst) {
    Token t = mock(Token.class);
    when(t.getContent()).thenReturn(content);
    when(t.isFirst()).thenReturn(isFirst);
    return t;
  }
}
