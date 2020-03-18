package com.online.taxi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.Message;
import com.googlecode.protobuf.format.JsonFormat;
import com.online.taxi.constant.IPCUrl;
import com.online.taxi.data.upload.proto.IPCBuilder;
import com.online.taxi.data.upload.proto.OTIpcDef;
import com.online.taxi.model.BaseMPRequest;
import com.online.taxi.service.InfoUploader;
import com.online.taxi.web.HttpClientService;
import com.online.taxi.web.HttpResult;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;

@Service
@Slf4j
public class InfoUploaderImpl implements InfoUploader {

    private final static String PRE_SEPERATE = "/";

    @Value("${gov.url}")
    private String govUrl;

    @Autowired
    protected HttpClientService httpClientService;

//	@Override
//	public HttpResult execute(BaseMPRequest baseMPRequest) throws Exception {
//		return execute(baseMPRequest.getRequestUrl(), baseMPRequest);
//	}
//
//	@Override
//	public HttpResult execute(String url, String json) throws Exception {
//		return httpClientService.doPost4Json(createUrl(url), json);
//	}

    @Override
    public HttpResult executeByProto(BaseMPRequest baseMPRequest) throws Exception {
        com.googlecode.protobuf.format.JsonFormat jsonFormat = new JsonFormat();
        com.google.protobuf.Message.Builder builder = IPCBuilder.getValue(baseMPRequest.getIpcType());
        builder.clear();
        jsonFormat.merge(new ByteArrayInputStream(JSON.toJSONString(baseMPRequest).getBytes()), builder);
        OTIpcDef.OTIpc.Builder otIpcBuilder = OTIpcDef.OTIpc.newBuilder();

        otIpcBuilder.setCompanyId("3301YPZCX78Q");
        otIpcBuilder.setSource("0");
        otIpcBuilder.setIPCType(OTIpcDef.IpcType.valueOf(baseMPRequest.getIpcType()));
        switch (otIpcBuilder.getIPCType().getNumber()) {
            case 4097:
                otIpcBuilder.addBaseInfoCompany((OTIpcDef.BaseInfoCompany.Builder) builder);
                break;
            case 4098:
                otIpcBuilder.addBaseInfoCompanyStat((OTIpcDef.BaseInfoCompanyStat.Builder) builder);
                break;
            case 4099:
                otIpcBuilder.addBaseInfoCompanyPay((OTIpcDef.BaseInfoCompanyPay.Builder) builder);
                break;
            case 4100:
                otIpcBuilder.addBaseInfoCompanyService((OTIpcDef.BaseInfoCompanyService.Builder) builder);
                break;
            case 4101:
                otIpcBuilder.addBaseInfoCompanyPermit((OTIpcDef.BaseInfoCompanyPermit.Builder) builder);
                break;
            case 4102:
                otIpcBuilder.addBaseInfoCompanyFare((OTIpcDef.BaseInfoCompanyFare.Builder) builder);
                break;
            case 4103:
                otIpcBuilder.addBaseInfoVehicle((OTIpcDef.BaseInfoVehicle.Builder) builder);
                break;
            case 4104:
                otIpcBuilder.addBaseInfoVehicleInsurance((OTIpcDef.BaseInfoVehicleInsurance.Builder) builder);
                break;
            case 4105:
                otIpcBuilder.addBaseInfoVehicleTotalMile((OTIpcDef.BaseInfoVehicleTotalMile.Builder) builder);
                break;
            case 4112:
                otIpcBuilder.addBaseInfoDriver((OTIpcDef.BaseInfoDriver.Builder) builder);
                break;
            case 4113:
                otIpcBuilder.addBaseInfoDriverEducate((OTIpcDef.BaseInfoDriverEducate.Builder) builder);
                break;
            case 4114:
                otIpcBuilder.addBaseInfoDriverApp((OTIpcDef.BaseInfoDriverApp.Builder) builder);
                break;
            case 4115:
                otIpcBuilder.addBaseInfoDriverStat((OTIpcDef.BaseInfoDriverStat.Builder) builder);
                break;
            case 4116:
                otIpcBuilder.addBaseInfoPassenger((OTIpcDef.BaseInfoPassenger.Builder) builder);
                break;
            case 8193:
                otIpcBuilder.addOrderCreate((OTIpcDef.OrderCreate.Builder) builder);
                break;
            case 8194:
                otIpcBuilder.addOrderMatch((OTIpcDef.OrderMatch.Builder) builder);
                break;
            case 8195:
                otIpcBuilder.addOrderCancel((OTIpcDef.OrderCancel.Builder) builder);
                break;
            case 12289:
                otIpcBuilder.addOperateLogin((OTIpcDef.OperateLogin.Builder) builder);
                break;
            case 12290:
                otIpcBuilder.addOperateLogout((OTIpcDef.OperateLogout.Builder) builder);
                break;
            case 12291:
                otIpcBuilder.addOperateDepart((OTIpcDef.OperateDepart.Builder) builder);
                break;
            case 12292:
                otIpcBuilder.addOperateArrive((OTIpcDef.OperateArrive.Builder) builder);
                break;
            case 12293:
                otIpcBuilder.addOperatePay((OTIpcDef.OperatePay.Builder) builder);
                break;
            case 16385:
                otIpcBuilder.addPositionDriver((OTIpcDef.PositionDriver.Builder) builder);
                break;
            case 16386:
                otIpcBuilder.addPositionVehicle((OTIpcDef.PositionVehicle.Builder) builder);
                break;
            case 20481:
                otIpcBuilder.addRatedPassenger((OTIpcDef.RatedPassenger.Builder) builder);
                break;
            case 20482:
                otIpcBuilder.addRatedPassengerComplaint((OTIpcDef.RatedPassengerComplaint.Builder) builder);
                break;
            case 20483:
                otIpcBuilder.addRatedDriverPunish((OTIpcDef.RatedDriverPunish.Builder) builder);
                break;
            case 20484:
                otIpcBuilder.addRatedDriver((OTIpcDef.RatedDriver.Builder) builder);
                break;
            case 24577:
                otIpcBuilder.addShareCompany((OTIpcDef.ShareCompany.Builder) builder);
                break;
            case 24578:
                otIpcBuilder.addShareRoute((OTIpcDef.ShareRoute.Builder) builder);
                break;
            case 24579:
                otIpcBuilder.addShareOrder((OTIpcDef.ShareOrder.Builder) builder);
                break;
            case 24580:
                otIpcBuilder.addSharePay((OTIpcDef.SharePay.Builder) builder);
                break;
            default:
                break;
        }
        OTIpcDef.OTIpcList.Builder otIpcListBuilder = OTIpcDef.OTIpcList.newBuilder();
        otIpcListBuilder.addOtpic(otIpcBuilder);
        log.info(" - " + baseMPRequest.getIpcType() + " - 数据上报内容 - " + otIpcListBuilder.build().toString());
        String url = govUrl;
        String urlpath = "";
        urlpath = baseMPRequest.getRequestUrl();
        if (urlpath.length() > 0) {
            if (urlpath.startsWith("/")) {

            } else {
                urlpath = "/" + urlpath;
            }
        }
        url = govUrl + urlpath;
        url = url + "?company=3301YPZCX78Q";
        byte[] bytes = otIpcBuilder.build().toByteArray();
        log.info(" - " + baseMPRequest.getIpcType() + " - 数据上报地址 - " + url);

        return httpClientService.doPost(url, bytes);


    }

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public HttpResult executePositionByProto(String ipcType, String positionData) throws Exception {
        // 封装基础参数
        OTIpcDef.OTIpc.Builder otIpcBuilder = OTIpcDef.OTIpc.newBuilder();
        otIpcBuilder.setCompanyId("3301YPZCX78Q");
        otIpcBuilder.setSource("0");
        otIpcBuilder.setIPCType(OTIpcDef.IpcType.valueOf(ipcType));

        // 封装定位参数
        JsonFormat jsonFormat = new JsonFormat();
        JSONArray array = JSON.parseArray(positionData);
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            Message.Builder builder = IPCBuilder.getValue(ipcType);
            jsonFormat.merge(new ByteArrayInputStream(object.toJSONString().getBytes()), builder);
            switch (otIpcBuilder.getIPCType().getNumber()) {
                case 16385:
                    otIpcBuilder.addPositionDriver((OTIpcDef.PositionDriver.Builder) builder);
                    break;
                case 16386:
                    otIpcBuilder.addPositionVehicle((OTIpcDef.PositionVehicle.Builder) builder);
                    break;
                default:
                    break;
            }
        }
        OTIpcDef.OTIpcList.Builder otIpcListBuilder = OTIpcDef.OTIpcList.newBuilder();
        otIpcListBuilder.addOtpic(otIpcBuilder);
        log.info("none - " + ipcType + " - none - 数据上报内容 - " + otIpcListBuilder.build());

        String urlpath = "";
        if (StringUtils.equals(ipcType, "positionDriver")) {
            urlpath = IPCUrl.POSITIONDRIVER.getValue();
        } else {
            urlpath = IPCUrl.POSITIONVEHICLE.getValue();
        }
        if (urlpath.length() > 0) {
            if (urlpath.startsWith("/")) {
                System.out.println("urlpath startwith //  ");
            } else {
                urlpath = "/" + urlpath;
            }
        }
        String url = govUrl + urlpath;
        url = url + "?company=3301YPZCX78Q";
        byte[] bytes = otIpcListBuilder.build().toByteArray();
        log.info("none - " + ipcType + " - none - 数据上报地址 - " + url);
        return httpClientService.doPost(url, bytes);
    }

//	public HttpResult execute(String url, BaseMPRequest baseMPRequest) throws Exception {
//		return httpClientService.doPost4Json(createUrl(url), baseMPRequest.toJson(new JsonParser() {
//			@Override
//			public String object2Json(BaseMPRequest baseMPRequest) throws Exception {
//				String jsonStr = JSON.toJSONString(baseMPRequest);
//				log.info("报部请求报文信息：" + jsonStr);
//				return jsonStr;
//			}
//		}));
//	}

    private String createUrl(String url) {
        String finalUrl = null;
        if (url == null || url.length() == 0) {
            return finalUrl;
        }
        if (!url.startsWith(PRE_SEPERATE)) {
            url = PRE_SEPERATE + url;
        }
        finalUrl = govUrl + url;
        return finalUrl;
    }

}