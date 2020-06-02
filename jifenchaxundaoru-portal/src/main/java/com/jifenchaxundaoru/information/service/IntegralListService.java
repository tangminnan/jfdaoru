package com.jifenchaxundaoru.information.service;

import com.jifenchaxundaoru.common.utils.R;
import com.jifenchaxundaoru.information.domain.IntegralListDO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 积分列表
 * 
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-05-26 18:42:54
 */
public interface IntegralListService {
	
	IntegralListDO get(Integer id);
	
	List<IntegralListDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(IntegralListDO integralList);
	
	int update(IntegralListDO integralList);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
	
	List<IntegralListDO> getIdentityCardByIntegral(String identityCard);

}
