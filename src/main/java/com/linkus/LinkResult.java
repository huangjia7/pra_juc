package com.linkus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class LinkResult {
    // 响应业务状态
    private Integer status;
    // 响应消息
    private String msg;
    // 响应中的数据
    private Object data;

    public static LinkResult build(Integer status, String msg, Object data) {
        return new LinkResult(status, msg, data);
    }

    public static void listUsers(String type, Long... ids) {

    }

    public static LinkResult ok(Object data) {
        return new LinkResult(data);
    }

    public static LinkResult ok() {
        return new LinkResult(null);
    }

    public LinkResult() {

    }

    public static LinkResult build(Integer status, String msg) {
        return new LinkResult(status, msg, null);
    }

    public LinkResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public LinkResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static void main(String[] args) throws JsonProcessingException {
        //LinkResult.ok("hello") LinkResult。error、

        System.out.println(objToJson(LinkResult.ok("hello")));

        Map map = new HashMap();
        map.put("name","张三");
        map.put("age","30");
        System.out.println(objToJson(LinkResult.ok(map)));

        listUsers("zhangsan",1L,2L,3L);

        BigDecimal recommend1 = new BigDecimal(1);
        BigDecimal recommend2 = BigDecimal.valueOf(0.1);
    }

    public static String objToJson(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}
