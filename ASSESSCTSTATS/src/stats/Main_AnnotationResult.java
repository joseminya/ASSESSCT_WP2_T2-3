package stats;

import concept_coverage.ConceptCoverage;
import concept_coverage.ParserTerminologySemanticType;
import parser_annotations.ProcessTableResults;
import term_coverage.TermCoverage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Main_AnnotationResult {
	
	public static void main(String[] args) {
		String input_folder = args[0];
		String output_folder = args[1];
		File input = new File(input_folder);
		if(!input.isDirectory()){
			System.err.println("ERROR: input folder is not a valid directory.");
			System.exit(1);
		}
		File output = new File(output_folder);
		if(!output.isDirectory()){
			System.err.println("ERROR: output folder is not a valid directory.");
			System.exit(1);
		}		
		
		//String file = "F:/projects/assess ct/Annotations berlin/resultTable2016.csv";
		String file = input_folder+"/resultTable2016.csv";
		
		ProcessTableResults ptr = new ProcessTableResults(file);
				
		//String adp_en = "F:/projects/assess ct/terminologies/EN_adopt.csv";
		//String alt_en = "F:/projects/assess ct/terminologies/alternative_en.obo.gz";
		//String alt_se = "F:/projects/assess ct/terminologies/alternative_sv.obo.gz";
		//String alt_fr = "F:/projects/assess ct/terminologies/alternative_fr.obo.gz";
		//String alt_nl = "F:/projects/assess ct/terminologies/alternative_nl.obo.gz";
		//String alt_de = "F:/projects/assess ct/terminologies/alternative_de.obo.gz";
		//String abs_de = "F:/projects/assess ct/terminologies/abstain/";
		//String type_group = "F:/projects/assess ct/Annotations berlin/semtype_semgroup.txt";
		
		String adp_en = input_folder+"/EN_adopt.csv";
		String alt_en = input_folder+"/alternative_en.obo.gz";
		String alt_se = input_folder+"/alternative_sv.obo.gz";
		String alt_fr = input_folder+"/alternative_fr.obo.gz";
		String alt_nl = input_folder+"/alternative_nl.obo.gz";
		String alt_de = input_folder+"/alternative_de.obo.gz";
		String abs_de = input_folder+"/abstain/";
		String type_group = input_folder+"/semtype_semgroup.txt";
		
		
		SamplesDescription.getSampleDescription(ptr,output_folder+"/SnippetsDescription.txt");
		
		ConceptCoverage cc = new ConceptCoverage(ptr);
	
		ParserTerminologySemanticType ptstAlt = new ParserTerminologySemanticType(alt_en);
		ptstAlt.addTerminology(alt_de);
		ptstAlt.addTerminology(alt_fr);
		ptstAlt.addTerminology(alt_se);
		ptstAlt.addTerminology(alt_nl);
		
		HashMap<String,String> semtype_group = getSemGroups(type_group);
		cc.setSemanticTypes(getAdoptSemTypes(adp_en), ptstAlt.getListConceptBySemType(), getAbstainSemTypes(abs_de), semtype_group);
		
		ArrayList<String> setOfKeysAbs	= new ArrayList<String>();
		setOfKeysAbs.add("ABDAMED");
		setOfKeysAbs.add("OPS");
		setOfKeysAbs.add("ICD10");
		setOfKeysAbs.add("ICD-O");
		setOfKeysAbs.add("MESH-ANAT");
		setOfKeysAbs.add("MESH-ORGA");
		
		ArrayList<String> setOfKeysAdpAlt	= new ArrayList<String>();
		for(String group: semtype_group.keySet()){
			if(semtype_group.get(group).equals(group)){
				if(setOfKeysAbs.contains(group)) continue;
				setOfKeysAdpAlt.add(group);
			}
		}
		
		ConceptCoverageDescription.getConceptCoverageDescription(cc, setOfKeysAdpAlt, setOfKeysAbs, output_folder+"/ConceptCoverageDescription.txt");
		
		TermCoverage tc = new TermCoverage(ptr);
		TermCoverageDescription.getConceptCoverageDescription(tc, output_folder+"/TermCoverageDescription.txt");
		
		File iaaFolder = new File(output_folder+"/IAA/");
		if(!iaaFolder.exists()){
			iaaFolder.mkdir();
		}
		InterAnnotatorAgreementDescription.getIAADescription(ptr, output_folder+"/IAA/");
	}
	
	public static HashMap<String,String> getSemGroups(String file){
		
		HashMap<String,String> res = new HashMap<String,String>();
		String line="";
		try{
			BufferedReader br= new BufferedReader(new FileReader(file));
			while((line=br.readLine())!=null){
				String[] tokens = line.split("\\|",-1);
				String group = tokens[0];
				String type = tokens[2];
				res.put(type, group);
				if(!res.containsKey(group)) res.put(group, group);
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		res.put("ABDAMED", "ABDAMED");
		res.put("OPS", "OPS");
		res.put("ICD10", "ICD10");
		res.put("ICD-O", "ICD-O");
		res.put("MESH-ANAT", "MESH-ANAT");
		res.put("MESH-ORGA", "MESH-ORGA");
		return res;
	}
	
	
	public static HashMap<String, HashSet<String>> getAdoptSemTypes(String root_file){
		HashMap<String,HashSet<String>> results = new HashMap<String,HashSet<String>>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(root_file));
			String line = "";
			while((line=br.readLine())!=null){
				String[] tokens = line.split(";",-1);
				String semtype = tokens[6];
				semtype = semtype.replaceAll("\"","");
				String code = tokens[0];
				code = code.replaceAll("\"", "");
				if(code.startsWith("T")){
					results.put(code, new HashSet<String>());
					results.get(code).add(code);
				}
				if(results.containsKey(code)){
					results.get(code).add(semtype);
				}else{
					results.put(code, new HashSet<String>());
					results.get(code).add(semtype);
				}
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return results;
	}
	
	public static HashMap<String,HashSet<String>> getAbstainSemTypes(String root_folder){
		HashMap<String,HashSet<String>> results = new HashMap<String,HashSet<String>>();
		File root = new File(root_folder);
		if(root.isDirectory()){
			File[] listFiles = root.listFiles();
			for(File file: listFiles){
				int type = 0;//CSV FILE
				if(file.getName().endsWith("obo")) type = 1; //OBO FILE
				String semtype = "";
				if(file.getName().contains("abdamed_de")) semtype = "ABDAMED";
				//if(file.getName().contains("atc_de")) semtype = "ATC";
				if(file.getName().contains("icd10_de")) semtype = "ICD10";
				if(file.getName().contains("icdo_generic")) semtype = "ICD-O";
				if(file.getName().contains("mesh_ana")) semtype = "MESH-ANAT";
				if(file.getName().contains("mesh_orga")) semtype = "MESH-ORGA";
				if(file.getName().contains("OPS_de")) semtype = "OPS";
				
				if(type == 0){
					try{
						BufferedReader br = new BufferedReader(new FileReader(file));
						String line = "";
						br.readLine();
						while((line=br.readLine())!=null){
							String[] tokens = line.split(";",-1);
							String code = tokens[0].replaceAll("\"","");
							if(results.containsKey(code)){
								results.get(code).add(semtype);
							}else{
								HashSet<String> listST = new HashSet<String>();
								listST.add(semtype);
								results.put(code,listST);
							}
						}
						br.close();
					}catch(Exception e){
						e.printStackTrace();
					}
				}else{
					try{
						BufferedReader br = new BufferedReader(new FileReader(file));
						
						String line = "";
						while((line=br.readLine())!=null){
							if(line.startsWith("id:")){
								String id = line.substring(line.indexOf(":")+1).trim();
								if(!results.containsKey(id)){
									results.put(id, new HashSet<String>());
								}
								results.get(id).add(semtype);
							}
						}
						br.close();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
		return results;
	}
}
