package ch.retorte.textsynthesizor.model;

/**
 * Factory for creating a {@link Token}.
 * 
 * @author nw
 */
public class TokenFactory {

  private static final String EMPTY_STRING = "";

  public Token createEmptyToken() {
    return createFrom(EMPTY_STRING);
  }

  public Token createFrom(String content) {
    return createFrom(content, false);
  }

  public Token createFrom(String content, boolean isFirst) {
    return new Token(content, isFirst);
  }
}
