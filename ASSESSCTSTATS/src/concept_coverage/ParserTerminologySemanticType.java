package concept_coverage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.zip.GZIPInputStream;

public class ParserTerminologySemanticType {
	private HashMap<String,HashSet<String>> mapSemTypes;
	
	public ParserTerminologySemanticType(String fileName){
		try{
			GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(gzip));
			String line="";
			String lastConcept="";
			
			mapSemTypes = new HashMap<String,HashSet<String>>();
			while((line=br.readLine())!=null){
				if(line.startsWith("subsetdef:")){
					String semtype = line.substring(line.indexOf(":")+1).trim();
					if(!semtype.startsWith("T") && !mapSemTypes.containsKey(semtype)){
						semtype = semtype.substring(0,semtype.indexOf(" "));
						mapSemTypes.put(semtype,new HashSet<String>());
						mapSemTypes.get(semtype).add(semtype);
					}
				}
				
				if(line.startsWith("id:")){
					lastConcept = line.substring(line.indexOf(":")+1).trim();
										
					if(!mapSemTypes.containsKey(lastConcept)){
						mapSemTypes.put(lastConcept, new HashSet<String>());
					}
				}
				
				if(line.startsWith("is_a:")){
					String semtype = line.substring(line.indexOf(":")+1).trim();
					mapSemTypes.get(lastConcept).add(semtype);
				}
			}		
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void addTerminology(String fileName){
		try{
			GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(fileName));
			BufferedReader br = new BufferedReader(new InputStreamReader(gzip));
			String line="";
			String lastConcept="";
			
			while((line=br.readLine())!=null){
				if(line.startsWith("subsetdef:")){
					String semtype = line.substring(line.indexOf(":")+1).trim();
					if(!semtype.startsWith("T") && !mapSemTypes.containsKey(semtype)){
						semtype = semtype.substring(0,semtype.indexOf(" "));
						mapSemTypes.put(semtype,new HashSet<String>());
						mapSemTypes.get(semtype).add(semtype);
					}
				}
				
				if(line.startsWith("id:")){
					lastConcept = line.substring(line.indexOf(":")+1).trim();
					if(!mapSemTypes.containsKey(lastConcept)){
						mapSemTypes.put(lastConcept, new HashSet<String>());
					}
				}
				
				if(line.startsWith("is_a:")){
					String semtype = line.substring(line.indexOf(":")+1).trim();
					mapSemTypes.get(lastConcept).add(semtype);
				}
			}		
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public HashMap<String,HashSet<String>> getListConceptBySemType(){
		return mapSemTypes;
	}
}
