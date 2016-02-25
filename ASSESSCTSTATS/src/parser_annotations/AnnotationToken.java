package parser_annotations;

import java.util.ArrayList;

public class AnnotationToken {
	private ArrayList<String> listCodes;
	private int conceptCoverage;
	private boolean termCoverage;
	
	public AnnotationToken(String codes, int conceptCoverage, String termCoverage){
		this.conceptCoverage	= conceptCoverage;
		this.termCoverage = false;
		if(termCoverage.equalsIgnoreCase("yes") || termCoverage.equalsIgnoreCase("y")){
			this.termCoverage = true;
		}
		
		listCodes = new ArrayList<String>();
		if(codes.contains(",")|| codes.contains(";")){
			String[] items = null;
			if(codes.contains(",")){
				items = codes.split(",");
			}else{
				items = codes.split(";");
			}
			for(String code: items){
				listCodes.add(code.trim());
			}
		}else{
			listCodes.add(codes.trim());
		}
	}
	
	public ArrayList<String> getListCodes(){
		return listCodes;
	}
	
	public int getConceptCoverage(){
		return conceptCoverage;
	}
	
	public boolean getTermCoverage(){
		return termCoverage;
	}
}
