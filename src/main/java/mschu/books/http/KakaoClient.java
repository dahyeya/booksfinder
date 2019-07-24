package mschu.books.http;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import mschu.books.dto.KakaoSearchOptions;
import mschu.books.error.RequestFailedException;
import mschu.books.error.ResponseParseException;

public class KakaoClient {
	private final String SERACH_BOOK_PATH = "https://dapi.kakao.com/v3/search/book";
	private String APP_KEY;
	
	private KakaoClient () {}
	private static class Singleton {
		private static final KakaoClient instance = new KakaoClient();
	}
	
	public void initialize(String appKey) {
		this.APP_KEY = String.format("KakaoAK %s", appKey);
	}
	
	public static KakaoClient getInstance () {
		return Singleton.instance;
	}
	
	public ClientResponse searchBook(String query) throws RequestFailedException, ResponseParseException{
		KakaoSearchOptions options = new KakaoSearchOptions();
		options.setQuery(query);
		return this.searchBook(options);	
	}
	
	public ClientResponse searchBook(KakaoSearchOptions options) throws RequestFailedException, ResponseParseException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpGet httpGet = new HttpGet(SERACH_BOOK_PATH+options.toString());
		httpGet.setHeader("Authorization", this.APP_KEY);
		
		ClientResponse response = new ClientResponse();

		try {
			response.parse(httpClient.execute(httpGet));
		} catch (ClientProtocolException e) {
			throw new RequestFailedException(e.getMessage());
		} catch (IOException e) {
			throw new RequestFailedException(e.getMessage());
		} 
		
		return response;
	}
}
