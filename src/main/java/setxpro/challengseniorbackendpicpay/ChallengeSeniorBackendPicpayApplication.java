package setxpro.challengseniorbackendpicpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

@EnableJdbcAuditing
@SpringBootApplication
public class ChallengeSeniorBackendPicpayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeSeniorBackendPicpayApplication.class, args);
	}

}
