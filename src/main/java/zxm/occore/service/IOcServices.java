package zxm.occore.service;


import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.*;
import zxm.occore.constant.OCConstant;
import zxm.occore.dto.OcPlatform;

import java.util.List;

public interface IOcServices {

    /**
     * 批量查询
     *
     * @param platform
     * @param dto
     * @return
     * @throws NorthApiException
     */
    QueryBatchDevicesInfoOutDTO getDeivceList(OcPlatform platform, QueryBatchDevicesInfoInDTO dto) throws NorthApiException;

    /**
     * 查询单个设备
     *
     * @param platform
     * @param deviceId
     * @return
     * @throws NorthApiException
     */
    QuerySingleDeviceInfoOutDTO getDevice(OcPlatform platform, String deviceId) throws NorthApiException;

    /**
     * 注册直连设备
     *
     * @param platform
     * @param verifyCode 在NB-IoT方案中，verifyCode为必填参数，且必须与nodeId设置成相同值。
     * @param nodeId
     * @return
     */
    RegDirectDeviceOutDTO registerDevice(OcPlatform platform, String verifyCode, String nodeId) throws NorthApiException;

    /**
     * 查询设备历史数据
     *
     * @param platform
     * @param qddhInDTO
     * @return
     * @throws NorthApiException
     */
    QueryDeviceDataHistoryOutDTO queryDeviceDataHistory(OcPlatform platform,
                                                        QueryDeviceDataHistoryInDTO qddhInDTO) throws NorthApiException;

    /**
     * 修改设备信息
     *
     * @param platform
     * @param modifyDeviceInforInDTO
     * @param deviceId
     * @return
     */
    void modifyDevice(OcPlatform platform, ModifyDeviceInforInDTO modifyDeviceInforInDTO, String deviceId) throws NorthApiException;


    /**
     * 删除
     *
     * @param platform
     * @param deviceId
     * @param cascade  仅当设备下连接了非直连设备时生效，不设置时可填写null。
     *                 true，级联删除，即删除直连设备和其下的非直连设备。
     *                 false，删除直连设备，但是不删其下的非直连设备，并将非直连设备的属性变为直连设备属性。
     * @throws NorthApiException
     */
    void deleteDevice(OcPlatform platform, String deviceId, Boolean cascade) throws NorthApiException;

    /**
     * 添加触发器
     *
     * @param seleProjPlat
     * @param ocRuleDTO
     * @param deviceid
     * @return
     * @throws NorthApiException
     */
//    public String addTrigger(OcPlatform seleProjPlat, OCRuleDTO ocRuleDTO, String deviceid, String
//    projectPlatformId) throws NorthApiException;

    /**
     * 获取触发器
     *
     * @param seleProjPlat
     * @param name
     * @return
     * @throws NorthApiException
     */
    public List<RuleDTO2> getTriggerList(OcPlatform seleProjPlat, String name) throws NorthApiException;

    /**
     * 修改触发器
     *
     * @param ocRuleDTO
     * @return
     * @throws NorthApiException
     */
//    public boolean editTrigger(OcPlatform seleProjPlat, OCRuleDTO ocRuleDTO) throws NorthApiException;

    /**
     * 删除触发器
     *
     * @param seleProjPlat
     * @param triggerId
     * @return
     * @throws NorthApiException
     */
    public boolean dropTrigger(OcPlatform seleProjPlat, String triggerId) throws NorthApiException;

    /**
     * 订阅平台业务数据
     *
     * @param platform
     * @param notifyType
     * @param callbackUrl
     * @return
     * @throws NorthApiException
     */
    boolean subDeviceData(OcPlatform platform, OCConstant.NotifyType notifyType, String callbackUrl) throws NorthApiException;

    /**
     * 订阅平台业务数据
     *
     * @param platform
     * @param notifyType
     * @return
     * @throws NorthApiException
     */
    boolean deleteBatchSubscriptions(OcPlatform platform, OCConstant.NotifyType notifyType) throws NorthApiException;

    /**
     * 设备命令下发
     *
     * @param donglePlatform
     * @param pdcInDTO
     * @return postDeviceCommandOutDTO2
     * @throws NorthApiException
     */
    PostDeviceCommandOutDTO2 createDeviceCommand(OcPlatform donglePlatform, PostDeviceCommandInDTO2 pdcInDTO) throws NorthApiException;

}
