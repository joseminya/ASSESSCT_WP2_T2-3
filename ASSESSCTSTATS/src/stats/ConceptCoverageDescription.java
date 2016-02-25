package stats;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import concept_coverage.AnnotationCC;
import concept_coverage.AnnotatorCC;
import concept_coverage.CodeCC;
import concept_coverage.ConceptCoverage;
import concept_coverage.SnippetCC;

public class ConceptCoverageDescription {
	public static void getConceptCoverageDescription(ConceptCoverage cc, ArrayList<String> setOfKeysAdpAlt, ArrayList<String> setOfKeysAbs){
		double significance = 0.95;
		HashMap<String,ArrayList<AnnotatorCC>> listAnnotatorsCC = cc.getListAnnotatorsCC();
		for(String language: listAnnotatorsCC.keySet()){
			
			//ADOPT
			HashMap<String,Integer> listFullCovBySemTypeAdpTotal	= new HashMap<String,Integer>();
			int fullCovAdpTotal	= 0;
			HashMap<String,Integer> listInfCovBySemTypeAdpTotal		= new HashMap<String,Integer>();
			int infCovAdpTotal	= 0;
			HashMap<String,Integer> listPartCovBySemTypeAdpTotal	= new HashMap<String,Integer>();
			int partCovAdpTotal	= 0;
			int noCovAdpTotal	= 0;
			SummaryStatistics strictCovAdpTotal	= new SummaryStatistics();
			SummaryStatistics looseCovAdpTotal	= new SummaryStatistics();
			
			//ALTERNATIVE
			HashMap<String,Integer> listFullCovBySemTypeAltTotal	= new HashMap<String,Integer>();
			int fullCovAltTotal	= 0;
			HashMap<String,Integer> listInfCovBySemTypeAltTotal		= new HashMap<String,Integer>();
			int infCovAltTotal	= 0;
			HashMap<String,Integer> listPartCovBySemTypeAltTotal	= new HashMap<String,Integer>();
			int partCovAltTotal	= 0;
			int noCovAltTotal	= 0;
			SummaryStatistics strictCovAltTotal	= new SummaryStatistics();
			SummaryStatistics looseCovAltTotal	= new SummaryStatistics();

			for(String key: setOfKeysAdpAlt){
				listFullCovBySemTypeAdpTotal.put(key,0);
				listInfCovBySemTypeAdpTotal.put(key,0);
				listPartCovBySemTypeAdpTotal.put(key,0);
				listFullCovBySemTypeAltTotal.put(key,0);
				listInfCovBySemTypeAltTotal.put(key,0);
				listPartCovBySemTypeAltTotal.put(key,0);
			}
			
			//ABSTAIN
			HashMap<String,Integer> listFullCovBySemTypeAbsTotal	= new HashMap<String,Integer>();
			int fullCovAbsTotal	= 0;
			HashMap<String,Integer> listInfCovBySemTypeAbsTotal		= new HashMap<String,Integer>();
			int infCovAbsTotal	= 0;
			HashMap<String,Integer> listPartCovBySemTypeAbsTotal	= new HashMap<String,Integer>();
			int partCovAbsTotal	= 0;
			int noCovAbsTotal	= 0;
			SummaryStatistics strictCovAbsTotal	= new SummaryStatistics();
			SummaryStatistics looseCovAbsTotal	= new SummaryStatistics();
			
			for(String key: setOfKeysAbs){
				listFullCovBySemTypeAbsTotal.put(key, 0);
				listInfCovBySemTypeAbsTotal.put(key, 0);
				listPartCovBySemTypeAbsTotal.put(key, 0);
			}
			
			for(AnnotatorCC annotator: listAnnotatorsCC.get(language)){
				//ADOPT
				HashMap<String,Integer> listFullCovBySemTypeAdp	= new HashMap<String,Integer>();
				int fullCovAdp	= 0;
				HashMap<String,Integer> listInfCovBySemTypeAdp	= new HashMap<String,Integer>();
				int infCovAdp	= 0;
				HashMap<String,Integer> listPartCovBySemTypeAdp	= new HashMap<String,Integer>();
				int partCovAdp	= 0;
				int noCovAdp	= 0;
				SummaryStatistics strictCovAdp	= new SummaryStatistics();
				SummaryStatistics looseCovAdp	= new SummaryStatistics();
				
				//ALTERNATIVE
				HashMap<String,Integer> listFullCovBySemTypeAlt	= new HashMap<String,Integer>();
				int fullCovAlt	= 0;
				HashMap<String,Integer> listInfCovBySemTypeAlt	= new HashMap<String,Integer>();
				int infCovAlt	= 0;
				HashMap<String,Integer> listPartCovBySemTypeAlt	= new HashMap<String,Integer>();
				int partCovAlt	= 0;
				int noCovAlt	= 0;
				SummaryStatistics strictCovAlt	= new SummaryStatistics();
				SummaryStatistics looseCovAlt	= new SummaryStatistics();
				
				for(String key: setOfKeysAdpAlt){
					listFullCovBySemTypeAdp.put(key,0);
					listInfCovBySemTypeAdp.put(key,0);
					listPartCovBySemTypeAdp.put(key,0);
					listFullCovBySemTypeAlt.put(key,0);
					listInfCovBySemTypeAlt.put(key,0);
					listPartCovBySemTypeAlt.put(key,0);
				}
				
				//ABSTAIN
				HashMap<String,Integer> listFullCovBySemTypeAbs	= new HashMap<String,Integer>();
				int fullCovAbs	= 0;
				HashMap<String,Integer> listInfCovBySemTypeAbs	= new HashMap<String,Integer>();
				int infCovAbs	= 0;
				HashMap<String,Integer> listPartCovBySemTypeAbs	= new HashMap<String,Integer>();
				int partCovAbs	= 0;
				int noCovAbs	= 0;
				SummaryStatistics strictCovAbs	= new SummaryStatistics();
				SummaryStatistics looseCovAbs	= new SummaryStatistics();
				
				for(String key: setOfKeysAbs){
					listFullCovBySemTypeAbs.put(key, 0);
					listInfCovBySemTypeAbs.put(key, 0);
					listPartCovBySemTypeAbs.put(key, 0);
				}
				
				
				for(SnippetCC snippet: annotator.getListOfSnippets()){
					double numerator	= snippet.getNumberOfFullCoverageAnnotationsOfAdopt()+snippet.getNumberOfInferredCoverageAnnotationsOfAdopt();
					double denominator	= snippet.getNumberOfFullCoverageAnnotationsOfAdopt()+snippet.getNumberOfInferredCoverageAnnotationsOfAdopt()+snippet.getNumberOfNoCoverageAnnotationsOfAdopt()+snippet.getNumberOfPartialCoverageAnnotationsOfAdopt();
					//System.out.println(numerator+"/"+denominator+"="+numerator/denominator);
					strictCovAdp.addValue(numerator/denominator);
					strictCovAdpTotal.addValue(numerator/denominator);
					numerator	= numerator+snippet.getNumberOfPartialCoverageAnnotationsOfAdopt();
					looseCovAdp.addValue(numerator/denominator);
					looseCovAdpTotal.addValue(numerator/denominator);
					
					numerator	= snippet.getNumberOfFullCoverageAnnotationsOfAlternative()+snippet.getNumberOfInferredCoverageAnnotationsOfAlternative();
					denominator	= snippet.getNumberOfFullCoverageAnnotationsOfAlternative()+snippet.getNumberOfInferredCoverageAnnotationsOfAlternative()+snippet.getNumberOfNoCoverageAnnotationsOfAlternative()+snippet.getNumberOfPartialCoverageAnnotationsOfAlternative();
					strictCovAlt.addValue(numerator/denominator);
					strictCovAltTotal.addValue(numerator/denominator);
					numerator	= numerator+snippet.getNumberOfPartialCoverageAnnotationsOfAlternative();
					looseCovAlt.addValue(numerator/denominator);
					looseCovAltTotal.addValue(numerator/denominator);
					
					numerator	= snippet.getNumberOfFullCoverageAnnotationsOfAbstain()+snippet.getNumberOfInferredCoverageAnnotationsOfAbstain();
					denominator	= snippet.getNumberOfFullCoverageAnnotationsOfAbstain()+snippet.getNumberOfInferredCoverageAnnotationsOfAbstain()+snippet.getNumberOfNoCoverageAnnotationsOfAbstain()+snippet.getNumberOfPartialCoverageAnnotationsOfAbstain();
					strictCovAbs.addValue(numerator/denominator);
					strictCovAbsTotal.addValue(numerator/denominator);
					numerator	= numerator+snippet.getNumberOfPartialCoverageAnnotationsOfAbstain();
					looseCovAbs.addValue(numerator/denominator);
					looseCovAbsTotal.addValue(numerator/denominator);
					
					for(AnnotationCC annotation: snippet.getListOfAnnotations()){
						//ADOPT
						ArrayList<CodeCC> listCodes = annotation.getListOfSCTCodesCC();
						for(CodeCC code: listCodes){
							if(code.getCoverageScore() == CodeCC.FULLCOV){
								fullCovAdp++;
								fullCovAdpTotal++;
								if(code.getSemanticGroup()!=null && !code.getSemanticGroup().isEmpty()){
									for(String semtype: code.getSemanticGroup()){
										if(listFullCovBySemTypeAdp.containsKey(semtype)){
											int freq = listFullCovBySemTypeAdp.get(semtype);
											freq++;
											listFullCovBySemTypeAdp.put(semtype, freq);
											freq = listFullCovBySemTypeAdpTotal.get(semtype);
											freq++;
											listFullCovBySemTypeAdpTotal.put(semtype, freq);
										}
									}
								}else{
									System.out.println("ERROR: code SCT without semantic type "+code.getCode()+" -> "+code.getTokens());
								}
								continue;
							}
							if(code.getCoverageScore() == CodeCC.INFECOV){
								infCovAdp++;
								infCovAdpTotal++;
								if(code.getSemanticGroup()!=null && !code.getSemanticGroup().isEmpty()){
									for(String semtype:code.getSemanticGroup()){
										if(listInfCovBySemTypeAdp.containsKey(semtype)){
											int freq = listInfCovBySemTypeAdp.get(semtype);
											freq++;
											listInfCovBySemTypeAdp.put(semtype, freq);
											freq = listInfCovBySemTypeAdpTotal.get(semtype);
											freq++;
											listInfCovBySemTypeAdpTotal.put(semtype, freq);
										}
									}
								}else{
									System.out.println("ERROR: code SCT without semantic type "+code.getCode()+" -> "+code.getTokens());
								}
								continue;
							}
							if(code.getCoverageScore() == CodeCC.PARTCOV){
								partCovAdp++;
								partCovAdpTotal++;
								if(code.getSemanticGroup()!=null && !code.getSemanticGroup().isEmpty()){
									for(String semtype:code.getSemanticGroup()){
										if(listPartCovBySemTypeAdp.containsKey(semtype)){
											int freq = listPartCovBySemTypeAdp.get(semtype);
											freq++;
											listPartCovBySemTypeAdp.put(semtype, freq);
											freq = listPartCovBySemTypeAdpTotal.get(semtype);
											freq++;
											listPartCovBySemTypeAdpTotal.put(semtype, freq);
										}
									}
								}else{
									System.out.println("ERROR: code SCT without semantic type "+code.getCode()+" -> "+code.getTokens());
								}
								continue;
							}
							if(code.getCoverageScore() == CodeCC.NOCOV){
								noCovAdp++;
								noCovAdpTotal++;
								continue;
							}
						}
						
						//ALTERNATIVE
						listCodes = annotation.getListOfUMLSCodesCC();
						for(CodeCC code: listCodes){
							if(code.getCoverageScore() == CodeCC.FULLCOV){
								fullCovAlt++;
								fullCovAltTotal++;
								if(code.getSemanticGroup()!=null && !code.getSemanticGroup().isEmpty()){
									for(String semtype:code.getSemanticGroup()){
										if(listFullCovBySemTypeAlt.containsKey(semtype)){
											int freq = listFullCovBySemTypeAlt.get(semtype);
											freq++;
											listFullCovBySemTypeAlt.put(semtype, freq);
											freq = listFullCovBySemTypeAltTotal.get(semtype);
											freq++;
											listFullCovBySemTypeAltTotal.put(semtype, freq);
										}
									}
								}else{
									System.out.println("ERROR: code ALT without semantic type "+code.getCode()+" -> "+code.getTokens());
								}
								continue;
							}
							if(code.getCoverageScore() == CodeCC.INFECOV){
								infCovAlt++;
								infCovAltTotal++;
								if(code.getSemanticGroup()!=null && !code.getSemanticGroup().isEmpty()){
									for(String semtype:code.getSemanticGroup()){
										if(listInfCovBySemTypeAlt.containsKey(semtype)){
											int freq = listInfCovBySemTypeAlt.get(semtype);
											freq++;
											listInfCovBySemTypeAlt.put(semtype, freq);
											freq = listInfCovBySemTypeAltTotal.get(semtype);
											freq++;
											listInfCovBySemTypeAltTotal.put(semtype, freq);
										}
									}
								}else{
									System.out.println("ERROR: code ALT without semantic type "+code.getCode()+" -> "+code.getTokens());
								}
								continue;
							}
							if(code.getCoverageScore() == CodeCC.PARTCOV){
								partCovAlt++;
								partCovAltTotal++;
								if(code.getSemanticGroup()!=null && !code.getSemanticGroup().isEmpty()){
									for(String semtype:code.getSemanticGroup()){
										if(listPartCovBySemTypeAlt.containsKey(semtype)){
											int freq = listPartCovBySemTypeAlt.get(semtype);
											freq++;
											listPartCovBySemTypeAlt.put(semtype, freq);
											freq = listPartCovBySemTypeAltTotal.get(semtype);
											freq++;
											listPartCovBySemTypeAltTotal.put(semtype, freq);
										}
									}
								}else{
									System.out.println("ERROR: code ALT without semantic type "+code.getCode()+" -> "+code.getTokens());
								}
								continue;
							}
							if(code.getCoverageScore() == CodeCC.NOCOV){
								noCovAlt++;
								noCovAltTotal++;
								continue;
							}
						}
						
						//ABSTAIN
						listCodes = annotation.getListOfLocalCodesCC();
						for(CodeCC code: listCodes){
							if(code.getCoverageScore() == CodeCC.FULLCOV){
								fullCovAbs++;
								fullCovAbsTotal++;
								if(code.getSemanticGroup()!=null && !code.getSemanticGroup().isEmpty()){
									for(String semtype:code.getSemanticGroup()){
										if(listFullCovBySemTypeAbs.containsKey(semtype)){
											int freq = listFullCovBySemTypeAbs.get(semtype);
											freq++;
											listFullCovBySemTypeAbs.put(semtype, freq);
											freq = listFullCovBySemTypeAbsTotal.get(semtype);
											freq++;
											listFullCovBySemTypeAbsTotal.put(semtype, freq);
										}
									}
								}else{
									//System.out.println("ERROR: code ABS without semantic type "+code.getCode()+" -> "+code.getTokens());
								}
								continue;
							}
							if(code.getCoverageScore() == CodeCC.INFECOV){
								infCovAbsTotal++;
								if(code.getSemanticGroup()!=null && !code.getSemanticGroup().isEmpty()){
									for(String semtype:code.getSemanticGroup()){
										if(listInfCovBySemTypeAbs.containsKey(semtype)){
											int freq = listInfCovBySemTypeAbs.get(semtype);
											freq++;
											listInfCovBySemTypeAbs.put(semtype, freq);
											freq = listInfCovBySemTypeAbsTotal.get(semtype);
											freq++;
											listInfCovBySemTypeAbsTotal.put(semtype, freq);
										}
									}
								}else{
									//System.out.println("ERROR: code ABS without semantic type "+code.getCode()+" -> "+code.getTokens());
								}
								continue;
							}
							if(code.getCoverageScore() == CodeCC.PARTCOV){
								partCovAbs++;
								partCovAbsTotal++;
								if(code.getSemanticGroup()!=null && !code.getSemanticGroup().isEmpty()){
									for(String semtype:code.getSemanticGroup()){
										if(listPartCovBySemTypeAbs.containsKey(semtype)){
											int freq = listPartCovBySemTypeAbs.get(semtype);
											freq++;
											listPartCovBySemTypeAbs.put(semtype, freq);
											freq = listPartCovBySemTypeAbsTotal.get(semtype);
											freq++;
											listPartCovBySemTypeAbsTotal.put(semtype, freq);
										}
									}
								}else{
									//System.out.println("ERROR: code ABS without semantic type "+code.getCode()+" -> "+code.getTokens());
								}
								continue;
							}
							if(code.getCoverageScore() == CodeCC.NOCOV){
								noCovAbsTotal++;
								continue;
							}
						}
					}
				}
				
				Double[] strictCovCIAdp = new Double[2];
				double meanCI = calcMeanCI(strictCovAdp,significance);
				strictCovCIAdp[0]	= strictCovAdp.getMean() - meanCI;
				strictCovCIAdp[1]	= strictCovAdp.getMean() + meanCI;
				
				Double[] looseCovCIAdp	= new Double[2];
				meanCI = calcMeanCI(looseCovAdp,significance);
				looseCovCIAdp[0]	= looseCovAdp.getMean() - meanCI;
				looseCovCIAdp[1]	= looseCovAdp.getMean() + meanCI;
				
				Double[] strictCovCIAlt	= new Double[2];
				meanCI = calcMeanCI(strictCovAlt,significance);
				strictCovCIAlt[0]	= strictCovAlt.getMean() - meanCI;
				strictCovCIAlt[1]	= strictCovAlt.getMean() + meanCI;
				
				Double[] looseCovCIAlt	= new Double[2];
				meanCI = calcMeanCI(looseCovAlt,significance);
				looseCovCIAlt[0]	= looseCovAlt.getMean() - meanCI;
				looseCovCIAlt[1]	= looseCovAlt.getMean() + meanCI;
				
				Double[] strictCovCIAbs	= new Double[2];
				meanCI = calcMeanCI(strictCovAbs,significance);
				strictCovCIAbs[0]	= strictCovAbs.getMean() - meanCI;
				strictCovCIAbs[1]	= strictCovAbs.getMean() + meanCI;
				
				Double[] looseCovCIAbs	= new Double[2];
				meanCI = calcMeanCI(looseCovAbs,significance);
				looseCovCIAbs[0]	= looseCovAbs.getMean() - meanCI;
				looseCovCIAbs[1]	= looseCovAbs.getMean() + meanCI;
				
				System.out.println("Language\tAnnotator\tAdopt - Full\tAdopt - Inf\tAdopt - Part\tAdopt - No cov\tAlt - Full\tAlt - Inf\tAlt - Part\tAlt - No cov\tAbs - Full\tAbs - Inf\tAbs - Part\tAbs - No cov");
				System.out.println(language+"\t"+annotator.getId()+"\t"+fullCovAdp+"\t"+infCovAdp+"\t"+partCovAdp+"\t"+noCovAdp+"\t"+fullCovAlt+"\t"+infCovAlt+"\t"+partCovAlt+"\t"+noCovAlt+"\t"+fullCovAbs+"\t"+infCovAbs+"\t"+partCovAbs+"\t"+noCovAbs);
				System.out.println("Scenario\tLanguage\tAnnotator\tStrict - Coverage\tStrict CI-left\tStrict CI-right\tLoose - Coverage\tLoose CI-left\tLoose CI-right");
				if((fullCovAdp+infCovAdp+partCovAdp+noCovAdp)>0){
					System.out.println("ADOPT\t"+language+"\t"+annotator.getId()+"\t"+(((double)(fullCovAdp+infCovAdp))/((double)(fullCovAdp+infCovAdp+partCovAdp+noCovAdp)))+"\t"+strictCovCIAdp[0]+"\t"+strictCovCIAdp[1]+"\t"+(((double)(fullCovAdp+infCovAdp+partCovAdp))/((double)(fullCovAdp+infCovAdp+partCovAdp+noCovAdp)))+"\t"+looseCovCIAdp[0]+"\t"+looseCovCIAdp[1]);
				}
				if((fullCovAlt+infCovAlt+partCovAlt+noCovAlt)>0){
					System.out.println("ALTERNATIVE\t"+language+"\t"+annotator.getId()+"\t"+(((double)(fullCovAlt+infCovAlt))/((double)(fullCovAlt+infCovAlt+partCovAlt+noCovAlt)))+"\t"+strictCovCIAlt[0]+"\t"+strictCovCIAlt[1]+"\t"+(((double)(fullCovAlt+infCovAlt+partCovAlt))/((double)(fullCovAlt+infCovAlt+partCovAlt+noCovAlt)))+"\t"+looseCovCIAlt[0]+"\t"+looseCovCIAlt[1]);
				}
				if((fullCovAbs+infCovAbs+partCovAbs+noCovAbs)>0){
					System.out.println("ABSTAIN\t"+language+"\t"+annotator.getId()+"\t"+(((double)(fullCovAbs+infCovAbs))/((double)(fullCovAbs+infCovAbs+partCovAbs+noCovAbs)))+"\t"+strictCovCIAbs[0]+"\t"+strictCovCIAbs[1]+"\t"+(((double)(fullCovAbs+infCovAbs+partCovAbs))/((double)(fullCovAbs+infCovAbs+partCovAbs+noCovAbs)))+"\t"+looseCovCIAbs[0]+"\t"+looseCovCIAbs[1]);
				}
				
				//ADOPT
				System.out.println("ADOPT - Full cov\nSemantic type\tOccurrences");
				for(String semtype: setOfKeysAdpAlt){
					System.out.println(semtype+"\t"+listFullCovBySemTypeAdp.get(semtype));
				}
				System.out.println("ADOPT - Inf cov\nSemantic type\tOccurrences");
				for(String semtype: setOfKeysAdpAlt){
					System.out.println(semtype+"\t"+listInfCovBySemTypeAdp.get(semtype));
				}
				System.out.println("ADOPT - Part cov\nSemantic type\tOccurrences");
				for(String semtype: setOfKeysAdpAlt){
					System.out.println(semtype+"\t"+listPartCovBySemTypeAdp.get(semtype));
				}
				
				//ALTERNATIVE
				System.out.println("ALTERNATIVE - Full cov\nSemantic type\tOccurrences");
				for(String semtype: setOfKeysAdpAlt){
					System.out.println(semtype+"\t"+listFullCovBySemTypeAlt.get(semtype));
				}
				System.out.println("ALTERNATIVE - Inf cov\nSemantic type\tOccurrences");
				for(String semtype: setOfKeysAdpAlt){
					System.out.println(semtype+"\t"+listInfCovBySemTypeAlt.get(semtype));
				}
				System.out.println("ALTERNATIVE - Part cov\nSemantic type\tOccurrences");
				for(String semtype: setOfKeysAdpAlt){
					System.out.println(semtype+"\t"+listPartCovBySemTypeAlt.get(semtype));
				}
				
				//ABSTAIN
				System.out.println("ABSTAIN - Full cov\nSemantic type\tOccurrences");
				for(String semtype: setOfKeysAbs){
					System.out.println(semtype+"\t"+listFullCovBySemTypeAbs.get(semtype));
				}
				System.out.println("ABSTAIN - Inf cov\nSemantic type\tOccurrences");
				for(String semtype: setOfKeysAbs){
					System.out.println(semtype+"\t"+listInfCovBySemTypeAbs.get(semtype));
				}
				System.out.println("ABSTAIN - Part cov\nSemantic type\tOccurrences");
				for(String semtype: setOfKeysAbs){
					System.out.println(semtype+"\t"+listPartCovBySemTypeAbs.get(semtype));
				}
			}
			
			Double[] strictCovCIAdp = new Double[2];
			double meanCI = calcMeanCI(strictCovAdpTotal,significance);
			strictCovCIAdp[0]	= strictCovAdpTotal.getMean() - meanCI;
			strictCovCIAdp[1]	= strictCovAdpTotal.getMean() + meanCI;
			
			Double[] looseCovCIAdp	= new Double[2];
			meanCI = calcMeanCI(looseCovAdpTotal,significance);
			looseCovCIAdp[0]	= looseCovAdpTotal.getMean() - meanCI;
			looseCovCIAdp[1]	= looseCovAdpTotal.getMean() + meanCI;
			
			Double[] strictCovCIAlt	= new Double[2];
			meanCI = calcMeanCI(strictCovAltTotal,significance);
			strictCovCIAlt[0]	= strictCovAltTotal.getMean() - meanCI;
			strictCovCIAlt[1]	= strictCovAltTotal.getMean() + meanCI;
			
			Double[] looseCovCIAlt	= new Double[2];
			meanCI = calcMeanCI(looseCovAltTotal,significance);
			looseCovCIAlt[0]	= looseCovAltTotal.getMean() - meanCI;
			looseCovCIAlt[1]	= looseCovAltTotal.getMean() + meanCI;
			
			Double[] strictCovCIAbs	= new Double[2];
			meanCI = calcMeanCI(strictCovAbsTotal,significance);
			strictCovCIAbs[0]	= strictCovAbsTotal.getMean() - meanCI;
			strictCovCIAbs[1]	= strictCovAbsTotal.getMean() + meanCI;
			
			Double[] looseCovCIAbs	= new Double[2];
			meanCI = calcMeanCI(looseCovAbsTotal,significance);
			looseCovCIAbs[0]	= looseCovAbsTotal.getMean() - meanCI;
			looseCovCIAbs[1]	= looseCovAbsTotal.getMean() + meanCI;
			
			System.out.println("Language\tAnnotator\tAdopt - Full\tAdopt - Inf\tAdopt - Part\tAdopt - No cov\tAlt - Full\tAlt - Inf\tAlt - Part\tAlt - No cov\tAbs - Full\tAbs - Inf\tAbs - Part\tAbs - No cov");
			System.out.println(language+"\t"+listAnnotatorsCC.get(language).size()+"\t"+fullCovAdpTotal+"\t"+infCovAdpTotal+"\t"+partCovAdpTotal+"\t"+noCovAdpTotal+"\t"+fullCovAltTotal+"\t"+infCovAltTotal+"\t"+partCovAltTotal+"\t"+noCovAltTotal+"\t"+fullCovAbsTotal+"\t"+infCovAbsTotal+"\t"+partCovAbsTotal+"\t"+noCovAbsTotal);
			System.out.println("Scenario\tLanguage\tStrict - Coverage\tStrict CI-left\tStrict CI-right\tLoose - Coverage\tLoose CI-left\tLoose CI-right");
			if((fullCovAdpTotal+infCovAdpTotal+partCovAdpTotal+noCovAdpTotal)>0){
				System.out.println("ADOPT\t"+language+"\t"+(((double)(fullCovAdpTotal+infCovAdpTotal))/((double)(fullCovAdpTotal+infCovAdpTotal+partCovAdpTotal+noCovAdpTotal)))+"\t"+strictCovCIAdp[0]+"\t"+strictCovCIAdp[1]+"\t"+(((double)(fullCovAdpTotal+infCovAdpTotal+partCovAdpTotal))/((double)(fullCovAdpTotal+infCovAdpTotal+partCovAdpTotal+noCovAdpTotal)))+"\t"+looseCovCIAdp[0]+"\t"+looseCovCIAdp[1]);
			}
			if((fullCovAltTotal+infCovAltTotal+partCovAltTotal+noCovAltTotal)>0){
				System.out.println("ALTERNATIVE\t"+language+"\t"+(((double)(fullCovAltTotal+infCovAltTotal))/((double)(fullCovAltTotal+infCovAltTotal+partCovAltTotal+noCovAltTotal)))+"\t"+strictCovCIAlt[0]+"\t"+strictCovCIAlt[1]+"\t"+(((double)(fullCovAltTotal+infCovAltTotal+partCovAltTotal))/((double)(fullCovAltTotal+infCovAltTotal+partCovAltTotal+noCovAltTotal)))+"\t"+looseCovCIAlt[0]+"\t"+looseCovCIAlt[1]);
			}
			if((fullCovAbsTotal+infCovAbsTotal+partCovAbsTotal+noCovAbsTotal)>0){
				System.out.println("ABSTAIN\t"+language+"\t"+(((double)(fullCovAbsTotal+infCovAbsTotal))/((double)(fullCovAbsTotal+infCovAbsTotal+partCovAbsTotal+noCovAbsTotal)))+"\t"+strictCovCIAbs[0]+"\t"+strictCovCIAbs[1]+"\t"+(((double)(fullCovAbsTotal+infCovAbsTotal+partCovAbsTotal))/((double)(fullCovAbsTotal+infCovAbsTotal+partCovAbsTotal+noCovAbsTotal)))+"\t"+looseCovCIAbs[0]+"\t"+looseCovCIAbs[1]);
			}
			 
			//ADOPT
			System.out.println("ADOPT - Full cov\nSemantic type\tOccurrences");
			for(String semtype: setOfKeysAdpAlt){
				System.out.println(semtype+"\t"+listFullCovBySemTypeAdpTotal.get(semtype));
			}
			System.out.println("ADOPT - Inf cov\nSemantic type\tOccurrences");
			for(String semtype: setOfKeysAdpAlt){
				System.out.println(semtype+"\t"+listInfCovBySemTypeAdpTotal.get(semtype));
			}
			System.out.println("ADOPT - Part cov\nSemantic type\tOccurrences");
			for(String semtype: setOfKeysAdpAlt){
				System.out.println(semtype+"\t"+listPartCovBySemTypeAdpTotal.get(semtype));
			}
			
			//ALTERNATIVE
			System.out.println("ALTERNATIVE - Full cov\nSemantic type\tOccurrences");
			for(String semtype: setOfKeysAdpAlt){
				System.out.println(semtype+"\t"+listFullCovBySemTypeAltTotal.get(semtype));
			}
			System.out.println("ALTERNATIVE - Inf cov\nSemantic type\tOccurrences");
			for(String semtype: setOfKeysAdpAlt){
				System.out.println(semtype+"\t"+listInfCovBySemTypeAltTotal.get(semtype));
			}
			System.out.println("ALTERNATIVE - Part cov\nSemantic type\tOccurrences");
			for(String semtype: setOfKeysAdpAlt){
				System.out.println(semtype+"\t"+listPartCovBySemTypeAltTotal.get(semtype));
			}
			
			//ABSTAIN
			System.out.println("ABSTAIN - Full cov\nSemantic type\tOccurrences");
			for(String semtype: setOfKeysAbs){
				System.out.println(semtype+"\t"+listFullCovBySemTypeAbsTotal.get(semtype));
			}
			System.out.println("ABSTAIN - Inf cov\nSemantic type\tOccurrences");
			for(String semtype: setOfKeysAbs){
				System.out.println(semtype+"\t"+listInfCovBySemTypeAbsTotal.get(semtype));
			}
			System.out.println("ABSTAIN - Part cov\nSemantic type\tOccurrences");
			for(String semtype: setOfKeysAbs){
				System.out.println(semtype+"\t"+listPartCovBySemTypeAbsTotal.get(semtype));
			}
		}
	}
	
	public static void getConceptCoverageDescription(ConceptCoverage cc, ArrayList<String> setOfKeysAdpAlt, ArrayList<String> setOfKeysAbs, String outFile){
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
			double significance = 0.95;
			HashMap<String,ArrayList<AnnotatorCC>> listAnnotatorsCC = cc.getListAnnotatorsCC();
			for(String language: listAnnotatorsCC.keySet()){
				
				//ADOPT
				HashMap<String,Integer> listFullCovBySemTypeAdpTotal	= new HashMap<String,Integer>();
				int fullCovAdpTotal	= 0;
				HashMap<String,Integer> listInfCovBySemTypeAdpTotal		= new HashMap<String,Integer>();
				int infCovAdpTotal	= 0;
				HashMap<String,Integer> listPartCovBySemTypeAdpTotal	= new HashMap<String,Integer>();
				int partCovAdpTotal	= 0;
				int noCovAdpTotal	= 0;
				SummaryStatistics strictCovAdpTotal	= new SummaryStatistics();
				SummaryStatistics looseCovAdpTotal	= new SummaryStatistics();
				
				//ALTERNATIVE
				HashMap<String,Integer> listFullCovBySemTypeAltTotal	= new HashMap<String,Integer>();
				int fullCovAltTotal	= 0;
				HashMap<String,Integer> listInfCovBySemTypeAltTotal		= new HashMap<String,Integer>();
				int infCovAltTotal	= 0;
				HashMap<String,Integer> listPartCovBySemTypeAltTotal	= new HashMap<String,Integer>();
				int partCovAltTotal	= 0;
				int noCovAltTotal	= 0;
				SummaryStatistics strictCovAltTotal	= new SummaryStatistics();
				SummaryStatistics looseCovAltTotal	= new SummaryStatistics();

				for(String key: setOfKeysAdpAlt){
					listFullCovBySemTypeAdpTotal.put(key,0);
					listInfCovBySemTypeAdpTotal.put(key,0);
					listPartCovBySemTypeAdpTotal.put(key,0);
					listFullCovBySemTypeAltTotal.put(key,0);
					listInfCovBySemTypeAltTotal.put(key,0);
					listPartCovBySemTypeAltTotal.put(key,0);
				}
				
				//ABSTAIN
				HashMap<String,Integer> listFullCovBySemTypeAbsTotal	= new HashMap<String,Integer>();
				int fullCovAbsTotal	= 0;
				HashMap<String,Integer> listInfCovBySemTypeAbsTotal		= new HashMap<String,Integer>();
				int infCovAbsTotal	= 0;
				HashMap<String,Integer> listPartCovBySemTypeAbsTotal	= new HashMap<String,Integer>();
				int partCovAbsTotal	= 0;
				int noCovAbsTotal	= 0;
				SummaryStatistics strictCovAbsTotal	= new SummaryStatistics();
				SummaryStatistics looseCovAbsTotal	= new SummaryStatistics();
				
				for(String key: setOfKeysAbs){
					listFullCovBySemTypeAbsTotal.put(key, 0);
					listInfCovBySemTypeAbsTotal.put(key, 0);
					listPartCovBySemTypeAbsTotal.put(key, 0);
				}
				
				for(AnnotatorCC annotator: listAnnotatorsCC.get(language)){
					//ADOPT
					HashMap<String,Integer> listFullCovBySemTypeAdp	= new HashMap<String,Integer>();
					int fullCovAdp	= 0;
					HashMap<String,Integer> listInfCovBySemTypeAdp	= new HashMap<String,Integer>();
					int infCovAdp	= 0;
					HashMap<String,Integer> listPartCovBySemTypeAdp	= new HashMap<String,Integer>();
					int partCovAdp	= 0;
					int noCovAdp	= 0;
					SummaryStatistics strictCovAdp	= new SummaryStatistics();
					SummaryStatistics looseCovAdp	= new SummaryStatistics();
					
					//ALTERNATIVE
					HashMap<String,Integer> listFullCovBySemTypeAlt	= new HashMap<String,Integer>();
					int fullCovAlt	= 0;
					HashMap<String,Integer> listInfCovBySemTypeAlt	= new HashMap<String,Integer>();
					int infCovAlt	= 0;
					HashMap<String,Integer> listPartCovBySemTypeAlt	= new HashMap<String,Integer>();
					int partCovAlt	= 0;
					int noCovAlt	= 0;
					SummaryStatistics strictCovAlt	= new SummaryStatistics();
					SummaryStatistics looseCovAlt	= new SummaryStatistics();
					
					for(String key: setOfKeysAdpAlt){
						listFullCovBySemTypeAdp.put(key,0);
						listInfCovBySemTypeAdp.put(key,0);
						listPartCovBySemTypeAdp.put(key,0);
						listFullCovBySemTypeAlt.put(key,0);
						listInfCovBySemTypeAlt.put(key,0);
						listPartCovBySemTypeAlt.put(key,0);
					}
					
					//ABSTAIN
					HashMap<String,Integer> listFullCovBySemTypeAbs	= new HashMap<String,Integer>();
					int fullCovAbs	= 0;
					HashMap<String,Integer> listInfCovBySemTypeAbs	= new HashMap<String,Integer>();
					int infCovAbs	= 0;
					HashMap<String,Integer> listPartCovBySemTypeAbs	= new HashMap<String,Integer>();
					int partCovAbs	= 0;
					int noCovAbs	= 0;
					SummaryStatistics strictCovAbs	= new SummaryStatistics();
					SummaryStatistics looseCovAbs	= new SummaryStatistics();
					
					for(String key: setOfKeysAbs){
						listFullCovBySemTypeAbs.put(key, 0);
						listInfCovBySemTypeAbs.put(key, 0);
						listPartCovBySemTypeAbs.put(key, 0);
					}
					
					
					for(SnippetCC snippet: annotator.getListOfSnippets()){
						double numerator	= snippet.getNumberOfFullCoverageAnnotationsOfAdopt()+snippet.getNumberOfInferredCoverageAnnotationsOfAdopt();
						double denominator	= snippet.getNumberOfFullCoverageAnnotationsOfAdopt()+snippet.getNumberOfInferredCoverageAnnotationsOfAdopt()+snippet.getNumberOfNoCoverageAnnotationsOfAdopt()+snippet.getNumberOfPartialCoverageAnnotationsOfAdopt();
						//System.out.println(numerator+"/"+denominator+"="+numerator/denominator);
						strictCovAdp.addValue(numerator/denominator);
						strictCovAdpTotal.addValue(numerator/denominator);
						numerator	= numerator+snippet.getNumberOfPartialCoverageAnnotationsOfAdopt();
						looseCovAdp.addValue(numerator/denominator);
						looseCovAdpTotal.addValue(numerator/denominator);
						
						numerator	= snippet.getNumberOfFullCoverageAnnotationsOfAlternative()+snippet.getNumberOfInferredCoverageAnnotationsOfAlternative();
						denominator	= snippet.getNumberOfFullCoverageAnnotationsOfAlternative()+snippet.getNumberOfInferredCoverageAnnotationsOfAlternative()+snippet.getNumberOfNoCoverageAnnotationsOfAlternative()+snippet.getNumberOfPartialCoverageAnnotationsOfAlternative();
						strictCovAlt.addValue(numerator/denominator);
						strictCovAltTotal.addValue(numerator/denominator);
						numerator	= numerator+snippet.getNumberOfPartialCoverageAnnotationsOfAlternative();
						looseCovAlt.addValue(numerator/denominator);
						looseCovAltTotal.addValue(numerator/denominator);
						
						numerator	= snippet.getNumberOfFullCoverageAnnotationsOfAbstain()+snippet.getNumberOfInferredCoverageAnnotationsOfAbstain();
						denominator	= snippet.getNumberOfFullCoverageAnnotationsOfAbstain()+snippet.getNumberOfInferredCoverageAnnotationsOfAbstain()+snippet.getNumberOfNoCoverageAnnotationsOfAbstain()+snippet.getNumberOfPartialCoverageAnnotationsOfAbstain();
						strictCovAbs.addValue(numerator/denominator);
						strictCovAbsTotal.addValue(numerator/denominator);
						numerator	= numerator+snippet.getNumberOfPartialCoverageAnnotationsOfAbstain();
						looseCovAbs.addValue(numerator/denominator);
						looseCovAbsTotal.addValue(numerator/denominator);
						
						for(AnnotationCC annotation: snippet.getListOfAnnotations()){
							//ADOPT
							ArrayList<CodeCC> listCodes = annotation.getListOfSCTCodesCC();
							for(CodeCC code: listCodes){
								if(code.getCoverageScore() == CodeCC.FULLCOV){
									fullCovAdp++;
									fullCovAdpTotal++;
									if(code.getSemanticGroup()!=null && !code.getSemanticGroup().isEmpty()){
										for(String semtype: code.getSemanticGroup()){
											if(listFullCovBySemTypeAdp.containsKey(semtype)){
												int freq = listFullCovBySemTypeAdp.get(semtype);
												freq++;
												listFullCovBySemTypeAdp.put(semtype, freq);
												freq = listFullCovBySemTypeAdpTotal.get(semtype);
												freq++;
												listFullCovBySemTypeAdpTotal.put(semtype, freq);
											}
										}
									}else{
										//System.out.println("ERROR: code SCT without semantic type "+code.getCode()+" -> "+code.getTokens());
									}
									continue;
								}
								if(code.getCoverageScore() == CodeCC.INFECOV){
									infCovAdp++;
									infCovAdpTotal++;
									if(code.getSemanticGroup()!=null && !code.getSemanticGroup().isEmpty()){
										for(String semtype:code.getSemanticGroup()){
											if(listInfCovBySemTypeAdp.containsKey(semtype)){
												int freq = listInfCovBySemTypeAdp.get(semtype);
												freq++;
												listInfCovBySemTypeAdp.put(semtype, freq);
												freq = listInfCovBySemTypeAdpTotal.get(semtype);
												freq++;
												listInfCovBySemTypeAdpTotal.put(semtype, freq);
											}
										}
									}else{
										//System.out.println("ERROR: code SCT without semantic type "+code.getCode()+" -> "+code.getTokens());
									}
									continue;
								}
								if(code.getCoverageScore() == CodeCC.PARTCOV){
									partCovAdp++;
									partCovAdpTotal++;
									if(code.getSemanticGroup()!=null && !code.getSemanticGroup().isEmpty()){
										for(String semtype:code.getSemanticGroup()){
											if(listPartCovBySemTypeAdp.containsKey(semtype)){
												int freq = listPartCovBySemTypeAdp.get(semtype);
												freq++;
												listPartCovBySemTypeAdp.put(semtype, freq);
												freq = listPartCovBySemTypeAdpTotal.get(semtype);
												freq++;
												listPartCovBySemTypeAdpTotal.put(semtype, freq);
											}
										}
									}else{
										//System.out.println("ERROR: code SCT without semantic type "+code.getCode()+" -> "+code.getTokens());
									}
									continue;
								}
								if(code.getCoverageScore() == CodeCC.NOCOV){
									noCovAdp++;
									noCovAdpTotal++;
									continue;
								}
							}
							
							//ALTERNATIVE
							listCodes = annotation.getListOfUMLSCodesCC();
							for(CodeCC code: listCodes){
								if(code.getCoverageScore() == CodeCC.FULLCOV){
									fullCovAlt++;
									fullCovAltTotal++;
									if(code.getSemanticGroup()!=null && !code.getSemanticGroup().isEmpty()){
										for(String semtype:code.getSemanticGroup()){
											if(listFullCovBySemTypeAlt.containsKey(semtype)){
												int freq = listFullCovBySemTypeAlt.get(semtype);
												freq++;
												listFullCovBySemTypeAlt.put(semtype, freq);
												freq = listFullCovBySemTypeAltTotal.get(semtype);
												freq++;
												listFullCovBySemTypeAltTotal.put(semtype, freq);
											}
										}
									}else{
										//System.out.println("ERROR: code ALT without semantic type "+code.getCode()+" -> "+code.getTokens());
									}
									continue;
								}
								if(code.getCoverageScore() == CodeCC.INFECOV){
									infCovAlt++;
									infCovAltTotal++;
									if(code.getSemanticGroup()!=null && !code.getSemanticGroup().isEmpty()){
										for(String semtype:code.getSemanticGroup()){
											if(listInfCovBySemTypeAlt.containsKey(semtype)){
												int freq = listInfCovBySemTypeAlt.get(semtype);
												freq++;
												listInfCovBySemTypeAlt.put(semtype, freq);
												freq = listInfCovBySemTypeAltTotal.get(semtype);
												freq++;
												listInfCovBySemTypeAltTotal.put(semtype, freq);
											}
										}
									}else{
										//System.out.println("ERROR: code ALT without semantic type "+code.getCode()+" -> "+code.getTokens());
									}
									continue;
								}
								if(code.getCoverageScore() == CodeCC.PARTCOV){
									partCovAlt++;
									partCovAltTotal++;
									if(code.getSemanticGroup()!=null && !code.getSemanticGroup().isEmpty()){
										for(String semtype:code.getSemanticGroup()){
											if(listPartCovBySemTypeAlt.containsKey(semtype)){
												int freq = listPartCovBySemTypeAlt.get(semtype);
												freq++;
												listPartCovBySemTypeAlt.put(semtype, freq);
												freq = listPartCovBySemTypeAltTotal.get(semtype);
												freq++;
												listPartCovBySemTypeAltTotal.put(semtype, freq);
											}
										}
									}else{
										//System.out.println("ERROR: code ALT without semantic type "+code.getCode()+" -> "+code.getTokens());
									}
									continue;
								}
								if(code.getCoverageScore() == CodeCC.NOCOV){
									noCovAlt++;
									noCovAltTotal++;
									continue;
								}
							}
							
							//ABSTAIN
							listCodes = annotation.getListOfLocalCodesCC();
							for(CodeCC code: listCodes){
								if(code.getCoverageScore() == CodeCC.FULLCOV){
									fullCovAbs++;
									fullCovAbsTotal++;
									if(code.getSemanticGroup()!=null && !code.getSemanticGroup().isEmpty()){
										for(String semtype:code.getSemanticGroup()){
											if(listFullCovBySemTypeAbs.containsKey(semtype)){
												int freq = listFullCovBySemTypeAbs.get(semtype);
												freq++;
												listFullCovBySemTypeAbs.put(semtype, freq);
												freq = listFullCovBySemTypeAbsTotal.get(semtype);
												freq++;
												listFullCovBySemTypeAbsTotal.put(semtype, freq);
											}
										}
									}else{
										//System.out.println("ERROR: code ABS without semantic type "+code.getCode()+" -> "+code.getTokens());
									}
									continue;
								}
								if(code.getCoverageScore() == CodeCC.INFECOV){
									infCovAbsTotal++;
									if(code.getSemanticGroup()!=null && !code.getSemanticGroup().isEmpty()){
										for(String semtype:code.getSemanticGroup()){
											if(listInfCovBySemTypeAbs.containsKey(semtype)){
												int freq = listInfCovBySemTypeAbs.get(semtype);
												freq++;
												listInfCovBySemTypeAbs.put(semtype, freq);
												freq = listInfCovBySemTypeAbsTotal.get(semtype);
												freq++;
												listInfCovBySemTypeAbsTotal.put(semtype, freq);
											}
										}
									}else{
										//System.out.println("ERROR: code ABS without semantic type "+code.getCode()+" -> "+code.getTokens());
									}
									continue;
								}
								if(code.getCoverageScore() == CodeCC.PARTCOV){
									partCovAbs++;
									partCovAbsTotal++;
									if(code.getSemanticGroup()!=null && !code.getSemanticGroup().isEmpty()){
										for(String semtype:code.getSemanticGroup()){
											if(listPartCovBySemTypeAbs.containsKey(semtype)){
												int freq = listPartCovBySemTypeAbs.get(semtype);
												freq++;
												listPartCovBySemTypeAbs.put(semtype, freq);
												freq = listPartCovBySemTypeAbsTotal.get(semtype);
												freq++;
												listPartCovBySemTypeAbsTotal.put(semtype, freq);
											}
										}
									}else{
										//System.out.println("ERROR: code ABS without semantic type "+code.getCode()+" -> "+code.getTokens());
									}
									continue;
								}
								if(code.getCoverageScore() == CodeCC.NOCOV){
									noCovAbsTotal++;
									continue;
								}
							}
						}
					}
					
					Double[] strictCovCIAdp = new Double[2];
					double meanCI = calcMeanCI(strictCovAdp,significance);
					strictCovCIAdp[0]	= strictCovAdp.getMean() - meanCI;
					strictCovCIAdp[1]	= strictCovAdp.getMean() + meanCI;
					
					Double[] looseCovCIAdp	= new Double[2];
					meanCI = calcMeanCI(looseCovAdp,significance);
					looseCovCIAdp[0]	= looseCovAdp.getMean() - meanCI;
					looseCovCIAdp[1]	= looseCovAdp.getMean() + meanCI;
					
					Double[] strictCovCIAlt	= new Double[2];
					meanCI = calcMeanCI(strictCovAlt,significance);
					strictCovCIAlt[0]	= strictCovAlt.getMean() - meanCI;
					strictCovCIAlt[1]	= strictCovAlt.getMean() + meanCI;
					
					Double[] looseCovCIAlt	= new Double[2];
					meanCI = calcMeanCI(looseCovAlt,significance);
					looseCovCIAlt[0]	= looseCovAlt.getMean() - meanCI;
					looseCovCIAlt[1]	= looseCovAlt.getMean() + meanCI;
					
					Double[] strictCovCIAbs	= new Double[2];
					meanCI = calcMeanCI(strictCovAbs,significance);
					strictCovCIAbs[0]	= strictCovAbs.getMean() - meanCI;
					strictCovCIAbs[1]	= strictCovAbs.getMean() + meanCI;
					
					Double[] looseCovCIAbs	= new Double[2];
					meanCI = calcMeanCI(looseCovAbs,significance);
					looseCovCIAbs[0]	= looseCovAbs.getMean() - meanCI;
					looseCovCIAbs[1]	= looseCovAbs.getMean() + meanCI;
					
					bw.write("Language\tAnnotator\tAdopt - Full\tAdopt - Inf\tAdopt - Part\tAdopt - No cov\tAlt - Full\tAlt - Inf\tAlt - Part\tAlt - No cov\tAbs - Full\tAbs - Inf\tAbs - Part\tAbs - No cov\n");
					bw.write(language+"\t"+annotator.getId()+"\t"+fullCovAdp+"\t"+infCovAdp+"\t"+partCovAdp+"\t"+noCovAdp+"\t"+fullCovAlt+"\t"+infCovAlt+"\t"+partCovAlt+"\t"+noCovAlt+"\t"+fullCovAbs+"\t"+infCovAbs+"\t"+partCovAbs+"\t"+noCovAbs+"\n");
					bw.write("Scenario\tLanguage\tAnnotator\tStrict - Coverage\tStrict CI-left\tStrict CI-right\tLoose - Coverage\tLoose CI-left\tLoose CI-right\n");
					if((fullCovAdp+infCovAdp+partCovAdp+noCovAdp)>0){
						bw.write("ADOPT\t"+language+"\t"+annotator.getId()+"\t"+(((double)(fullCovAdp+infCovAdp))/((double)(fullCovAdp+infCovAdp+partCovAdp+noCovAdp)))+"\t"+strictCovCIAdp[0]+"\t"+strictCovCIAdp[1]+"\t"+(((double)(fullCovAdp+infCovAdp+partCovAdp))/((double)(fullCovAdp+infCovAdp+partCovAdp+noCovAdp)))+"\t"+looseCovCIAdp[0]+"\t"+looseCovCIAdp[1]+"\n");
					}
					if((fullCovAlt+infCovAlt+partCovAlt+noCovAlt)>0){
						bw.write("ALTERNATIVE\t"+language+"\t"+annotator.getId()+"\t"+(((double)(fullCovAlt+infCovAlt))/((double)(fullCovAlt+infCovAlt+partCovAlt+noCovAlt)))+"\t"+strictCovCIAlt[0]+"\t"+strictCovCIAlt[1]+"\t"+(((double)(fullCovAlt+infCovAlt+partCovAlt))/((double)(fullCovAlt+infCovAlt+partCovAlt+noCovAlt)))+"\t"+looseCovCIAlt[0]+"\t"+looseCovCIAlt[1]+"\n");
					}
					if((fullCovAbs+infCovAbs+partCovAbs+noCovAbs)>0){
						bw.write("ABSTAIN\t"+language+"\t"+annotator.getId()+"\t"+(((double)(fullCovAbs+infCovAbs))/((double)(fullCovAbs+infCovAbs+partCovAbs+noCovAbs)))+"\t"+strictCovCIAbs[0]+"\t"+strictCovCIAbs[1]+"\t"+(((double)(fullCovAbs+infCovAbs+partCovAbs))/((double)(fullCovAbs+infCovAbs+partCovAbs+noCovAbs)))+"\t"+looseCovCIAbs[0]+"\t"+looseCovCIAbs[1]+"\n");
					}
					
					//ADOPT
					bw.write("ADOPT - Full cov\nSemantic type\tOccurrences\n");
					for(String semtype: setOfKeysAdpAlt){
						System.out.println(semtype+"\t"+listFullCovBySemTypeAdp.get(semtype)+"\n");
					}
					System.out.println("ADOPT - Inf cov\nSemantic type\tOccurrences\n");
					for(String semtype: setOfKeysAdpAlt){
						System.out.println(semtype+"\t"+listInfCovBySemTypeAdp.get(semtype)+"\n");
					}
					System.out.println("ADOPT - Part cov\nSemantic type\tOccurrences\n");
					for(String semtype: setOfKeysAdpAlt){
						System.out.println(semtype+"\t"+listPartCovBySemTypeAdp.get(semtype)+"\n");
					}
					
					//ALTERNATIVE
					bw.write("ALTERNATIVE - Full cov\nSemantic type\tOccurrences\n");
					for(String semtype: setOfKeysAdpAlt){
						bw.write(semtype+"\t"+listFullCovBySemTypeAlt.get(semtype)+"\n");
					}
					bw.write("ALTERNATIVE - Inf cov\nSemantic type\tOccurrences\n");
					for(String semtype: setOfKeysAdpAlt){
						bw.write(semtype+"\t"+listInfCovBySemTypeAlt.get(semtype)+"\n");
					}
					bw.write("ALTERNATIVE - Part cov\nSemantic type\tOccurrences\n");
					for(String semtype: setOfKeysAdpAlt){
						bw.write(semtype+"\t"+listPartCovBySemTypeAlt.get(semtype)+"\n");
					}
					
					//ABSTAIN
					bw.write("ABSTAIN - Full cov\nSemantic type\tOccurrences\n");
					for(String semtype: setOfKeysAbs){
						bw.write(semtype+"\t"+listFullCovBySemTypeAbs.get(semtype)+"\n");
					}
					bw.write("ABSTAIN - Inf cov\nSemantic type\tOccurrences\n");
					for(String semtype: setOfKeysAbs){
						bw.write(semtype+"\t"+listInfCovBySemTypeAbs.get(semtype)+"\n");
					}
					bw.write("ABSTAIN - Part cov\nSemantic type\tOccurrences\n");
					for(String semtype: setOfKeysAbs){
						bw.write(semtype+"\t"+listPartCovBySemTypeAbs.get(semtype)+"\n");
					}
				}
				
				Double[] strictCovCIAdp = new Double[2];
				double meanCI = calcMeanCI(strictCovAdpTotal,significance);
				strictCovCIAdp[0]	= strictCovAdpTotal.getMean() - meanCI;
				strictCovCIAdp[1]	= strictCovAdpTotal.getMean() + meanCI;
				
				Double[] looseCovCIAdp	= new Double[2];
				meanCI = calcMeanCI(looseCovAdpTotal,significance);
				looseCovCIAdp[0]	= looseCovAdpTotal.getMean() - meanCI;
				looseCovCIAdp[1]	= looseCovAdpTotal.getMean() + meanCI;
				
				Double[] strictCovCIAlt	= new Double[2];
				meanCI = calcMeanCI(strictCovAltTotal,significance);
				strictCovCIAlt[0]	= strictCovAltTotal.getMean() - meanCI;
				strictCovCIAlt[1]	= strictCovAltTotal.getMean() + meanCI;
				
				Double[] looseCovCIAlt	= new Double[2];
				meanCI = calcMeanCI(looseCovAltTotal,significance);
				looseCovCIAlt[0]	= looseCovAltTotal.getMean() - meanCI;
				looseCovCIAlt[1]	= looseCovAltTotal.getMean() + meanCI;
				
				Double[] strictCovCIAbs	= new Double[2];
				meanCI = calcMeanCI(strictCovAbsTotal,significance);
				strictCovCIAbs[0]	= strictCovAbsTotal.getMean() - meanCI;
				strictCovCIAbs[1]	= strictCovAbsTotal.getMean() + meanCI;
				
				Double[] looseCovCIAbs	= new Double[2];
				meanCI = calcMeanCI(looseCovAbsTotal,significance);
				looseCovCIAbs[0]	= looseCovAbsTotal.getMean() - meanCI;
				looseCovCIAbs[1]	= looseCovAbsTotal.getMean() + meanCI;
				
				bw.write("Language\tAnnotator\tAdopt - Full\tAdopt - Inf\tAdopt - Part\tAdopt - No cov\tAlt - Full\tAlt - Inf\tAlt - Part\tAlt - No cov\tAbs - Full\tAbs - Inf\tAbs - Part\tAbs - No cov\n");
				bw.write(language+"\t"+listAnnotatorsCC.get(language).size()+"\t"+fullCovAdpTotal+"\t"+infCovAdpTotal+"\t"+partCovAdpTotal+"\t"+noCovAdpTotal+"\t"+fullCovAltTotal+"\t"+infCovAltTotal+"\t"+partCovAltTotal+"\t"+noCovAltTotal+"\t"+fullCovAbsTotal+"\t"+infCovAbsTotal+"\t"+partCovAbsTotal+"\t"+noCovAbsTotal+"\n");
				bw.write("Scenario\tLanguage\tStrict - Coverage\tStrict CI-left\tStrict CI-right\tLoose - Coverage\tLoose CI-left\tLoose CI-right");
				if((fullCovAdpTotal+infCovAdpTotal+partCovAdpTotal+noCovAdpTotal)>0){
					bw.write("ADOPT\t"+language+"\t"+(((double)(fullCovAdpTotal+infCovAdpTotal))/((double)(fullCovAdpTotal+infCovAdpTotal+partCovAdpTotal+noCovAdpTotal)))+"\t"+strictCovCIAdp[0]+"\t"+strictCovCIAdp[1]+"\t"+(((double)(fullCovAdpTotal+infCovAdpTotal+partCovAdpTotal))/((double)(fullCovAdpTotal+infCovAdpTotal+partCovAdpTotal+noCovAdpTotal)))+"\t"+looseCovCIAdp[0]+"\t"+looseCovCIAdp[1]+"\n");
				}
				if((fullCovAltTotal+infCovAltTotal+partCovAltTotal+noCovAltTotal)>0){
					bw.write("ALTERNATIVE\t"+language+"\t"+(((double)(fullCovAltTotal+infCovAltTotal))/((double)(fullCovAltTotal+infCovAltTotal+partCovAltTotal+noCovAltTotal)))+"\t"+strictCovCIAlt[0]+"\t"+strictCovCIAlt[1]+"\t"+(((double)(fullCovAltTotal+infCovAltTotal+partCovAltTotal))/((double)(fullCovAltTotal+infCovAltTotal+partCovAltTotal+noCovAltTotal)))+"\t"+looseCovCIAlt[0]+"\t"+looseCovCIAlt[1]+"\n");
				}
				if((fullCovAbsTotal+infCovAbsTotal+partCovAbsTotal+noCovAbsTotal)>0){
					bw.write("ABSTAIN\t"+language+"\t"+(((double)(fullCovAbsTotal+infCovAbsTotal))/((double)(fullCovAbsTotal+infCovAbsTotal+partCovAbsTotal+noCovAbsTotal)))+"\t"+strictCovCIAbs[0]+"\t"+strictCovCIAbs[1]+"\t"+(((double)(fullCovAbsTotal+infCovAbsTotal+partCovAbsTotal))/((double)(fullCovAbsTotal+infCovAbsTotal+partCovAbsTotal+noCovAbsTotal)))+"\t"+looseCovCIAbs[0]+"\t"+looseCovCIAbs[1]+"\n");
				}
				 
				//ADOPT
				bw.write("ADOPT - Full cov\nSemantic type\tOccurrences\n");
				for(String semtype: setOfKeysAdpAlt){
					bw.write(semtype+"\t"+listFullCovBySemTypeAdpTotal.get(semtype)+"\n");
				}
				bw.write("ADOPT - Inf cov\nSemantic type\tOccurrences\n");
				for(String semtype: setOfKeysAdpAlt){
					bw.write(semtype+"\t"+listInfCovBySemTypeAdpTotal.get(semtype)+"\n");
				}
				bw.write("ADOPT - Part cov\nSemantic type\tOccurrences\n");
				for(String semtype: setOfKeysAdpAlt){
					bw.write(semtype+"\t"+listPartCovBySemTypeAdpTotal.get(semtype)+"\n");
				}
				
				//ALTERNATIVE
				bw.write("ALTERNATIVE - Full cov\nSemantic type\tOccurrences\n");
				for(String semtype: setOfKeysAdpAlt){
					bw.write(semtype+"\t"+listFullCovBySemTypeAltTotal.get(semtype)+"\n");
				}
				bw.write("ALTERNATIVE - Inf cov\nSemantic type\tOccurrences\n");
				for(String semtype: setOfKeysAdpAlt){
					bw.write(semtype+"\t"+listInfCovBySemTypeAltTotal.get(semtype)+"\n");
				}
				bw.write("ALTERNATIVE - Part cov\nSemantic type\tOccurrences\n");
				for(String semtype: setOfKeysAdpAlt){
					bw.write(semtype+"\t"+listPartCovBySemTypeAltTotal.get(semtype)+"\n");
				}
				
				//ABSTAIN
				bw.write("ABSTAIN - Full cov\nSemantic type\tOccurrences\n");
				for(String semtype: setOfKeysAbs){
					bw.write(semtype+"\t"+listFullCovBySemTypeAbsTotal.get(semtype)+"\n");
				}
				bw.write("ABSTAIN - Inf cov\nSemantic type\tOccurrences\n");
				for(String semtype: setOfKeysAbs){
					bw.write(semtype+"\t"+listInfCovBySemTypeAbsTotal.get(semtype)+"\n");
				}
				bw.write("ABSTAIN - Part cov\nSemantic type\tOccurrences\n");
				for(String semtype: setOfKeysAbs){
					bw.write(semtype+"\t"+listPartCovBySemTypeAbsTotal.get(semtype)+"\n");
				}
			}
			bw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static double calcMeanCI(SummaryStatistics stats, double significance) {
        try {
            TDistribution tDist = new TDistribution(stats.getN() - 1);
            double critVal = tDist.inverseCumulativeProbability(1.0 - (1 - significance) / 2);
            return critVal * stats.getStandardDeviation() / Math.sqrt(stats.getN());
        } catch (MathIllegalArgumentException e) {
            return Double.NaN;
        }
    }
}
