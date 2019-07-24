package mschu.books.router;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;

import mschu.books.db.Keywords;
import mschu.books.db.KeywordsIdentity;
import mschu.books.db.KeywordsRepository;
import mschu.books.db.KeywordsToJson;
import mschu.books.db.Top10;
import mschu.books.db.Top10Repository;
import mschu.books.db.Top10ToJson;
import mschu.books.dto.KakaoSearchOptions;
import mschu.books.dto.NaverSearchOptions;
import mschu.books.error.RequestFailedException;
import mschu.books.error.ResponseParseException;
import mschu.books.http.ClientResponse;
import mschu.books.http.KakaoClient;
import mschu.books.http.NaverClient;

@Controller
@RequestMapping("/api")
public class KakaoController {
	private final KakaoClient kakaoClient = KakaoClient.getInstance();
	private final NaverClient naverClient = NaverClient.getInstance();
	
	@Autowired
	private KeywordsRepository keywordsRepository;
	
	@Autowired
	private Top10Repository top10Repository;

	@RequestMapping(value = "/books", method=RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> search(
			HttpServletRequest request,
			@RequestParam(value="query", required=true) String query, 
			@RequestParam(value="sort", required=false) String sort, 
			@RequestParam(value="page", required=false) String page, 
			@RequestParam(value="size", required=false) String size,
			@RequestParam(value="target", required=false) String target) throws RequestFailedException, ResponseParseException{
		
		HttpSession session = request.getSession();
		int id = Integer.parseInt(session.getAttribute("id").toString());
		
		KakaoSearchOptions options = new KakaoSearchOptions();
		options.setQuery(query);
		options.setSort(sort);
		options.setPage(page);
		options.setSize(size);
		options.setTarget(target);
		
		Keywords keyword = new Keywords();
		KeywordsIdentity identity = new KeywordsIdentity();
		
		identity.setId(id);
		identity.setKeyword(query);
		
		keyword.setIdentity(identity);
		keyword.setIssue(new Date());
		
		if(keywordsRepository.existsById(identity)) {
			int count = keywordsRepository.findById(identity).get().getSearch();
			keyword.setSearch(count+1);
		}else {
			keyword.setSearch(1);
		}
		keywordsRepository.save(keyword);
		
		Top10 top10 = new Top10();
		top10.setKeyword(query);
		if(top10Repository.existsById(query)) {
			int count = top10Repository.findById(query).get().getSearch();
			top10.setSearch(count+1);
		}else {
			top10.setSearch(1);
		}
		top10Repository.save(top10);
		
		ClientResponse response = kakaoClient.searchBook(options);
		if(response.getStatus()!=HttpStatus.OK.value()) {
			response = naverClient.searchBook(new NaverSearchOptions(options));
		}
		return response.toResponseEntity();
	}
	
	@RequestMapping(value = "/books/history", method=RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> history(HttpServletRequest request) throws JsonProcessingException {
		HttpSession session = request.getSession();
		int id = Integer.parseInt(session.getAttribute("id").toString());
		
		List<Keywords> data = keywordsRepository.findViewKeywordHistory(id);
			
		String dataJson = new KeywordsToJson().parseArrayString(data);
				
		return new ResponseEntity<>(dataJson, HttpStatus.OK);
		
	}
	@RequestMapping(value = "/books/top10", method=RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> top10() throws JsonProcessingException {
		
		List<Top10> data = top10Repository.findAllSortTop10();
		
		String dataJson = new Top10ToJson().parseArrayString(data);
		
		return new ResponseEntity<>(dataJson, HttpStatus.OK);
	}
}
