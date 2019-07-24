package mschu.books.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mschu.books.db.UserRepository;

@Controller
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value = "/user/{account}", method = RequestMethod.HEAD) 
	public ResponseEntity<?> valid(@PathVariable("account") String account) {
				
		HttpStatus status;
		if(userRepository.existsById(account)) {
			status = HttpStatus.CONFLICT;
		}else {
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}
	@RequestMapping(value = "/user", method = RequestMethod.POST) 
	public ResponseEntity<?> regist(@RequestParam("account") String account, @RequestParam("password") String password) {
		userRepository.saveHashUser(account, password);
				
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@RequestMapping(value = "/user/{account}", method = RequestMethod.DELETE) 
	public ResponseEntity<?> unregist(@PathVariable("account") String account) {
		userRepository.deleteById(account);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
