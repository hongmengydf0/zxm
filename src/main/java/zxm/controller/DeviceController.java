package zxm.controller;

import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.QueryBatchDevicesInfoInDTO;
import com.huawei.iotplatform.client.dto.QueryBatchDevicesInfoOutDTO;
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


@RestController()
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

}
