package com.jifenchaxundaoru.common.controller;

import org.springframework.stereotype.Controller;

import com.jifenchaxundaoru.common.utils.ShiroUtils;
import com.jifenchaxundaoru.system.domain.UserDO;
import com.jifenchaxundaoru.system.domain.UserToken;

@Controller
public class BaseController {
	public UserDO getUser() {
		return ShiroUtils.getUser();
	}

	public Long getUserId() {
		return getUser().getUserId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}
}