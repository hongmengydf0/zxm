package zxm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;


@CrossOrigin
@Controller
public class TransferController {


    @RequestMapping("transfer")
    public String transfer(String url) {
        return "";
    }

}
