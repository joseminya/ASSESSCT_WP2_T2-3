package stats;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import term_coverage.AnnotatorTC;
import term_coverage.SnippetTC;
import term_coverage.TermCoverage;

public class TermCoverageDescription {
	public static void getConceptCoverageDescription(TermCoverage tc){
		double significance = 0.95;
		HashMap<String,ArrayList<AnnotatorTC>> listAnnotatorsTC = tc.getListAnnotatorsTC();
		for(String language: listAnnotatorsTC.keySet()){
			//ADOPT
			int positiveTermCovAdpTotal = 0;
			int negativeTermCovAdpTotal = 0;
			SummaryStatistics termCovAdpTotal = new SummaryStatistics();
			
			//ALTERNATIVE
			int positiveTermCovAltTotal = 0;
			int negativeTermCovAltTotal = 0;
			SummaryStatistics termCovAltTotal = new SummaryStatistics();
						
			//ABSTAIN
			int positiveTermCovAbsTotal = 0;
			int negativeTermCovAbsTotal = 0;
			SummaryStatistics termCovAbsTotal = new SummaryStatistics();
			
			for(AnnotatorTC annotator: listAnnotatorsTC.get(language)){
				//ADOPT
				int positiveTermCovAdp = 0;
				int negativeTermCovAdp = 0;
				SummaryStatistics termCovAdp = new SummaryStatistics();
				
				//ALTERNATIVE
				int positiveTermCovAlt = 0;
				int negativeTermCovAlt = 0;
				SummaryStatistics termCovAlt = new SummaryStatistics();
							
				//ABSTAIN
				int positiveTermCovAbs = 0;
				int negativeTermCovAbs = 0;
				SummaryStatistics termCovAbs = new SummaryStatistics();
								
				for(SnippetTC snippet: annotator.getListOfSnippets()){
					double positive	= (double) snippet.getNumberOfPositiveTermCoverageAdopt();
					
					double negative	= (double) snippet.getNumberOfNegativeTermCoverageAdopt();
					positiveTermCovAdp		+= positive;
					negativeTermCovAdp		+= negative;
					positiveTermCovAdpTotal	+= positive;
					negativeTermCovAdpTotal	+= negative;
					termCovAdp.addValue(positive/(positive+negative));
					termCovAdpTotal.addValue(positive/(positive+negative));
					
					positive = (double) snippet.getNumberOfPositiveTermCoverageAlternative();
					negative = (double) snippet.getNumberOfNegativeTermCoverageAlternative();
					positiveTermCovAlt		+= positive;
					negativeTermCovAlt		+= negative;
					positiveTermCovAltTotal	+= positive;
					negativeTermCovAltTotal	+= negative;
					termCovAlt.addValue(positive/(positive+negative));
					termCovAltTotal.addValue(positive/(positive+negative));
					
					positive = (double) snippet.getNumberOfPositiveTermCoverageAbstain();
					negative = (double) snippet.getNumberOfNegativeTermCoverageAbstain();
					positiveTermCovAbs		+= positive;
					negativeTermCovAbs		+= negative;
					positiveTermCovAbsTotal	+= positive;
					negativeTermCovAbsTotal	+= negative;
					termCovAbs.addValue(positive/(positive+negative));
					termCovAbsTotal.addValue(positive/(positive+negative));
				}
				
				Double[] termCovCIAdp = new Double[2];
				double meanCI = calcMeanCI(termCovAdp,significance);
				termCovCIAdp[0]	= termCovAdp.getMean() - meanCI;
				termCovCIAdp[1]	= termCovAdp.getMean() + meanCI;
				
				Double[] termCovCIAlt	= new Double[2];
				meanCI = calcMeanCI(termCovAlt,significance);
				termCovCIAlt[0]	= termCovAlt.getMean() - meanCI;
				termCovCIAlt[1]	= termCovAlt.getMean() + meanCI;
				
				Double[] termCovCIAbs	= new Double[2];
				meanCI = calcMeanCI(termCovAbs,significance);
				termCovCIAbs[0]	= termCovAbs.getMean() - meanCI;
				termCovCIAbs[1]	= termCovAbs.getMean() + meanCI;
				
				System.out.println("Language\tAnnotator\tAdopt - Positive\tAdopt - Negative\tAlt - Positive\tAlt - Negative\tAbs - Positive\tAbs - Negative");
				System.out.println(language+"\t"+annotator.getId()+"\t"+positiveTermCovAdp+"\t"+negativeTermCovAdp+"\t"+positiveTermCovAlt+"\t"+negativeTermCovAlt+"\t"+positiveTermCovAbs+"\t"+negativeTermCovAbs);
				System.out.println("Language\tAnnotator\tAdopt Term Coverage\tCI-left\tCI-right\tAlternative Term Coverage\tCI-left\tCI-right\tAbstain Term Coverage\tCI-left\tCI-right");
				String termCovDesc = language+"\t"+annotator.getId()+"\t";
				if((negativeTermCovAdp+positiveTermCovAdp) > 0){
					double termc = (double)((double)positiveTermCovAdp/(double)(positiveTermCovAdp+negativeTermCovAdp));
					termCovDesc += (termc)+"\t"+termCovCIAdp[0]+"\t"+termCovCIAdp[1]+"\t";
				}else{
					termCovDesc += "0\t0\t0\t";
				}
				if((negativeTermCovAlt+positiveTermCovAlt) > 0){
					double termc = (double)((double)positiveTermCovAlt/(double)(positiveTermCovAlt+negativeTermCovAlt));
					termCovDesc += (termc)+"\t"+termCovCIAlt[0]+"\t"+termCovCIAlt[1]+"\t";
				}else{
					termCovDesc += "0\t0\t0\t";
				}
				if((negativeTermCovAbs+positiveTermCovAbs) > 0){
					double termc = (double)((double)positiveTermCovAbs/(double)(positiveTermCovAbs+negativeTermCovAbs));
					termCovDesc += (termc)+"\t"+termCovCIAbs[0]+"\t"+termCovCIAbs[1]+"\t";
				}else{
					termCovDesc += "0\t0\t0";
				}
				System.out.println(termCovDesc);				
			}
			
			Double[] termCovCIAdpTotal = new Double[2];
			double meanCI = calcMeanCI(termCovAdpTotal,significance);
			termCovCIAdpTotal[0]	= termCovAdpTotal.getMean() - meanCI;
			termCovCIAdpTotal[1]	= termCovAdpTotal.getMean() + meanCI;
			
			Double[] termCovCIAltTotal	= new Double[2];
			meanCI = calcMeanCI(termCovAltTotal,significance);
			termCovCIAltTotal[0]	= termCovAltTotal.getMean() - meanCI;
			termCovCIAltTotal[1]	= termCovAltTotal.getMean() + meanCI;
			
			Double[] termCovCIAbsTotal	= new Double[2];
			meanCI = calcMeanCI(termCovAbsTotal,significance);
			termCovCIAbsTotal[0]	= termCovAbsTotal.getMean() - meanCI;
			termCovCIAbsTotal[1]	= termCovAbsTotal.getMean() + meanCI;
			
			System.out.println("Language\tAnnotators\tAdopt - Positive\tAdopt - Negative\tAlt - Positive\tAlt - Negative\tAbs - Positive\tAbs - Negative");
			System.out.println(language+"\t"+listAnnotatorsTC.get(language).size()+"\t"+positiveTermCovAdpTotal+"\t"+negativeTermCovAdpTotal+"\t"+positiveTermCovAltTotal+"\t"+negativeTermCovAltTotal+"\t"+positiveTermCovAbsTotal+"\t"+negativeTermCovAbsTotal);
			System.out.println("Language\tAdopt Term Coverage\tCI-left\tCI-right\tAlternative Term Coverage\tCI-left\tCI-right\tAbstain Term Coverage\tCI-left\tCI-right");
			String termCovDesc = language+"\t";
			if((negativeTermCovAdpTotal+positiveTermCovAdpTotal) > 0){
				double termc = (double)((double)positiveTermCovAdpTotal/(double)(positiveTermCovAdpTotal+negativeTermCovAdpTotal));
				termCovDesc += (termc)+"\t"+termCovCIAdpTotal[0]+"\t"+termCovCIAdpTotal[1]+"\t";
			}else{
				termCovDesc += "0\t0\t0\t";
			}
			if((negativeTermCovAltTotal+positiveTermCovAltTotal) > 0){
				double termc = (double)((double)positiveTermCovAltTotal/(double)(positiveTermCovAltTotal+negativeTermCovAltTotal));
				termCovDesc += (termc)+"\t"+termCovCIAltTotal[0]+"\t"+termCovCIAltTotal[1]+"\t";
			}else{
				termCovDesc += "0\t0\t0\t";
			}
			if((negativeTermCovAbsTotal+positiveTermCovAbsTotal) > 0){
				double termc = (double)((double)positiveTermCovAbsTotal/(double)(positiveTermCovAbsTotal+negativeTermCovAbsTotal));
				termCovDesc += (termc)+"\t"+termCovCIAbsTotal[0]+"\t"+termCovCIAbsTotal[1]+"\t";
			}else{
				termCovDesc += "0\t0\t0";
			}
			System.out.println(termCovDesc);
		}
	}
	
	public static void getConceptCoverageDescription(TermCoverage tc, String output_file){
		double significance = 0.95;
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(output_file));
			HashMap<String,ArrayList<AnnotatorTC>> listAnnotatorsTC = tc.getListAnnotatorsTC();
			for(String language: listAnnotatorsTC.keySet()){
				//ADOPT
				int positiveTermCovAdpTotal = 0;
				int negativeTermCovAdpTotal = 0;
				SummaryStatistics termCovAdpTotal = new SummaryStatistics();
				
				//ALTERNATIVE
				int positiveTermCovAltTotal = 0;
				int negativeTermCovAltTotal = 0;
				SummaryStatistics termCovAltTotal = new SummaryStatistics();
							
				//ABSTAIN
				int positiveTermCovAbsTotal = 0;
				int negativeTermCovAbsTotal = 0;
				SummaryStatistics termCovAbsTotal = new SummaryStatistics();
				
				for(AnnotatorTC annotator: listAnnotatorsTC.get(language)){
					//ADOPT
					int positiveTermCovAdp = 0;
					int negativeTermCovAdp = 0;
					SummaryStatistics termCovAdp = new SummaryStatistics();
					
					//ALTERNATIVE
					int positiveTermCovAlt = 0;
					int negativeTermCovAlt = 0;
					SummaryStatistics termCovAlt = new SummaryStatistics();
								
					//ABSTAIN
					int positiveTermCovAbs = 0;
					int negativeTermCovAbs = 0;
					SummaryStatistics termCovAbs = new SummaryStatistics();
									
					for(SnippetTC snippet: annotator.getListOfSnippets()){
						double positive	= (double) snippet.getNumberOfPositiveTermCoverageAdopt();
						
						double negative	= (double) snippet.getNumberOfNegativeTermCoverageAdopt();
						positiveTermCovAdp		+= positive;
						negativeTermCovAdp		+= negative;
						positiveTermCovAdpTotal	+= positive;
						negativeTermCovAdpTotal	+= negative;
						termCovAdp.addValue(positive/(positive+negative));
						termCovAdpTotal.addValue(positive/(positive+negative));
						
						positive = (double) snippet.getNumberOfPositiveTermCoverageAlternative();
						negative = (double) snippet.getNumberOfNegativeTermCoverageAlternative();
						positiveTermCovAlt		+= positive;
						negativeTermCovAlt		+= negative;
						positiveTermCovAltTotal	+= positive;
						negativeTermCovAltTotal	+= negative;
						termCovAlt.addValue(positive/(positive+negative));
						termCovAltTotal.addValue(positive/(positive+negative));
						
						positive = (double) snippet.getNumberOfPositiveTermCoverageAbstain();
						negative = (double) snippet.getNumberOfNegativeTermCoverageAbstain();
						positiveTermCovAbs		+= positive;
						negativeTermCovAbs		+= negative;
						positiveTermCovAbsTotal	+= positive;
						negativeTermCovAbsTotal	+= negative;
						termCovAbs.addValue(positive/(positive+negative));
						termCovAbsTotal.addValue(positive/(positive+negative));
					}
					
					Double[] termCovCIAdp = new Double[2];
					double meanCI = calcMeanCI(termCovAdp,significance);
					termCovCIAdp[0]	= termCovAdp.getMean() - meanCI;
					termCovCIAdp[1]	= termCovAdp.getMean() + meanCI;
					
					Double[] termCovCIAlt	= new Double[2];
					meanCI = calcMeanCI(termCovAlt,significance);
					termCovCIAlt[0]	= termCovAlt.getMean() - meanCI;
					termCovCIAlt[1]	= termCovAlt.getMean() + meanCI;
					
					Double[] termCovCIAbs	= new Double[2];
					meanCI = calcMeanCI(termCovAbs,significance);
					termCovCIAbs[0]	= termCovAbs.getMean() - meanCI;
					termCovCIAbs[1]	= termCovAbs.getMean() + meanCI;
					
					bw.write("Language\tAnnotator\tAdopt - Positive\tAdopt - Negative\tAlt - Positive\tAlt - Negative\tAbs - Positive\tAbs - Negative\n");
					bw.write(language+"\t"+annotator.getId()+"\t"+positiveTermCovAdp+"\t"+negativeTermCovAdp+"\t"+positiveTermCovAlt+"\t"+negativeTermCovAlt+"\t"+positiveTermCovAbs+"\t"+negativeTermCovAbs+"\n");
					bw.write("Language\tAnnotator\tAdopt Term Coverage\tCI-left\tCI-right\tAlternative Term Coverage\tCI-left\tCI-right\tAbstain Term Coverage\tCI-left\tCI-right\n");
					String termCovDesc = language+"\t"+annotator.getId()+"\t";
					if((negativeTermCovAdp+positiveTermCovAdp) > 0){
						double termc = (double)((double)positiveTermCovAdp/(double)(positiveTermCovAdp+negativeTermCovAdp));
						termCovDesc += (termc)+"\t"+termCovCIAdp[0]+"\t"+termCovCIAdp[1]+"\t";
					}else{
						termCovDesc += "0\t0\t0\t";
					}
					if((negativeTermCovAlt+positiveTermCovAlt) > 0){
						double termc = (double)((double)positiveTermCovAlt/(double)(positiveTermCovAlt+negativeTermCovAlt));
						termCovDesc += (termc)+"\t"+termCovCIAlt[0]+"\t"+termCovCIAlt[1]+"\t";
					}else{
						termCovDesc += "0\t0\t0\t";
					}
					if((negativeTermCovAbs+positiveTermCovAbs) > 0){
						double termc = (double)((double)positiveTermCovAbs/(double)(positiveTermCovAbs+negativeTermCovAbs));
						termCovDesc += (termc)+"\t"+termCovCIAbs[0]+"\t"+termCovCIAbs[1]+"\t";
					}else{
						termCovDesc += "0\t0\t0";
					}
					bw.write(termCovDesc+"\n");				
				}
				
				Double[] termCovCIAdpTotal = new Double[2];
				double meanCI = calcMeanCI(termCovAdpTotal,significance);
				termCovCIAdpTotal[0]	= termCovAdpTotal.getMean() - meanCI;
				termCovCIAdpTotal[1]	= termCovAdpTotal.getMean() + meanCI;
				
				Double[] termCovCIAltTotal	= new Double[2];
				meanCI = calcMeanCI(termCovAltTotal,significance);
				termCovCIAltTotal[0]	= termCovAltTotal.getMean() - meanCI;
				termCovCIAltTotal[1]	= termCovAltTotal.getMean() + meanCI;
				
				Double[] termCovCIAbsTotal	= new Double[2];
				meanCI = calcMeanCI(termCovAbsTotal,significance);
				termCovCIAbsTotal[0]	= termCovAbsTotal.getMean() - meanCI;
				termCovCIAbsTotal[1]	= termCovAbsTotal.getMean() + meanCI;
				
				bw.write("Language\tAnnotators\tAdopt - Positive\tAdopt - Negative\tAlt - Positive\tAlt - Negative\tAbs - Positive\tAbs - Negative\n");
				bw.write(language+"\t"+listAnnotatorsTC.get(language).size()+"\t"+positiveTermCovAdpTotal+"\t"+negativeTermCovAdpTotal+"\t"+positiveTermCovAltTotal+"\t"+negativeTermCovAltTotal+"\t"+positiveTermCovAbsTotal+"\t"+negativeTermCovAbsTotal+"\n");
				bw.write("Language\tAdopt Term Coverage\tCI-left\tCI-right\tAlternative Term Coverage\tCI-left\tCI-right\tAbstain Term Coverage\tCI-left\tCI-right\n");
				String termCovDesc = language+"\t";
				if((negativeTermCovAdpTotal+positiveTermCovAdpTotal) > 0){
					double termc = (double)((double)positiveTermCovAdpTotal/(double)(positiveTermCovAdpTotal+negativeTermCovAdpTotal));
					termCovDesc += (termc)+"\t"+termCovCIAdpTotal[0]+"\t"+termCovCIAdpTotal[1]+"\t";
				}else{
					termCovDesc += "0\t0\t0\t";
				}
				if((negativeTermCovAltTotal+positiveTermCovAltTotal) > 0){
					double termc = (double)((double)positiveTermCovAltTotal/(double)(positiveTermCovAltTotal+negativeTermCovAltTotal));
					termCovDesc += (termc)+"\t"+termCovCIAltTotal[0]+"\t"+termCovCIAltTotal[1]+"\t";
				}else{
					termCovDesc += "0\t0\t0\t";
				}
				if((negativeTermCovAbsTotal+positiveTermCovAbsTotal) > 0){
					double termc = (double)((double)positiveTermCovAbsTotal/(double)(positiveTermCovAbsTotal+negativeTermCovAbsTotal));
					termCovDesc += (termc)+"\t"+termCovCIAbsTotal[0]+"\t"+termCovCIAbsTotal[1]+"\t";
				}else{
					termCovDesc += "0\t0\t0";
				}
				bw.write(termCovDesc+"\n");
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
