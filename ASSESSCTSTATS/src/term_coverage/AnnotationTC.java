package term_coverage;

import java.util.ArrayList;

public class AnnotationTC {
	private String chunkId;
	private String sentenceId;
	private ArrayList<CodeTC> listCodesAdp;
	private ArrayList<CodeTC> listCodesAlt;
	private ArrayList<CodeTC> listCodesAbs;
	
	public AnnotationTC(String chunkId, String sentenceId){
		this.chunkId	= chunkId;
		this.sentenceId	= sentenceId;
		listCodesAdp	= new ArrayList<CodeTC>();
		listCodesAlt	= new ArrayList<CodeTC>();
		listCodesAbs	= new ArrayList<CodeTC>();
	}
	
	public String getChunkId(){
		return chunkId;
	}
	
	public String getSentenceId(){
		return sentenceId;
	}
	
	public void addAdpCodeTC(CodeTC codetc){
		listCodesAdp.add(codetc);
	}
	
	public ArrayList<CodeTC> getListOfAdpCodesTC(){
		return listCodesAdp;
	}

	public void addAltCodeTC(CodeTC codetc){
		listCodesAlt.add(codetc);
	}
	
	public ArrayList<CodeTC> getListOfAltCodesTC(){
		return listCodesAlt;
	}

	public void addAbsCodeTC(CodeTC codetc){
		listCodesAbs.add(codetc);
	}
	
	public ArrayList<CodeTC> getListOfAbsCodesTC(){
		return listCodesAbs;
	}
}
