package parser_annotations;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;


public class ProcessTableResults {
	private String file;
	private HashMap<String,ArrayList<Annotator>> annotations;
	
	public ProcessTableResults(String file){
		this.file = file;
		annotations = new HashMap<String,ArrayList<Annotator>>();
		initialize();
	}
	
	private void initialize(){
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line			= "";
			String language		= "";
			int position = 0;
			Annotator currentAnnotator	= null;
			Snippet currentSnippet		= null;
			Sentence currentSentence	= null;
			Chunk currentChunk			= null;
			
			br.readLine();
			while((line = br.readLine()) != null){
				String[] columns = line.split(";",-1);
				String value_language	= columns[0];
				String value_annotator	= columns[1];
				String value_snippet	= columns[2];
				String value_sentence	= columns[3];
				String value_chunk		= columns[5];
				
				if(!language.equals(value_language)){
					if(currentAnnotator!=null){
						currentAnnotator.getListOfSnippets().get(currentAnnotator.getListOfSnippets().size()-1).setNumberOfLines(position);
						annotations.get(language).add(currentAnnotator);
					}
					language = value_language;
					annotations.put(language, new ArrayList<Annotator>());
					currentAnnotator	= null;
					currentSnippet		= null;
					currentSentence		= null;
					currentChunk		= null;
					
					position = 0;
				}
				
				if(currentAnnotator == null || !currentAnnotator.getId().equals(value_annotator)){
					if(currentAnnotator!=null){
						currentSnippet.setNumberOfLines(position);
						annotations.get(language).add(currentAnnotator);
						currentAnnotator	= null;
						currentSnippet		= null;
						currentSentence		= null;
						currentChunk		= null;
					}
					currentAnnotator = new Annotator(value_annotator,language);
					position = 0;
				}
				
				if(currentSnippet == null || !currentSnippet.getId().equals(value_snippet)){
					if(currentSnippet != null){
						currentSnippet.setNumberOfLines(position);
					}
					
					currentSnippet = new Snippet(value_snippet,value_snippet.substring(0,2));
					currentAnnotator.addSnippet(currentSnippet);
					position = 0;
				}
				
				position++;
				
				if(currentSentence == null || !currentSentence.getId().equals(value_sentence)){
					currentSentence = new Sentence(value_sentence);
					currentSnippet.addSentence(currentSentence);
				}
				if(value_chunk.isEmpty())continue;
				if(currentChunk == null || !currentChunk.getId().equals(value_chunk)){
					currentChunk = new Chunk(value_chunk);
					currentSentence.addChunk(currentChunk);
				}
				
				Token token = new Token(columns[4],position);
				currentChunk.addToken(token);
				
				//ADOPT
				String codes			= columns[6];
				String conceptCoverage	= columns[7];
				String termCoverage		= columns[8];
				if(!conceptCoverage.isEmpty()){
					if(Integer.parseInt(conceptCoverage)<3 && codes.isEmpty()) System.out.println("ADP="+currentAnnotator.getId()+"\t"+currentSnippet.getId()+"\t"+currentChunk.getId()+"\t"+token.getToken());
					if(codes.startsWith("C"))System.out.println("ADP="+currentAnnotator.getId()+"\t"+currentSnippet.getId()+"\t"+currentChunk.getId()+"\t"+token.getToken());
					AnnotationToken ann = new AnnotationToken(codes, Integer.parseInt(conceptCoverage), termCoverage);
					token.setAdoptAnnotations(ann);
				}
				
				//ALTERNATIVE
				codes			= columns[9];
				conceptCoverage	= columns[10];
				termCoverage	= columns[11];
				if(!conceptCoverage.isEmpty()){
					if(Integer.parseInt(conceptCoverage)<3 && codes.isEmpty()) System.out.println("ALT="+currentAnnotator.getId()+"\t"+currentSnippet.getId()+"\t"+currentChunk.getId()+"\t"+token.getToken());
					
					AnnotationToken ann = new AnnotationToken(codes, Integer.parseInt(conceptCoverage), termCoverage);
					token.setAlternativeAnnotations(ann);
				}
				
				//ABSTAIN
				codes			= columns[12];
				conceptCoverage	= columns[13];
				termCoverage	= columns[14];
				if(!conceptCoverage.isEmpty()){
					if(Integer.parseInt(conceptCoverage)<3 && codes.isEmpty()) System.out.println("ABS="+currentAnnotator.getId()+"\t"+currentSnippet.getId()+"\t"+currentChunk.getId()+"\t"+token.getToken());
					
					AnnotationToken ann = new AnnotationToken(codes, Integer.parseInt(conceptCoverage), termCoverage);
					token.setAbstainAnnotations(ann);
				}
			}
			
			if(currentAnnotator!=null){
				currentAnnotator.getListOfSnippets().get(currentAnnotator.getListOfSnippets().size()-1).setNumberOfLines(position);
				annotations.get(language).add(currentAnnotator);
			}
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public HashMap<String,ArrayList<Annotator>> getListAnnotations(){
		return annotations;
	}
}
