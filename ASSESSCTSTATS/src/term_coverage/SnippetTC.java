package term_coverage;

import java.util.ArrayList;

public class SnippetTC {
	private String id;
	private String original_language;
	private ArrayList<AnnotationTC> listAnnotations;
		
	public SnippetTC(String id, String original_language){
		this.id = id;
		this.original_language = original_language;
		listAnnotations = new ArrayList<AnnotationTC>();
	}
	
	public String getId(){
		return id;
	}
	
	public String getOriginalLanguage(){
		return original_language;
	}
	
	public void addAnnotation(AnnotationTC annotationcc){
		listAnnotations.add(annotationcc);
	}
	
	public ArrayList<AnnotationTC> getListOfAnnotations(){
		return listAnnotations;
	}
	
	public Integer getNumberOfPositiveTermCoverageAdopt(){
		int positiveTermCovAdp = 0;
		for(AnnotationTC annotation: listAnnotations){
			for(CodeTC code: annotation.getListOfAdpCodesTC()){
				if(code.isPositiveTermCoverage()){
					positiveTermCovAdp++;
				}
			}
		}
		return positiveTermCovAdp;
	}
	
	public Integer getNumberOfNegativeTermCoverageAdopt(){
		int negativeTermCovAdp = 0;
		for(AnnotationTC annotation: listAnnotations){
			for(CodeTC code: annotation.getListOfAdpCodesTC()){
				if(!code.isPositiveTermCoverage()){
					negativeTermCovAdp++;
				}
			}
		}
		return negativeTermCovAdp;
	}

	public Integer getNumberOfPositiveTermCoverageAlternative(){
		int positiveTermCovAlt = 0;
		for(AnnotationTC annotation: listAnnotations){
			for(CodeTC code: annotation.getListOfAltCodesTC()){
				if(code.isPositiveTermCoverage()){
					positiveTermCovAlt++;
				}
			}
		}
		return positiveTermCovAlt;
	}
	
	public Integer getNumberOfNegativeTermCoverageAlternative(){
		int negativeTermCovAlt = 0;
		for(AnnotationTC annotation: listAnnotations){
			for(CodeTC code: annotation.getListOfAltCodesTC()){
				if(!code.isPositiveTermCoverage()){
					negativeTermCovAlt++;
				}
			}
		}
		return negativeTermCovAlt;
	}

	public Integer getNumberOfPositiveTermCoverageAbstain(){
		int positiveTermCovAbs = 0;
		for(AnnotationTC annotation: listAnnotations){
			for(CodeTC code: annotation.getListOfAbsCodesTC()){
				if(code.isPositiveTermCoverage()){
					positiveTermCovAbs++;
				}
			}
		}
		return positiveTermCovAbs;
	}
	
	public Integer getNumberOfNegativeTermCoverageAbstain(){
		int negativeTermCovAbs = 0;
		for(AnnotationTC annotation: listAnnotations){
			for(CodeTC code: annotation.getListOfAbsCodesTC()){
				if(!code.isPositiveTermCoverage()){
					negativeTermCovAbs++;
				}
			}
		}
		return negativeTermCovAbs;
	}	
	
}
