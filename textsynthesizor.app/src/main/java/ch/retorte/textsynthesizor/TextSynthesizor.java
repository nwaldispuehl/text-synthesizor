package ch.retorte.textsynthesizor;

import java.io.File;
import java.nio.charset.Charset;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import ch.retorte.textsynthesizor.builder.MarkovChainBuilder;
import ch.retorte.textsynthesizor.tokenizer.LetterTokenizer;
import ch.retorte.textsynthesizor.tokenizer.SentenceAwareWordTokenizer;
import ch.retorte.textsynthesizor.tokenizer.SimpleWordTokenizer;
import ch.retorte.textsynthesizor.tokenizer.Tokenizer;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.io.Files;

public class TextSynthesizor {

  private static final String N_GRAM_OPTION_VALUE = "n";
  private static final String OUTPUT_SIZE_OPTION_VALUE = "s";
  private static final String CUSTOM_TOKENIZER_OPTION_VALUE = "t";

  private static final String CUSTOM_SIMPLE_WORD_TOKENIZER = "word";
  private static final String CUSTOM_SENTENCE_WORD_TOKENIZER = "sentence";
  private static final String CUSTOM_LETTER_TOKENIZER = "letter";

  private Integer nGramSize = 2;
  private Integer outputSize = 40;
  private String customTokenizerName = CUSTOM_SENTENCE_WORD_TOKENIZER;
  private String inputFilePath;
  private String input = "";

  private Options cliOptions;

  public void startWith(String[] args) {

    createOptions();
    parseArguments(args);
    readInputFromFile();

    MarkovChainBuilder builder = createBuilder();
    String generatedText = builder.generateTextWith(nGramSize, outputSize, input);

    System.out.println(generatedText);
  }

  @VisibleForTesting
  void createOptions() {
    cliOptions = new Options();
    cliOptions.addOption(N_GRAM_OPTION_VALUE, true, "The n in n-gram. E.g. 2 for bigrams. Defaults to 2.");
    cliOptions.addOption(OUTPUT_SIZE_OPTION_VALUE, true, "The output size (number of tokens) to generate. Defaults to 40.");
    cliOptions.addOption(CUSTOM_TOKENIZER_OPTION_VALUE, true, "The tokenizer to use, currently either 'sentence', 'word', or 'letter'. Defaults to 'sentence'.");
  }

  @VisibleForTesting
  void parseArguments(String[] args) {
    CommandLineParser parser = new PosixParser();
    CommandLine cmd = null;
    try {
      cmd = parser.parse(cliOptions, args);
    } catch (ParseException e) {
      printUsageMessage();
      System.out.print(e.getMessage());
      quitProgram();
    }

    try {
      if (cmd.hasOption(N_GRAM_OPTION_VALUE)) {
        nGramSize = Integer.parseInt(cmd.getOptionValue(N_GRAM_OPTION_VALUE));
      }

      if (cmd.hasOption(OUTPUT_SIZE_OPTION_VALUE)) {
        outputSize = Integer.parseInt(cmd.getOptionValue(OUTPUT_SIZE_OPTION_VALUE));
      }

      if (cmd.hasOption(CUSTOM_TOKENIZER_OPTION_VALUE)) {
        customTokenizerName = cmd.getOptionValue(CUSTOM_TOKENIZER_OPTION_VALUE);
      }

      inputFilePath = (String) cmd.getArgList().get(cmd.getArgList().size() - 1);
    } catch (Exception e) {
      printUsageMessage();
      quitProgram();
    }
  }

  private void printProgramTitle() {
    System.out.println("Text Synthesizor -- Markov Chain Text Generation");
    System.out.println("Analyses a input string by splitting it up into n-grams and observes the probability of tokens ");
    System.out.println("which follow. Then generates a string which has the same probability characteristic.");
    System.out.println();
  }

  @VisibleForTesting
  void printUsageMessage() {
    printProgramTitle();

    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp("textsynthesizor [OPTIONS] inputfile", cliOptions);
  }

  @VisibleForTesting
  void quitProgram() {
    System.exit(0);
  }

  private void quitProgramWithError() {
    System.exit(1);
  }

  private void readInputFromFile() {
    try {
      input = Files.toString(new File(inputFilePath), Charset.defaultCharset());
    } catch (Exception e) {
      printFileNotFoundMessage(e.getMessage());
      quitProgramWithError();
    }
  }

  private void printFileNotFoundMessage(String message) {
    printProgramTitle();
    System.err.println("Provided file not found: " + message);
  }

  private MarkovChainBuilder createBuilder() {
    return new MarkovChainBuilder(getTokenizer());
  }

  private Tokenizer getTokenizer() {
    if (CUSTOM_LETTER_TOKENIZER.equals(customTokenizerName)) {
      return new LetterTokenizer();
    }
    else if (CUSTOM_SIMPLE_WORD_TOKENIZER.equals(customTokenizerName)) {
      return new SimpleWordTokenizer();
    }

    return new SentenceAwareWordTokenizer();
  }

  @VisibleForTesting
  Integer getNGramSize() {
    return nGramSize;
  }

  @VisibleForTesting
  Integer getOutputSize() {
    return outputSize;
  }

  @VisibleForTesting
  String getCustomTokenizerName() {
    return customTokenizerName;
  }

  @VisibleForTesting
  String getInputFilePath() {
    return inputFilePath;
  }

  @VisibleForTesting
  String getInput() {
    return input;
  }

}
