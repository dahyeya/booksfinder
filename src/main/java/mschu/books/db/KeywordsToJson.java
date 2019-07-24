package mschu.books.db;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class KeywordsToJson {
	public String parseArrayString(List<Keywords> keywordList) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode rootNode = mapper.createArrayNode();
		
		int count = 0;
		for(Keywords keyword : keywordList) {
			if(count==10) {
				break;
			}
			ObjectNode item = mapper.createObjectNode();
		
			item.put("keyword", keyword.getIdentity().getKeyword());
			item.put("issue", keyword.getIssue().getTime());
			item.put("search", keyword.getSearch());
			rootNode.add(item);
			count++;
		}
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);	
	}
}
