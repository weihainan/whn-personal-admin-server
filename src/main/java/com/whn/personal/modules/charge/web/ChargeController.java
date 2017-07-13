package com.whn.personal.modules.charge.web;

import com.whn.personal.modules.charge.domain.Charge;
import com.whn.personal.modules.charge.service.ChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */
@RestController
@RequestMapping(value = "/v0.1/personal/charges")
public class ChargeController {

    @Autowired
    private ChargeService chargeService;

    @RequestMapping(method = RequestMethod.POST)
    public Object post(@RequestBody Charge charge) {
        return chargeService.add(charge);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Object post(@PathVariable String id) {
        return chargeService.delete(id);
    }
}
