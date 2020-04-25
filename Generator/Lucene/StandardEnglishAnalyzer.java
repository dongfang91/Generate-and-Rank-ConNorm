package com.lucene.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.Analyzer.TokenStreamComponents;
import org.apache.lucene.analysis.core.KeywordTokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.pattern.PatternReplaceFilter;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class StandardEnglishAnalyzer extends Analyzer {
	
    CharArraySet stopwords = getStopWordsList();
    
    private static final String[] ENGLISH_STOP_WORDS = {
            "a", "an", "and", "are", "as", "at", "be", "but", "by",
            "for", "if", "in", "into", "is", "it", "no", "not", "of",
            "on", "or", "such", "that", "the", "their", "then", "there", "these",
            "they", "this", "to", "was", "will", "with","he","she","patient", 
            "&apos;d", "&apos;s", "&quot;", "&lt;", "&gt;","his","her"
        };
    
    
	public StandardEnglishAnalyzer() {
    }
    
	@Override
    protected TokenStreamComponents createComponents(String s) {
		
		final Tokenizer source = new StandardTokenizer();
		TokenStream StandardTokenizer_LowerCaseFilter  = new LowerCaseFilter(source);
		TokenStream StandardTokenizer_LowerCaseFilter_EnglishPossessiveFilter = new EnglishPossessiveFilter(StandardTokenizer_LowerCaseFilter);
		TokenStream StandardTokenizer_LowerCaseFilter_EnglishPossessiveFilter_StopFilter = new StopFilter(StandardTokenizer_LowerCaseFilter_EnglishPossessiveFilter, stopwords);
		TokenStream StandardTokenizer_LowerCaseFilter_EnglishPossessiveFilter_StopFilter_snowballfilter = new SnowballFilter(StandardTokenizer_LowerCaseFilter_EnglishPossessiveFilter_StopFilter, "English");        
        return new TokenStreamComponents(source, StandardTokenizer_LowerCaseFilter_EnglishPossessiveFilter_StopFilter_snowballfilter);
    }
	
    private class SavedStreams {

        Tokenizer source;
        TokenStream result;
    };
    
    private CharArraySet getStopWordsList() {

        Set<String> list = new HashSet<String>();

        for (int i = 0; i < ENGLISH_STOP_WORDS.length; i++) {
            String string = ENGLISH_STOP_WORDS[i];
            list.add(string);
        }
        
        final CharArraySet phraseSets = new CharArraySet(list, false);

        return phraseSets;

    }

}
