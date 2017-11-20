package util;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import service.ModelService;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(classes = TestConfig.class)
@ContextConfiguration
@WebAppConfiguration
public abstract class HibernateTest<T> implements ClassSetter<T> {
	protected static int BATCH_SIZE = 20;

	@Autowired
	protected WebApplicationContext context;

	@Autowired
	protected ModelService<T> service;

	public ModelService<T> getService() {
		return service;
	}

	public void setService(ModelService<T> service) {
		this.service = service;
	}
}
