package com.yhl.zuulresource.controller;

import com.yhl.base.baseController.BaseController;
import com.yhl.zuulresource.entity.OAuthClientDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("test123")
public class testcontroller extends BaseController<OAuthClientDetails,String> {

    @GetMapping(params = {"flag"})
    public String testStr(@RequestParam("test") String test){
        return "真他妈的不容易";
    }
}
