package com.jifenchaxundaoru.common.controller;

import org.springframework.stereotype.Controller;

import com.jifenchaxundaoru.common.utils.ShiroUtils;
import com.jifenchaxundaoru.owneruser.domain.OwnerUserDO;
import com.jifenchaxundaoru.system.domain.UserToken;

@Controller
public class BaseController {
	public OwnerUserDO getUser() {
		return ShiroUtils.getUser();
	}

	public Long getUserId() {
		return getUser().getId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}
	public Long getforIds() {
		return getUser().getUserId();
	}
}