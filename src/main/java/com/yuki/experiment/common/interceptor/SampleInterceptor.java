package com.yuki.experiment.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.yuki.experiment.common.result.CommonResult;
import com.yuki.experiment.common.result.IErrorCode;
import com.yuki.experiment.common.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class SampleInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String token = request.getHeader("Authorization");
        System.out.println("\n进入拦截器");
        log.info("token:{}", token);
        try {
            JwtUtil.checkToken(token);
            return true;
        } catch (NullPointerException e) {
            setReturn(response, TokenResultCode.TOKEN_IS_NULL);
            //writer.write("token为空");
        } catch (SignatureVerificationException e) {
            setReturn(response,TokenResultCode.TOKEN_SIGNATURE_INVALIDATE);
            //writer.write("无效签名");
        } catch (TokenExpiredException e) {
            setReturn(response, TokenResultCode.TOKEN_EXPIRED);
            //System.out.println("token过期");
        } catch (AlgorithmMismatchException e) {
            setReturn(response, TokenResultCode.TOKEN_ALGORITHM_MISMATCH);
            //writer.write("算法不匹配");
        } catch (Exception e) {
            setReturn(response, TokenResultCode.TOKEN_INVALIDATE);
            //writer.write("token无效");
        }
        //异常不放行
        return false;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) {
        //System.out.println("控制器执行完毕");
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) {
        //System.out.println("得到返回结果"+response);
        System.out.println("请求完毕\n");
    }

    //返回错误信息
    private static void setReturn(HttpServletResponse response, IErrorCode code) throws IOException {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        //response.setHeader("Access-Control-Allow-Origin", HttpContextUtil.getOrigin());
        //UTF-8编码
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        String json = JSONObject.toJSONString(CommonResult.failed(code));
        response.getWriter().print(json);
    }

}
