package ch.retorte.textsynthesizor.tokenizer;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import ch.retorte.textsynthesizor.model.Token;
import ch.retorte.textsynthesizor.model.TokenFactory;

/**
 * This {@link SentenceAwareWordTokenizer} splits a string up into single words, delimited by spaces, but also marks the first tokens of sentences with the
 * 'isFirst' flag. This has the effect that the text generator considers every sentence on its own. The synthesized text thus might start with the first token
 * of every sentence of the source, as opposed to the {@link SimpleWordTokenizer} which treats the input text a one long sentence so to say.
 * 
 * @author nw
 */
public class SentenceAwareWordTokenizer implements Tokenizer {

  private TokenFactory tokenFactory = new TokenFactory();

  @Override
  public List<Token> tokenize(String input) {
    List<Token> result = newArrayList();

    boolean isFirst = true;
    for (String s : input.split("\\s")) {
      if (!s.isEmpty()) {
        result.add(tokenFactory.createFrom(s, isFirst));
        isFirst = false;

        for (String terminator : getSequenceTerminators()) {
          if (s.endsWith(terminator)) {
            isFirst = true;
          }
        }
      }
    }

    return result;
  }

  @Override
  public String getDelimiter() {
    return " ";
  }

  private String[] getSequenceTerminators() {
    return new String[] { ".", "!", "?" };
  }

}
