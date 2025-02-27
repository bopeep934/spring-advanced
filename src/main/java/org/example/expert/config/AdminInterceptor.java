package org.example.expert.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;

@Slf4j
@Component
public class AdminInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AdminInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String userRole = (String) request.getAttribute("userRole");

        System.out.println("User Role: " + userRole);  // userRole 값 확인

        if (!"ADMIN".equals(userRole)) {
            logger.warn("관리자만 접근 가능합니다.");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자 권한이 없습니다.");

            return false;
        }
        if ("ADMIN".equals(userRole)) {
            long requestTime = System.currentTimeMillis();
            String nowRequest = new SimpleDateFormat("yyyy-MM-dd HH: mm :ss").format(requestTime);
            request.setAttribute("requestTime", nowRequest); // 요청 객체에 저장

            logger.info("==================== START ====================");
            logger.info(" Request URI \t: " + request.getRequestURI() + " Request received at: {}", nowRequest);

            return true;
        }// 정상 요청이면 컨트롤러로 이동
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView
            modelAndView) throws Exception {
        System.out.println("[postHandle] 컨트롤러 실행 후");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception
            ex) throws Exception {
        System.out.println("[afterCompletion] 요청 처리 완료");
    }
}

