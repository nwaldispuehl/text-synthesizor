package ch.retorte.textsynthesizor.model;

import static com.google.common.base.Joiner.on;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

/**
 * Immutable n-gram representation.
 * 
 * @author nw
 */
public class NGram {
  
  private static final String N_GRAM_DELIMITER = "-";

  private final String stringRepresentation;
  
  public NGram(List<Token> tokens) {
    stringRepresentation = stringifyTokens(tokens);
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof NGram) {
      return stringRepresentation.equals(((NGram) obj).stringRepresentation);
    }
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return stringRepresentation.hashCode();
  }

  @Override
  public String toString() {
    return "[" + stringRepresentation + "]";
  }

  private String stringifyTokens(List<Token> tokens) {
    List<Token> result = newArrayList();
    for (Token t : tokens) {
      if (!t.getContent().isEmpty()) {
        result.add(t);
      }
    }

    return on(N_GRAM_DELIMITER).join(result);
  }
}
