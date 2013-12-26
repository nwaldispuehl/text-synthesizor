package ch.retorte.textsynthesizor.builder;

import static com.google.common.base.Joiner.on;
import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.List;

import com.google.common.annotations.VisibleForTesting;

import ch.retorte.textsynthesizor.model.NGram;
import ch.retorte.textsynthesizor.model.NGramFactory;
import ch.retorte.textsynthesizor.model.Token;
import ch.retorte.textsynthesizor.model.TokenFactory;
import ch.retorte.textsynthesizor.tokenizer.Tokenizer;
import ch.retorte.textsynthesizor.utils.RingBuffer;

/**
 * The {@link MarkovChainBuilder} manages to put all tokens together to the desired n-grams and puts them into the {@link Chain} appropriately. It then
 * constitutes the synthesized text based on these probabilities.
 * 
 * @author nw
 */
public class MarkovChainBuilder {

  private final Tokenizer tokenizer;
  private List<Token> tokens;

  private RingBuffer<Token> buffer;


  private Chain chain = new Chain();
  private NGramFactory nGramFactory = new NGramFactory();
  private TokenFactory tokenFactory = new TokenFactory();

  public MarkovChainBuilder(Tokenizer tokenizer) {
    this.tokenizer = tokenizer;
  }

  public String generateTextWith(int nGram, int outputTokens, String input) {
    generateTokensOf(input);
    buildProbabilityTableWith(nGram);
    initializeBufferWithSize(nGram);
    return generateStringOfLength(outputTokens);
  }

  @VisibleForTesting
  void generateTokensOf(String input) {
    tokens = tokenizer.tokenize(input);
  }

  @VisibleForTesting
  void buildProbabilityTableWith(int n) {
    List<Token> inputList = prepareTokenList(getTokens(), n);

    for (int i = 0; i < inputList.size() - n; i++) {

      List<Token> nGramTokens = inputList.subList(i, i + n);
      Token nextToken = inputList.get(i + n);

      addToChain(nGramTokens, nextToken);
    }
  }

  @VisibleForTesting
  List<Token> getTokens() {
    return tokens;
  }

  @VisibleForTesting
  void addToChain(List<Token> nGramTokens, Token nextToken) {
    chain.add(createNGramFrom(nGramTokens), nextToken);
  }

  @VisibleForTesting
  NGram createNGramFrom(List<Token> nGramTokens) {
    return nGramFactory.createFrom(nGramTokens);
  }

  /**
   * Prepares the token list for processing, that is, copies the tokens to a new list which also contains n empty tokens in front of every 'first' token.
   */
  @VisibleForTesting
  List<Token> prepareTokenList(List<Token> tokenList, int n) {
    List<Token> inputList = new ArrayList<Token>(tokenList.size() + 2 * n);

    for (Token t : tokenList) {
      if (t.isFirst()) {
        for (int i = 0; i < n; i++) {
          inputList.add(tokenFactory.createEmptyToken());
        }
      }

      inputList.add(t);
    }

    return inputList;
  }

  @VisibleForTesting
  void initializeBufferWithSize(int n) {
    buffer = new RingBuffer<Token>(n);
  }

  @VisibleForTesting
  String generateStringOfLength(int tokens) {
    List<String> result = newArrayList();

    while (result.size() < tokens) {

      Token nextToken = extractNextToken();

      if (!nextToken.isEmpty()) {
        result.add(nextToken.getContent());
      }

      buffer.addItem(nextToken);
    }

    return on(tokenizer.getDelimiter()).join(result);
  }

  @VisibleForTesting
  Token extractNextToken() {
    NGram latestNGram = nGramFactory.createFrom(buffer.getLatestItems());
    return chain.getNextTokenFor(latestNGram);
  }

}
