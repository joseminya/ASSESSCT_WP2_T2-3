package stats;

import interRaterAgreement.AnnotatorIAA;
import interRaterAgreement.IAAUnit;
import interRaterAgreement.InterAnnotatorAgreement;
import interRaterAgreement.SentenceIAA;
import interRaterAgreement.SnippetIAA;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import parser_annotations.ProcessTableResults;

public class InterAnnotatorAgreementDescription {
	public static void getIAADescription(ProcessTableResults ptr, String output_folder){
		
		InterAnnotatorAgreement iaa = new InterAnnotatorAgreement(ptr);
		//SCT Test all units are parallel to other annotators units.
		ArrayList<String> commonSnippetIds = null;
		for(String language: iaa.getAgreement().keySet()){
			if(commonSnippetIds==null) commonSnippetIds = new ArrayList<String>();
			for(AnnotatorIAA annIaa: iaa.getAgreement().get(language)){
				if(commonSnippetIds.isEmpty()){
					for(SnippetIAA snippetIaa: annIaa.getListSnippets()){
						commonSnippetIds.add(snippetIaa.getId());
					}
				}else{
					ArrayList<String> newCommonIds = new ArrayList<String>();
					for(SnippetIAA snippetIaa: annIaa.getListSnippets()){
						if(commonSnippetIds.contains(snippetIaa.getId())){
							newCommonIds.add(snippetIaa.getId());
						}
					}
					commonSnippetIds = newCommonIds;
				}
			}
		}
		
		//createRawDataAdp(iaa.getAgreement(), commonSnippetIds, "F:/projects/assess ct/Annotations berlin/iaa/ADOPT_IAA.csv", "F:/projects/assess ct/Annotations berlin/iaa/coincidences_ADP.txt", "F:/projects/assess ct/Annotations berlin/iaa/ratings_ADP.txt", "F:/projects/assess ct/Annotations berlin/iaa/weights_ADP.csv");
		//createRawDataAlt(iaa.getAgreement(), commonSnippetIds, "F:/projects/assess ct/Annotations berlin/iaa/ALTERNATIVE_IAA.csv", "F:/projects/assess ct/Annotations berlin/iaa/coincidences_ALT.txt", "F:/projects/assess ct/Annotations berlin/iaa/ratings_ALT.txt", "F:/projects/assess ct/Annotations berlin/iaa/weights_ALT.csv");
		//createRawDataAbs(iaa.getAgreement(), commonSnippetIds, "F:/projects/assess ct/Annotations berlin/iaa/ABSTAIN_IAA.csv", "F:/projects/assess ct/Annotations berlin/iaa/coincidences_ABS.txt", "F:/projects/assess ct/Annotations berlin/iaa/ratings_ABS.txt", "F:/projects/assess ct/Annotations berlin/iaa/weights_ABS.csv");	
		
		File lang = null;
		lang = new File(output_folder+"/english/");
		if(!lang.exists()) lang.mkdir();
		lang = new File(output_folder+"/swedish/");
		if(!lang.exists()) lang.mkdir();
		lang = new File(output_folder+"/dutch/");
		if(!lang.exists()) lang.mkdir();
		lang = new File(output_folder+"/german/");
		if(!lang.exists()) lang.mkdir();
		lang = new File(output_folder+"/french/");
		if(!lang.exists()) lang.mkdir();
		
		createRawDataAdp(iaa.getAgreement(), commonSnippetIds, output_folder);
		createRawDataAlt(iaa.getAgreement(), commonSnippetIds, output_folder);
		createRawDataAbs(iaa.getAgreement(), commonSnippetIds, output_folder);	
	}
		
	public static void createRawDataAdp(HashMap<String,ArrayList<AnnotatorIAA>> listAnnotations, ArrayList<String> commonSnippetIds, String output_folder){
		
		//SCT Test all units are parallel to other annotators units.
		for(String language: listAnnotations.keySet()){
			String lang_folder="";
			switch (language) {
            case "EN":  lang_folder = "english/";
                     break;
            case "DE":  lang_folder = "german/";
                     break;
            case "FR":  lang_folder = "french/";
                     break;
            case "SE":  lang_folder = "swedish/";
                     break;
            case "NL":  lang_folder = "dutch/";
                     break;
            default: lang_folder = "";
                     break;
			}
			
			ArrayList<ArrayList<SentenceIAA>> rawData = new ArrayList<ArrayList<SentenceIAA>>();
			for(AnnotatorIAA annIaa: listAnnotations.get(language)){
				ArrayList<SentenceIAA> listSentences = new ArrayList<SentenceIAA>();
				for(SnippetIAA snippetIaa: annIaa.getListSnippets()){
					if(!commonSnippetIds.contains(snippetIaa.getId())) continue;
					for(SentenceIAA sentenceIaa: snippetIaa.getListSentences()){
						listSentences.add(sentenceIaa);
					}
				}
				rawData.add(listSentences);
			}
			
			//GET NON EMPTY ANNOTATIONS OF ANNOTATORS	
			ArrayList<Integer> mu_values = new ArrayList<Integer>();
			ArrayList<ArrayList<IAAUnit>> data = new ArrayList<ArrayList<IAAUnit>>();
			for(int i=0;i<rawData.size();i++) data.add(new ArrayList<IAAUnit>());
			for(int j=0;j<rawData.get(0).size();j++){
				int nNotEmpty = 0;
				for(int i=0;i<rawData.size();i++){
					if(!rawData.get(i).get(j).getAdpUnit().isEmpty()) nNotEmpty++;
				}
				if(nNotEmpty>1){
					for(int i=0;i<rawData.size();i++){
						data.get(i).add(rawData.get(i).get(j).getAdpUnit());
					}
					mu_values.add(nNotEmpty);
				}
			}
			
			//GET UNIQUE ANNOTATIONS
			ArrayList<IAAUnit> listUniqueUnitsStrict	= new ArrayList<IAAUnit>();
			ArrayList<IAAUnit> listUniqueUnitsLoose		= new ArrayList<IAAUnit>();
			for(int k=0; k< data.size(); k++){
				ArrayList<IAAUnit> listUnits = data.get(k);
				for(IAAUnit unitIaa: listUnits){
					boolean duplicated_strict = false;
					boolean duplicated_loose = false;
					for(IAAUnit unit: listUniqueUnitsStrict){
						if(!duplicated_strict && unit.equalsStrict(unitIaa)){
							duplicated_strict = true;
						}
						if(!duplicated_loose && unit.equalsLoose(unitIaa)){
							duplicated_loose = true;
						}
					}

					if(!duplicated_strict)	listUniqueUnitsStrict.add(unitIaa);
					if(!duplicated_loose)	listUniqueUnitsLoose.add(unitIaa);
				}
			}
			
			try{
				BufferedWriter bw = new BufferedWriter(new FileWriter(output_folder+"/IAA_ADOPT.csv",true));
				bw.write("\n\n"+language+"\n");
				bw.write("LIST OF UNIQUE UNITS STRICT\n");
				for(int i=0;i<listUniqueUnitsStrict.size();i++){
					String anns = "";
					HashSet<String> listCodes = listUniqueUnitsStrict.get(i).getListCodes();
					if(listCodes.isEmpty()){
						anns+="empty_"+language+"_"+i;
					}else{
						for(String code: listCodes){
							anns+=code+" ";
						}
					}
					
					bw.write(i+";"+anns+";"+listUniqueUnitsStrict.get(i).getId()+"\n");
				}
				bw.write("\n");
				
				Double[][] matrixCoincidencesStrict	= new Double[listUniqueUnitsStrict.size()][listUniqueUnitsStrict.size()];
				Double[][] matrixWeightsStrict		= new Double[listUniqueUnitsStrict.size()][listUniqueUnitsStrict.size()];
				for(int i=0;i<listUniqueUnitsStrict.size();i++){
					IAAUnit rowI = listUniqueUnitsStrict.get(i);
					for(int j=0;j<listUniqueUnitsStrict.size();j++){
						IAAUnit columnI = listUniqueUnitsStrict.get(j);
						matrixWeightsStrict[i][j] = rowI.getQuadraticWeightStrict(columnI);
						double coincidences = 0;
						for(int h=0;h<data.get(0).size();h++){//For each column of result table
							int nRowI = 0;
							int nColumnI = 0;
							for(int k=0;k<data.size();k++){
								if(data.get(k).get(h).equalsStrict(rowI)) nRowI++;
								if(data.get(k).get(h).equalsStrict(columnI)){
									nColumnI++;
								}
							}
							
							double numerator = 0;
							if(rowI.equalsStrict(columnI)){
								numerator = (double) (nRowI * (nColumnI - 1)); 
							}else{
								numerator = (double) (nRowI * nColumnI);
							}
							coincidences+=(double)(numerator / ((double)(mu_values.get(h) - 1.0)));
						}
						matrixCoincidencesStrict[i][j] = coincidences;
					}
				}
				
				bw.write("\n\nResults table Strict\n");
				for(int k=0;k<data.size();k++){
					ArrayList<IAAUnit> listUnits = data.get(k);
					for(int i=0;i<listUnits.size();i++){
						IAAUnit unit = listUnits.get(i);
						for(int j=0;j<listUniqueUnitsStrict.size();j++){
							if(listUniqueUnitsStrict.get(j).equalsStrict(unit)){
								bw.write(j+";");
								break;
							}
						}
					}
					bw.write("\n");
				}		
				bw.write("\n");
				
				bw.write("LIST OF UNIQUE UNITS LOOSE\n");
				for(int i=0;i<listUniqueUnitsLoose.size();i++){
					String anns = "";
					HashSet<String> listCodes = listUniqueUnitsLoose.get(i).getListCodes();
					if(listCodes.isEmpty()){
						anns+="empty_"+language+"_"+i;
					}else{
						for(String code: listCodes){
							anns+=code+" ";
						}
					}
					
					bw.write(i+";"+anns+";"+listUniqueUnitsLoose.get(i).getId()+"\n");
				}
				bw.write("\n");
				
				Double[][] matrixCoincidencesLoose	= new Double[listUniqueUnitsLoose.size()][listUniqueUnitsLoose.size()];
				Double[][] matrixWeightsLoose		= new Double[listUniqueUnitsLoose.size()][listUniqueUnitsLoose.size()];
				for(int i=0;i<listUniqueUnitsLoose.size();i++){
					IAAUnit rowI = listUniqueUnitsLoose.get(i);
					for(int j=0;j<listUniqueUnitsLoose.size();j++){
						IAAUnit columnI = listUniqueUnitsLoose.get(j);
						matrixWeightsLoose[i][j] = rowI.getQuadraticWeightLoose(columnI);
						double coincidences = 0;
						for(int h=0;h<data.get(0).size();h++){//For each column of result table
							int nRowI = 0;
							int nColumnI = 0;
							for(int k=0;k<data.size();k++){
								if(data.get(k).get(h).equalsLoose(rowI)) nRowI++;
								if(data.get(k).get(h).equalsLoose(columnI)){
									nColumnI++;
								}
							}
							
							double numerator = 0;
							if(rowI.equalsStrict(columnI)){
								numerator = (double) (nRowI * (nColumnI - 1)); 
							}else{
								numerator = (double) (nRowI * nColumnI);
							}
							coincidences+=(double)(numerator / ((double)(mu_values.get(h) - 1.0)));
						}
						matrixCoincidencesLoose[i][j] = coincidences;
					}
				}
				
				bw.write("\n\nResults table Loose\n");
				for(int k=0;k<data.size();k++){
					ArrayList<IAAUnit> listUnits = data.get(k);
					for(int i=0;i<listUnits.size();i++){
						IAAUnit unit = listUnits.get(i);
						for(int j=0;j<listUniqueUnitsLoose.size();j++){
							if(listUniqueUnitsLoose.get(j).equalsLoose(unit)){
								bw.write(j+";");
								break;
							}
						}
					}
					bw.write("\n");
				}		
				bw.write("\n");
				bw.close();
				
				BufferedWriter bwS = new BufferedWriter(new FileWriter(output_folder+lang_folder+"/coincidences_ADP_S.txt",true));
				//bwS.write("\n\n"+language+"\n");
				//bw.write("\nSTRICT\n");
				for(int k=0;k<matrixCoincidencesStrict.length;k++){
					for(int m=0;m<matrixCoincidencesStrict[k].length;m++){
						if(m>0) bwS.write(",");
						String val = ""+matrixCoincidencesStrict[k][m].intValue();
						bwS.write(val);
					}
					bwS.write("\n");
				}
				bwS.close();
				BufferedWriter bwL = new BufferedWriter(new FileWriter(output_folder+lang_folder+"/coincidences_ADP_L.txt",true));
				//bw.write("\nLOOSE\n");
				for(int k=0;k<matrixCoincidencesLoose.length;k++){
					for(int m=0;m<matrixCoincidencesLoose[k].length;m++){
						if(m>0) bwL.write(",");
						String val = ""+matrixCoincidencesLoose[k][m].intValue();
						bwL.write(val);
					}
					bwL.write("\n");
				}
				bwL.close();
				
				bwS = new BufferedWriter(new FileWriter(output_folder+lang_folder+"/ratings_ADP_S.txt",true));
				//bw.write("\n\n"+language+"\n");
				//bw.write("\nSTRICT\n");
				for(int k=0;k<data.get(0).size();k++){
					for(int i=0;i<data.size();i++){
						IAAUnit unit = data.get(i).get(k);
						if(i>0) bwS.write(",");
						for(int j=0;j<listUniqueUnitsStrict.size();j++){
							if(listUniqueUnitsStrict.get(j).equalsStrict(unit)){
								bwS.write(j+"");
								break;
							}
						}
					}
					bwS.write("\n");
				}
				bwS.close();
				bwL = new BufferedWriter(new FileWriter(output_folder+lang_folder+"/ratings_ADP_L.txt",true));
				//bw.write("\nLOOSE\n");
				for(int k=0;k<data.get(0).size();k++){
					for(int i=0;i<data.size();i++){
						IAAUnit unit = data.get(i).get(k);
						if(i>0) bwL.write(",");
						for(int j=0;j<listUniqueUnitsLoose.size();j++){
							if(listUniqueUnitsLoose.get(j).equalsLoose(unit)){
								bwL.write(j+"");
								break;
							}
						}
					}
					bwL.write("\n");
				}
				bwL.close();
				
				
				bwS = new BufferedWriter(new FileWriter(output_folder+lang_folder+"/weights_ADP_S.txt",true));
				//bw.write("\n\n"+language+"\n");
				//bw.write("\nSTRICT\n");
				for(int i=0;i<matrixWeightsStrict.length;i++){
					for(int j=0;j<matrixWeightsStrict[i].length;j++){
						if(j>0) bwS.write(",");
						String val = ""+matrixWeightsStrict[i][j];
						val = val.replaceAll(",", ".");
						bwS.write(val);					
					}
					bwS.write("\n");
				}
				bwS.close();
				
				bwL = new BufferedWriter(new FileWriter(output_folder+lang_folder+"/weights_ADP_L.txt",true));
				//bw.write("\nLOOSE\n");
				for(int i=0;i<matrixWeightsLoose.length;i++){
					for(int j=0;j<matrixWeightsLoose[i].length;j++){
						if(j>0) bwL.write(",");
						String val = ""+matrixWeightsLoose[i][j];
						val = val.replaceAll(",", ".");
						bwL.write(val);					
					}
					bwL.write("\n");
				}
				bwL.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static void createRawDataAlt(HashMap<String,ArrayList<AnnotatorIAA>> listAnnotations, ArrayList<String> commonSnippetIds, String output_folder){
		
		//SCT Test all units are parallel to other annotators units.
		for(String language: listAnnotations.keySet()){

			String lang_folder="";
			switch (language) {
            case "EN":  lang_folder = "english/";
                     break;
            case "DE":  lang_folder = "german/";
                     break;
            case "FR":  lang_folder = "french/";
                     break;
            case "SE":  lang_folder = "swedish/";
                     break;
            case "NL":  lang_folder = "dutch/";
                     break;
            default: lang_folder = "";
                     break;
			}
			
			ArrayList<ArrayList<SentenceIAA>> rawData = new ArrayList<ArrayList<SentenceIAA>>();
			for(AnnotatorIAA annIaa: listAnnotations.get(language)){
				ArrayList<SentenceIAA> listSentences = new ArrayList<SentenceIAA>();
				for(SnippetIAA snippetIaa: annIaa.getListSnippets()){
					if(!commonSnippetIds.contains(snippetIaa.getId())) continue;
					for(SentenceIAA sentenceIaa: snippetIaa.getListSentences()){
						listSentences.add(sentenceIaa);
					}
				}
				rawData.add(listSentences);
			}
			
			//GET NON EMPTY ANNOTATIONS OF ANNOTATORS	
			ArrayList<Integer> mu_values = new ArrayList<Integer>();
			ArrayList<ArrayList<IAAUnit>> data = new ArrayList<ArrayList<IAAUnit>>();
			for(int i=0;i<rawData.size();i++) data.add(new ArrayList<IAAUnit>());
			for(int j=0;j<rawData.get(0).size();j++){
				int nNotEmpty = 0;
				for(int i=0;i<rawData.size();i++){
					if(!rawData.get(i).get(j).getAltUnit().isEmpty()) nNotEmpty++;
				}
				if(nNotEmpty>1){
					for(int i=0;i<rawData.size();i++){
						data.get(i).add(rawData.get(i).get(j).getAltUnit());
					}
					mu_values.add(nNotEmpty);
				}
			}
			
			//GET UNIQUE ANNOTATIONS
			ArrayList<IAAUnit> listUniqueUnitsStrict	= new ArrayList<IAAUnit>();
			ArrayList<IAAUnit> listUniqueUnitsLoose		= new ArrayList<IAAUnit>();
			for(int k=0; k< data.size(); k++){
				ArrayList<IAAUnit> listUnits = data.get(k);
				for(IAAUnit unitIaa: listUnits){
					boolean duplicated_strict = false;
					boolean duplicated_loose = false;
					for(IAAUnit unit: listUniqueUnitsStrict){
						if(!duplicated_strict && unit.equalsStrict(unitIaa)){
							duplicated_strict = true;
						}
						if(!duplicated_loose && unit.equalsLoose(unitIaa)){
							duplicated_loose = true;
						}
					}

					if(!duplicated_strict)	listUniqueUnitsStrict.add(unitIaa);
					if(!duplicated_loose)	listUniqueUnitsLoose.add(unitIaa);
				}
			}
			
			try{
				BufferedWriter bw = new BufferedWriter(new FileWriter(output_folder+"/IAA_ALTERNATIVE.csv",true));
				bw.write("\n\n"+language+"\n");
				bw.write("LIST OF UNIQUE UNITS STRICT\n");
				for(int i=0;i<listUniqueUnitsStrict.size();i++){
					String anns = "";
					HashSet<String> listCodes = listUniqueUnitsStrict.get(i).getListCodes();
					if(listCodes.isEmpty()){
						anns+="empty_"+language+"_"+i;
					}else{
						for(String code: listCodes){
							anns+=code+" ";
						}
					}
					
					bw.write(i+";"+anns+";"+listUniqueUnitsStrict.get(i).getId()+"\n");
				}
				bw.write("\n");
				
				Double[][] matrixCoincidencesStrict	= new Double[listUniqueUnitsStrict.size()][listUniqueUnitsStrict.size()];
				Double[][] matrixWeightsStrict		= new Double[listUniqueUnitsStrict.size()][listUniqueUnitsStrict.size()];
				for(int i=0;i<listUniqueUnitsStrict.size();i++){
					IAAUnit rowI = listUniqueUnitsStrict.get(i);
					for(int j=0;j<listUniqueUnitsStrict.size();j++){
						IAAUnit columnI = listUniqueUnitsStrict.get(j);
						matrixWeightsStrict[i][j] = rowI.getQuadraticWeightStrict(columnI);
						double coincidences = 0;
						for(int h=0;h<data.get(0).size();h++){//For each column of result table
							int nRowI = 0;
							int nColumnI = 0;
							for(int k=0;k<data.size();k++){
								if(data.get(k).get(h).equalsStrict(rowI)) nRowI++;
								if(data.get(k).get(h).equalsStrict(columnI)){
									nColumnI++;
								}
							}
							
							double numerator = 0;
							if(rowI.equalsStrict(columnI)){
								numerator = (double) (nRowI * (nColumnI - 1)); 
							}else{
								numerator = (double) (nRowI * nColumnI);
							}
							coincidences+=(double)(numerator / ((double)(mu_values.get(h) - 1.0)));
						}
						matrixCoincidencesStrict[i][j] = coincidences;
					}
				}
				
				bw.write("\n\nResults table Strict\n");
				for(int k=0;k<data.size();k++){
					ArrayList<IAAUnit> listUnits = data.get(k);
					for(int i=0;i<listUnits.size();i++){
						IAAUnit unit = listUnits.get(i);
						for(int j=0;j<listUniqueUnitsStrict.size();j++){
							if(listUniqueUnitsStrict.get(j).equalsStrict(unit)){
								bw.write(j+";");
								break;
							}
						}
					}
					bw.write("\n");
				}		
				bw.write("\n");
				
				bw.write("LIST OF UNIQUE UNITS LOOSE\n");
				for(int i=0;i<listUniqueUnitsLoose.size();i++){
					String anns = "";
					HashSet<String> listCodes = listUniqueUnitsLoose.get(i).getListCodes();
					if(listCodes.isEmpty()){
						anns+="empty_"+language+"_"+i;
					}else{
						for(String code: listCodes){
							anns+=code+" ";
						}
					}
					
					bw.write(i+";"+anns+";"+listUniqueUnitsLoose.get(i).getId()+"\n");
				}
				bw.write("\n");
				
				Double[][] matrixCoincidencesLoose	= new Double[listUniqueUnitsLoose.size()][listUniqueUnitsLoose.size()];
				Double[][] matrixWeightsLoose		= new Double[listUniqueUnitsLoose.size()][listUniqueUnitsLoose.size()];
				for(int i=0;i<listUniqueUnitsLoose.size();i++){
					IAAUnit rowI = listUniqueUnitsLoose.get(i);
					for(int j=0;j<listUniqueUnitsLoose.size();j++){
						IAAUnit columnI = listUniqueUnitsLoose.get(j);
						matrixWeightsLoose[i][j] = rowI.getQuadraticWeightLoose(columnI);
						double coincidences = 0;
						for(int h=0;h<data.get(0).size();h++){//For each column of result table
							int nRowI = 0;
							int nColumnI = 0;
							for(int k=0;k<data.size();k++){
								if(data.get(k).get(h).equalsLoose(rowI)) nRowI++;
								if(data.get(k).get(h).equalsLoose(columnI)){
									nColumnI++;
								}
							}
							
							double numerator = 0;
							if(rowI.equalsStrict(columnI)){
								numerator = (double) (nRowI * (nColumnI - 1)); 
							}else{
								numerator = (double) (nRowI * nColumnI);
							}
							coincidences+=(double)(numerator / ((double)(mu_values.get(h) - 1.0)));
						}
						matrixCoincidencesLoose[i][j] = coincidences;
					}
				}
				
				bw.write("\n\nResults table Loose\n");
				for(int k=0;k<data.size();k++){
					ArrayList<IAAUnit> listUnits = data.get(k);
					for(int i=0;i<listUnits.size();i++){
						IAAUnit unit = listUnits.get(i);
						for(int j=0;j<listUniqueUnitsLoose.size();j++){
							if(listUniqueUnitsLoose.get(j).equalsLoose(unit)){
								bw.write(j+";");
								break;
							}
						}
					}
					bw.write("\n");
				}		
				bw.write("\n");
				bw.close();

				BufferedWriter bwS = new BufferedWriter(new FileWriter(output_folder+lang_folder+"/coincidences_ALT_S.txt",true));
				//bw.write("\n\n"+language+"\n");
				//bw.write("\nSTRICT\n");
				for(int k=0;k<matrixCoincidencesStrict.length;k++){
					for(int m=0;m<matrixCoincidencesStrict[k].length;m++){
						if(m>0) bwS.write(",");
						String val = ""+matrixCoincidencesStrict[k][m].intValue();
						bwS.write(val);
					}
					bwS.write("\n");
				}
				bwS.close();
				
				BufferedWriter bwL = new BufferedWriter(new FileWriter(output_folder+lang_folder+"/coincidences_ALT_L.txt",true));
				//bw.write("\nLOOSE\n");
				for(int k=0;k<matrixCoincidencesLoose.length;k++){
					for(int m=0;m<matrixCoincidencesLoose[k].length;m++){
						if(m>0) bwL.write(",");
						String val = ""+matrixCoincidencesLoose[k][m].intValue();
						bwL.write(val);
					}
					bwL.write("\n");
				}
				bwL.close();
				
				bwS = new BufferedWriter(new FileWriter(output_folder+lang_folder+"/ratings_ALT_S.txt",true));
				//bw.write("\n\n"+language+"\n");
				//bw.write("\nSTRICT\n");
				for(int k=0;k<data.get(0).size();k++){
					for(int i=0;i<data.size();i++){
						IAAUnit unit = data.get(i).get(k);
						if(i>0) bwS.write(",");
						for(int j=0;j<listUniqueUnitsStrict.size();j++){
							if(listUniqueUnitsStrict.get(j).equalsStrict(unit)){
								bwS.write(j+"");
								break;
							}
						}
					}
					bwS.write("\n");
				}
				bwS.close();
				
				bwL = new BufferedWriter(new FileWriter(output_folder+lang_folder+"/ratings_ALT_L.txt",true));
				//bw.write("\nLOOSE\n");
				for(int k=0;k<data.get(0).size();k++){
					for(int i=0;i<data.size();i++){
						IAAUnit unit = data.get(i).get(k);
						if(i>0) bwL.write(",");
						for(int j=0;j<listUniqueUnitsLoose.size();j++){
							if(listUniqueUnitsLoose.get(j).equalsLoose(unit)){
								bwL.write(j+"");
								break;
							}
						}
					}
					bwL.write("\n");
				}
				bwL.close();
				
				
				bwS = new BufferedWriter(new FileWriter(output_folder+lang_folder+"/weights_ALT_S.csv",true));
				//bw.write("\n\n"+language+"\n");
				//bw.write("\nSTRICT\n");
				for(int i=0;i<matrixWeightsStrict.length;i++){
					for(int j=0;j<matrixWeightsStrict[i].length;j++){
						if(j>0) bwS.write(",");
						String val = ""+matrixWeightsStrict[i][j];
						val = val.replaceAll(",", ".");
						bwS.write(val);					
					}
					bwS.write("\n");
				}
				bwS.close();
				
				bwL = new BufferedWriter(new FileWriter(output_folder+lang_folder+"/weights_ALT_L.csv",true));
				//bw.write("\nLOOSE\n");
				for(int i=0;i<matrixWeightsLoose.length;i++){
					for(int j=0;j<matrixWeightsLoose[i].length;j++){
						if(j>0) bwL.write(",");
						String val = ""+matrixWeightsLoose[i][j];
						val = val.replaceAll(",", ".");
						bwL.write(val);					
					}
					bwL.write("\n");
				}
				bwL.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static void createRawDataAbs(HashMap<String,ArrayList<AnnotatorIAA>> listAnnotations, ArrayList<String> commonSnippetIds, String output_folder){
		
		//SCT Test all units are parallel to other annotators units.
		for(String language: listAnnotations.keySet()){
			String lang_folder="";
			switch (language) {
            case "EN":  lang_folder = "english/";
                     break;
            case "DE":  lang_folder = "german/";
                     break;
            case "FR":  lang_folder = "french/";
                     break;
            case "SE":  lang_folder = "swedish/";
                     break;
            case "NL":  lang_folder = "dutch/";
                     break;
            default: lang_folder = "";
                     break;
			}

			ArrayList<ArrayList<SentenceIAA>> rawData = new ArrayList<ArrayList<SentenceIAA>>();
			for(AnnotatorIAA annIaa: listAnnotations.get(language)){
				ArrayList<SentenceIAA> listSentences = new ArrayList<SentenceIAA>();
				for(SnippetIAA snippetIaa: annIaa.getListSnippets()){
					if(!commonSnippetIds.contains(snippetIaa.getId())) continue;
					for(SentenceIAA sentenceIaa: snippetIaa.getListSentences()){
						listSentences.add(sentenceIaa);
					}
				}
				rawData.add(listSentences);
			}
			
			//GET NON EMPTY ANNOTATIONS OF ANNOTATORS	
			ArrayList<Integer> mu_values = new ArrayList<Integer>();
			ArrayList<ArrayList<IAAUnit>> data = new ArrayList<ArrayList<IAAUnit>>();
			for(int i=0;i<rawData.size();i++) data.add(new ArrayList<IAAUnit>());
			for(int j=0;j<rawData.get(0).size();j++){
				int nNotEmpty = 0;
				for(int i=0;i<rawData.size();i++){
					if(!rawData.get(i).get(j).getAbsUnit().isEmpty()) nNotEmpty++;
				}
				if(nNotEmpty>1){
					for(int i=0;i<rawData.size();i++){
						data.get(i).add(rawData.get(i).get(j).getAbsUnit());
					}
					mu_values.add(nNotEmpty);
				}
			}
			
			//GET UNIQUE ANNOTATIONS
			ArrayList<IAAUnit> listUniqueUnitsStrict	= new ArrayList<IAAUnit>();
			ArrayList<IAAUnit> listUniqueUnitsLoose		= new ArrayList<IAAUnit>();
			for(int k=0; k< data.size(); k++){
				ArrayList<IAAUnit> listUnits = data.get(k);
				for(IAAUnit unitIaa: listUnits){
					boolean duplicated_strict = false;
					boolean duplicated_loose = false;
					for(IAAUnit unit: listUniqueUnitsStrict){
						if(!duplicated_strict && unit.equalsStrict(unitIaa)){
							duplicated_strict = true;
						}
						if(!duplicated_loose && unit.equalsLoose(unitIaa)){
							duplicated_loose = true;
						}
					}
					
					if(!duplicated_strict)	listUniqueUnitsStrict.add(unitIaa);
					if(!duplicated_loose)	listUniqueUnitsLoose.add(unitIaa);
				}
			}
			
			try{
				BufferedWriter bw = new BufferedWriter(new FileWriter(output_folder+"/IAA_ABSTAIN.csv",true));
				bw.write("\n\n"+language+"\n");
				bw.write("LIST OF UNIQUE UNITS STRICT\n");
				for(int i=0;i<listUniqueUnitsStrict.size();i++){
					String anns = "";
					HashSet<String> listCodes = listUniqueUnitsStrict.get(i).getListCodes();
					if(listCodes.isEmpty()){
						anns+="empty_"+language+"_"+i;
					}else{
						for(String code: listCodes){
							anns+=code+" ";
						}
					}
					
					bw.write(i+";"+anns+";"+listUniqueUnitsStrict.get(i).getId()+"\n");
				}
				bw.write("\n");
				
				Double[][] matrixCoincidencesStrict	= new Double[listUniqueUnitsStrict.size()][listUniqueUnitsStrict.size()];
				Double[][] matrixWeightsStrict		= new Double[listUniqueUnitsStrict.size()][listUniqueUnitsStrict.size()];
				for(int i=0;i<listUniqueUnitsStrict.size();i++){
					IAAUnit rowI = listUniqueUnitsStrict.get(i);
					for(int j=0;j<listUniqueUnitsStrict.size();j++){
						IAAUnit columnI = listUniqueUnitsStrict.get(j);
						matrixWeightsStrict[i][j] = rowI.getQuadraticWeightStrict(columnI);
						double coincidences = 0;
						for(int h=0;h<data.get(0).size();h++){//For each column of result table
							int nRowI = 0;
							int nColumnI = 0;
							for(int k=0;k<data.size();k++){
								if(data.get(k).get(h).equalsStrict(rowI)) nRowI++;
								if(data.get(k).get(h).equalsStrict(columnI)){
									nColumnI++;
								}
							}
							
							double numerator = 0;
							if(rowI.equalsStrict(columnI)){
								numerator = (double) (nRowI * (nColumnI - 1)); 
							}else{
								numerator = (double) (nRowI * nColumnI);
							}
							coincidences+=(double)(numerator / ((double)(mu_values.get(h) - 1.0)));
						}
						matrixCoincidencesStrict[i][j] = coincidences;
					}
				}
				
				bw.write("\n\nResults table Strict\n");
				for(int k=0;k<data.size();k++){
					ArrayList<IAAUnit> listUnits = data.get(k);
					for(int i=0;i<listUnits.size();i++){
						IAAUnit unit = listUnits.get(i);
						for(int j=0;j<listUniqueUnitsStrict.size();j++){
							if(listUniqueUnitsStrict.get(j).equalsStrict(unit)){
								bw.write(j+";");
								break;
							}
						}
					}
					bw.write("\n");
				}		
				bw.write("\n");
				
				bw.write("LIST OF UNIQUE UNITS LOOSE\n");
				for(int i=0;i<listUniqueUnitsLoose.size();i++){
					String anns = "";
					HashSet<String> listCodes = listUniqueUnitsLoose.get(i).getListCodes();
					if(listCodes.isEmpty()){
						anns+="empty_"+language+"_"+i;
					}else{
						for(String code: listCodes){
							anns+=code+" ";
						}
					}
					
					bw.write(i+";"+anns+";"+listUniqueUnitsLoose.get(i).getId()+"\n");
				}
				bw.write("\n");
				
				Double[][] matrixCoincidencesLoose	= new Double[listUniqueUnitsLoose.size()][listUniqueUnitsLoose.size()];
				Double[][] matrixWeightsLoose		= new Double[listUniqueUnitsLoose.size()][listUniqueUnitsLoose.size()];
				for(int i=0;i<listUniqueUnitsLoose.size();i++){
					IAAUnit rowI = listUniqueUnitsLoose.get(i);
					for(int j=0;j<listUniqueUnitsLoose.size();j++){
						IAAUnit columnI = listUniqueUnitsLoose.get(j);
						matrixWeightsLoose[i][j] = rowI.getQuadraticWeightLoose(columnI);
						double coincidences = 0;
						for(int h=0;h<data.get(0).size();h++){//For each column of result table
							int nRowI = 0;
							int nColumnI = 0;
							for(int k=0;k<data.size();k++){
								if(data.get(k).get(h).equalsLoose(rowI)) nRowI++;
								if(data.get(k).get(h).equalsLoose(columnI)){
									nColumnI++;
								}
							}
							
							double numerator = 0;
							if(rowI.equalsStrict(columnI)){
								numerator = (double) (nRowI * (nColumnI - 1)); 
							}else{
								numerator = (double) (nRowI * nColumnI);
							}
							coincidences+=(double)(numerator / ((double)(mu_values.get(h) - 1.0)));
						}
						matrixCoincidencesLoose[i][j] = coincidences;
					}
				}
				
				bw.write("\n\nResults table Loose\n");
				for(int k=0;k<data.size();k++){
					ArrayList<IAAUnit> listUnits = data.get(k);
					for(int i=0;i<listUnits.size();i++){
						IAAUnit unit = listUnits.get(i);
						for(int j=0;j<listUniqueUnitsLoose.size();j++){
							if(listUniqueUnitsLoose.get(j).equalsLoose(unit)){
								bw.write(j+";");
								break;
							}
						}
					}
					bw.write("\n");
				}		
				bw.write("\n");
				bw.close();

				BufferedWriter bwS = new BufferedWriter(new FileWriter(output_folder+lang_folder+"/coincidences_ABS_S.txt",true));
				//bw.write("\n\n"+language+"\n");
				//bw.write("\nSTRICT\n");
				for(int k=0;k<matrixCoincidencesStrict.length;k++){
					for(int m=0;m<matrixCoincidencesStrict[k].length;m++){
						if(m>0) bwS.write(",");
						String val = ""+matrixCoincidencesStrict[k][m].intValue();
						bwS.write(val);
					}
					bwS.write("\n");
				}
				bwS.close();
				
				BufferedWriter bwL = new BufferedWriter(new FileWriter(output_folder+lang_folder+"/coincidences_ABS_L.txt",true));
				//bw.write("\nLOOSE\n");
				for(int k=0;k<matrixCoincidencesLoose.length;k++){
					for(int m=0;m<matrixCoincidencesLoose[k].length;m++){
						if(m>0) bwL.write(",");
						String val = ""+matrixCoincidencesLoose[k][m].intValue();
						bwL.write(val);
					}
					bwL.write("\n");
				}
				bwL.close();
				
				bwS = new BufferedWriter(new FileWriter(output_folder+lang_folder+"/ratings_ABS_S.txt",true));
				//bw.write("\n\n"+language+"\n");
				//bw.write("\nSTRICT\n");
				for(int k=0;k<data.get(0).size();k++){
					for(int i=0;i<data.size();i++){
						IAAUnit unit = data.get(i).get(k);
						if(i>0) bwS.write(",");
						for(int j=0;j<listUniqueUnitsStrict.size();j++){
							if(listUniqueUnitsStrict.get(j).equalsStrict(unit)){
								bwS.write(j+"");
								break;
							}
						}
					}
					bwS.write("\n");
				}
				bwS.close();
				
				bwL = new BufferedWriter(new FileWriter(output_folder+lang_folder+"/ratings_ABS_L.txt",true));
				//bw.write("\nLOOSE\n");
				for(int k=0;k<data.get(0).size();k++){
					for(int i=0;i<data.size();i++){
						IAAUnit unit = data.get(i).get(k);
						if(i>0) bwL.write(",");
						for(int j=0;j<listUniqueUnitsLoose.size();j++){
							if(listUniqueUnitsLoose.get(j).equalsLoose(unit)){
								bwL.write(j+"");
								break;
							}
						}
					}
					bwL.write("\n");
				}
				bwL.close();
				
				bwS = new BufferedWriter(new FileWriter(output_folder+lang_folder+"/weights_ABS_S.csv",true));
				//bw.write("\n\n"+language+"\n");
				//bw.write("\nSTRICT\n");
				for(int i=0;i<matrixWeightsStrict.length;i++){
					for(int j=0;j<matrixWeightsStrict[i].length;j++){
						if(j>0) bwS.write(",");
						String val = ""+matrixWeightsStrict[i][j];
						val = val.replaceAll(",", ".");
						bwS.write(val);					
					}
					bwS.write("\n");
				}
				bwS.close();
				
				bwL = new BufferedWriter(new FileWriter(output_folder+lang_folder+"/weights_ABS_L.csv",true));
				//bw.write("\nLOOSE\n");
				for(int i=0;i<matrixWeightsLoose.length;i++){
					for(int j=0;j<matrixWeightsLoose[i].length;j++){
						if(j>0) bwL.write(",");
						String val = ""+matrixWeightsLoose[i][j];
						val = val.replaceAll(",", ".");
						bwL.write(val);					
					}
					bwL.write("\n");
				}
				
				bwL.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
