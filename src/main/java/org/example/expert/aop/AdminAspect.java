package org.example.expert.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AdminAspect {

    private final HttpServletRequest request;

    // 포인트 컷 설정
    @Pointcut("@annotation(org.example.expert.annotation.AdminLog)")
    private void adminLogPointcut() {}

    @Around("adminLogPointcut()")
    public Object adminLogAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Object result = joinPoint.proceed();
            return result;
        }finally {
            //API 요청 시간
            long requestTime = System.currentTimeMillis();
            //요청 API URL
            String requestUrl = request.getRequestURL().toString();
            //요청한 사용자 ID
            Long userId = (Long) request.getAttribute("userId");

            log.info("요청한 UserId ={}, 요청 시각 = {}, 요청 URL = {}", userId, requestUrl, requestTime);
        }
    }
}
