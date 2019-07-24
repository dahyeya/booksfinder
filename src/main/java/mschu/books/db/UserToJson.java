package mschu.books.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class UserToJson {
	public String parseString(User user) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();
		
		rootNode.put("account", user.getAccount());
		rootNode.put("id", user.getId());
		
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);	
	}
	
}
