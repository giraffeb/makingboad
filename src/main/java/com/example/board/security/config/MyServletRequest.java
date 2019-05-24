package com.example.board.security.config;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class MyServletRequest extends HttpServletRequestWrapper{

    private Map<String, String[]> newParamMap = new HashMap<>();


    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public MyServletRequest(HttpServletRequest request) {
        super(request);
        this.newParamMap.putAll(request.getParameterMap());
    }

    @Override
    public String getParameter(String name) {
        if(this.newParamMap.get(name) != null){
            return this.newParamMap.get(name)[0];
        }

        return super.getParameter(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.newParamMap;
    }

    @Override
    public String[] getParameterValues(String name) {
        return this.newParamMap.get(name);
    }

    public void setParameter(String key, String value){
        this.newParamMap.put(key,new String[]{value});

    }
}
