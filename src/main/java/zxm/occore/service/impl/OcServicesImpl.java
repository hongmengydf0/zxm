package zxm.occore.service.impl;

import com.huawei.iotplatform.client.NorthApiClient;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.*;
import com.huawei.iotplatform.client.invokeapi.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import zxm.occore.constant.OCConstant;
import zxm.occore.dto.OcPlatform;
import zxm.occore.service.IOcServices;
import zxm.occore.util.OcTokenHandler;

import java.util.List;

@Service
public class OcServicesImpl implements IOcServices {


    private Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String... args) {

//        OcServicesImpl huaWeiServices = new OcServicesImpl();
//        OcPlatform platform = new OcPlatform();
//        platform.setAppId("YTk312uISbLKXghRYimfxyfdrYwa");
//        platform.setSecret("R46AEGemwQAGJIyrsFzFLcBe6KIa");
//        platform.setPlatformIp("139.159.133.59");
//        platform.setPlatformPort("8743");
//        NorthApiClient northApiClient = huaWeiServices.defaultApiClient();
//        String accessToken = huaWeiServices.getAuthToken(northApiClient).getAccessToken();
//        System.out.println("accessToken ========== " + accessToken);


//        DataCollection dataCollection = new DataCollection(northApiClient);

//        QueryBatchDevicesInfoInDTO dto = new QueryBatchDevicesInfoInDTO();
//        dto.setAppId("YTk312uISbLKXghRYimfxyfdrYwa");
//        dto.setPageNo(0);
//        dto.setPageSize(10);
//        try {
//            QueryBatchDevicesInfoOutDTO outDTO = dataCollection.queryBatchDevicesInfo(dto, accessToken);
//            System.out.println(outDTO.getDevices().get(0).toString());
//            System.out.println(outDTO.getDevices().get(0).getDeviceInfo().toString());
//        } catch (NorthApiException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 获取默认的NorthApiClient
     *
     * @return
     * @throws NorthApiException
     */
    public NorthApiClient defaultApiClient() throws NorthApiException {
        return OcTokenHandler.getInstance().defaultApiClient();
    }


    /**
     * 初始化NorthApiClient
     *
     * @param platform
     * @return
     */
    public NorthApiClient initApiClient(OcPlatform platform) throws NorthApiException {
        NorthApiClient northApiClient = new NorthApiClient();
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setAppId(platform.getAppId());
        clientInfo.setPlatformIp(platform.getPlatformIp());
        clientInfo.setPlatformPort(platform.getPlatformPort());
        clientInfo.setSecret(platform.getSecret());
        northApiClient.setClientInfo(clientInfo);
        northApiClient.initSSLConfig();
        return northApiClient;
    }

    /**
     * 取accessToken
     *
     * @return
     */
    public String getAuthToken(NorthApiClient northApiClient) throws NorthApiException {
        String accessToken = OcTokenHandler.getInstance().getAuthToken(northApiClient);
        return accessToken;
    }

    /**
     * 批量查询设备
     *
     * @param platform
     * @param dto
     * @return
     * @throws NorthApiException
     */
    @Override
    public QueryBatchDevicesInfoOutDTO getDeivceList(OcPlatform platform, QueryBatchDevicesInfoInDTO dto) throws NorthApiException {
        NorthApiClient northApiClient = defaultApiClient();
        String accessToken = getAuthToken(northApiClient);
        DataCollection dataCollection = new DataCollection(northApiClient);
        QueryBatchDevicesInfoOutDTO outDTO = dataCollection.queryBatchDevicesInfo(dto, accessToken);
        return outDTO;

    }

    /**
     * 查询单个设备
     *
     * @param platform
     * @param deviceId
     * @return
     * @throws NorthApiException
     */
    @Override
    public QuerySingleDeviceInfoOutDTO getDevice(OcPlatform platform, String deviceId) throws NorthApiException {
        NorthApiClient northApiClient = defaultApiClient();
        String accessToken = getAuthToken(northApiClient);
        DataCollection dataCollection = new DataCollection(northApiClient);
        return dataCollection.querySingleDeviceInfo(deviceId, null, platform.getAppId(), accessToken);
    }


    @Override
    public RegDirectDeviceOutDTO registerDevice(OcPlatform platform, String verifyCode, String nodeId) throws NorthApiException {
        NorthApiClient northApiClient = defaultApiClient();
        String accessToken = getAuthToken(northApiClient);
        DeviceManagement deviceManagement = new DeviceManagement(northApiClient);

        RegDirectDeviceInDTO2 rddid = new RegDirectDeviceInDTO2();
        rddid.setNodeId(nodeId);
        rddid.setVerifyCode(verifyCode);

        RegDirectDeviceOutDTO rddod = deviceManagement.regDirectDevice(rddid, platform.getAppId(), accessToken);

        return rddod;

    }

    @Override
    public QueryDeviceDataHistoryOutDTO queryDeviceDataHistory(OcPlatform platform,
                                                               QueryDeviceDataHistoryInDTO qddhInDTO) throws NorthApiException {
        NorthApiClient northApiClient = defaultApiClient();
        String accessToken = getAuthToken(northApiClient);
        DataCollection dataCollection = new DataCollection(northApiClient);

        return dataCollection.queryDeviceDataHistory(qddhInDTO, accessToken);
    }

    @Override
    public void modifyDevice(OcPlatform platform, ModifyDeviceInforInDTO modifyDeviceInforInDTO, String deviceId) throws NorthApiException {
        NorthApiClient northApiClient = defaultApiClient();
        String accessToken = getAuthToken(northApiClient);
        DeviceManagement deviceManagement = new DeviceManagement(northApiClient);

        deviceManagement.modifyDeviceInfo(modifyDeviceInforInDTO, deviceId, platform.getAppId(), accessToken);
    }

    @Override
    public void deleteDevice(OcPlatform platform, String deviceId, Boolean cascade) throws NorthApiException {
        NorthApiClient northApiClient = defaultApiClient();
        String accessToken = getAuthToken(northApiClient);
        DeviceManagement deviceManagement = new DeviceManagement(northApiClient);
        deviceManagement.deleteDirectDevice(deviceId, cascade, platform.getAppId(), accessToken);
    }


    /**
     * 订阅平台业务数据
     *
     * @param platform
     * @param notifyType  通知类型
     * @param callbackUrl
     * @return
     * @throws NorthApiException
     */
    @Override
    public boolean subDeviceData(OcPlatform platform, OCConstant.NotifyType notifyType, String callbackUrl) throws NorthApiException {
        NorthApiClient northApiClient = defaultApiClient();
        String accessToken = getAuthToken(northApiClient);

        SubscriptionManagement subscriptionManagement = new SubscriptionManagement(northApiClient);
        SubDeviceDataInDTO sddInDTO = new SubDeviceDataInDTO();
        sddInDTO.setNotifyType(notifyType.getName());
        sddInDTO.setCallbackUrl(callbackUrl);
        subscriptionManagement.subDeviceData(sddInDTO, null, accessToken);
        return true;
    }

    @Override
    public boolean deleteBatchSubscriptions(OcPlatform platform, OCConstant.NotifyType notifyType) throws NorthApiException {
        NorthApiClient northApiClient = defaultApiClient();
        String accessToken = getAuthToken(northApiClient);

        SubscriptionManagement subscriptionManagement = new SubscriptionManagement(northApiClient);
        DeleteBatchSubInDTO sddInDTO = new DeleteBatchSubInDTO();
        sddInDTO.setNotifyType(notifyType.getName());
        sddInDTO.setAppId(platform.getAppId());
        subscriptionManagement.deleteBatchSubscriptions(sddInDTO, accessToken);
        return true;
    }

    @Override
    public List<RuleDTO2> getTriggerList(OcPlatform seleProjPlat, String name) throws NorthApiException {
        List<RuleDTO2> ruleList;
        NorthApiClient northApiClient = initApiClient(seleProjPlat);
        String accessToken = getAuthToken(northApiClient);
        RuleEngine ruleEngine = new RuleEngine(northApiClient);
        QueryRulesInDTO2 qrInDTO = new QueryRulesInDTO2();
        qrInDTO.setAuthor(seleProjPlat.getAppId());//
        qrInDTO.setAppId(seleProjPlat.getAppId());
        ruleList = ruleEngine.queryRules(qrInDTO, accessToken);
        return ruleList;
    }

    @Override
    public boolean dropTrigger(OcPlatform seleProjPlat, String triggerId) {
        return false;
    }


    @Override
    public PostDeviceCommandOutDTO2 createDeviceCommand(OcPlatform donglePlatform, PostDeviceCommandInDTO2 pdcInDTO) throws NorthApiException {
        NorthApiClient northApiClient = initApiClient(donglePlatform);
        String accessToken = getAuthToken(northApiClient);
        SignalDelivery cmd = new SignalDelivery(northApiClient);
        PostDeviceCommandOutDTO2 postDeviceCommandOutDTO2 = cmd
                .postDeviceCommand(pdcInDTO, donglePlatform.getAppId(), accessToken);
        return postDeviceCommandOutDTO2;
    }

}
