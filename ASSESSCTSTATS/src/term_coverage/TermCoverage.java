package term_coverage;
import java.util.ArrayList;
import java.util.HashMap;

import parser_annotations.AnnotationToken;
import parser_annotations.Annotator;
import parser_annotations.Chunk;
import parser_annotations.ProcessTableResults;
import parser_annotations.Sentence;
import parser_annotations.Snippet;
import parser_annotations.Token;


public class TermCoverage {
	private HashMap<String,ArrayList<AnnotatorTC>> listTermCoverage;
	public TermCoverage(ProcessTableResults ptr){
		listTermCoverage = new HashMap<String,ArrayList<AnnotatorTC>>();
		initializeConceptCoverage(ptr);
	}
	private void initializeConceptCoverage(ProcessTableResults ptr){
		HashMap<String,ArrayList<Annotator>> rawListAnnotations = ptr.getListAnnotations();
		for(String language: rawListAnnotations.keySet()){
			ArrayList<AnnotatorTC> listAnnotatorTC = new ArrayList<AnnotatorTC>();
			listTermCoverage.put(language, listAnnotatorTC);
			ArrayList<Annotator> rawListAnnotators = rawListAnnotations.get(language);
			for(Annotator annotator: rawListAnnotators){
				AnnotatorTC annotatortc = new AnnotatorTC(annotator.getId(),annotator.getLanguage());
				listTermCoverage.get(language).add(annotatortc);
				ArrayList<Snippet> rawListSnippets = annotator.getListOfSnippets();
				for(Snippet snippet: rawListSnippets){
					SnippetTC snippettc = new SnippetTC(snippet.getId(),snippet.getOriginalLanguage());
					annotatortc.addSnippet(snippettc);
					ArrayList<Sentence> listSentences = snippet.getListOfSentences();
					for(Sentence sentence: listSentences){
						ArrayList<Chunk> listChunks = sentence.getListOfChunks();
						for(Chunk chunk: listChunks){
							AnnotationTC annotationtc = new AnnotationTC(chunk.getId(),sentence.getId());
							snippettc.addAnnotation(annotationtc);
							ArrayList<Token> listTokens = chunk.getListOfTokens();
							
							//ADOPT							
							for(int i=0;i<listTokens.size();i++){
								Token token = listTokens.get(i);
								AnnotationToken at = token.getAdoptAnnotations();
								if(at==null) continue;
								if(at.getConceptCoverage() == CodeTC.NOCOV){
									int sizeList = annotationtc.getListOfAdpCodesTC().size();
									if((sizeList > 0) && annotationtc.getListOfAdpCodesTC().get(sizeList - 1).getCoverageScore() == CodeTC.NOCOV){
										annotationtc.getListOfAdpCodesTC().get(sizeList - 1).addToken(token.getToken());
									}else{
										CodeTC codetc = new CodeTC(token.getToken(),"",CodeTC.NOCOV);
										codetc.setTermCoverage(false);
										annotationtc.addAdpCodeTC(codetc);
									}
									continue;
								}
								if(at.getConceptCoverage() == CodeTC.PARTCOV){
									for(String code: at.getListCodes()){
										boolean notIncluded = true;
										for(CodeTC codetc: annotationtc.getListOfAdpCodesTC()){
											if(codetc.getCode().equalsIgnoreCase(code)){
												notIncluded = false;
												codetc.addToken(token.getToken());
												codetc.setTermCoverage(at.getTermCoverage());
												break;
											}
										}
										if(notIncluded){
											CodeTC codetc = new CodeTC(token.getToken(),code,CodeTC.PARTCOV);
											codetc.setTermCoverage(at.getTermCoverage());
											annotationtc.addAdpCodeTC(codetc);
										}
									}
									continue;
								}
								if(at.getConceptCoverage() == CodeTC.INFECOV){
									for(String code: at.getListCodes()){
										boolean notIncluded = true;
										for(CodeTC codetc: annotationtc.getListOfAdpCodesTC()){
											if(codetc.getCode().equalsIgnoreCase(code)){
												notIncluded = false;
												codetc.addToken(token.getToken());
												codetc.setTermCoverage(at.getTermCoverage());
												break;
											}
										}
										if(notIncluded){
											CodeTC codetc = new CodeTC(token.getToken(),code,CodeTC.INFECOV);
											codetc.setTermCoverage(at.getTermCoverage());
											annotationtc.addAdpCodeTC(codetc);
										}
									}
									continue;
								}
								if(at.getConceptCoverage() == CodeTC.FULLCOV){
									for(String code: at.getListCodes()){
										boolean notIncluded = true;
										for(CodeTC codetc: annotationtc.getListOfAdpCodesTC()){
											if(codetc.getCode().equalsIgnoreCase(code)){
												notIncluded = false;
												codetc.addToken(token.getToken());
												codetc.setTermCoverage(at.getTermCoverage());
												break;
											}
										}
										if(notIncluded){
											CodeTC codetc = new CodeTC(token.getToken(),code,CodeTC.FULLCOV);
											codetc.setTermCoverage(at.getTermCoverage());
											annotationtc.addAdpCodeTC(codetc);
										}
									}
									continue;
								}
								
								System.out.println("ERROR: Unknown coverage score: "+at.getConceptCoverage());
							}
							
														
							//ALTERNATIVE
							for(int i=0;i<listTokens.size();i++){
								Token token = listTokens.get(i);
								AnnotationToken at = token.getAlternativeAnnotations();
								if(at==null)continue;
								if(at.getConceptCoverage() == CodeTC.NOCOV){
									int sizeList = annotationtc.getListOfAltCodesTC().size();
									if((sizeList > 0) && annotationtc.getListOfAltCodesTC().get(sizeList - 1).getCoverageScore() == CodeTC.NOCOV){
										annotationtc.getListOfAltCodesTC().get(sizeList - 1).addToken(token.getToken());
									}else{
										CodeTC codetc = new CodeTC(token.getToken(),"",CodeTC.NOCOV);
										codetc.setTermCoverage(false);
										annotationtc.addAltCodeTC(codetc);
									}
									continue;
								}
								if(at.getConceptCoverage() == CodeTC.PARTCOV){
									for(String code: at.getListCodes()){
										boolean notIncluded = true;
										for(CodeTC codetc: annotationtc.getListOfAltCodesTC()){
											if(codetc.getCode().equalsIgnoreCase(code)){
												notIncluded = false;
												codetc.addToken(token.getToken());
												codetc.setTermCoverage(at.getTermCoverage());
												break;
											}
										}
										if(notIncluded){
											CodeTC codetc = new CodeTC(token.getToken(),code,CodeTC.PARTCOV);
											codetc.setTermCoverage(at.getTermCoverage());
											annotationtc.addAltCodeTC(codetc);
										}
									}
									continue;
								}
								if(at.getConceptCoverage() == CodeTC.INFECOV){
									for(String code: at.getListCodes()){
										boolean notIncluded = true;
										for(CodeTC codetc: annotationtc.getListOfAltCodesTC()){
											if(codetc.getCode().equalsIgnoreCase(code)){
												notIncluded = false;
												codetc.addToken(token.getToken());
												codetc.setTermCoverage(at.getTermCoverage());
												break;
											}
										}
										if(notIncluded){
											CodeTC codetc = new CodeTC(token.getToken(),code,CodeTC.INFECOV);
											codetc.setTermCoverage(at.getTermCoverage());
											annotationtc.addAltCodeTC(codetc);
										}
									}
									continue;
								}
								if(at.getConceptCoverage() == CodeTC.FULLCOV){
									for(String code: at.getListCodes()){
										boolean notIncluded = true;
										for(CodeTC codetc: annotationtc.getListOfAltCodesTC()){
											if(codetc.getCode().equalsIgnoreCase(code)){
												notIncluded = false;
												codetc.addToken(token.getToken());
												codetc.setTermCoverage(at.getTermCoverage());
												break;
											}
										}
										if(notIncluded){
											CodeTC codetc = new CodeTC(token.getToken(),code,CodeTC.FULLCOV);
											codetc.setTermCoverage(at.getTermCoverage());
											annotationtc.addAltCodeTC(codetc);
										}
									}
									continue;
								}
								
								System.out.println("ERROR: Unknown coverage score: "+at.getConceptCoverage());
							}
							
							
							//ABSTAIN
							for(int i=0;i<listTokens.size();i++){
								Token token = listTokens.get(i);
								AnnotationToken at = token.getAbstainAnnotations();
								if(at==null) continue;
								if(at.getConceptCoverage() == CodeTC.NOCOV){
									int sizeList = annotationtc.getListOfAbsCodesTC().size();
									if((sizeList > 0) && annotationtc.getListOfAbsCodesTC().get(sizeList - 1).getCoverageScore() == CodeTC.NOCOV){
										annotationtc.getListOfAbsCodesTC().get(sizeList - 1).addToken(token.getToken());
									}else{
										CodeTC codetc = new CodeTC(token.getToken(),"",CodeTC.NOCOV);
										codetc.setTermCoverage(false);
										annotationtc.addAbsCodeTC(codetc);
									}
									continue;
								}
								if(at.getConceptCoverage() == CodeTC.PARTCOV){
									for(String code: at.getListCodes()){
										boolean notIncluded = true;
										for(CodeTC codetc: annotationtc.getListOfAbsCodesTC()){
											if(codetc.getCode().equalsIgnoreCase(code)){
												notIncluded = false;
												codetc.addToken(token.getToken());
												codetc.setTermCoverage(at.getTermCoverage());
												break;
											}
										}
										if(notIncluded){
											CodeTC codetc = new CodeTC(token.getToken(),code,CodeTC.PARTCOV);
											codetc.setTermCoverage(at.getTermCoverage());
											annotationtc.addAbsCodeTC(codetc);
										}
									}
									continue;
								}
								if(at.getConceptCoverage() == CodeTC.INFECOV){
									for(String code: at.getListCodes()){
										boolean notIncluded = true;
										for(CodeTC codetc: annotationtc.getListOfAbsCodesTC()){
											if(codetc.getCode().equalsIgnoreCase(code)){
												notIncluded = false;
												codetc.addToken(token.getToken());
												codetc.setTermCoverage(at.getTermCoverage());
												break;
											}
										}
										if(notIncluded){
											CodeTC codetc = new CodeTC(token.getToken(),code,CodeTC.INFECOV);
											codetc.setTermCoverage(at.getTermCoverage());
											annotationtc.addAbsCodeTC(codetc);
										}
									}
									continue;
								}
								if(at.getConceptCoverage() == CodeTC.FULLCOV){
									for(String code: at.getListCodes()){
										boolean notIncluded = true;
										for(CodeTC codetc: annotationtc.getListOfAbsCodesTC()){
											if(codetc.getCode().equalsIgnoreCase(code)){
												notIncluded = false;
												codetc.addToken(token.getToken());
												codetc.setTermCoverage(at.getTermCoverage());
												break;
											}
										}
										if(notIncluded){
											CodeTC codetc = new CodeTC(token.getToken(),code,CodeTC.FULLCOV);
											codetc.setTermCoverage(at.getTermCoverage());
											annotationtc.addAbsCodeTC(codetc);
										}
									}
									continue;
								}
								
								System.out.println("ERROR: Unknown coverage score: "+at.getConceptCoverage());
							}
						}
					}
				}
			}
		}
	}
	
	public HashMap<String,ArrayList<AnnotatorTC>> getListAnnotatorsTC(){
		return listTermCoverage;
	}
}
