package hogwarts.school_2;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition                                          // данную аннотацию добавляем для подключения к базе данных
public class School2Application {

	public static void main(String[] args) {
		SpringApplication.run(School2Application.class, args);
	}

}
