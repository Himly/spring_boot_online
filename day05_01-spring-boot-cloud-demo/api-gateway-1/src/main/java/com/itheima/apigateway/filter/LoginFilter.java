package com.itheima.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Service // 这里用 @Component 和 @Service 都可以
public class LoginFilter extends ZuulFilter {
    /**
     * filterType: 过滤器类型
     * pre：前置过滤，在网关转发请求到后端微服务之前
     * route: 在网关路由的过程中执行过滤，可以动态改变路由规则
     * post: 在请求转发到后端微服务返回响应之后再执行过滤，比如在响应内容中处理敏感数据
     * error: 请求出现异常执行过滤
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE; // 前置过滤器
    }

    /**
     * 过滤器的执行顺序
     * 数值越小，优先级越高
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否对请求进行过滤
     * 如果是返回true，表示需要过滤
     * 返回false，就不需要过滤
     */
    @Override
    public boolean shouldFilter() {
        // 假设场景，比如get请求，不执行过滤，post请求需要执行过滤
        // 比如设置黑白名单，白名单中的IP不需要过滤等等
        return true;
    }

    /**
     * 过滤器执行的逻辑
     * 当上面的shouldFilter返回true时，就进入到这里的逻辑
     */
    @Override
    public Object run() throws ZuulException {
        // 判断请求的参数中是否包含access_token，有的话就放行，没有的话返回给前端未授权状态401
        // 获取当前的请求上下文对象
        RequestContext currentContext = RequestContext.getCurrentContext();
        // 获取当前的请求对象
        HttpServletRequest request = currentContext.getRequest();
        // 获取请求参数
        String accessToken = request.getParameter("access_token");
        // 判断accessToken是否为空
        if (StringUtils.isEmpty(accessToken)) {
            // 过滤请求,不再向后端微服务转发请求
            currentContext.setSendZuulResponse(false);
            // 返回未授权信息
            currentContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }
        return null;
    }
}
