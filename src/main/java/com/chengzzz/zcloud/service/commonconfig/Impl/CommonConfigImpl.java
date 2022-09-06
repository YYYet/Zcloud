package com.chengzzz.zcloud.service.commonconfig.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chengzzz.zcloud.dao.CommonConfigMapper;
import com.chengzzz.zcloud.model.ConfigDo;
import com.chengzzz.zcloud.service.commonconfig.ICommonConfigService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


/**
 * @author Yet
 * @date 2022/09/01 22:55
 **/
@Service
@Component
public class CommonConfigImpl extends ServiceImpl<CommonConfigMapper, ConfigDo> implements ICommonConfigService {
}
