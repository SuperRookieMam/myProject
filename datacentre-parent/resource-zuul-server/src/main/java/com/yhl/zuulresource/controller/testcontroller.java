package com.yhl.zuulresource.controller;

import com.yhl.base.baseController.BaseController;
import com.yhl.zuulresource.entity.OAuthClientDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("test")
public class testcontroller extends BaseController<OAuthClientDetails,String> {
}
