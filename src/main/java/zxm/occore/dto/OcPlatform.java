package zxm.occore.dto;

import java.util.Date;

public class OcPlatform{


    private String protocol;
    private String apiKey;
    private String appId;
    private String secret;
    private String platformIp;
    private String platformPort;
    private String psk;
    private Date createDate;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getPlatformIp() {
        return platformIp;
    }

    public void setPlatformIp(String platformIp) {
        this.platformIp = platformIp;
    }

    public String getPlatformPort() {
        return platformPort;
    }

    public void setPlatformPort(String platformPort) {
        this.platformPort = platformPort;
    }

    public String getPsk() {
        return psk;
    }

    public void setPsk(String psk) {
        this.psk = psk;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
