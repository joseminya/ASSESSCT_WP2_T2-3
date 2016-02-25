package parser_annotations;

import java.util.ArrayList;

public class Snippet {
	private String id;
	private String original_language;
	private ArrayList<Sentence> listSentences;
	private int nLines;
	public Snippet(String id, String original_language){
		this.id = id;
		this.original_language = original_language;
		listSentences = new ArrayList<Sentence>();
		nLines = 0;
	}
	
	public String getId(){
		return id;
	}
	
	public String getOriginalLanguage(){
		return original_language;
	}
	
	public void addSentence(Sentence sentence){
		listSentences.add(sentence);
	}
	
	public ArrayList<Sentence> getListOfSentences(){
		return listSentences;
	}
	
	public int getNumberOfTokens(){
		int nTokens = 0;
		for(Sentence sentence: listSentences){
			nTokens += sentence.getNumberOfTokens();
		}
		return nTokens;
	}
	
	public void setNumberOfLines(int numberOfLines){
		nLines = numberOfLines;
	}
	
	public int getNumberOfLines(){
		return nLines;
	}
}
