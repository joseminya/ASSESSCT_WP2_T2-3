package concept_coverage;

import java.util.ArrayList;
import java.util.HashSet;



public class CodeCC {
	public static final int FULLCOV = 0;
	public static final int INFECOV = 1;
	public static final int PARTCOV = 2;
	public static final int NOCOV	= 3;
	public static final int OUTSCOPE= 4;
	private String code;
	private ArrayList<String> tokens;
	private int covScore;
	private HashSet<String> semgroups;
	
	public CodeCC(String token, String code, int covScore){
		this.code = code;
		tokens = new ArrayList<String>();
		tokens.add(token);
		this.covScore = covScore;
		semgroups = null;
	}
	
	public String getCode(){
		return code;
	}
	
	public int getCoverageScore(){
		return covScore;
	}
	
	public ArrayList<String> getTokens(){
		return tokens;
	}
	
	public void addToken(String token){
		tokens.add(token);
	}
	
	public void setSemanticGroups(HashSet<String> semanticGroups){
		semgroups = semanticGroups;
	}
	
	public HashSet<String> getSemanticGroup(){
		return semgroups;
	}
}
