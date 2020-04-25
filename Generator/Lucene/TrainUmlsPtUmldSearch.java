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



public class TrainUmlsPtUmldSearch {
	
//////////////////////train/////////////////////////////////
//	private static String csvInputTrain = "source/train/train_cui_joint.tsv";
//	private static String csvInputUmls = "source/umls/umls_new_joint.tsv";
//	private static String csvInputUmlsPt = "source/umls_pt/umls_pt_joint.tsv";
//	
////	private static String csvTarget = "data/train/input_1_cuiless_train.tsv";
//	private static String csvTarget = "data/test_dev/input_train.tsv";
//	private static String module = "TrainUmls";
//	
//	private static String indexDirTrain = "index/train/"+ module +"/";
//	private static String indexDirUmls = "index/umls/"+ module +"/";
//	private static String indexDirUmlsPt = "index/umls/"+ module +"Pt/";
////	private static String csvOutput = "output/train/" + module +"PtUmls_train_40/";
//	
//	private static String csvOutput = "output/train/e/";
	
//////////////////////train_split_dev /////////////////////////////////
//private static String csvInputTrain = "source/train_split/train_joint.tsv";
//private static String csvInputUmls = "source/umls/umls_new_joint.tsv";
//private static String csvInputUmlsPt = "source/umls_pt/umls_pt_joint.tsv";
//
//private static String csvTarget = "data/test_dev/input_1_cuiless_dev.tsv";
//private static String module = "TrainUmls";
//
//private static String indexDirTrain = "index/train_split/"+ module +"/";
//private static String indexDirUmls = "index/umls/"+ module +"/";
//private static String indexDirUmlsPt = "index/umls/"+ module +"Pt/";
//private static String csvOutput = "output/test_dev/" + module +"PtUmls_dev_exp1_40/";

////////////////////// JAMIA train_split_dev ablation study/////////////////////////////////
private static String csvInputTrain = "source/train_split/train_joint.tsv";
private static String csvInputUmls = "source/umls/umls_new_joint.tsv";
private static String csvInputUmlsPt = "source/umls_pt/umls_pt_joint.tsv";

private static String csvTarget = "data/test_dev/input_1_cuiless_dev.tsv";
//private static String csvTarget = "data/test_dev/input_dev.tsv";
private static String module = "TrainUmls";

private static String indexDirTrain = "index/train_split/"+ module +"/";
private static String indexDirUmls = "index/umls/"+ module +"/";
private static String indexDirUmlsPt = "index/umls/"+ module +"Pt/";
//private static String csvOutput = "output/test_dev/a+b/";
//private static String csvOutput = "output/test_dev/c/";
//private static String csvOutput = "output/test_dev/d/";
//private static String csvOutput = "output/test_dev/e/";
//private static String csvOutput = "output/test_dev/c+d+e/";
//private static String csvOutput = "output/test_dev/a+b+c+d+e/";
//private static String csvOutput = "output/test_dev/d+e/";
//private static String csvOutput = "output/test_dev/a+b+e/";
private static String csvOutput = "output/test_dev/a+b+c+d+e_100/";

//private static String csvOutput = "output/test_dev/" + module +"PtUmls_dev_exp1_40/";

////////////////////test /////////////////////////////////
//private static String csvInputTrain = "source/train/train_cui_joint.tsv";
//private static String csvInputUmls = "source/umls/umls_new_joint.tsv";
//private static String csvInputUmlsPt = "source/umls_pt/umls_pt_joint.tsv";
//
//private static String csvTarget = "data/test/input_1_cuiless_test.tsv";
//private static String module = "TrainUmls";
//
//private static String indexDirTrain = "index/train/"+ module +"/";
//private static String indexDirUmls = "index/umls/"+ module +"/";
//private static String indexDirUmlsPt = "index/umls/"+ module +"Pt/";
//private static String csvOutput = "output/test/" + module +"PtUmls_test_40/";

////////////////// Jamia test ablation study/////////////////////////////////
//private static String csvInputTrain = "source/train/train_cui_joint.tsv";
//private static String csvInputUmls = "source/umls/umls_new_joint.tsv";
//private static String csvInputUmlsPt = "source/umls_pt/umls_pt_joint.tsv";

//private static String csvTarget = "data/test/input_1_cuiless_test.tsv";
//private static String csvTarget = "data/test/input_test.tsv";
//private static String module = "TrainUmls";

//private static String indexDirTrain = "index/train/"+ module +"/";
//private static String indexDirUmls = "index/umls/"+ module +"/";
//private static String indexDirUmlsPt = "index/umls/"+ module +"Pt/";
//private static String csvOutput = "output/test/a+b/";
//private static String csvOutput = "output/test/a+b+c+d+e/";
//private static String csvOutput = "output/test/c/";
//private static String csvOutput = "output/test/d/";
//private static String csvOutput = "output/test/e/";
//private static String csvOutput = "output/test/d+e/";
//private static String csvOutput = "output/test/a+b+e/";
//private static String csvOutput = "output/test/" + module +"PtUmls_test_40/";

	public static void main(String[] args) throws Exception 
	{
		File directory = new File(csvOutput);
		if(!directory.exists()){directory.mkdir();}
		

	
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = "\t";
        
//		IndexWriter writerTrain = trainCreateWriter(indexDirTrain);
//        br = new BufferedReader(new FileReader(csvInputTrain));
//        while ((line = br.readLine()) != null) {
//
//                // use comma as separator
//                String[] concepts = line.split(cvsSplitBy);
//        		List<Document> documentlist = createDocumentTrain(concepts[0], concepts[1]);
//        		writerTrain.addDocuments(documentlist);
//        		documentlist.clear();
//        }
//        writerTrain.close();
//        
//		IndexWriter writerUmls = umlsCreateWriter(indexDirUmls);
//        br = new BufferedReader(new FileReader(csvInputUmls));
//        while ((line = br.readLine()) != null) {
//
//                // use comma as separator
//                String[] concepts = line.split(cvsSplitBy);
//        		List<Document> documentlist = createDocumentUmls(concepts[0], concepts[1]);
//        		writerUmls.addDocuments(documentlist);
//        		documentlist.clear();
//        }
//        writerUmls.close();
        
        
//		IndexWriter writerUmls = umlsPtCreateWriter(indexDirUmlsPt);
//		br = new BufferedReader(new FileReader(csvInputUmlsPt));
//		while ((line = br.readLine()) != null) {
//
//              // use comma as separator
//            String[] concepts = line.split(cvsSplitBy);
//      		List<Document> documentlist = createDocumentUmlsPt(concepts[0], concepts[1]);
//      		writerUmls.addDocuments(documentlist);
//      		documentlist.clear();
//		}
//		writerUmls.close();
        
	    
		IndexSearcher searcherTrain = createSearcher(indexDirTrain);
		IndexSearcher searcherUmls = createSearcher(indexDirUmls);
		IndexSearcher searcherUmlsPt = createSearcher(indexDirUmlsPt);
//      ArrayList<String[]> results  = searchByMention("The permanent pathology sections", searcher,maxHits);
//
//      for (String[] result:results) {
//      	System.out.println(result[0]);
//      	System.out.println(result[1]);
//      	
//      }
		
		ArrayList<ArrayList<String>> listOMentions = new ArrayList<ArrayList<String>>();
		ArrayList<String> mentions = new ArrayList<String>();
        br = new BufferedReader(new FileReader(csvTarget));
        FileWriter csvWriter = new FileWriter(csvOutput+ "dev_prediction.tsv");
        while ((line = br.readLine()) != null) {

            // use comma as separator
            String[] rows = line.split(cvsSplitBy);
            if (rows.length>1) {
	    			csvWriter.append(rows[0]);  
	    			csvWriter.append("\t");
	    			csvWriter.append(rows[1]);
	    			csvWriter.append("\t");
            }
            else {
            	ArrayList<String[]> results = searchByMention(rows[0], searcherTrain,searcherUmlsPt,searcherUmls);
    			csvWriter.append(rows[0]);  
    			csvWriter.append("\t");
	            for (String[] result:results) {
	            	System.out.println(result[0]);
	            	System.out.println(result[1]);
	    			csvWriter.append(result[0]);  
	    			csvWriter.append("\t");
	    			csvWriter.append(result[1]);  
	    			csvWriter.append("\t");
	    			csvWriter.append(result[2]);  
	    			csvWriter.append("\t");
	            }
            }

			System.out.println("----------------------------------------------");
			csvWriter.append("\n");

        }
	    csvWriter.flush();  
	    csvWriter.close(); 
    }
        

	
	private static List<Document> createDocumentTrain(String cui, String conceptNames) 
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
	
	private static List<Document> createDocumentUmlsPt(String cui, String conceptNames) 
	{
		List<Document> documents = new ArrayList<>();
		String[] conceptNameAray = conceptNames.split("###");
		for (String conceptName : conceptNameAray) {
			Document document = new Document();
	    	document.add(new StringField("cui", cui , Field.Store.YES));
	    	document.add(new TextField("conceptNameEmS", conceptName , Field.Store.YES));
	    	document.add(new TextField("conceptNameEm", conceptName , Field.Store.YES));
	    	document.add(new TextField("conceptNameTokens", conceptName , Field.Store.YES));
	    	document.add(new TextField("conceptNameNgrams", conceptName , Field.Store.YES));
	    	
	    	documents.add(document);
		}
		documents.get(conceptNameAray.length-1).add(new StringField("cuiEnd", "x" , Field.Store.NO));

    	return documents;
    }
	
	private static List<Document> createDocumentUmls(String cui, String conceptNames) 
	{
		List<Document> documents = new ArrayList<>();
		String[] conceptNameAray = conceptNames.split("###");
		for (String conceptName : conceptNameAray) {
			Document document = new Document();
	    	document.add(new StringField("cui", cui , Field.Store.YES));
//	    	document.add(new TextField("conceptNameEmS", conceptName , Field.Store.YES));
	    	document.add(new TextField("conceptNameEm", conceptName , Field.Store.YES));
	    	document.add(new TextField("conceptNameTokens", conceptName , Field.Store.YES));
	    	document.add(new TextField("conceptNameNgrams", conceptName , Field.Store.YES));
	    	
	    	documents.add(document);
		}
		documents.get(conceptNameAray.length-1).add(new StringField("cuiEnd", "x" , Field.Store.NO));

    	return documents;
    }
	
	private static IndexWriter trainCreateWriter(String INDEX_DIR) throws IOException 
	{
		FSDirectory dir = FSDirectory.open(Paths.get(INDEX_DIR));
		ExactMatchStrongAnalyzer ExactSanalyzer = new ExactMatchStrongAnalyzer();
		ExactMatchAnalyzer Exactanalyzer = new ExactMatchAnalyzer();
		////////////////////////////////////////////////////////////////
		StandardEnglishAnalyzer Standardanalyzer = new StandardEnglishAnalyzer();
		NGramAnalyzer Nganalyzer = new NGramAnalyzer();

		
		Map analyzerMapTrain = new HashMap();
		analyzerMapTrain.put("conceptNameEmS", ExactSanalyzer);
		analyzerMapTrain.put("conceptNameEm", Exactanalyzer);
		////////////////////////////////////////////////////////////////
//		analyzerMapTrain.put("conceptNameTokens", Standardanalyzer);
//		analyzerMapTrain.put("conceptNameNgrams", Nganalyzer);

		PerFieldAnalyzerWrapper perFieldAnalyzer = new PerFieldAnalyzerWrapper(new KeywordAnalyzer(),analyzerMapTrain);
		IndexWriterConfig config = new IndexWriterConfig(perFieldAnalyzer);
		config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
		IndexWriter writer = new IndexWriter(dir, config);
		return writer;
	}

	private static IndexWriter umlsPtCreateWriter(String INDEX_DIR) throws IOException 
	{
		FSDirectory dir = FSDirectory.open(Paths.get(INDEX_DIR));
		ExactMatchStrongAnalyzer ExactSanalyzer = new ExactMatchStrongAnalyzer();
		ExactMatchAnalyzer Exactanalyzer = new ExactMatchAnalyzer();
		StandardEnglishAnalyzer Standardanalyzer = new StandardEnglishAnalyzer();
		NGramAnalyzer Nganalyzer = new NGramAnalyzer();
		
		Map analyzerMapUmlsPt = new HashMap();
		analyzerMapUmlsPt.put("conceptNameEmS", ExactSanalyzer);
		analyzerMapUmlsPt.put("conceptNameEm", Exactanalyzer);
		analyzerMapUmlsPt.put("conceptNameTokens", Standardanalyzer);
		analyzerMapUmlsPt.put("conceptNameNgrams", Nganalyzer);
		PerFieldAnalyzerWrapper perFieldAnalyzer = new PerFieldAnalyzerWrapper(new KeywordAnalyzer(),analyzerMapUmlsPt);
		IndexWriterConfig config = new IndexWriterConfig(perFieldAnalyzer);
		config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
		IndexWriter writer = new IndexWriter(dir, config);
		return writer;
	}
	
	private static IndexWriter umlsCreateWriter(String INDEX_DIR) throws IOException 
	{
		FSDirectory dir = FSDirectory.open(Paths.get(INDEX_DIR));
		ExactMatchAnalyzer Exactanalyzer = new ExactMatchAnalyzer();
		StandardEnglishAnalyzer Standardanalyzer = new StandardEnglishAnalyzer();
		NGramAnalyzer Nganalyzer = new NGramAnalyzer();
		
		Map analyzerMapUmls = new HashMap();
//		analyzerMap.put("conceptNameEmS", ExactSanalyzer);
		analyzerMapUmls.put("conceptNameEm", Exactanalyzer);
		analyzerMapUmls.put("conceptNameTokens", Standardanalyzer);
		analyzerMapUmls.put("conceptNameNgrams", Nganalyzer);
		PerFieldAnalyzerWrapper perFieldAnalyzer = new PerFieldAnalyzerWrapper(new KeywordAnalyzer(),analyzerMapUmls);
		IndexWriterConfig config = new IndexWriterConfig(perFieldAnalyzer);
		config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
		IndexWriter writer = new IndexWriter(dir, config);
		return writer;
	}
	
	private static ArrayList<String[]> searchByMention(String mention,IndexSearcher searcherTrain, IndexSearcher searcherUmlsPt ,IndexSearcher searcherUmls) throws Exception
	{

		ExactMatchStrongAnalyzer ExactSanalyzer = new ExactMatchStrongAnalyzer();
		ExactMatchAnalyzer Exactanalyzer = new ExactMatchAnalyzer();
		StandardEnglishAnalyzer Standardanalyzer = new StandardEnglishAnalyzer();
		NGramAnalyzer Nganalyzer = new NGramAnalyzer();
		
		Map analyzerMap = new HashMap();
		analyzerMap.put("conceptNameEmS", ExactSanalyzer);
		analyzerMap.put("conceptNameEm", Exactanalyzer);
		analyzerMap.put("conceptNameTokens", Standardanalyzer);
		analyzerMap.put("conceptNameNgrams", Nganalyzer);
		PerFieldAnalyzerWrapper perFieldAnalyzer = new PerFieldAnalyzerWrapper(new KeywordAnalyzer(),analyzerMap);

		Query idEndQuery  = new TermQuery(new Term("cuiEnd", "x"));
		GroupingSearch groupingSearch = new GroupingSearch(idEndQuery);
		QueryParser SnameQueryParser = new QueryParser("conceptNameEmS", perFieldAnalyzer);
		QueryParser nameQueryParser = new QueryParser("conceptNameEm", perFieldAnalyzer);  
		QueryParser NameTokensQueryParser = new QueryParser("conceptNameTokens", perFieldAnalyzer);
		QueryParser NgQueryParser = new QueryParser("conceptNameNgrams", perFieldAnalyzer);
		
		String escapedQueryString = QueryParser.escape(mention);
		
		ArrayList<String[]> results; 
//	    String luceneSpecialCharacters = """([-+&|!(){}\[\]^"~*?:\\/\s])""";
//	    val escapedQueryString = mention.replaceAll(luceneSpecialCharacters, """\\$1""")

	    // first look for an exact match of the input phrase (the "name" field ignores spaces, punctuation, etc.)
		
		int candidate_size = 100;
		
		results =  scoredEntries( groupingSearch,  searcherTrain,  SnameQueryParser,  escapedQueryString,candidate_size,"SEM_Train");
		
		if (results.isEmpty()) {
	    	results =  scoredEntries( groupingSearch,  searcherTrain,  nameQueryParser,  escapedQueryString,candidate_size,"EM_Train");
		}
		
		
		if (results.isEmpty()) {
	    	results =  scoredEntries( groupingSearch,  searcherUmlsPt,  SnameQueryParser,  escapedQueryString,candidate_size,"SEM_UmlsPt");
		}
		
		if (results.isEmpty()) {
	    	results =  scoredEntries( groupingSearch,  searcherUmlsPt,  nameQueryParser,  escapedQueryString,candidate_size,"EM_UmlsPt");
		}

		if (results.isEmpty()) {
    	results =  scoredEntries( groupingSearch,  searcherUmls,  nameQueryParser,  escapedQueryString,candidate_size,"EM_Umls");
		}
				
	    if (results.isEmpty()) {
	    	results = scoredEntries(groupingSearch,  searcherUmls,  NameTokensQueryParser,  escapedQueryString,candidate_size,"token_Umls");
	    }
//		results = scoredEntries(groupingSearch,  searcherUmls,  NameTokensQueryParser,  escapedQueryString,40,"token_Umls");
	    if (results.isEmpty()) { 
	        results = scoredEntries(groupingSearch,  searcherUmls,  nameQueryParser,  escapedQueryString +"~",candidate_size,"fuzzy_Umls" );
	  	    }
	    
	    if (results.isEmpty()) {
	    	results = scoredEntries(groupingSearch,  searcherUmls,  NgQueryParser,  escapedQueryString,candidate_size,"ngram_Umls");
	    }
	    
//	    else if (!results.isEmpty() && results.size()<20){
//	    	System.out.println("%%%%%%%%%%%%%%");
//	    	System.out.println(results.size());
//	    	results.addAll(scoredEntries(groupingSearch,  searcher,  NgQueryParser,  escapedQueryString));
//	    	System.out.println(results.size());
//	    	
//	    }
	    
	

		return results;
	}

	private static IndexSearcher createSearcher(String indx_dir) throws IOException {
		Directory dir = FSDirectory.open(Paths.get(indx_dir));
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		return searcher;
	}
	
	private static ArrayList<String[]> scoredEntries(GroupingSearch groupingSearch, IndexSearcher searcher, QueryParser queryparser
			, String querystring, int maxHits,String type) throws IOException, ParseException {
		TopGroups<?> topGroups =  groupingSearch.search(searcher, queryparser.parse(querystring), 0, maxHits);
		
		ArrayList<String[]> listOLists = new ArrayList<String[]>();
	    if (topGroups == null) return listOLists;
	    else {
	        for (GroupDocs groupdocs: topGroups.groups){
	          Document headDoc = searcher.doc(groupdocs.scoreDocs[0].doc);
	          String[] Info = new String[] {headDoc.get("cui"),type,Float.toString(groupdocs.maxScore)};
	          listOLists.add(Info);
	        }
	        return listOLists;
	      }
	  }

	}

