package dao;

import org.springframework.stereotype.Repository;

import model.Opinion;

@Repository
public class OpinionDAO extends ModelDAO<Opinion> {

	public OpinionDAO() {
		tClass = Opinion.class;
	}
}
