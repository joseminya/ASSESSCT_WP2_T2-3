package interRaterAgreement;

import java.util.ArrayList;

public class AnnotatorIAA {
	private String id;
	private String language;
	private ArrayList<SnippetIAA> listSnippets;
	
	public AnnotatorIAA(String id,String language){
		this.id = id;
		this.language = language;
		listSnippets = new ArrayList<SnippetIAA>();
	}
	
	public void addSnippet(SnippetIAA snippetIaa){
		listSnippets.add(snippetIaa);
	}
	
	public ArrayList<SnippetIAA> getListSnippets(){
		return listSnippets;
	}
	
	public String getId(){
		return id;
	}
	
	public String getLanguage(){
		return language;
	}
}
