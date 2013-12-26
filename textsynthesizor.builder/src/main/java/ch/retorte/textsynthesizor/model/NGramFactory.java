package ch.retorte.textsynthesizor.model;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

/**
 * Factory for creating a {@link NGram}.
 * 
 * @author nw
 */
public class NGramFactory {

  public NGram createFrom(Token... tokens) {
    return createFrom(newArrayList(tokens));
  }

  public NGram createFrom(List<Token> tokens) {
    return new NGram(tokens);
  }

}
