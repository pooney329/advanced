package com.example.proxy;

import com.example.proxy.config.AppV1Config;
import com.example.proxy.config.AppV2Config;
import com.example.proxy.config.v1_proxy.ConcreteProxyConfig;
import com.example.proxy.config.v1_proxy.InterfaceProxyConfig;
import com.example.proxy.config.v2_dynamicproxy.DynamicProxyBasicConfig;
import com.example.proxy.config.v2_dynamicproxy.DynamicProxyFilterConfig;
import com.example.proxy.config.v3_proxyfactory.ProxyFactoryConfigV1;
import com.example.proxy.config.v3_proxyfactory.ProxyFactoryConfigV2;
import com.example.proxy.config.v4_postprocessor.BeanPostProcessorConfig;
import com.example.proxy.config.v5_autoproxy.AutoProxyConfig;
import com.example.proxy.config.v6_app.AopConfig;
import com.example.proxy.trace.logtrace.LogTrace;
import com.example.proxy.trace.logtrace.ThreadLocalLogTrace;
import com.example.proxy.config.AppV1Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import org.springframework.context.annotation.Import;


/**
 * 스캔 범위 지정한 이유는 컴포넌트 스캔이되면 내가 원하는
 * {@link AppV1Config} 와 같은 Config를 내가 원하는 버전을 선택해서 Bean으로 등록하지 못하기 때문
 */

//@Import(AppV2Config.class)
//@Import({AppV2Config.class, AppV2Config.class}) //버전 별로 Config를 다르게 하기 위해 Config 별도 지정하여 Bean으로 등록
//@Import(InterfaceProxyConfig.class)
//@Import(ConcreteProxyConfig.class)
//@Import(DynamicProxyBasicConfig.class)
//@Import(DynamicProxyFilterConfig.class)
//@Import(ProxyFactoryConfigV1.class)
//@Import(ProxyFactoryConfigV2.class)
//@Import(BeanPostProcessorConfig.class)
//@Import(AutoProxyConfig.class)
@Import(AopConfig.class)
@SpringBootApplication(scanBasePackages = "com.example.proxy.app.v3")
public class ProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProxyApplication.class, args);
    }


    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }

}
