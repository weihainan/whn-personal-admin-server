package com.whn.personal.modules.charge.web;

import com.whn.personal.modules.charge.domain.ChargeLabel;
import com.whn.personal.modules.charge.service.ChargeLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */
@RestController
@RequestMapping(value = "/v0.1/personal/charge_labels")
public class ChargeLabelController {

    @Autowired
    private ChargeLabelService chargeLabelService;

    @RequestMapping(method = RequestMethod.POST)
    public Object add(@RequestBody ChargeLabel label) {
        return chargeLabelService.add(label);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Object delete(@PathVariable String id) {
        return chargeLabelService.delete(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Object selectAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                         @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                         @RequestParam(value = "all", required = false, defaultValue = "false") boolean all) {
        return chargeLabelService.selectAll(page, size, all);
    }
}
