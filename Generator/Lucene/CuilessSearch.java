package com.lucene.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.UnicodeWhitespaceAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.grouping.GroupDocs;
import org.apache.lucene.search.grouping.GroupingSearch;
import org.apache.lucene.search.grouping.TopGroups;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.util.Arrays;



public class CuilessSearch {
	
//////////////////////train/////////////////////////////////
//	private static String csvInput = "source/train/train_cuiless_joint.tsv";
//	private static String module = "cuilessSearch";
//	private static String csvTarget = "data/train/input_train.tsv";
//	private static int maxHits = 1;
//	private static String INDEX_DIR = "index/train/"+ module +"/";
//	private static String csvOutput = "output/train/" + module +"_1_train/";

//////////////////////train_split_dev/////////////////////////////////
private static String csvInput = "source/train_split/train_cuiless.tsv";
private static String module = "cuilessSearch";
private static String csvTarget = "data/test_dev/input_dev.tsv";
private static int maxHits = 1;
private static String INDEX_DIR = "index/train_split/"+ module +"/";
private static String csvOutput = "output/test_dev/" + module +"_1_dev/";

//////////////////////train_split_train/////////////////////////////////
//private static String csvInput = "source/train_split/train_cuiless.tsv";
//private static String module = "cuilessSearch";
//private static String csvTarget = "data/test_dev/input_train.tsv";
//private static int maxHits = 1;
//private static String INDEX_DIR = "index/train_split/"+ module +"/";
//private static String csvOutput = "output/test_dev/" + module +"_1_train/";

//////////////////////test/////////////////////////////////
//	private static String csvInput = "source/train/train_cuiless_joint.tsv";
//	private static String module = "cuilessSearch";
//	private static String csvTarget = "data/test/input_test.tsv";
//	private static int maxHits = 1;
//	private static String INDEX_DIR = "index/train/"+ module +"/";
//	private static String csvOutput = "output/test/" + module +"_1_test/";
	
	
	public static void main(String[] args) throws Exception 
	{
		File directory = new File(csvOutput);
		if(!directory.exists()){directory.mkdir();}
		

	
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = "\t";
//		IndexWriter writer = createWriter();
//        br = new BufferedReader(new FileReader(csvInput));
//        while ((line = br.readLine()) != null) {
//
//                // use comma as separator
//                String[] concepts = line.split(cvsSplitBy);
//        		List<Document> documentlist = createDocument(concepts[0], concepts[1]);
//        		writer.addDocuments(documentlist);
//        		documentlist.clear();
//        }
//        
//	    writer.close();
	    
	    
		IndexSearcher searcher = createSearcher(INDEX_DIR);
		List<String> mentions = new ArrayList<>();
        br = new BufferedReader(new FileReader(csvTarget));
        while ((line = br.readLine()) != null) {

            // use comma as separator
            String[] rows = line.split(cvsSplitBy);
            mentions.add(rows[0]);
        }
        
//        ArrayList<String[]> results  = searchByMention("The permanent pathology sections", searcher,maxHits);
//
//        for (String[] result:results) {
//        	System.out.println(result[0]);
//        	System.out.println(result[1]);
//        	
//        }
		
        

        FileWriter csvWriter = new FileWriter(csvOutput+ "dev_prediction.tsv"); 
        for (String mention : mentions) {
        	System.out.println(mention);
    		//Search by conceptName
        	ArrayList<String[]> results = searchByMention(mention, searcher,maxHits);
		            for (String[] result:results) {
		            	System.out.println(result[0]);
		            	System.out.println(result[1]);
		    			csvWriter.append(result[0]);  
		    			csvWriter.append("\t");
//		    			csvWriter.append(result[1]);  
//		    			csvWriter.append("\t");
		            }
			System.out.println("----------------------------------------------");
			csvWriter.append("\n");
        }
	    csvWriter.flush();  
	    csvWriter.close();  
    }
        

	
	private static List<Document> createDocument(String cui, String conceptNames) 
	{
		List<Document> documents = new ArrayList<>();
		String[] conceptNameAray = conceptNames.split("###");
		for (String conceptName : conceptNameAray) {
			Document document = new Document();
	    	document.add(new StringField("cui", cui , Field.Store.YES));
	    	document.add(new TextField("conceptNameEmS", conceptName , Field.Store.YES));
	    	document.add(new TextField("conceptNameEm", conceptName , Field.Store.YES));
//	    	document.add(new TextField("conceptNameTokens", conceptName , Field.Store.YES));
//	    	document.add(new TextField("conceptNameNgrams", conceptName , Field.Store.YES));
	    	
	    	documents.add(document);
		}
		documents.get(conceptNameAray.length-1).add(new StringField("cuiEnd", "x" , Field.Store.NO));

    	return documents;
    }

	private static IndexWriter createWriter() throws IOException 
	{
		FSDirectory dir = FSDirectory.open(Paths.get(INDEX_DIR));
		CuilessExactMatchStrongAnalyzer ExactSanalyzer = new CuilessExactMatchStrongAnalyzer();
		CuilessExactMatchAnalyzer Exactanalyzer = new CuilessExactMatchAnalyzer();
//		CuilessStandardEnglishAnalyzer Standardanalyzer = new CuilessStandardEnglishAnalyzer();

		
		Map analyzerMap = new HashMap();
		analyzerMap.put("conceptNameEmS", ExactSanalyzer);
		analyzerMap.put("conceptNameEm", Exactanalyzer);
//		analyzerMap.put("conceptNameTokens", Standardanalyzer);
		PerFieldAnalyzerWrapper perFieldAnalyzer = new PerFieldAnalyzerWrapper(new KeywordAnalyzer(),analyzerMap);
		IndexWriterConfig config = new IndexWriterConfig(perFieldAnalyzer);
		config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
		IndexWriter writer = new IndexWriter(dir, config);
		return writer;
	}
	
	private static ArrayList<String[]> searchByMention(String mention, IndexSearcher searcher,int maxFuzzyHits) throws Exception
	{
		CuilessExactMatchStrongAnalyzer ExactSanalyzer = new CuilessExactMatchStrongAnalyzer();
		CuilessExactMatchAnalyzer Exactanalyzer = new CuilessExactMatchAnalyzer();
//		CuilessStandardEnglishAnalyzer Standardanalyzer = new CuilessStandardEnglishAnalyzer();
		Map analyzerMap = new HashMap();
		analyzerMap.put("conceptNameEmS", ExactSanalyzer);
		analyzerMap.put("conceptNameEm", Exactanalyzer);
//		analyzerMap.put("conceptNameTokens", Standardanalyzer);
		PerFieldAnalyzerWrapper perFieldAnalyzer = new PerFieldAnalyzerWrapper(new KeywordAnalyzer(),analyzerMap);
		Query idEndQuery  = new TermQuery(new Term("cuiEnd", "x"));
		GroupingSearch groupingSearch = new GroupingSearch(idEndQuery);
		QueryParser SnameQueryParser = new QueryParser("conceptNameEmS", perFieldAnalyzer);
		QueryParser nameQueryParser = new QueryParser("conceptNameEm", perFieldAnalyzer);
//		QueryParser NameTokensQueryParser = new QueryParser("conceptNameTokens", perFieldAnalyzer);
		String escapedQueryString = QueryParser.escape(mention);
		
		ArrayList<String[]> results; 
//	    String luceneSpecialCharacters = """([-+&|!(){}\[\]^"~*?:\\/\s])""";
//	    val escapedQueryString = mention.replaceAll(luceneSpecialCharacters, """\\$1""")

	    // first look for an exact match of the input phrase (the "name" field ignores spaces, punctuation, etc.)
		

    	results =  scoredEntries( groupingSearch,  searcher,  SnameQueryParser,  escapedQueryString);

	    if (results.isEmpty()) {
	    	results =  scoredEntries( groupingSearch,  searcher,  nameQueryParser,  escapedQueryString);
	    }
		
//		if (results.isEmpty()) {
//	    	results =  scoredEntries( groupingSearch,  searcher,  NameTokensQueryParser,  escapedQueryString);
//			}

		return results;
	}

	private static IndexSearcher createSearcher(String indx_dir) throws IOException {
		Directory dir = FSDirectory.open(Paths.get(indx_dir));
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		return searcher;
	}
	
	private static ArrayList<String[]> scoredEntries(GroupingSearch groupingSearch, IndexSearcher searcher, QueryParser queryparser
			, String querystring) throws IOException, ParseException {
		TopGroups<?> topGroups =  groupingSearch.search(searcher, queryparser.parse(querystring), 0, maxHits);
		
		ArrayList<String[]> listOLists = new ArrayList<String[]>();
	    if (topGroups == null) return listOLists;
	    else {
	        for (GroupDocs groupdocs: topGroups.groups){
	          Document headDoc = searcher.doc(groupdocs.scoreDocs[0].doc);
	          String[] Info = new String[] {headDoc.get("cui"),headDoc.get("conceptNameTokens"),Float.toString(groupdocs.maxScore)};
	          listOLists.add(Info);
	        }
	        return listOLists;
	      }
	  }

	}

