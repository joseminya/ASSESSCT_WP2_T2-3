package interRaterAgreement;

import java.util.ArrayList;
import java.util.HashMap;

import parser_annotations.AnnotationToken;
import parser_annotations.Annotator;
import parser_annotations.Chunk;
import parser_annotations.ProcessTableResults;
import parser_annotations.Sentence;
import parser_annotations.Snippet;
import parser_annotations.Token;

public class InterAnnotatorAgreement {
	private HashMap<String,ArrayList<AnnotatorIAA>> listIAA;
	
	public HashMap<String,ArrayList<AnnotatorIAA>> getAgreement(){
		return listIAA;
	}
	
	public InterAnnotatorAgreement(ProcessTableResults ptr){
		listIAA = new HashMap<String,ArrayList<AnnotatorIAA>>();
		try{	
			HashMap<String,ArrayList<Annotator>> listAnnotators = ptr.getListAnnotations();
			for(String language: listAnnotators.keySet()){
				ArrayList<AnnotatorIAA> listIaaAnnotators = new ArrayList<AnnotatorIAA>();
				listIAA.put(language, listIaaAnnotators);
				for(Annotator annotator: listAnnotators.get(language)){
					AnnotatorIAA annotatorIaa = new AnnotatorIAA(annotator.getId(),annotator.getLanguage());
					listIaaAnnotators.add(annotatorIaa);
					for(Snippet snippet: annotator.getListOfSnippets()){
						SnippetIAA snippetIaa = new SnippetIAA(snippet.getId(),snippet.getOriginalLanguage());
						annotatorIaa.addSnippet(snippetIaa);
						for(Sentence sentence: snippet.getListOfSentences()){
							SentenceIAA sentenceIaa = new SentenceIAA(sentence.getId());
							snippetIaa.addSentence(sentenceIaa);
							IAAUnit unitAdp = new IAAUnit(snippetIaa.getId()+"_"+sentenceIaa.getId());
							IAAUnit unitAlt = new IAAUnit(snippetIaa.getId()+"_"+sentenceIaa.getId());
							IAAUnit unitAbs = new IAAUnit(snippetIaa.getId()+"_"+sentenceIaa.getId());
							sentenceIaa.setAdpUnit(unitAdp);
							sentenceIaa.setAltUnit(unitAlt);
							sentenceIaa.setAbsUnit(unitAbs);
							for(Chunk chunk: sentence.getListOfChunks()){
								for(Token token: chunk.getListOfTokens()){
									AnnotationToken annTokenAdp = token.getAdoptAnnotations();
									if(annTokenAdp!=null){
										if(annTokenAdp.getConceptCoverage()<=1){//FULL & INFERRED COV
											for(String code: annTokenAdp.getListCodes()){
												unitAdp.addCodeFull(code);
											}
										}
										if(annTokenAdp.getConceptCoverage()==2){//PARTIAL COV
											for(String code: annTokenAdp.getListCodes()){
												unitAdp.addCodePartial(code);
											}
										}
									}
									AnnotationToken annTokenAlt = token.getAlternativeAnnotations();
									if(annTokenAlt!=null){
										if(annTokenAlt.getConceptCoverage()<=1){//FULL & INFERRED COV
											for(String code: annTokenAlt.getListCodes()){
												unitAlt.addCodeFull(code);
											}
										}
										if(annTokenAlt.getConceptCoverage()==2){//PARTIAL COV
											for(String code: annTokenAlt.getListCodes()){
												unitAlt.addCodePartial(code);
											}
										}
									}
									AnnotationToken annTokenAbs = token.getAbstainAnnotations();
									if(annTokenAbs!=null){
										if(annTokenAbs.getConceptCoverage()<=1){//FULL & INFERRED COV
											for(String code: annTokenAbs.getListCodes()){
												unitAbs.addCodeFull(code);
											}
										}
										if(annTokenAbs.getConceptCoverage()==2){//PARTIAL COV
											for(String code: annTokenAbs.getListCodes()){
												unitAbs.addCodePartial(code);
											}
										}
									}
								}
							}
						}
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
