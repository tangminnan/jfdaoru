package com.jifenchaxundaoru.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jifenchaxundaoru.common.domain.LogDO;
import com.jifenchaxundaoru.common.domain.PageDO;
import com.jifenchaxundaoru.common.utils.Query;
@Service
public interface LogService {
	void save(LogDO logDO);
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
