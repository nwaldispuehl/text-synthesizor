package ch.retorte.textsynthesizor.model;

import ch.retorte.textsynthesizor.tokenizer.Tokenizer;

/**
 * Representation of a token of the input string. This string may be tokenized by the {@link Tokenizer} into arbitrary token strings, which is then stored in
 * the content of this object. If this token is suitable to start a newly generated string, the isFirst flag has to be set to true.
 * 
 * @author nw
 */
public class Token {

  private final String content;
  private final boolean isFirst;

  public Token(String content, boolean isFirst) {
    this.content = content;
    this.isFirst = isFirst;
  }

  public String getContent() {
    return content;
  }

  public boolean isFirst() {
    return isFirst;
  }

  public boolean isEmpty() {
    return content.isEmpty();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Token) {
      return content.equals(((Token) obj).getContent());
    }

    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return content.hashCode();
  }

  @Override
  public String toString() {
    return content;
  }

}
