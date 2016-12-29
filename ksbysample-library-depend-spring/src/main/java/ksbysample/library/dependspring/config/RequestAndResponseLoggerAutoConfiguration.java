package ksbysample.library.dependspring.config;

import ksbysample.library.dependspring.intercepter.RequestAndResponseLogger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestAndResponseLoggerAutoConfiguration {

    private static final String REQUESTMAPPING_EXPRESSION = "@annotation(org.springframework.web.bind.annotation.RequestMapping)";

    @Value("${ksbysample.library.request-and-response-logger.execution:}")
    private String execution;

    @Bean
    @ConditionalOnWebApplication
    @ConditionalOnProperty(value = {"ksbysample.library.request-and-response-logger.enabled"}, havingValue = "true")
    public Advisor requestAndResponseLoggerAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        String expression = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(this.execution)) {
            expression = this.execution + " && ";
        }
        expression += REQUESTMAPPING_EXPRESSION;
        pointcut.setExpression(expression);
        return new DefaultPointcutAdvisor(pointcut, new RequestAndResponseLogger());
    }

}
