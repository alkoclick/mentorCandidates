package util;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import control.Controller;
import dao.ModelDAO;
import model.Model;
import service.ModelService;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackageClasses = Model.class)
@ComponentScan(basePackageClasses = { Controller.class, ModelDAO.class, ModelService.class, AppConfig.class })
public class TestConfig {

}
