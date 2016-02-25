package parser_annotations;

import java.util.ArrayList;

public class Chunk {
	private String id;
	private ArrayList<Token> listTokens;
	
	public Chunk(String id){
		this.id = id;
		listTokens = new ArrayList<Token>();
	}
	
	public String getId(){
		return id;
	}
	
	public void addToken(Token token){
		listTokens.add(token);
	}
	
	public ArrayList<Token> getListOfTokens(){
		return listTokens;
	}
	
	public int getNumberOfTokens(){
		return listTokens.size();
	}
}
