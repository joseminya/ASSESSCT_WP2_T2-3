package concept_coverage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import parser_annotations.AnnotationToken;
import parser_annotations.Annotator;
import parser_annotations.Chunk;
import parser_annotations.ProcessTableResults;
import parser_annotations.Sentence;
import parser_annotations.Snippet;
import parser_annotations.Token;

public class ConceptCoverage {
	private HashMap<String,ArrayList<AnnotatorCC>> listLanguageAnnotations;
	
	public ConceptCoverage(ProcessTableResults ptr){
		listLanguageAnnotations = new HashMap<String,ArrayList<AnnotatorCC>>();
		initializeConceptCoverage(ptr);
	}
	
	private void initializeConceptCoverage(ProcessTableResults ptr){
		HashMap<String,ArrayList<Annotator>> rawListAnnotations = ptr.getListAnnotations();
		for(String language: rawListAnnotations.keySet()){
			ArrayList<AnnotatorCC> listAnnotatorCC = new ArrayList<AnnotatorCC>();
			listLanguageAnnotations.put(language, listAnnotatorCC);
			ArrayList<Annotator> rawListAnnotators = rawListAnnotations.get(language);
			for(Annotator annotator: rawListAnnotators){
				AnnotatorCC annotatorcc = new AnnotatorCC(annotator.getId(),annotator.getLanguage());
				listLanguageAnnotations.get(language).add(annotatorcc);
				ArrayList<Snippet> rawListSnippets = annotator.getListOfSnippets();
				for(Snippet snippet: rawListSnippets){
					SnippetCC snippetcc = new SnippetCC(snippet.getId(),snippet.getOriginalLanguage());
					annotatorcc.addSnippet(snippetcc);
					ArrayList<Sentence> listSentences = snippet.getListOfSentences();
					for(Sentence sentence: listSentences){
						ArrayList<Chunk> listChunks = sentence.getListOfChunks();
						for(Chunk chunk: listChunks){
							AnnotationCC annotationcc = new AnnotationCC(chunk.getId(),sentence.getId());
							snippetcc.addAnnotation(annotationcc);
							ArrayList<Token> listTokens = chunk.getListOfTokens();
							
							//ADOPT							
							for(int i=0;i<listTokens.size();i++){
								Token token = listTokens.get(i);
								AnnotationToken at = token.getAdoptAnnotations();
								if(at==null) continue;
								if(at.getConceptCoverage() == CodeCC.NOCOV){
									int sizeList = annotationcc.getListOfSCTCodesCC().size();
									if((sizeList > 0) && annotationcc.getListOfSCTCodesCC().get(sizeList - 1).getCoverageScore() == CodeCC.NOCOV){
										annotationcc.getListOfSCTCodesCC().get(sizeList - 1).addToken(token.getToken());
									}else{
										CodeCC codecc = new CodeCC(token.getToken(),"",CodeCC.NOCOV);
										annotationcc.addSCTCodeCC(codecc);
									}
									continue;
								}
								if(at.getConceptCoverage() == CodeCC.PARTCOV){
									for(String code: at.getListCodes()){
										boolean notIncluded = true;
										for(CodeCC codecc: annotationcc.getListOfSCTCodesCC()){
											if(codecc.getCode().equalsIgnoreCase(code)){
												notIncluded = false;
												codecc.addToken(token.getToken());
												break;
											}
										}
										if(notIncluded){
											CodeCC codecc = new CodeCC(token.getToken(),code,CodeCC.PARTCOV);
											annotationcc.addSCTCodeCC(codecc);
										}
									}
									continue;
								}
								if(at.getConceptCoverage() == CodeCC.INFECOV){
									for(String code: at.getListCodes()){
										boolean notIncluded = true;
										for(CodeCC codecc: annotationcc.getListOfSCTCodesCC()){
											if(codecc.getCode().equalsIgnoreCase(code)){
												notIncluded = false;
												codecc.addToken(token.getToken());
												break;
											}
										}
										if(notIncluded){
											CodeCC codecc = new CodeCC(token.getToken(),code,CodeCC.INFECOV);
											annotationcc.addSCTCodeCC(codecc);
										}
									}
									continue;
								}
								if(at.getConceptCoverage() == CodeCC.FULLCOV){
									for(String code: at.getListCodes()){
										boolean notIncluded = true;
										for(CodeCC codecc: annotationcc.getListOfSCTCodesCC()){
											if(codecc.getCode().equalsIgnoreCase(code)){
												notIncluded = false;
												codecc.addToken(token.getToken());
												break;
											}
										}
										if(notIncluded){
											CodeCC codecc = new CodeCC(token.getToken(),code,CodeCC.FULLCOV);
											annotationcc.addSCTCodeCC(codecc);
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
								if(at.getConceptCoverage() == CodeCC.NOCOV){
									int sizeList = annotationcc.getListOfUMLSCodesCC().size();
									if((sizeList > 0) && annotationcc.getListOfUMLSCodesCC().get(sizeList - 1).getCoverageScore() == CodeCC.NOCOV){
										annotationcc.getListOfUMLSCodesCC().get(sizeList - 1).addToken(token.getToken());
									}else{
										CodeCC codecc = new CodeCC(token.getToken(),"",CodeCC.NOCOV);
										annotationcc.addUMLSCodeCC(codecc);
									}
									continue;
								}
								if(at.getConceptCoverage() == CodeCC.PARTCOV){
									for(String code: at.getListCodes()){
										boolean notIncluded = true;
										for(CodeCC codecc: annotationcc.getListOfUMLSCodesCC()){
											if(codecc.getCode().equalsIgnoreCase(code)){
												notIncluded = false;
												codecc.addToken(token.getToken());
												break;
											}
										}
										if(notIncluded){
											CodeCC codecc = new CodeCC(token.getToken(),code,CodeCC.PARTCOV);
											annotationcc.addUMLSCodeCC(codecc);
										}
									}
									continue;
								}
								if(at.getConceptCoverage() == CodeCC.INFECOV){
									for(String code: at.getListCodes()){
										boolean notIncluded = true;
										for(CodeCC codecc: annotationcc.getListOfUMLSCodesCC()){
											if(codecc.getCode().equalsIgnoreCase(code)){
												notIncluded = false;
												codecc.addToken(token.getToken());
												break;
											}
										}
										if(notIncluded){
											CodeCC codecc = new CodeCC(token.getToken(),code,CodeCC.INFECOV);
											annotationcc.addUMLSCodeCC(codecc);
										}
									}
									continue;
								}
								if(at.getConceptCoverage() == CodeCC.FULLCOV){
									for(String code: at.getListCodes()){
										boolean notIncluded = true;
										for(CodeCC codecc: annotationcc.getListOfUMLSCodesCC()){
											if(codecc.getCode().equalsIgnoreCase(code)){
												notIncluded = false;
												codecc.addToken(token.getToken());
												break;
											}
										}
										if(notIncluded){
											CodeCC codecc = new CodeCC(token.getToken(),code,CodeCC.FULLCOV);
											annotationcc.addUMLSCodeCC(codecc);
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
								if(at.getConceptCoverage() == CodeCC.NOCOV){
									int sizeList = annotationcc.getListOfLocalCodesCC().size();
									if((sizeList > 0) && annotationcc.getListOfLocalCodesCC().get(sizeList - 1).getCoverageScore() == CodeCC.NOCOV){
										annotationcc.getListOfLocalCodesCC().get(sizeList - 1).addToken(token.getToken());
									}else{
										CodeCC codecc = new CodeCC(token.getToken(),"",CodeCC.NOCOV);
										annotationcc.addLocalCodeCC(codecc);
									}
									continue;
								}
								if(at.getConceptCoverage() == CodeCC.PARTCOV){
									for(String code: at.getListCodes()){
										boolean notIncluded = true;
										for(CodeCC codecc: annotationcc.getListOfLocalCodesCC()){
											if(codecc.getCode().equalsIgnoreCase(code)){
												notIncluded = false;
												codecc.addToken(token.getToken());
												break;
											}
										}
										if(notIncluded){
											CodeCC codecc = new CodeCC(token.getToken(),code,CodeCC.PARTCOV);
											annotationcc.addLocalCodeCC(codecc);
										}
									}
									continue;
								}
								if(at.getConceptCoverage() == CodeCC.INFECOV){
									for(String code: at.getListCodes()){
										boolean notIncluded = true;
										for(CodeCC codecc: annotationcc.getListOfLocalCodesCC()){
											if(codecc.getCode().equalsIgnoreCase(code)){
												notIncluded = false;
												codecc.addToken(token.getToken());
												break;
											}
										}
										if(notIncluded){
											CodeCC codecc = new CodeCC(token.getToken(),code,CodeCC.INFECOV);
											annotationcc.addLocalCodeCC(codecc);
										}
									}
									continue;
								}
								if(at.getConceptCoverage() == CodeCC.FULLCOV){
									for(String code: at.getListCodes()){
										boolean notIncluded = true;
										for(CodeCC codecc: annotationcc.getListOfLocalCodesCC()){
											if(codecc.getCode().equalsIgnoreCase(code)){
												notIncluded = false;
												codecc.addToken(token.getToken());
												break;
											}
										}
										if(notIncluded){
											CodeCC codecc = new CodeCC(token.getToken(),code,CodeCC.FULLCOV);
											annotationcc.addLocalCodeCC(codecc);
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
	
	public void setSemanticTypes(HashMap<String,HashSet<String>> mapSemTypesAdt, HashMap<String,HashSet<String>> mapSemTypesAlt, HashMap<String,HashSet<String>> mapSemTypesAbs, HashMap<String,String> semtype_group){
		
		for(String language: listLanguageAnnotations.keySet()){
			for(AnnotatorCC annotator: listLanguageAnnotations.get(language)){
				for(SnippetCC snippet: annotator.getListOfSnippets()){
					for(AnnotationCC annotation: snippet.getListOfAnnotations()){
						for(CodeCC code: annotation.getListOfSCTCodesCC()){
							if(!code.getCode().isEmpty() && mapSemTypesAdt.containsKey(code.getCode())){
								HashSet<String> semgroups = new HashSet<String>();
								for(String type: mapSemTypesAdt.get(code.getCode())){
									if(type.isEmpty())continue;
									String[] tokens = type.split("///");
									for(String semtype: tokens){
										if(semtype_group.containsKey(semtype)){
											semgroups.add(semtype_group.get(semtype));
										}else{
											System.out.println("This semtype is not in semtype_group "+semtype);
										}
									}
								}
								code.setSemanticGroups(semgroups);
							}
						}
						
						for(CodeCC code: annotation.getListOfUMLSCodesCC()){
							if(!code.getCode().isEmpty() && mapSemTypesAlt.containsKey(code.getCode())){
								HashSet<String> semgroups = new HashSet<String>();
								for(String type: mapSemTypesAlt.get(code.getCode())){
									if(type.isEmpty())continue;
									String[] tokens = type.split("///");
									for(String semtype: tokens){
										if(semtype_group.containsKey(semtype)){
											semgroups.add(semtype_group.get(semtype));
										}else{
											System.out.println("This semtype is not in semtype_group "+semtype);
										}
									}
								}
								code.setSemanticGroups(semgroups);
							}
						}
						
						for(CodeCC code: annotation.getListOfLocalCodesCC()){
							if(!code.getCode().isEmpty() && mapSemTypesAbs.containsKey(code.getCode())){
								HashSet<String> semgroups = new HashSet<String>();
								for(String type: mapSemTypesAbs.get(code.getCode())){
									if(type.isEmpty())continue;
									String[] tokens = type.split("///",-1);
									for(String semtype: tokens){
										if(semtype_group.containsKey(semtype)){
											semgroups.add(semtype_group.get(semtype));
										}else{
											//System.out.println("This semtype is not in semtype_group "+semtype);
										}
									}
								}
								code.setSemanticGroups(semgroups);
							}
						}
					}
				}
			}
		}
	}
	
	public HashMap<String,ArrayList<AnnotatorCC>> getListAnnotatorsCC(){
		return listLanguageAnnotations;
	}
}
