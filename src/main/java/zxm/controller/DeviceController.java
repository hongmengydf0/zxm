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
        QueryDeviceDataHistoryInDTO qddhInDTO = new QueryDeviceDataHistoryInDTO();
        qddhInDTO.setAppId(OCConstant.APP_ID);
        qddhInDTO.setPageNo(0);
        qddhInDTO.setPageSize(1);
        qddhInDTO.setDeviceId(deviceId);
        qddhInDTO.setGatewayId(gatewayId);
//        qddhInDTO.setStartTime(startTime);//yyyyMMdd'T'HHmmss'Z'
//        qddhInDTO.setEndTime(endTime);
        try {
            QueryDeviceDataHistoryOutDTO out = ocService.queryDeviceDataHistory(qddhInDTO);
            return ResultFactory.INSTANCE.success(out);
        } catch (NorthApiException e) {
            logger.error(e.toString());
            return ResultFactory.INSTANCE.error(e.toString());
        }
    }

    @GetMapping("getDeviceHistoryData")
    public Result getDeviceHistoryData(String deviceId, String gatewayId, String startTime, String endTime) {
        QueryDeviceDataHistoryInDTO qddhInDTO = new QueryDeviceDataHistoryInDTO();
        qddhInDTO.setAppId(OCConstant.APP_ID);
        qddhInDTO.setPageNo(0);
        qddhInDTO.setPageSize(100);
        qddhInDTO.setDeviceId(deviceId);
        qddhInDTO.setGatewayId(gatewayId);
        if (StringUtils.isNotEmpty(startTime)) {
            qddhInDTO.setStartTime(Tools.localToUTC(startTime));
        }
        if (StringUtils.isNotEmpty(endTime)) {
            qddhInDTO.setEndTime(Tools.localToUTC(endTime));
        }
        try {
            QueryDeviceDataHistoryOutDTO out = ocService.queryDeviceDataHistory(qddhInDTO);
            return ResultFactory.INSTANCE.success(out);
        } catch (NorthApiException e) {
            logger.error(e.toString());
            return ResultFactory.INSTANCE.error(e.toString());
        }
    }

    @PostMapping("createCommand")
    public Result createCommand(String deviceId) {
        PostDeviceCommandInDTO2 pdcInDTO = new PostDeviceCommandInDTO2();
        pdcInDTO.setCallbackUrl("");
        pdcInDTO.setDeviceId(deviceId);
        pdcInDTO.setExpireTime(0);
        CommandDTOV4 commandDTOV4 = new CommandDTOV4();
        commandDTOV4.setServiceId(OCConstant.SERVICE_ID);
        commandDTOV4.setMethod(OCConstant.METHOD);
        Map<String, Object> param = new HashMap<>();
        param.put("zhuodu", "1");
        param.put("ph", "1");
        param.put("temp", "1");
        param.put("shuiwei", "1");
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


}
