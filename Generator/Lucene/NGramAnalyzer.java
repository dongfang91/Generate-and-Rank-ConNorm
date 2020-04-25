package com.lucene.search;

import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.Analyzer.TokenStreamComponents;
import org.apache.lucene.analysis.ngram.NGramTokenFilter;
import org.apache.lucene.analysis.ngram.NGramTokenizer;
import org.apache.lucene.analysis.standard.StandardTokenizer;

import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.snowball.SnowballFilter;



public class NGramAnalyzer extends Analyzer {
    static int MIN_N_GRAMS = 4;
    //Maximum Number of N-Grams
    static int MAX_N_GRAMS = 4;

    CharArraySet stopwords = getStopWordsList();
    
    private static final String[] ENGLISH_STOP_WORDS = {
            "a", "an", "and", "are", "as", "at", "be", "but", "by",
            "for", "if", "in", "into", "is", "it", "no", "not", "of",
            "on", "or", "such", "that", "the", "their", "then", "there", "these",
            "they", "this", "to", "was", "will", "with","he","she","patient", 
            "&apos;d", "&apos;s", "&quot;", "&lt;", "&gt;","his","her"
        };

    public NGramAnalyzer() {
    }
    
	@Override
    protected TokenStreamComponents createComponents(String s) {
		

		final Tokenizer source = new StandardTokenizer();
		TokenStream StandardTokenizer_LowerCaseFilter  = new LowerCaseFilter(source);
		TokenStream StandardTokenizer_LowerCaseFilter_EnglishPossessiveFilter = new EnglishPossessiveFilter(StandardTokenizer_LowerCaseFilter);
		TokenStream StandardTokenizer_LowerCaseFilter_EnglishPossessiveFilter_StopFilter = new StopFilter(StandardTokenizer_LowerCaseFilter_EnglishPossessiveFilter, stopwords);
		TokenStream StandardTokenizer_LowerCaseFilter_EnglishPossessiveFilter_StopFilter_snowballfilter = new SnowballFilter(StandardTokenizer_LowerCaseFilter_EnglishPossessiveFilter_StopFilter, "English"); 
        TokenStream StandardTokenizer_LowerCaseFilter_EnglishPossessiveFilter_StopFilter_snowballfilter_nGramTokenFilter =new NGramTokenFilter(StandardTokenizer_LowerCaseFilter_EnglishPossessiveFilter_StopFilter_snowballfilter, MIN_N_GRAMS, MAX_N_GRAMS,false);
        return new TokenStreamComponents(source, StandardTokenizer_LowerCaseFilter_EnglishPossessiveFilter_StopFilter_snowballfilter_nGramTokenFilter);

    }
	

    private CharArraySet getStopWordsList() {

        Set<String> list = new HashSet<String>();

        for (int i = 0; i < ENGLISH_STOP_WORDS.length; i++) {
            String string = ENGLISH_STOP_WORDS[i];
            list.add(string);
        }
        
        final CharArraySet phraseSets = new CharArraySet(list, false);

        return phraseSets;

    }
    
    /** Filters NGramCaseTokenizer with LowerCaseFilter,PorterStemFilter,StopFilter. */
    private class SavedStreams {

        Tokenizer source;
        TokenStream result;
    };

	}
