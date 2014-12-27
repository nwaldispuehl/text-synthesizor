Text Synthesizor
================

_TextSynthesizor, a small text synthesizing utility based on the theory of [markov chains](http://en.wikipedia.org/wiki/Markov_chain)._

```
Text Synthesizor -- Markov Chain Text Generation
Analyses a input string by splitting it up into n-grams and observes the 
probability of tokens which follow. Then generates a string which has the 
same probability characteristic.

usage: textsynthesizor.app [OPTIONS] inputfile
 -n    The n in n-gram. E.g. 2 for bigrams. Defaults to 2.
 -s    The output size (number of tokens) to generate. Defaults to 40.
 -t    The tokenizer to use, currently either 'sentence', 'word', or
       'letter'. Defaults to 'sentence'.
```

##### Table of Contents  
* [How does it work?](#how_does_it_work)  
 * [Varying N-Grams](#varying_ngrams) 
 * [Switch Tokenizers](#switch_tokenizers) 
* [How to build and run?](#how_to_build_and_run)  
* [How to download?](#how_to_download)  
* [Current build status](#build)  

<a name='how_does_it_work' />
How does it work?
-----------------
Run the textsynthesizor with any text body to synthesize text with similar probability characteristic. Let's assume for the following examples we have a text file `king_cole.txt` containing this text:

> Old King Cole was a merry old soul
> and a merry old soul was he;
> He called for his pipe, and he called for his bowl
> and he called for his fiddlers three.
> Every fiddler he had a fiddle,
> and a very fine fiddle had he;
> Oh there's none so rare, as can compare
> with King Cole and his fiddlers three.

_(See [en.wikipedia.org/wiki/Old_King_Cole](http://en.wikipedia.org/wiki/Old_King_Cole))._

When we call the application on this file without any other arguments, it produces the following output:
```
$ textsynthesizor.app king_cole.txt
```
> Every fiddler he had a fiddle, and a merry old soul and a merry old soul and a merry old soul and a very fine fiddle had he; Oh there's none so rare, as can compare with King Cole was

By default, the textsynthesizor analyses all [2-grams](http://en.wikipedia.org/wiki/N-gram) (or, bigrams) with a sentence tokenizer and produces 40 tokens of output (in this case: words).

<a name='varying_ngrams' />
### Varying N-Grams

By varying the n-gram argument ('n' option) one can affect how much the output text looks like the original text. When selecting a larger value, the text is more similar to the source:
```
$ textsynthesizor.app -n 4 king_cole.txt
```
> Every fiddler he had a fiddle, and a very fine fiddle had he; Oh there's none so rare, as can compare with King Cole and his fiddlers three. Old King Cole was a merry old soul and a merry old

When choosing a smaller value, the output looks less like the source:
```
$ textsynthesizor.app -n 1 king_cole.txt
```
> Old King Cole and he called for his pipe, and he had a fiddle, and his bowl and a fiddle, and he had he; He called for his fiddlers three. Old King Cole and a merry old soul and he

When setting n to zero, the words are chosen randomly solely based on their occurrence probability:
```
$ textsynthesizor.app -n 0 king_cole.txt
```
> Every three. had and three. Oh called compare King Cole his and He can for for for soul three. Old can bowl soul so had and He bowl old He he; and King compare his compare soul had and a

<a name='switch_tokenizers' />
### Switch Tokenizers

We currently have three different tokenizers which can be selected by the 't' option. The default is the **'sentence'** tokenizer. It splits the source text up into words, but treats every sentence as independent source. The following two calls are equivalent.
```
$ textsynthesizor.app -t sentence king_cole.txt
$ textsynthesizor.app king_cole.txt
```

The **'word'** tokenizer just splits the source up into single words. The most visible effect is that the output text always starts with the same token as the source text. Furthermore, sentences are not treated in a special way so also the order of sentences is relevant to the analysis.
```
$ textsynthesizor.app -t word king_cole.txt
```
> Old King Cole was a merry old soul was he; He called for his bowl and he called for his fiddlers three. Every fiddler he had a fiddle, and a very fine fiddle had he; Oh there's none so rare,

The **'letter'** tokenizer treats the single letter as token.
```
$ textsynthesizor.app -t letter king_cole.txt
```
> Old he his for had fing Cold fiddle comp

In this example we can observe two things:

* The produced sample is much shorter as before. Since the inspected token is now the single letter, 40 tokens are just 40 letters. To increase the size of the output text, we can provide a larger size with the 's' option. We try it with 120 tokens.
* The produced text is not very meaningful. Since we are only considering bigrams (aka 2-grams), that is, 2 letters, there is often gibberish produced. We can change this by increasing the n-gram size with the 'n' option. Let's try it with trigrams.

```
$ textsynthesizor.app -t letter -s 120 -n 3 king_cole.txt
```
> Old King Cole was he;
> He called for he had a merry old soul was he;
> Oh there's none soul was called for his pipe, and a

There, that's a bit better. Certainly, we can also produce random letters with the origins probability distribution with the following call:
```
$ textsynthesizor.app -t letter -s 120 -n 0 king_cole.txt
```
> feriwirhlra  laefrf m r Hugtirsolpyc h  
> wh rtnd lte i
> sn
> dso hln cidiprn'neli trtshrel
> eiplfnaadmOldrenaltuh eeh ffl h

Or, with 1-grams, some interesting pidgin-styled text:
```
$ textsynthesizor.app -t letter -s 120 -n 1 king_cole.txt
```
> Ol
> and mere hery Cory s pin bora ng calery fine cald hisolera fole.
> wa forere fisoll cas hid hang addlle wang fipe,
> ars

<a name='how_to_build_and_run' />
How to build and run?
---------------------

Clone the repository and change into the cloned project:
```
$ git clone https://github.com/nwaldispuehl/text-synthesizor.git
$ cd text-synthesizor
``` 

Build the project with [**gradle**](http://www.gradle.org/). Either use your own installation, or use the shipped version as shown here:
```
text-synthesizor$ ./gradlew installApp
``` 

Switch into the build directory and run the installed app:
```
text-synthesizor$ cd textsynthesizor.app/build/install/textsynthesizor.app/
text-synthesizor/textsynthesizor.app/build/install/textsynthesizor.app$ ./bin/textsynthesizor.app
``` 

Certainly, you could run the project also directly with gradle, but providing command line arguments is not that easy this way:
```
text-synthesizor$ ./gradlew run
```

<a name='how_to_download' />
How to download?
---------------------
If you just want to try it out, download the latest release from the release directory:

[Text-Synthesizor releases](https://github.com/nwaldispuehl/text-synthesizor/releases)

Unpack the archive (adapt version if needed) and start the application as follows on Linux and OS X operating systems. Certainly, you need a installed [Java](http://www.java.com/) runtime (>= 1.6) on your system. On Microsoft Windows systems just use the .bat file.

```
$ unzip textsynthesizor.app-0.0.1.zip
$ cd textsynthesizor.app-0.0.1.app/
$ ./bin/textsynthesizor.app
``` 

<a name='build' />
Current build status
---------------------
[![Build Status](https://travis-ci.org/nwaldispuehl/text-synthesizor.svg?branch=master)](https://travis-ci.org/nwaldispuehl/text-synthesizor)
