package parser_annotations;

public class Token {
	private String token;
	private int position;
	private AnnotationToken adoptAnnotations;
	private AnnotationToken alterAnnotations;
	private AnnotationToken abstaAnnotations;
	
	public Token(String token, int position){
		this.token			= token;
		this.position		= position;
		adoptAnnotations	= null;
		alterAnnotations	= null;
		abstaAnnotations	= null;
	}
	
	public String getToken(){
		return token;
	}
	
	public int getPosition(){
		return position;
	}
	
	public void setAdoptAnnotations(AnnotationToken adoptAnnotations){
		this.adoptAnnotations = adoptAnnotations;
	}
	
	public AnnotationToken getAdoptAnnotations(){
		return adoptAnnotations;
	}
	
	public void setAlternativeAnnotations(AnnotationToken alterAnnotations){
		this.alterAnnotations = alterAnnotations;
	}
	
	public AnnotationToken getAlternativeAnnotations(){
		return alterAnnotations;
	}
	
	public void setAbstainAnnotations(AnnotationToken abstaAnnotations){
		this.abstaAnnotations = abstaAnnotations;
	}
	
	public AnnotationToken getAbstainAnnotations(){
		return abstaAnnotations;
	}
	
	public boolean isEmpty(){
		return ((adoptAnnotations == null) && (alterAnnotations == null) && (abstaAnnotations == null));
	}
}
