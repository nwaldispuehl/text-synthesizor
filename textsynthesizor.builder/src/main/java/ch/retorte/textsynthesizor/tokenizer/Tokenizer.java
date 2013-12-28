package ch.retorte.textsynthesizor.tokenizer;

import java.util.List;

import ch.retorte.textsynthesizor.model.Token;

/**
 * Converts a string into substrings.
 * 
 * @author nw
 */
public interface Tokenizer {

  /**
   * Tokenizes the input string according to the rules imposed by the tokenizer implementation. Note that if the number of tokens is larger than zero, at least
   * one token created by the tokenizer implementation must have set the isFirst flag to true.
   * 
   * @param input
   *          the input string to be tokenized.
   * @return a list of tokens.
   */
  List<Token> tokenize(String input);

  /**
   * Returns the delimiter used to join single tokens to a complete sequence.
   * 
   * @return a string which is used the join the tokens.
   */
  String getDelimiter();

}
