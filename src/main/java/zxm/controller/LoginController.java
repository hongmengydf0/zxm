package zxm.controller;

import com.huawei.iotplatform.client.NorthApiException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zxm.common.result.Result;
import zxm.common.result.ResultFactory;
import zxm.occore.util.OcTokenHandler;

@RestController()
@RequestMapping("/login")
public class LoginController {


    @PostMapping("/login")
    public Result login(String userName, String password) {
        try {
            String accessToken = OcTokenHandler.getInstance().getAccessToken();
            return ResultFactory.INSTANCE.success(accessToken);
        } catch (NorthApiException e) {
            return ResultFactory.INSTANCE.error(e.toString());
        }
    }

    @PostMapping("/logout")
    public Result logout(String account, String password) {

        return ResultFactory.INSTANCE.success();
    }


}
