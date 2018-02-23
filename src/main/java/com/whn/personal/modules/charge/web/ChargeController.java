package com.whn.personal.modules.charge.web;

import com.google.common.util.concurrent.RateLimiter;
import com.whn.personal.internal.constant.ErrorCode;
import com.whn.personal.modules.charge.domain.Charge;
import com.whn.personal.modules.charge.service.ChargeService;
import com.whn.personal.modules.charge.vo.SearchVo;
import com.whn.waf.foundation.exception.WafBizException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */
@RestController
@RequestMapping(value = "/v0.1/personal/charges")
public class ChargeController {

    @Resource
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
    public Object getYearMonth() {
        return chargeService.getYearMonth();
    }


    private RateLimiter rateLimiter = RateLimiter.create(0.1);

    /**
     * 该接口做限流 允许10秒访问一次
     */
    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public Object statistics() {
        if (!rateLimiter.tryAcquire(2, TimeUnit.SECONDS)) {
            throw WafBizException.of(ErrorCode.REQUEST_LIMIT);
        }
        return chargeService.statistics();
    }
}
