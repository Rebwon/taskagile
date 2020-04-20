package com.rebwon.taskagile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan(
  basePackages = {"com.rebwon.taskagile.infra.file.local"}
)
@SpringBootApplication
public class TaskAgileApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskAgileApplication.class, args);
	}

}
