package br.com.bassi.trabalho_facu_lp1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class TrabalhoFaculLp1EAApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrabalhoFaculLp1EAApplication.class, args);
	}

}
