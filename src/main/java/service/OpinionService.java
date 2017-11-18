package service;

import org.springframework.stereotype.Service;

import dao.OpinionDAO;
import model.Opinion;

@Service
public class OpinionService extends ModelService<Opinion> {

	public OpinionService() {
		modelDAO = new OpinionDAO();
	}

}
