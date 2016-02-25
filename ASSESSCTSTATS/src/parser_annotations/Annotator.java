package parser_annotations;

import java.util.ArrayList;

public class Annotator {
	private String id;
	private String language;
	private ArrayList<Snippet> listSnippets;
	
	public Annotator(String id,String language){
		this.id = id;
		this.language = language;
		listSnippets = new ArrayList<Snippet>();
	}
	
	public String getId(){
		return id;
	}
	
	public String getLanguage(){
		return language;
	}
	
	public void addSnippet(Snippet snippet){
		listSnippets.add(snippet);
	}
	
	public ArrayList<Snippet> getListOfSnippets(){
		return listSnippets;
	}
}
