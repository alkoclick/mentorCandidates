package util;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import control.Controller;
import dao.DAO;
import model.Model;
import service.EntityService;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackageClasses = Model.class)
@ComponentScan(basePackageClasses = { Controller.class, DAO.class, EntityService.class, AppConfig.class })
public class TestConfig {

}
