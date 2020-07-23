package com.olande.hiphop.user.web.config;

import com.olande.common.converter.SqlDateConverter;
import com.olande.common.converter.SqlTimeConverter;
import com.olande.common.converter.SqlTimestampConverter;
import com.olande.common.converter.UtilDateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;

@Configuration
public class ConverterConfigBean {
    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    @PostConstruct
    public void initEditableAvlidation() {

        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter.getWebBindingInitializer();
        if (initializer.getConversionService() != null) {
            GenericConversionService genericConversionService = (GenericConversionService) initializer.getConversionService();
            // 注册日期时间类型转换器
            genericConversionService.addConverter(new SqlDateConverter());
            genericConversionService.addConverter(new UtilDateConverter());
            genericConversionService.addConverter(new SqlTimestampConverter());
            genericConversionService.addConverter(new SqlTimeConverter());
        }
    }
}
