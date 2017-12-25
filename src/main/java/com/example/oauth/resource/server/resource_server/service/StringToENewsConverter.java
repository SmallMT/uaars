package com.example.oauth.resource.server.resource_server.service;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToENewsConverter implements Converter<String, RealNameState> {

    /* (non-Javadoc)
     * @see com.fasterxml.jackson.databind.util.Converter#convert(java.lang.Object)
     * @author: ShangJianguo
     * 2014-6-12 下午4:56:30
     */
//    @Override
//    public ENews convert(String source) {
//        String value = source.trim();
//        if ("".equals(value)) {
//            return null;
//        }
//        return ENews.get(Integer.parseInt(source));
//
//    }


    @Override
    public RealNameState convert(String source) {
        return RealNameState.valueOf(source);
    }
}