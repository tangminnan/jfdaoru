package com.jifenchaxundaoru.information.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.jifenchaxundaoru.information.dao.IntegralListDao;
import com.jifenchaxundaoru.information.domain.IntegralListDO;
import com.jifenchaxundaoru.information.service.IntegralListService;

@Service
public class IntegralListServiceImpl implements IntegralListService {
	@Autowired
	private IntegralListDao integralListDao;
	
	@Override
	public IntegralListDO get(Integer id){
		return integralListDao.get(id);
	}
	
	@Override
	public List<IntegralListDO> list(Map<String, Object> map){
		return integralListDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return integralListDao.count(map);
	}
	
	@Override
	public int save(IntegralListDO integralList){
		return integralListDao.save(integralList);
	}
	
	@Override
	public int update(IntegralListDO integralList){
		return integralListDao.update(integralList);
	}
	
	@Override
	public int remove(Integer id){
		return integralListDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return integralListDao.batchRemove(ids);
	}

	@Override
	public List<IntegralListDO> getIdentityCardByIntegral(String identityCard) {
		return integralListDao.getIdentityCardByIntegral(identityCard);
	}

	

}
