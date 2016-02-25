package interRaterAgreement;

import java.util.ArrayList;

public class SnippetIAA {
	private String id;
	private String original_language;
	private ArrayList<SentenceIAA> listSentences;

	public SnippetIAA(String id, String original_language){
		this.id = id;
		this.original_language = original_language;
		listSentences = new ArrayList<SentenceIAA>();
	}
	
	public String getId(){
		return id;
	}
	
	public String getOriginalLanguage(){
		return original_language;
	}
	
	public void addSentence(SentenceIAA sentenceIaa){
		listSentences.add(sentenceIaa);
	}
	
	public ArrayList<SentenceIAA> getListSentences(){
		return listSentences;
	}
}
