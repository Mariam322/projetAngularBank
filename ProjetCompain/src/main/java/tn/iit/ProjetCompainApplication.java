package tn.iit;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@EnableDiscoveryClient
@SpringBootApplication

public class ProjetCompainApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetCompainApplication.class, args);
	}

}
