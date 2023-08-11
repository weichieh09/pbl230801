package com.wcc.pbl230801.pblService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class Wcc101Service {

    private final Logger log = LoggerFactory.getLogger(Wcc101Service.class);
}
