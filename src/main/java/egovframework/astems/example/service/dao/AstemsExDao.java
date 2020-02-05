package egovframework.astems.example.service.dao;

import java.util.List;

import egovframework.astems.example.vo.AstemsExVo;

public interface AstemsExDao {

	List<AstemsExVo> selectAstemsExList(AstemsExVo vo) throws Exception;
}
