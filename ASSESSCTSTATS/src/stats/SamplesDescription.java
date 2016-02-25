package stats;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import parser_annotations.Annotator;
import parser_annotations.Chunk;
import parser_annotations.ProcessTableResults;
import parser_annotations.Sentence;
import parser_annotations.Snippet;
import parser_annotations.Token;

public class SamplesDescription {
	public static void getSampleDescription(ProcessTableResults ptr){
		HashMap<String,ArrayList<Annotator>> listAnnotations = ptr.getListAnnotations();
		System.out.println("Language\tAnnotators\tSnippets\tSentences\tChunks\tTokens\tAdoptAnn\tAlterAnn\tAbstaAnn\tLines");
		
		for(String language: listAnnotations.keySet()){
			ArrayList<Annotator> listAnnotators = listAnnotations.get(language);
			int nSnippetsTotal	= 0;
			int nSentencesTotal	= 0;
			int nChunksTotal	= 0;
			int nTokensTotal	= 0;
			int nAdoptAnnTotal	= 0;
			int nAlterAnnTotal	= 0;
			int nAbstaAnnTotal	= 0;
			int nLinesTotal		= 0;
			for(Annotator annotator: listAnnotators){
				int nSnippets	= 0;
				int nSentences	= 0;
				int nChunks		= 0;
				int nTokens		= 0;
				int nAdoptAnn	= 0;
				int nAlterAnn	= 0;
				int nAbstaAnn	= 0;
				int nLines		= 0;
				ArrayList<Snippet> listSnippets = annotator.getListOfSnippets();
				nSnippets +=listSnippets.size();
				nSnippetsTotal +=listSnippets.size();
				
				for(Snippet snippet: listSnippets){
					nLines += snippet.getNumberOfLines();
					nLinesTotal += snippet.getNumberOfLines();
					ArrayList<Sentence> listSentences = snippet.getListOfSentences();
					nSentences += listSentences.size();
					nSentencesTotal += listSentences.size();
					
					for(Sentence sentence: listSentences){
						ArrayList<Chunk> listChunks = sentence.getListOfChunks();
						nChunks += listChunks.size();
						nChunksTotal += listChunks.size();
						for(Chunk chunk: listChunks){
							ArrayList<Token> listTokens = chunk.getListOfTokens();
							nTokens += listTokens.size();
							nTokensTotal += listTokens.size();
							
							for(Token token: listTokens){
								if(token.getAdoptAnnotations()!=null){
									nAdoptAnn++;
									nAdoptAnnTotal++;
								}
								if(token.getAlternativeAnnotations()!=null){
									nAlterAnn++;
									nAlterAnnTotal++;
								}
								if(token.getAbstainAnnotations()!=null){
									nAbstaAnn++;
									nAbstaAnnTotal++;
								}
							}
						}
					}
				}
				System.out.println(language+"\t"+annotator.getId()+"\t"+nSnippets+"\t"+nSentences+"\t"+nChunks+"\t"+nTokens+"\t"+nAdoptAnn+"\t"+nAlterAnn+"\t"+nAbstaAnn+"\t"+nLines);
			}
			System.out.println(language+"\t"+listAnnotators.size()+"\t"+nSnippetsTotal+"\t"+nSentencesTotal+"\t"+nChunksTotal+"\t"+nTokensTotal+"\t"+nAdoptAnnTotal+"\t"+nAlterAnnTotal+"\t"+nAbstaAnnTotal+"\t"+nLinesTotal);
		}
	}
	
	public static void getSampleDescription(ProcessTableResults ptr, String outFile){
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(outFile,true));
			HashMap<String,ArrayList<Annotator>> listAnnotations = ptr.getListAnnotations();
			bw.write("Language\tAnnotators\tSnippets\tSentences\tChunks\tTokens\tAdoptAnn\tAlterAnn\tAbstaAnn\tLines\n");
		
			for(String language: listAnnotations.keySet()){
				ArrayList<Annotator> listAnnotators = listAnnotations.get(language);
				int nAnnotators = listAnnotators.size();
				int nSnippets	= 0;
				int nSentences	= 0;
				int nChunks		= 0;
				int nTokens		= 0;
				int nAdoptAnn	= 0;
				int nAlterAnn	= 0;
				int nAbstaAnn	= 0;
				int nLines		= 0;
				for(Annotator annotator: listAnnotators){
					ArrayList<Snippet> listSnippets = annotator.getListOfSnippets();
					nSnippets +=listSnippets.size();
					for(Snippet snippet: listSnippets){
						nLines += snippet.getNumberOfLines();
						ArrayList<Sentence> listSentences = snippet.getListOfSentences();
						nSentences += listSentences.size();
						for(Sentence sentence: listSentences){
							ArrayList<Chunk> listChunks = sentence.getListOfChunks();
							nChunks += listChunks.size();
							for(Chunk chunk: listChunks){
								ArrayList<Token> listTokens = chunk.getListOfTokens();
								nTokens += listTokens.size();
								for(Token token: listTokens){
									if(token.getAdoptAnnotations()!=null)		nAdoptAnn++;
									if(token.getAlternativeAnnotations()!=null)	nAlterAnn++;
									if(token.getAbstainAnnotations()!=null)		nAbstaAnn++;
								}
							}
						}
					}
				}
				
				bw.write(language+"\t"+nAnnotators+"\t"+nSnippets+"\t"+nSentences+"\t"+nChunks+"\t"+nTokens+"\t"+nAdoptAnn+"\t"+nAlterAnn+"\t"+nAbstaAnn+"\t"+nLines+"\n");
			}
			bw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
