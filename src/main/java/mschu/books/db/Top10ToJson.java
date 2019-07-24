package mschu.books.db;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Top10ToJson {

	public String parseString(Top10 top10) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();
		
		rootNode.put("keyword", top10.getKeyword());
		rootNode.put("search", top10.getSearch());
		
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);	

	};
	public String parseArrayString(List<Top10> top10List) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode rootNode = mapper.createArrayNode();
		
		int count = 0;
		for(Top10 top10 : top10List) {
			if(count==10) {
				break;
			}
			ObjectNode item = mapper.createObjectNode();
		
			item.put("keyword", top10.getKeyword());
			item.put("search", top10.getSearch());
			rootNode.add(item);
			count++;
		}
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);	
	}
}
