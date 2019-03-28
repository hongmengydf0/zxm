package zxm.controller;

import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zxm.common.result.Result;
import zxm.common.result.ResultFactory;
import zxm.occore.constant.OCConstant;
import zxm.occore.service.IOcService;
import zxm.occore.util.Tools;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/device")
public class DeviceController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IOcService ocService;

    @GetMapping("getAllDevice")
    public Result getAllDevice() {
        QueryBatchDevicesInfoInDTO inDTO = new QueryBatchDevicesInfoInDTO();
        try {
            inDTO.setAppId(OCConstant.APP_ID);
            inDTO.setPageSize(1000);
            inDTO.setPageNo(0);
            QueryBatchDevicesInfoOutDTO outDTO = ocService.getDeivceList(inDTO);
            return ResultFactory.INSTANCE.success(outDTO.getDevices());
        } catch (NorthApiException e) {
            logger.error(e.toString());
            return ResultFactory.INSTANCE.error(e.toString());
        }
    }

    @GetMapping("getDeviceRealData")
    public Result getDeviceRealData(String deviceId, String gatewayId) {
        try {
            QueryDeviceDataHistoryOutDTO out = getHistoryData(deviceId, gatewayId, null, null, 1);
            return ResultFactory.INSTANCE.success(out);
        } catch (NorthApiException e) {
            logger.error(e.toString());
            return ResultFactory.INSTANCE.error(e.toString());
        }
    }

    @GetMapping("getDeviceHistoryData")
    public Result getDeviceHistoryData(String deviceId, String gatewayId, String startTime, String endTime) {

        try {
            QueryDeviceDataHistoryOutDTO out = getHistoryData(deviceId, gatewayId, startTime, endTime, 100);
            return ResultFactory.INSTANCE.success(out);
        } catch (NorthApiException e) {
            logger.error(e.toString());
            return ResultFactory.INSTANCE.error(e.toString());
        }
    }

    @PostMapping("createCommand")
    public Result createCommand(String deviceId, String gatewayId) {
        //先获取实时数据
        QueryDeviceDataHistoryOutDTO out;
        try {
            out = getHistoryData(deviceId, gatewayId, null, null, 1);
        } catch (NorthApiException e) {
            logger.error(e.toString());
            return ResultFactory.INSTANCE.error(e.toString());
        }
        //判断
        String shuiwei = out.getDeviceDataHistoryDTOs().get(0).getData().path("shuiwei").asText();
        String key = Integer.valueOf(shuiwei) >= 1 ? "ON" : "OFF";

        PostDeviceCommandInDTO2 pdcInDTO = new PostDeviceCommandInDTO2();
        pdcInDTO.setCallbackUrl("");
        pdcInDTO.setDeviceId(deviceId);
        pdcInDTO.setExpireTime(0);
        CommandDTOV4 commandDTOV4 = new CommandDTOV4();
        commandDTOV4.setServiceId(OCConstant.SERVICE_ID);
        commandDTOV4.setMethod(OCConstant.METHOD);
        Map<String, Object> param = new HashMap<>();
        param.put("Key", key); //ON,OFF
        commandDTOV4.setParas(param);
        pdcInDTO.setCommand(commandDTOV4);
        try {
            PostDeviceCommandOutDTO2 postDeviceCommandOutDTO2 = ocService
                    .createDeviceCommand(OCConstant.APP_ID, pdcInDTO);
            logger.info(postDeviceCommandOutDTO2.toString());
            return ResultFactory.INSTANCE.success("下发成功");
        } catch (NorthApiException e) {
            logger.error(e.toString());
            return ResultFactory.INSTANCE.error(e.toString());
        }
    }

    /**
     * 获取平台数据
     *
     * @param deviceId
     * @param gatewayId
     * @param startTime
     * @param endTime
     * @return
     * @throws NorthApiException
     */

    private QueryDeviceDataHistoryOutDTO getHistoryData(String deviceId, String gatewayId, String startTime,
                                                        String endTime, Integer pageSize) throws NorthApiException {

        QueryDeviceDataHistoryInDTO qddhInDTO = new QueryDeviceDataHistoryInDTO();
        qddhInDTO.setAppId(OCConstant.APP_ID);
        qddhInDTO.setPageNo(0);
        qddhInDTO.setPageSize(pageSize);
        qddhInDTO.setDeviceId(deviceId);
        qddhInDTO.setGatewayId(gatewayId);
        if (StringUtils.isNotEmpty(startTime)) {
            qddhInDTO.setStartTime(Tools.localToUTC(startTime));
        }
        if (StringUtils.isNotEmpty(endTime)) {
            qddhInDTO.setEndTime(Tools.localToUTC(endTime));
        }
        QueryDeviceDataHistoryOutDTO out = ocService.queryDeviceDataHistory(qddhInDTO);
        return out;
    }


}
