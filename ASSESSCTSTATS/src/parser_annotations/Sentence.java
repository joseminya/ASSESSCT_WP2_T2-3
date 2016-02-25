package parser_annotations;

import java.util.ArrayList;

public class Sentence {
	private String id;
	private ArrayList<Chunk> listChunks;
	
	public Sentence(String id){
		this.id = id;
		listChunks = new ArrayList<Chunk>();
	}
	
	public String getId(){
		return id;
	}
	
	public void addChunk(Chunk chunk){
		listChunks.add(chunk);
	}
	
	public ArrayList<Chunk> getListOfChunks(){
		return listChunks;
	}
	
	public int getNumberOfTokens(){
		int nTokens = 0;
		for(Chunk chunk: listChunks){
			nTokens += chunk.getNumberOfTokens();
		}
		return nTokens;
	}
}
