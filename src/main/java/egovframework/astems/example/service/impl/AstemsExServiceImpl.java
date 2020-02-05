package egovframework.astems.example.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import egovframework.astems.example.service.AstemsExService;
import egovframework.astems.example.service.dao.AstemsExDao;
import egovframework.astems.example.vo.AstemsExVo;

@Service("astemsExService")
public class AstemsExServiceImpl implements AstemsExService{

	private AstemsExDao astemsExDao;
	
	@Override
	public Map<String, Object> selectAstemsExList(AstemsExVo vo) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("list", astemsExDao.selectAstemsExList(vo));
		
		return map;
	}

}
