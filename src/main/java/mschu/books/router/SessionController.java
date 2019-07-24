package mschu.books.router;

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

import mschu.books.db.User;
import mschu.books.db.UserRepository;
import mschu.books.db.UserToJson;
import mschu.books.error.UnauthroizeException;

@Controller
@RequestMapping("/api")
public class SessionController {

	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value = "/session", method = RequestMethod.POST, produces = MimeTypeUtils.APPLICATION_JSON_VALUE) 
	public ResponseEntity<?> login(
			HttpServletRequest request, 
			@RequestParam("account") String account,
			@RequestParam("password") String password) throws JsonProcessingException, UnauthroizeException {
				
		User data = userRepository.findByValidUser(account, password);
		if(data==null) {
			throw new UnauthroizeException();
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("id", data.getId());
		
		String dataJson = new UserToJson().parseString(data);
	
		return new ResponseEntity<>(dataJson, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/session", method = RequestMethod.DELETE) 
	public ResponseEntity<?> logout(HttpServletRequest request) {
		request.getSession().invalidate();
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
