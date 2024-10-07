package clinina.vet.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		System.out.println("Lucas é um otario");
	}

}
