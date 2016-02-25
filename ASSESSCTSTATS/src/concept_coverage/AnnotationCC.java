package concept_coverage;

import java.util.ArrayList;

public class AnnotationCC {
	private String chunkId;
	private String sentenceId;
	private ArrayList<CodeCC> listCodesSCT;
	private ArrayList<CodeCC> listCodesUMLS;
	private ArrayList<CodeCC> listCodesLocal;
	
	public AnnotationCC(String chunkId, String sentenceId){
		this.chunkId	= chunkId;
		this.sentenceId	= sentenceId;
		listCodesSCT	= new ArrayList<CodeCC>();
		listCodesUMLS	= new ArrayList<CodeCC>();
		listCodesLocal	= new ArrayList<CodeCC>();
	}
	
	public String getChunkId(){
		return chunkId;
	}
	
	public String getSentenceId(){
		return sentenceId;
	}
	
	public void addSCTCodeCC(CodeCC codecc){
		listCodesSCT.add(codecc);
	}
	
	public ArrayList<CodeCC> getListOfSCTCodesCC(){
		return listCodesSCT;
	}

	public void addUMLSCodeCC(CodeCC codecc){
		listCodesUMLS.add(codecc);
	}
	
	public ArrayList<CodeCC> getListOfUMLSCodesCC(){
		return listCodesUMLS;
	}

	public void addLocalCodeCC(CodeCC codecc){
		listCodesLocal.add(codecc);
	}
	
	public ArrayList<CodeCC> getListOfLocalCodesCC(){
		return listCodesLocal;
	}
}
