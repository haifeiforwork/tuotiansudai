package com.ttsd.api.controller;

import com.ttsd.api.dto.BaseParamDto;
import com.ttsd.api.service.MobileAppChannelService;
import com.ttsd.api.service.impl.MobileAppChannelServiceImpl;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MobileAppChannelController {

    @Autowired
    private MobileAppChannelService mobileAppChannelService;

    @RequestMapping(value = "/domob", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject clickNotifyDomob(HttpServletRequest request) {
        boolean recordSuccess;
        if (MobileAppChannelServiceImpl.MOBILE_APP_ID
                .equalsIgnoreCase(request.getParameter("appkey"))) {
            recordSuccess = recordChannelDomob(request.getParameter("mac"),
                    request.getParameter("macmd5"),
                    request.getParameter("ifa"),
                    request.getParameter("ifamd5"),
                    request.getParameter("source"));
        } else {
            recordSuccess = false;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "message");
        jsonObject.put("success", recordSuccess);
        return jsonObject;
    }

    @RequestMapping(value = "/install-notify", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject installNotify(BaseParamDto paramDto) {
        mobileAppChannelService.sendInstallNotify(paramDto.getBaseParam());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", "");
        jsonObject.put("code", "0000");
        return jsonObject;
    }

    public boolean recordChannelDomob(String mac, String macmd5, String ifa, String ifamd5, String subChannel) {
        String channel = "domob";
        boolean r1 = mobileAppChannelService.recordDeviceId("mac", mac, channel, subChannel);
        boolean r2 = mobileAppChannelService.recordDeviceId("macmd5", macmd5, channel, subChannel);
        boolean r3 = mobileAppChannelService.recordDeviceId("ifa", ifa, channel, subChannel);
        boolean r4 = mobileAppChannelService.recordDeviceId("ifamd5", ifamd5, channel, subChannel);
        return (r1 || r2 || r3 || r4);
    }
}
