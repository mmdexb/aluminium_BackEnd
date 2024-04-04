package com.hyper.aluminium.interceptor;

import com.alibaba.fastjson2.JSONObject;
import com.hyper.aluminium.pojo.Result;
import com.hyper.aluminium.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url=request.getRequestURI().toString();
        log.info("拦截的url是{}",url);
        if(url.contains("login")||url.contains("reg")||url.contains("Reset")||url.contains("public")){
            return true;
        }

        String jwt=request.getHeader("token");
        if (!StringUtils.hasLength(jwt)){
            log.info("没有token");
            Result error=Result.error("NOT_LOGIN");
            String json= JSONObject.toJSONString(error);
            response.getWriter().write(json);
            return false;
        }



        try {
            Claims claims= JwtUtils.parseJWT(jwt);
            String level=claims.get("level",String.class);

            if(!"ADMINISTRATOR".equals(level) && request.getRequestURI().contains("admin")){
                log.info("权限不足");
                Result error= Result.error("FORBIDDEN");
                String json= JSONObject.toJSONString(error);
                response.getWriter().write(json);
                return false;
            }

        }catch (Exception e){
            e.printStackTrace();
            log.info("解析失败");
            Result error=Result.error("NOT_LOGIN");
            String json= JSONObject.toJSONString(error);
            response.getWriter().write(json);
            return false;
        }

        log.info("令牌合法");

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
