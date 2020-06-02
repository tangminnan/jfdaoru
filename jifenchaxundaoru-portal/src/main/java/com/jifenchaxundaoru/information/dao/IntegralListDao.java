package com.jifenchaxundaoru.information.dao;

import com.jifenchaxundaoru.information.domain.IntegralListDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 积分列表
 * @author wjl
 * @email bushuo@163.com
 * @date 2020-05-26 18:42:54
 */
@Mapper
public interface IntegralListDao {

	IntegralListDO get(Integer id);
	
	List<IntegralListDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(IntegralListDO integralList);
	
	int update(IntegralListDO integralList);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
	
	List<IntegralListDO> getIdentityCardByIntegral(String identityCard);
}
