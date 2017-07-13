package com.whn.personal.modules.charge.service;

import com.whn.personal.modules.charge.mapper.ChargeLabelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author weihainan.
 * @since 0.1 created on 2017/7/13.
 */
@Service
public class ChargeLabelService {
    @Autowired
    private ChargeLabelMapper chargeLabelMapper;
}
