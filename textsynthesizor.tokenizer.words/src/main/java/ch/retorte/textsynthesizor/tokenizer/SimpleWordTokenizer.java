package ch.retorte.textsynthesizor.tokenizer;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import ch.retorte.textsynthesizor.model.Token;
import ch.retorte.textsynthesizor.model.TokenFactory;

/**
 * This {@link SimpleWordTokenizer} splits a string up into single words, delimited by spaces.
 * 
 * @author nw
 */
public class SimpleWordTokenizer implements Tokenizer {

  private TokenFactory tokenFactory = new TokenFactory();

  @Override
  public List<Token> tokenize(String input) {
    List<Token> result = newArrayList();

    boolean isFirst = true;
    for (String s : input.split("\\s")) {
      if (!s.isEmpty()) {
        result.add(tokenFactory.createFrom(s, isFirst));
        isFirst = false;
      }
    }

    return result;
  }

  @Override
  public String getDelimiter() {
    return " ";
  }

}
