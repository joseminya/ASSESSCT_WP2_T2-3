package concept_coverage;

import java.util.ArrayList;

public class SnippetCC {
	private String id;
	private String original_language;
	private ArrayList<AnnotationCC> listAnnotations;
	
	public SnippetCC(String id, String original_language){
		this.id = id;
		this.original_language = original_language;
		listAnnotations = new ArrayList<AnnotationCC>();
	}
	
	public String getId(){
		return id;
	}
	
	public String getOriginalLanguage(){
		return original_language;
	}
	
	public void addAnnotation(AnnotationCC annotationcc){
		listAnnotations.add(annotationcc);
	}
	
	public ArrayList<AnnotationCC> getListOfAnnotations(){
		return listAnnotations;
	}
	
	public Integer getNumberOfFullCoverageAnnotationsOfAdopt(){
		int fullCovAdp = 0;
		for(AnnotationCC annotation: listAnnotations){
			for(CodeCC code: annotation.getListOfSCTCodesCC()){
				if(code.getCoverageScore() == CodeCC.FULLCOV){
					fullCovAdp++;
				}
			}
		}
		return fullCovAdp;
	}
	
	public Integer getNumberOfInferredCoverageAnnotationsOfAdopt(){
		int infCovAdp = 0;
		for(AnnotationCC annotation: listAnnotations){
			for(CodeCC code: annotation.getListOfSCTCodesCC()){
				if(code.getCoverageScore() == CodeCC.INFECOV){
					infCovAdp++;
				}
			}
		}
		return infCovAdp;
	}
	
	public Integer getNumberOfPartialCoverageAnnotationsOfAdopt(){
		int partCovAdp = 0;
		for(AnnotationCC annotation: listAnnotations){
			for(CodeCC code: annotation.getListOfSCTCodesCC()){
				if(code.getCoverageScore() == CodeCC.PARTCOV){
					partCovAdp++;
				}
			}
		}
		return partCovAdp;
	}
	
	public Integer getNumberOfNoCoverageAnnotationsOfAdopt(){
		int noCovAdp = 0;
		for(AnnotationCC annotation: listAnnotations){
			for(CodeCC code: annotation.getListOfSCTCodesCC()){
				if(code.getCoverageScore() == CodeCC.NOCOV){
					noCovAdp++;
				}
			}
		}
		return noCovAdp;
	}
	
	public Integer getNumberOfFullCoverageAnnotationsOfAlternative(){
		int fullCovAlt = 0;
		for(AnnotationCC annotation: listAnnotations){
			for(CodeCC code: annotation.getListOfUMLSCodesCC()){
				if(code.getCoverageScore() == CodeCC.FULLCOV){
					fullCovAlt++;
				}
			}
		}
		return fullCovAlt;
	}
	
	public Integer getNumberOfInferredCoverageAnnotationsOfAlternative(){
		int infCovAlt = 0;
		for(AnnotationCC annotation: listAnnotations){
			for(CodeCC code: annotation.getListOfUMLSCodesCC()){
				if(code.getCoverageScore() == CodeCC.INFECOV){
					infCovAlt++;
				}
			}
		}
		return infCovAlt;
	}
	
	public Integer getNumberOfPartialCoverageAnnotationsOfAlternative(){
		int partCovAlt = 0;
		for(AnnotationCC annotation: listAnnotations){
			for(CodeCC code: annotation.getListOfUMLSCodesCC()){
				if(code.getCoverageScore() == CodeCC.PARTCOV){
					partCovAlt++;
				}
			}
		}
		return partCovAlt;
	}
	
	public Integer getNumberOfNoCoverageAnnotationsOfAlternative(){
		int noCovAlt = 0;
		for(AnnotationCC annotation: listAnnotations){
			for(CodeCC code: annotation.getListOfUMLSCodesCC()){
				if(code.getCoverageScore() == CodeCC.NOCOV){
					noCovAlt++;
				}
			}
		}
		return noCovAlt;
	}
	
	public Integer getNumberOfFullCoverageAnnotationsOfAbstain(){
		int fullCovAbs = 0;
		for(AnnotationCC annotation: listAnnotations){
			for(CodeCC code: annotation.getListOfLocalCodesCC()){
				if(code.getCoverageScore() == CodeCC.FULLCOV){
					fullCovAbs++;
				}
			}
		}
		return fullCovAbs;
	}
	
	public Integer getNumberOfInferredCoverageAnnotationsOfAbstain(){
		int infCovAbs = 0;
		for(AnnotationCC annotation: listAnnotations){
			for(CodeCC code: annotation.getListOfLocalCodesCC()){
				if(code.getCoverageScore() == CodeCC.INFECOV){
					infCovAbs++;
				}
			}
		}
		return infCovAbs;
	}
	
	public Integer getNumberOfPartialCoverageAnnotationsOfAbstain(){
		int partCovAbs = 0;
		for(AnnotationCC annotation: listAnnotations){
			for(CodeCC code: annotation.getListOfLocalCodesCC()){
				if(code.getCoverageScore() == CodeCC.PARTCOV){
					partCovAbs++;
				}
			}
		}
		return partCovAbs;
	}
	
	public Integer getNumberOfNoCoverageAnnotationsOfAbstain(){
		int noCovAbs = 0;
		for(AnnotationCC annotation: listAnnotations){
			for(CodeCC code: annotation.getListOfLocalCodesCC()){
				if(code.getCoverageScore() == CodeCC.NOCOV){
					noCovAbs++;
				}
			}
		}
		return noCovAbs;
	}
}
