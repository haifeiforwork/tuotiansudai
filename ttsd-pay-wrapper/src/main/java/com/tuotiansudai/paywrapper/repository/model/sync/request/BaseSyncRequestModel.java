package com.tuotiansudai.paywrapper.repository.model.sync.request;

import com.google.common.collect.Maps;
import com.tuotiansudai.etcd.ETCDConfigReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

public abstract class BaseSyncRequestModel {

    private static Logger logger = Logger.getLogger(BaseSyncRequestModel.class);

    public final static String HUIZU_API_HOST = ETCDConfigReader.getReader().getValue("huizu.api.host");

    public final static String CREDIT_LOAN_ID = ETCDConfigReader.getReader().getValue("credit.loan");

    public final static String PAY_CALLBACK_WEB_HOST = ETCDConfigReader.getReader().getValue("pay.callback.web.host");

    public final static String PAY_CALLBACK_APP_WEB_HOST = ETCDConfigReader.getReader().getValue("pay.callback.app.web.host");

    public final static String PAY_CALLBACK_BACK_HOST = ETCDConfigReader.getReader().getValue("pay.callback.back.host");

    public final static String PAY_CALLBACK_CONSOLE_HOST = ETCDConfigReader.getReader().getValue("pay.callback.console.host");

    protected static Properties UMP_PROPS = new Properties();

    private Long id;

    protected String service;

    private String signType;

    private String sign;

    private String charset;

    private String version;

    private String merId;

    private String sourceV;

    private Date requestTime = new Date();

    private String requestData;

    private String requestUrl;

    private SyncRequestStatus status = SyncRequestStatus.READY;

    static {
        if (UMP_PROPS.isEmpty()) {
            try {
                Resource resource = new ClassPathResource("SignVerProp.properties");
                BaseSyncRequestModel.UMP_PROPS = PropertiesLoaderUtils.loadProperties(resource);

            } catch (IOException e) {
                logger.error("SignVerProp.properties 不存在!");
                logger.error(e);
            }
        }
    }

    public BaseSyncRequestModel() {
        this.signType = UMP_PROPS.getProperty("sign_type");
        this.charset = UMP_PROPS.getProperty("charset");
        this.version = UMP_PROPS.getProperty("version");
        this.merId = UMP_PROPS.getProperty("mer_id");
    }

    public Map<String, String> generatePayRequestData() {
        Map<String, String> payRequestData = Maps.newHashMap();
        payRequestData.put("service", this.service);
        payRequestData.put("sign_type", this.signType);
        payRequestData.put("charset", this.charset);
        payRequestData.put("mer_id", this.merId);
        payRequestData.put("version", this.version);
        if(StringUtils.isNotEmpty(this.sourceV)){
            payRequestData.put("sourceV", this.sourceV);
        }
        return payRequestData;
    }

    public Long getId() {
        return id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public SyncRequestStatus getStatus() {
        return status;
    }

    public void setStatus(SyncRequestStatus status) {
        this.status = status;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getSourceV() {
        return sourceV;
    }

    public void setSourceV(String sourceV) {
        this.sourceV = sourceV;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseSyncRequestModel that = (BaseSyncRequestModel) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }


}
