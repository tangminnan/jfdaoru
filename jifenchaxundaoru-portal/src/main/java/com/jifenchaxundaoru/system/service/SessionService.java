package com.jifenchaxundaoru.system.service;

import java.util.Collection;
import java.util.List;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import com.jifenchaxundaoru.owneruser.domain.OwnerUserDO;
import com.jifenchaxundaoru.system.domain.UserOnline;

@Service
public interface SessionService {
	List<UserOnline> list();

	List<OwnerUserDO> listOnlineUser();

	Collection<Session> sessionList();
	
	boolean forceLogout(String sessionId);
}
