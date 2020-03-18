package com.online.taxi.web;

/**
 * Created by weiqin on 2017/8/10.
 */
public class HttpResult {

    /**
     * HTTP 返回码
     * 200 （成功）服务器已成功处理了请求。
     * 201 请求已经完成并一个新的返回资源被创建。
     * 400 （错误请求）服务器不理解请求的语法。
     * 401 （未授权）请求要求身份验证。
     * 403 （禁止）服务器拒绝请求。
     * 404 （未找到）服务器找不到请求的网页。
     * 500 服务器遭遇异常阻止了当前请求的执行。
     * 502 （错区网关）服务器作为网关或代理，从上游服务器收到无效响应。
     * 702 请求文件不存在。
     * 948 请求文件名格式不正确。
     * 949 文件解压失败。
     * 952 格式校验失败。
     */
    private Integer status;

    private String data;

    public HttpResult(){

    }

    public HttpResult(Integer status, String data){
        this.data = data;
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
