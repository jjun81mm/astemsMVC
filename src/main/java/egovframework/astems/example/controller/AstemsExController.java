package egovframework.astems.example.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import egovframework.astems.example.service.AstemsExService;
import egovframework.astems.example.vo.AstemsExVo;
import egovframework.astems.example.vo.JSONTest;
import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.SampleDefaultVO;
import egovframework.example.sample.service.SampleVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;


@RestController
public class AstemsExController {
	
	@Resource(name = "astemsExService")
	private AstemsExService astemsExService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AstemsExController.class);

	/**
	 * POST 방식으로 값을 전달하는 방법 입니다.
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/searchAstemsExPost.do", method = {RequestMethod.GET, RequestMethod.POST})
	public Map<String, Object> searchPost(AstemsExVo vo) throws Exception {
		return astemsExService.selectAstemsExList(vo);
		
	}
	
	@RequestMapping(value="/test", method = RequestMethod.GET)
	public Map<String, Object> test(AstemsExVo vo){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("1", "111");
    	map.put("2", 222);
    	return map;
    }
	
	@RequestMapping(value = "/test/call", method = RequestMethod.POST)
	public Map testCall(HttpServletRequest request){
	
		Map result = new HashMap<String, Object>();
		result.put("test1", "test1");
		result.put("test2", "test2");
	
		return result;

	}
	
	@RequestMapping(value="/jsonTest", method = RequestMethod.POST)
	public JSONTest jsonTest() {
	    	//가상의 배열및 리스트에 데이터 add
	    	ArrayList<String> arraylist = new ArrayList<String>();
	    	arraylist.add("a");
	    	arraylist.add("b");
	    	String[] array = {"a","b","c"};
	    	
	    	//VO객체에 SET한후 vo객체자체를 return
	    	JSONTest test = new JSONTest();
	    	test.setId(1);
	    	test.setTxt("textTxt");
	    	test.setList(arraylist);
	    	test.setArr(array);
		return test;
	}

		
	/** EgovSampleService */
	@Resource(name = "sampleService")
	private EgovSampleService sampleService;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;

	/**
	 * 글 목록을 조회한다. (pageing)
	 * @param searchVO - 조회할 정보가 담긴 SampleDefaultVO
	 * @param model
	 * @return "egovSampleList"
	 * @exception Exception
	 */
	@RequestMapping(value = "/JsonSampleList.do", method = {RequestMethod.GET , RequestMethod.POST})
	public ModelAndView selectSampleList(@ModelAttribute("searchVO") SampleDefaultVO searchVO, ModelMap model) throws Exception {

		/** EgovPropertyService.sample */
		searchVO.setPageUnit(propertiesService.getInt("pageUnit"));
		searchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing setting */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<?> sampleList = sampleService.selectSampleList(searchVO);
		model.addAttribute("resultList", sampleList);

		int totCnt = sampleService.selectSampleListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

		//return "sample/egovSampleList";
		return new ModelAndView( "jsonV", model );
	}
	
	
	/**
	 * 글 수정화면을 조회한다.
	 * @param id - 수정할 글 id
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param model
	 * @return "egovSampleRegister"
	 * @exception Exception
	 */
	@RequestMapping(value = "/JsonupdateSampleView.do", method = {RequestMethod.GET , RequestMethod.POST})
	public ModelAndView updateSampleView(@RequestParam("selectedId") String id, @ModelAttribute("searchVO") SampleDefaultVO searchVO, ModelMap model) throws Exception {
		SampleVO sampleVO = new SampleVO();
		sampleVO.setId(id);
		// 변수명은 CoC 에 따라 sampleVO
		model.addAttribute("injectBoard",selectSample(sampleVO, searchVO));
		
		return new ModelAndView( "jsonV", model );
		
	}	
	/**
	 * 글을 조회한다.
	 * @param sampleVO - 조회할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return @ModelAttribute("sampleVO") - 조회한 정보
	 * @exception Exception
	 */
	public SampleVO selectSample(SampleVO sampleVO, @ModelAttribute("searchVO") SampleDefaultVO searchVO) throws Exception {
		return sampleService.selectSample(sampleVO);
	}



}
