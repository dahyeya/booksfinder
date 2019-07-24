package mschu.books;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import mschu.books.http.KakaoClient;
import mschu.books.http.NaverClient;

@ComponentScan("mschu.books")
@SpringBootApplication
public class BooksFinderApplication {
		
	public static void main(String[] args) throws ClientProtocolException, IOException {
		SpringApplication.run(BooksFinderApplication.class, args);
		
		KakaoClient kakaoClient = KakaoClient.getInstance();
		kakaoClient.initialize("2e5cc5768a484345ade391cd2a9d8ee6");
	
		NaverClient naverClient = NaverClient.getInstance();
		naverClient.initialize("zp5zVT_Fnz4z4XozwKqR", "hx4u98Pnb9");
		
	}
}
