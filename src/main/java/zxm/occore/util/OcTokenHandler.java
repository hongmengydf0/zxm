package zxm.occore.util;

import com.huawei.iotplatform.client.NorthApiClient;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.AuthOutDTO;
import com.huawei.iotplatform.client.dto.ClientInfo;
import com.huawei.iotplatform.client.invokeapi.Authentication;
import org.apache.commons.lang3.StringUtils;
import zxm.occore.constant.OCConstant;

public class OcTokenHandler {

    private final static OcTokenHandler INSTANCE = new OcTokenHandler();
    private String appId = "";
    private String secret = "";
    private String scope;
    private String tokenType;
    private long expiresIn;
    private String accessToken;
    private String refreshToken;
    private long createTime;

    private OcTokenHandler() {
    }

    public static OcTokenHandler getInstance() {
        return INSTANCE;
    }

    public String getAuthToken(NorthApiClient northApiClient) throws NorthApiException {

        //获取到
        if (StringUtils.isNotEmpty(accessToken)) {
            long exppires = System.currentTimeMillis();
            //是否过期
            boolean b = (exppires - this.createTime) >= this.expiresIn;
            if (!b) {
                return accessToken;
            }
        }
        //未取到或者已经过期，直接调用API去获取
        long exppires = System.currentTimeMillis();
        if (northApiClient == null) {
            northApiClient = defaultApiClient();
        }

        Authentication authentication = new Authentication(northApiClient);
        AuthOutDTO authOutDTO = authentication.getAuthToken();
        accessToken = authOutDTO.getAccessToken();
        this.createTime = exppires;
        this.refreshToken = authOutDTO.getRefreshToken();
        this.scope = authOutDTO.getScope();
        this.tokenType = authOutDTO.getTokenType();
        return accessToken;
    }

    public NorthApiClient defaultApiClient() throws NorthApiException {
        NorthApiClient northApiClient = new NorthApiClient();
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setAppId(OCConstant.APP_ID);
        clientInfo.setPlatformIp(OCConstant.IP);
        clientInfo.setPlatformPort(OCConstant.PORT);
        clientInfo.setSecret(OCConstant.SECRET);
        northApiClient.setClientInfo(clientInfo);
        northApiClient.initSSLConfig();
        return northApiClient;
    }

    public String getAccessToken() throws NorthApiException {
        return getAuthToken(null);
    }

    private void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
