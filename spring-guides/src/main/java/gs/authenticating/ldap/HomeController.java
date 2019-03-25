package gs.authenticating.ldap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping(name="/")
	public String index() {
		
		return "welcome to home page.";
	}
}
