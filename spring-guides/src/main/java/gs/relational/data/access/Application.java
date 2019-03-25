package gs.relational.data.access;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {

		log.info("create tables");
		jdbcTemplate.execute("drop table customers if exists");
		jdbcTemplate.execute("create table customers(id serial, first_name varchar(265), last_name varchar(265))");
		
		List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Long")
				.stream().map(name -> name.split(" "))
				.collect(Collectors.toList());
		
		log.info(String.format("splitUpNames: %s", splitUpNames.get(0)));
		
		splitUpNames.forEach(name -> log.info(String.format("insert customer record fo %s %s", name[0], name[1])));
		
//		splitUpNames.forEach(name -> log.info(String.format("Inserting customer record for %s %s", name[0], name[1])));
		
		jdbcTemplate.batchUpdate("insert into customers(first_name, last_name) values (?,?)", splitUpNames);
		
		log.info("query for customer records where first_name is Josh");
		jdbcTemplate.query("select id, first_name, last_name from customers where first_name = ?", 
				new Object[] {"Josh"}, (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"))
				).forEach(customer -> log.info(customer.toString()));
		
	}

}
