package term_coverage;

import java.util.ArrayList;

public class AnnotatorTC {
	private String id;
	private String language;
	private ArrayList<SnippetTC> listSnippets;
	private int positiveTermCovAdp = 0;
	private int negativeTermCovAdp = 0;
	private int positiveTermCovAlt = 0;
	private int negativeTermCovAlt = 0;
	private int positiveTermCovAbs = 0;
	private int negativeTermCovAbs = 0;
	
	public AnnotatorTC(String id,String language){
		this.id = id;
		this.language = language;
		listSnippets = new ArrayList<SnippetTC>();
	}
	
	public String getId(){
		return id;
	}
	
	public String getLanguage(){
		return language;
	}
	
	public void addSnippet(SnippetTC snippet){
		listSnippets.add(snippet);
		
		positiveTermCovAdp	+= snippet.getNumberOfPositiveTermCoverageAdopt();
		negativeTermCovAdp	+= snippet.getNumberOfNegativeTermCoverageAdopt();
		
		positiveTermCovAlt	+= snippet.getNumberOfPositiveTermCoverageAlternative();
		negativeTermCovAlt	+= snippet.getNumberOfNegativeTermCoverageAlternative();
		
		positiveTermCovAbs	+= snippet.getNumberOfPositiveTermCoverageAbstain();
		negativeTermCovAbs	+= snippet.getNumberOfNegativeTermCoverageAbstain();
	}
	
	public ArrayList<SnippetTC> getListOfSnippets(){
		return listSnippets;
	}
	
	public int getNumberOfPositiveTermCoverageAdopt(){
		return positiveTermCovAdp;
	}
	
	public int getNumberOfPositiveTermCoverageAlternative(){
		return positiveTermCovAlt;
	}
	
	public int getNumberOfPositiveTermCoverageAbstain(){
		return positiveTermCovAbs;
	}
	
	public int getNumberOfNegativeTermCoverageAdopt(){
		return negativeTermCovAdp;
	}
	
	public int getNumberOfNegativeTermCoverageAlternative(){
		return negativeTermCovAlt;
	}
	
	public int getNumberOfNegativeTermCoverageAbstain(){
		return negativeTermCovAbs;
	}
}
