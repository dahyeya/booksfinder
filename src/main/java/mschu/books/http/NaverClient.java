package mschu.books.http;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import mschu.books.dto.NaverSearchOptions;
import mschu.books.error.RequestFailedException;
import mschu.books.error.ResponseParseException;

public class NaverClient {
	private final String SERACH_BOOK_PATH = "https://openapi.naver.com/v1/search/book.json";
	private String CLIENT_ID;
	private String CLIENT_SECRET;
	
	private NaverClient () {}
	private static class Singleton {
		private static final NaverClient instance = new NaverClient();
	}
	
	public void initialize(String clientId, String clientSecret) {
		this.CLIENT_ID = clientId;
		this.CLIENT_SECRET = clientSecret;
	}
	
	public static NaverClient getInstance () {
		return Singleton.instance;
	}
	
	public ClientResponse searchBook(String query) throws RequestFailedException, ResponseParseException{
		NaverSearchOptions options = new NaverSearchOptions();
		options.setQuery(query);
		return this.searchBook(options);	
	}
	
	public ClientResponse searchBook(NaverSearchOptions options) throws RequestFailedException, ResponseParseException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet(SERACH_BOOK_PATH+options.toString());
		httpGet.addHeader("X-Naver-Client-Id", this.CLIENT_ID);
		httpGet.addHeader("X-Naver-Client-Secret", this.CLIENT_SECRET);
		
		
		try {
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

			String data = EntityUtils.toString(httpResponse.getEntity());
			String convertData = searchBookResultForKakao(data);
			
			ClientResponse response = new ClientResponse();
			response.setBody(convertData);
			response.setStatus(httpResponse.getStatusLine().getStatusCode());
			
			return response;

		} catch (ClientProtocolException e) {
			throw new RequestFailedException(e.getMessage());
		}catch (ParseException e) {
			throw new ResponseParseException(e.getMessage());
		} catch (IOException e) {
			throw new ResponseParseException(e.getMessage());
		}
		
		
		
//		ClientResponse response = new ClientResponse();
//
//		
//		try {
//			response.parse(httpClient.execute(httpGet));
//		} catch (ClientProtocolException e) {
//			throw new RequestFailedException(e.getMessage());
//		} catch (IOException e) {
//			throw new RequestFailedException(e.getMessage());
//		} 
		
	}
	public String searchBookResultForKakao(String data) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(data);
		
		ObjectNode convertRootNode = mapper.createObjectNode();
		ObjectNode metaNode = mapper.createObjectNode();
		ArrayNode documentNode = mapper.createArrayNode();
		
		metaNode.put("pageable_count", Math.round(rootNode.get("total").asInt()/10));
		metaNode.put("total_count", Math.round(rootNode.get("total").asInt()/10));
		metaNode.put("is_end", (rootNode.get("total").asInt() - rootNode.get("start").asInt()) > 10 ? false : true);
		
		for (JsonNode item : rootNode.withArray("items")) {
			ObjectNode itemNode = mapper.createObjectNode();
			
			String date = item.get("pubdate").asText();
			String year = date.substring(0, 4);
			String month = date.substring(4, 6);
			String day = date.substring(6,8);
			
			itemNode.putArray("authors").add(item.get("author").asText());
			itemNode.put("contents", item.get("description").asText().replaceAll("<b>","").replaceAll("</b>",""));
			itemNode.put("datetime", year+"-"+month+"-"+day);
			itemNode.put("isbn", item.get("isbn").asText());
			itemNode.put("price", item.get("price").asInt());
			itemNode.put("publisher", item.get("publisher").asText());
			itemNode.put("sale_price", item.get("discount").asInt());
			itemNode.put("status", "정상판매");
			itemNode.put("thumbnail", item.get("image").asText());
			itemNode.put("title", item.get("title").asText().replaceAll("<b>","").replaceAll("</b>",""));
			itemNode.putArray("translators");
			itemNode.put("url", item.get("link").asText());
			
			documentNode.add(itemNode);
		}
		convertRootNode.putPOJO("meta", metaNode);
		convertRootNode.putPOJO("documents", documentNode);
		
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(convertRootNode);	
	}
}