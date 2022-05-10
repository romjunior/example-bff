package com.estudo;

import feign.Util;
import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class FeignConfig {

    @Bean
    public Decoder defaultDecoder() {
        return (response, type) -> {
            if(response.status() == 404) {
                return Util.emptyValueOf(type);
            }
            return delegateTo().decode(response, type);
        };
    }

    Decoder delegateTo() {
        HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        HttpMessageConverters httpMessageConverters = new HttpMessageConverters(jacksonConverter);
        ObjectFactory<HttpMessageConverters> objectFactory = () -> httpMessageConverters;

        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
    }

}
