package egovframework.astems.example.service;

import java.util.Map;

import egovframework.astems.example.vo.AstemsExVo;

public interface AstemsExService {
	
	Map<String, Object> selectAstemsExList(AstemsExVo vo) throws Exception;
}
