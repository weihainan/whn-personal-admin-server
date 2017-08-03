package com.whn.personal.modules.apple.web;

import com.whn.personal.modules.apple.service.AppleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator.
 * @since 0.1 created on 2017/7/29 0029.
 */
@RestController
@RequestMapping(value = "/v0.1/apples")
public class AppleController {

    @Autowired
    private AppleService appleService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object apples() {
        return appleService.selectList();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Object pickApple() {
        return appleService.add();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Object eatApple(@PathVariable int id) {
        return appleService.eat(id);
    }
}


