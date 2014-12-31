package ch.retorte.textsynthesizor.builder;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import ch.retorte.textsynthesizor.model.NGram;
import ch.retorte.textsynthesizor.model.Token;
import ch.retorte.textsynthesizor.model.TokenFactory;

/**
 * Manages the n-grams and their following tokens, forming the actual markov chain probability table.
 * 
 * @author nw
 */
public class Chain {

  private final Map<NGram, Map<Token, Integer>> nGrams = newHashMap();
  private Random random = new Random();

  public void add(NGram nGram, Token token) {

    if (!keyExist(nGram)) {
      Map<Token, Integer> tokenList = newHashMap();
      tokenList.put(token, 0);
      nGrams.put(nGram, tokenList);
    }

    Map<Token, Integer> nGramTable = nGrams.get(nGram);

    if (!nGramTable.containsKey(token)) {
      nGramTable.put(token, 0);
    }

    Integer occurrencesSoFar = nGramTable.get(token);
    nGramTable.put(token, occurrencesSoFar + 1);
  }

  private boolean keyExist(NGram nGram) {
    return nGrams.containsKey(nGram);
  }

  public Token getNextTokenFor(NGram nGram) {
    Map<Token, Integer> tokenMap = nGrams.get(nGram);

    if (tokenMap == null || tokenMap.isEmpty()) {
      return new TokenFactory().createEmptyToken();
    }

    return randomlyChooseSingleTokenOf(getFlatTokenList(tokenMap));
  }

  public boolean isEmpty() {
    return nGrams.isEmpty();
  }

  private Token randomlyChooseSingleTokenOf(List<Token> tokens) {
    return tokens.get(random.nextInt(tokens.size()));
  }

  /**
   * Flattens out the occurrence map of tokens, that is, puts every token as many times as it occurrs into a list so that we can just randomly pick one member
   * of the list.
   */
  private List<Token> getFlatTokenList(Map<Token, Integer> tokenMap) {
    List<Token> result = newArrayList();
    for (Entry<Token, Integer> entry : tokenMap.entrySet()) {
      for (int i = 0; i < entry.getValue(); i++) {
        result.add(entry.getKey());
      }
    }
    return result;
  }


}
