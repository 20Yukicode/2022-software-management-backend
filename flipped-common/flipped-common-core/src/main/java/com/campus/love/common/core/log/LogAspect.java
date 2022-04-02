package com.campus.love.common.core.log;

import com.alibaba.fastjson.JSON;
import com.campus.love.common.core.domain.WebLog;
import com.campus.love.common.core.util.HttpUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Slf4j
@Component
public class LogAspect {

    @Pointcut("execution(public * com.campus.love..controller.*.*(..))")
    public void record(){}

    @Around("record()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long startTime= System.currentTimeMillis();
        Object proceed = point.proceed();
        long endTime=System.currentTimeMillis();

        HttpServletRequest request = HttpUtil.currentRequest();
        Method method = currentMethod(point);
        Object[] args = point.getArgs();

        WebLog webLog = WebLog.builder()
                .startTime(startTime)
                .spendTime(endTime-startTime)
                .parameter(getParameters(method,args))
                .description(getDescription(method))
                .method(request.getMethod())
                .uri(request.getRequestURI())
                .url(request.getRequestURL().toString())
                .result(proceed)
                .build();
        //后续要转为ELK
        log.info("日志记录"+JSON.toJSONString(webLog));
        return proceed;
    }

    private Method currentMethod(ProceedingJoinPoint point) {
        return point == null ? null :
                ((MethodSignature) point.getSignature()).getMethod();
    }

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private static class Pair<K, V> {
        private final K key;
        private final V value;

        private Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public static <K, V> Pair<K, V> of(K key, V value) {
            return new Pair<>(key, value);
        }

        @Override
        public String toString() {
            return "(" +
                    "key=" + key +
                    ", value=" + value +
                    ')';
        }
    }

    private Object getParameters(Method method, Object[] args) {
        List<Object> list = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            String name = parameters[i].getName();
            Object arg = args[i];
            if (method.isAnnotationPresent(RequestParam.class)) {
                RequestParam requestParam = method.getAnnotation(RequestParam.class);
                String value = requestParam.value();
                name = value == null ? value : name;
                list.add(Pair.of(name, arg));
            }
            if (method.isAnnotationPresent(RequestBody.class)) {
                list.add(Pair.of(name, arg));
            }
            if (method.isAnnotationPresent(PathVariable.class)) {
                PathVariable pathVariable = method.getAnnotation(PathVariable.class);
                String value = pathVariable.value();
                name = value == null ? value : name;
                list.add(Pair.of(name, arg));
            }
        }
        return list.size() == 0 ? null : list.size() == 1 ? list.get(0) : list;
    }

    private String getDescription(Method method){
        if(method.isAnnotationPresent(ApiOperation.class)){
            ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
            return apiOperation.value();
        }
        return null;
    }



}
