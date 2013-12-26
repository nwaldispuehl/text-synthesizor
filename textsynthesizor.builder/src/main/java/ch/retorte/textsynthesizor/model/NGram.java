package ch.retorte.textsynthesizor.model;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.google.common.base.Joiner;

/**
 * Immutable n-gram representation.
 * 
 * @author nw
 */
public class NGram {
  
  private static final String N_GRAM_DELIMITER = "-";

  private final List<Token> tokens;
  private final String stringRepresentation;
  
  public NGram(List<Token> tokens) {
    this.tokens = tokens;
    stringRepresentation = stringifyTokens();
  }
  
  public List<Token> getTokens() {
    return tokens;
  }

  public boolean isEmpty() {
    boolean result = true;
    for (Token t : tokens) {
      result &= t.isEmpty();
    }
    return result;
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

  private String stringifyTokens() {
    List<Token> result = newArrayList();
    for (Token t : tokens) {
      if (!t.getContent().isEmpty()) {
        result.add(t);
      }
    }

    return Joiner.on(N_GRAM_DELIMITER).join(result);
  }
}