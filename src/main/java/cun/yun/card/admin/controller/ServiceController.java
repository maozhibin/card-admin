package cun.yun.card.admin.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping("sms")
public class ServiceController {
    @RequestMapping(value = "service/check" ,method = RequestMethod.GET)
    public Long check(HttpServletRequest request){
        String uri = request.getRequestURI();
        return System.currentTimeMillis();
    }
}
