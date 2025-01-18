package com.example.proxy.config.v4_postprocessor.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 원본 객체를 프록시로 반환하는 역할 수행
 * 기존 수동으로 프록시를 등록했다면 컴포넌트 스캔에 대상이 되는 것들도 포록시 적용이 가능해졌다.
 */
@Slf4j
public class PackageLogTracePostProcessor implements BeanPostProcessor {



    private final String basePackage; //모든 bean을 프록시로 대체할 필요가 없으니 필터하는 역할
    private final Advisor advisor;

    public PackageLogTracePostProcessor(String basePackage, Advisor advisor) {
        this.basePackage = basePackage;
        this.advisor = advisor;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("param beanName={} bean={}", beanName, bean.getClass());

        //프록시 적용 대상 여부 체크
        //프록시 적용 대상이 아니면 원본을 그대로 진행
        //FILTER 부분을 제외하면 오류가 난다. Spring이 bean으로 등록하는게 너무 많기도 하고 final로 등록되어 있는 것도 많기 때문!!!
        String packageName = bean.getClass().getPackageName();
        if (!packageName.startsWith(basePackage)){
            return bean;
        }
        //프록시 대상이면 프록시를 만들어서 반환
        ProxyFactory proxyFactory = new ProxyFactory(bean);
        proxyFactory.addAdvisor(advisor);

        Object proxy = proxyFactory.getProxy();
        log.info("create proxy: target={} proxy={}",  bean.getClass(), proxy.getClass());
        return proxy;

    }
}

