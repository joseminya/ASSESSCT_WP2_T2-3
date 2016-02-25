package interRaterAgreement;

public class SentenceIAA {
	private String id;
	private IAAUnit unitAdp;
	private IAAUnit unitAlt;
	private IAAUnit unitAbs;
	
	public SentenceIAA(String id){
		this.id = id;
	}
	
	public String getId(){
		return id;
	}
	
	public void setAdpUnit(IAAUnit unit){
		unitAdp = unit;
	}
	
	public IAAUnit getAdpUnit(){
		return unitAdp;
	}
	
	public void setAltUnit(IAAUnit unit){
		unitAlt = unit;
	}

	public IAAUnit getAltUnit(){
		return unitAlt;
	}
	
	public void setAbsUnit(IAAUnit unit){
		unitAbs = unit;
	}
	
	public IAAUnit getAbsUnit(){
		return unitAbs;
	}
	
	public boolean isEmpty(){
		return unitAdp.isEmpty() && unitAlt.isEmpty() && unitAbs.isEmpty();
	}
}
