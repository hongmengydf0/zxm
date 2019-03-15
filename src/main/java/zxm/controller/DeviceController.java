package zxm.controller;

import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.QueryBatchDevicesInfoInDTO;
import com.huawei.iotplatform.client.dto.QueryBatchDevicesInfoOutDTO;
import com.huawei.iotplatform.client.dto.QueryDeviceDataHistoryInDTO;
import com.huawei.iotplatform.client.dto.QueryDeviceDataHistoryOutDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zxm.common.result.Result;
import zxm.common.result.ResultFactory;
import zxm.occore.constant.OCConstant;
import zxm.occore.service.IOcService;


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
    public Result getDeviceRealData(String deviceId, String gatewayId, String startTime, String endTime) {
        QueryDeviceDataHistoryInDTO qddhInDTO = new QueryDeviceDataHistoryInDTO();
        qddhInDTO.setAppId(OCConstant.APP_ID);
        qddhInDTO.setPageNo(0);
        qddhInDTO.setPageSize(1);
        qddhInDTO.setDeviceId(deviceId);
        qddhInDTO.setGatewayId(gatewayId);
        qddhInDTO.setStartTime(startTime);
        qddhInDTO.setEndTime(endTime);
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
        qddhInDTO.setStartTime(startTime);
        qddhInDTO.setEndTime(endTime);
        try {
            QueryDeviceDataHistoryOutDTO out = ocService.queryDeviceDataHistory(qddhInDTO);
            return ResultFactory.INSTANCE.success(out);
        } catch (NorthApiException e) {
            logger.error(e.toString());
            return ResultFactory.INSTANCE.error(e.toString());
        }
    }


}
