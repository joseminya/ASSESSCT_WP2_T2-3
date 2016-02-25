package concept_coverage;

import java.util.ArrayList;


public class AnnotatorCC {
	private String id;
	private String language;
	private ArrayList<SnippetCC> listSnippets;
	
	//ADOPT
	private int fullCovAdp	= 0;
	private int infCovAdp	= 0;
	private int partCovAdp	= 0;
	private int noCovAdp	= 0;
	
	//ALTERNATIVE
	private int fullCovAlt	= 0;
	private int infCovAlt	= 0;
	private int partCovAlt	= 0;
	private int noCovAlt	= 0;
	
	//ABSTAIN
	int fullCovAbs	= 0;
	int infCovAbs	= 0;
	int partCovAbs	= 0;
	int noCovAbs	= 0;
	
	public AnnotatorCC(String id,String language){
		this.id = id;
		this.language = language;
		listSnippets = new ArrayList<SnippetCC>();
	}
	
	public String getId(){
		return id;
	}
	
	public String getLanguage(){
		return language;
	}
	
	public void addSnippet(SnippetCC snippet){
		listSnippets.add(snippet);
		
		fullCovAdp	+= snippet.getNumberOfFullCoverageAnnotationsOfAdopt();
		infCovAdp	+= snippet.getNumberOfInferredCoverageAnnotationsOfAdopt();
		partCovAdp	+= snippet.getNumberOfPartialCoverageAnnotationsOfAdopt();
		noCovAdp	+= snippet.getNumberOfNoCoverageAnnotationsOfAdopt();
		
		fullCovAlt	+= snippet.getNumberOfFullCoverageAnnotationsOfAlternative();
		infCovAlt	+= snippet.getNumberOfInferredCoverageAnnotationsOfAlternative();
		partCovAlt	+= snippet.getNumberOfPartialCoverageAnnotationsOfAlternative();
		noCovAlt	+= snippet.getNumberOfNoCoverageAnnotationsOfAlternative();
		
		fullCovAbs	+= snippet.getNumberOfFullCoverageAnnotationsOfAbstain();
		infCovAbs	+= snippet.getNumberOfInferredCoverageAnnotationsOfAbstain();
		partCovAbs	+= snippet.getNumberOfPartialCoverageAnnotationsOfAbstain();
		noCovAbs	+= snippet.getNumberOfNoCoverageAnnotationsOfAbstain();
	}
	
	public ArrayList<SnippetCC> getListOfSnippets(){
		return listSnippets;
	}
	
	public Integer getNumberOfFullCoverageAnnotationsOfAdopt(){
		return fullCovAdp;
	}
	
	public Integer getNumberOfInferredCoverageAnnotationsOfAdopt(){
		return infCovAdp;
	}
	
	public Integer getNumberOfPartialCoverageAnnotationsOfAdopt(){
		return partCovAdp;
	}
	
	public Integer getNumberOfNoCoverageAnnotationsOfAdopt(){
		return noCovAdp;
	}
	
	public Integer getNumberOfFullCoverageAnnotationsOfAlternative(){
		return fullCovAlt;
	}
	
	public Integer getNumberOfInferredCoverageAnnotationsOfAlternative(){
		return infCovAlt;
	}
	
	public Integer getNumberOfPartialCoverageAnnotationsOfAlternative(){
		return partCovAlt;
	}
	
	public Integer getNumberOfNoCoverageAnnotationsOfAlternative(){
		return noCovAlt;
	}
	
	public Integer getNumberOfFullCoverageAnnotationsOfAbstain(){
		return fullCovAbs;
	}
	
	public Integer getNumberOfInferredCoverageAnnotationsOfAbstain(){
		return infCovAbs;
	}
	
	public Integer getNumberOfPartialCoverageAnnotationsOfAbstain(){
		return partCovAbs;
	}
	
	public Integer getNumberOfNoCoverageAnnotationsOfAbstain(){
		return noCovAbs;
	}
}
