package gs.consuming.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;


public class ApplicationUrl {
	
	private static final Logger log = LoggerFactory.getLogger(ApplicationUrl.class);

	public static void main(String[] args) {
		RestTemplate template = new RestTemplate();
		
		Quote quote = template.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
		
		log.info(quote.toString());
		
	}
	
}
