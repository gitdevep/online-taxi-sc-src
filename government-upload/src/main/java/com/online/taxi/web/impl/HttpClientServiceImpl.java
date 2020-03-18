package com.online.taxi.web.impl;

import com.online.taxi.web.HttpClientService;
import com.online.taxi.web.HttpResult;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @date 2018/10/22
 */
@Service
public class HttpClientServiceImpl implements HttpClientService {

	@Autowired
	private CloseableHttpClient httpClient;

	@Autowired
	private RequestConfig requestConfig;

	@Override
	public String doGet(String url) throws Exception {
		// 创建httpClient对象
		CloseableHttpResponse response = null;
		HttpGet httpGet = new HttpGet(url);
		// 设置请求参数
		httpGet.setConfig(requestConfig);
		try {
			// 执行请求
			response = httpClient.execute(httpGet);
			// 判断返回状态码是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return null;
	}

	@Override
	public String doGet(String url, Map<String, String> paramMap) throws Exception {
		URIBuilder builder = new URIBuilder(url);
		for (String s : paramMap.keySet()) {
			builder.addParameter(s, paramMap.get(s));
		}
		return doGet(builder.build().toString());
	}

	@Override
	public HttpResult doPost(String url, Map<String, String> paramMap) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		// 设置请求参数
		httpPost.setConfig(requestConfig);
		if (paramMap != null) {
			List<NameValuePair> parameters = new ArrayList();
			for (String s : paramMap.keySet()) {
				parameters.add(new BasicNameValuePair(s, paramMap.get(s)));
			}
			// 构建一个form表单式的实体
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, Charset.forName("UTF-8"));
			// 将请求实体放入到httpPost中
			httpPost.setEntity(formEntity);
		}
		// 创建httpClient对象
		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = httpClient.execute(httpPost);
			return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity()));
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}

	@Override
	public HttpResult doPost(String url, byte[] bytes) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(this.requestConfig);
		httpPost.setHeader("Connection", "keep-alive");
		httpPost.setHeader("Content-type", "application/x-protobuf");
		httpPost.setHeader("Accept-Encoding", "gzip");
		httpPost.setHeader("Accept-Charset", "utf-8");

		if (bytes != null) {
			// 构造一个请求实体
			ByteArrayEntity byteArrayEntity = new ByteArrayEntity(bytes);
			// 将请求实体设置到httpPost对象中
			httpPost.setEntity(byteArrayEntity);
		}

		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = this.httpClient.execute(httpPost);
			return new HttpResult(response.getStatusLine().getStatusCode(),
					EntityUtils.toString(response.getEntity(), "UTF-8"));
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}

	@Override
	public HttpResult doPostT(String Url, byte[] paramStr) {
		URL url;
		String code = "";
		OutputStream outputStream = null;
		try {
			url = new URL(Url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setReadTimeout(15000);

			conn.setRequestProperty("Content-Type", "application/x-protobuf");
			// 建立输出流，并写入数据
			outputStream = conn.getOutputStream();
			outputStream.write(paramStr);

			// 获得响应状态
			int code1 = conn.getResponseCode();
			String enti = conn.getResponseMessage();
			code = code1 + "";
			return new HttpResult(code1, enti);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}
}