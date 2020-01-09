package com.syphan.practice.proxy.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class CustomPreFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * if return tru func run below will be run
     *
     * @return boolean
     */
    @Override
    public boolean shouldFilter() {
        //maybe custom or check something
        return true;
    }

    /**
     * using zuulFilter to custom headerRequest to another service
     *
     * @return object
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader("test", "phan tien sy");

        /**
         * if you want using zuulFilter to custom auth you can using code below.
         *
         RequestContext ctx = RequestContext.getCurrentContext();
         ctx.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
         ctx.setSendZuulResponse(false);
         */
        return null;
    }
}
