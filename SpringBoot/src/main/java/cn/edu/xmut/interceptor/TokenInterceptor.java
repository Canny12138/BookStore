package cn.edu.xmut.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.xmut.springboot.utils.Result;
import cn.edu.xmut.utils.JwtUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求头中的 token
        String token = request.getHeader("token");

        // 验证 token（这里使用您自己的 token 验证实现）
        Result<String> validationResult = validateToken(token);

        if (validationResult.isSuccess()) {
            // 如果 token 有效，继续处理请求
            return true;
        } else {
            // 如果 token 无效，返回 401 Unauthorized
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, validationResult.getMessage());
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    // 使用您自己的 token 验证实现替换此方法
    private Result<String> validateToken(String token) {
        // 实现您自己的 token 验证逻辑，并返回一个 Result<String> 对象
        return JwtUtil.validateToken(token);
    }
}