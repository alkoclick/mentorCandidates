package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import control.Controller;
import dao.DAO;
import model.Model;
import service.EntityService;
import util.AppConfig;

@SpringBootApplication(scanBasePackageClasses = { Controller.class, DAO.class, EntityService.class, AppConfig.class })
@EntityScan(basePackageClasses = Model.class)
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}
