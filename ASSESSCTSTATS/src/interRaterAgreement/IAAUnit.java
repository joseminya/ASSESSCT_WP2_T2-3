package interRaterAgreement;

import java.util.HashSet;

public class IAAUnit {
	private String id; //Annotator_Sheet_Sentence
	private HashSet<String> listCodesFull;
	private HashSet<String> listCodesPartial; 
	
	public IAAUnit(String id){
		this.id = id;
		listCodesFull = new HashSet<String>();
		listCodesPartial = new HashSet<String>();
	}
	
	public HashSet<String> getListCodes(){
		HashSet<String> results = new HashSet<String>();
		results.addAll(listCodesFull);
		for(String code: listCodesPartial){
			results.add(code+"*");
		}	
		
		return results;
	}
	
	public void addCodeFull(String code){
		if(!code.trim().isEmpty()){
			listCodesFull.add(code.trim());
		}
	}
	
	public void addCodePartial(String code){
		if(!code.trim().isEmpty()){
			listCodesPartial.add(code.trim());
		}
	}
	
	public HashSet<String> getListCodesFull(){
		return listCodesFull;
	}
	
	public HashSet<String> getListCodesPartial(){
		return listCodesPartial;
	}
	
	public double getQuadraticWeightStrict(IAAUnit iaa){
		double weight = 0.0;
		
		double maxDistance = listCodesFull.size()+listCodesPartial.size();
		if(iaa.getListCodes().size()>maxDistance) maxDistance = iaa.getListCodes().size();
		double overlap = 0.0;
		
		if(maxDistance == 0){
			if(id.equals(iaa.getId())) return 1.0;
			return overlap;
		}
		
		for(String code: iaa.getListCodesFull()){
			if(listCodesFull.contains(code)) overlap++;			
		}
		for(String code: iaa.getListCodesPartial()){
			if(listCodesPartial.contains(code)) overlap++;
		}
		
		double distance = maxDistance - overlap;
		weight = 1.0 - ((double)Math.sqrt(distance) / (double)Math.sqrt(maxDistance));
		return weight;
	}
	
	public double getQuadraticWeightLoose(IAAUnit iaa){
		double weight = 0.0;
		HashSet<String> listCodesA = new HashSet<String>();
		listCodesA.addAll(iaa.getListCodesFull());
		listCodesA.addAll(iaa.getListCodesPartial());
		
		HashSet<String> listCodesB = new HashSet<String>();
		listCodesB.addAll(listCodesFull);
		listCodesB.addAll(listCodesPartial);
				
		double maxDistance = listCodesA.size();
		if(listCodesB.size()> maxDistance) maxDistance = listCodesB.size();
		double overlap = 0.0;
		
		if(maxDistance == 0){
			if(id.equals(iaa.getId())) return 1.0;
			return overlap;
		}
		
		for(String code: listCodesA){
			if(listCodesB.contains(code)) overlap++;
		}
		
		double distance = maxDistance - overlap;
		weight = 1.0 - ((double)Math.pow(distance,2) / (double)Math.pow(maxDistance,2)); 
		return weight;
	}
	
	
	public double getQuadraticWeightPartial(IAAUnit iaa){
		double weight = 0.0;
		HashSet<String> listCodes = iaa.getListCodesPartial();
		
		double maxDistance = listCodesPartial.size();
		if(listCodes.size()>maxDistance) maxDistance = listCodes.size();
		double overlap = 0.0;
		
		if(maxDistance == 0){
			if(id.equals(iaa.getId())) return 1.0;
			return overlap;
		}
		
		for(String code: listCodesPartial){
			if(listCodes.contains(code)) overlap++;			
		}
		double distance = maxDistance - overlap;
		
		weight = 1.0 - ((double)Math.pow(distance,2) / (double)Math.pow(maxDistance,2));
		return weight;
	}
	
	public String getId(){
		return id;
	}
	
	public boolean isEmpty(){
		return listCodesPartial.isEmpty() && listCodesFull.isEmpty();
	}
	
	public boolean equalsStrict(IAAUnit unit){
		if(isEmpty() && unit.isEmpty()){
			return id.equals(unit.getId());
		}
		if(isEmpty() || unit.isEmpty()) return false;
		
		HashSet<String> listCodes = unit.getListCodesFull();
		if(listCodes.size() != listCodesFull.size()) return false;
		for(String code: listCodesFull){
			if(!listCodes.contains(code)) return false;			
		}
		listCodes = unit.getListCodesPartial();
		if(listCodes.size() != listCodesPartial.size()) return false;
		for(String code: listCodesPartial){
			if(!listCodes.contains(code)) return false;
		}
		return true;
	}
	
	public boolean equalsLoose(IAAUnit unit){
		HashSet<String> listCodesA = new HashSet<String>();
		HashSet<String> listCodesB = new HashSet<String>();
		listCodesA.addAll(listCodesFull);
		listCodesA.addAll(listCodesPartial);
		listCodesB.addAll(unit.getListCodesFull());
		listCodesB.addAll(unit.getListCodesPartial());
		
		if(listCodesA.isEmpty() && listCodesB.isEmpty()){
			return id.equals(unit.getId());
		}
		
		if(listCodesA.size() != listCodesB.size()) return false;
		for(String code: listCodesA){
			if(!listCodesB.contains(code)) return false;			
		}
		
		return true;
	}
}
