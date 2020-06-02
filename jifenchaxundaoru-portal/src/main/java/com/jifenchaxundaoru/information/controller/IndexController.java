package com.jifenchaxundaoru.information.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jifenchaxundaoru.information.domain.IntegralListDO;
import com.jifenchaxundaoru.information.service.IntegralListService;

@Controller
public class IndexController {
	
	@Autowired
	private IntegralListService integralListService;
	

    @GetMapping("/integralPage")
    String homePage(){
    	//ModelAndView mv =  new ModelAndView("jsp/integral");
        //return mv;
        return "jsp/integral";
    }
    
    
    @GetMapping("/integral/getSecIntegral")
    @ResponseBody
    List<IntegralListDO> getSecIntegral(String key , String val) throws IOException{
    	System.out.println(key+"==="+val);
		String replaceAll = val.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
		String uname = URLDecoder.decode((String) replaceAll, "UTF-8");
    	Map<String,Object> map = new HashMap<String, Object>();
    	map.put(key, uname);
    	List<IntegralListDO> list = integralListService.list(map);
    	for (IntegralListDO integralListDO : list) {
    		List<Double> ite = new ArrayList<Double>();
    		List<IntegralListDO> integral = integralListService.getIdentityCardByIntegral(integralListDO.getIdentityCard());
    		for (IntegralListDO integralListDO2 : integral) {
    			ite.add(Double.valueOf(integralListDO2.getWinIntegral()));
			}
    		integralListDO.setTotalIntegral(String.valueOf(ite.stream().reduce(Double::sum).orElse((double) 0)));
		}
		return list;
    	
    }

}
