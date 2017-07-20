package com.whn.personal.modules.charge.web;

import com.whn.personal.modules.charge.domain.Charge;
import com.whn.personal.modules.charge.service.ChargeService;
import com.whn.personal.modules.charge.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(code = HttpStatus.OK)
    public Object delete(@PathVariable String id) {
        return chargeService.delete(id);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object list(@RequestBody SearchVo condition) {
        return chargeService.search(condition);
    }

    @RequestMapping(value = "/year_month", method = RequestMethod.GET)
    public Object getYearMaonth() {
        return chargeService.getYearMonth();
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public Object statistics() {
        return chargeService.statistics();
    }

}
