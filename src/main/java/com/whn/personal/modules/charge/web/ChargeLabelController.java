package com.whn.personal.modules.charge.web;

import com.whn.personal.modules.charge.service.ChargeLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */
@RestController
@RequestMapping(value = "/v0.1/personal/charge_label")
public class ChargeLabelController {

    @Autowired
    private ChargeLabelService chargeLabelService;
}
