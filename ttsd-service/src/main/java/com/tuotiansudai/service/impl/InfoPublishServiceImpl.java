package com.tuotiansudai.service.impl;

import com.tuotiansudai.client.RedisWrapperClient;
import com.tuotiansudai.repository.mapper.InvestMapper;
import com.tuotiansudai.repository.model.InvestDataView;
import com.tuotiansudai.service.InfoPublishService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class InfoPublishServiceImpl implements InfoPublishService {

    @Autowired
    private InvestMapper investMapper;

    @Autowired
    private RedisWrapperClient redisWrapperClient;

    static Logger logger = Logger.getLogger(InfoPublishServiceImpl.class);

    private static final String INFO_PUBLISH_KEY_TEMPLATE = "web:info:publish:table:{0}";

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Override
    public List<InvestDataView> getInvestDetail() {
        List<InvestDataView> investDataViewList = new ArrayList<InvestDataView>();
        if(redisWrapperClient.exists(MessageFormat.format(INFO_PUBLISH_KEY_TEMPLATE, String.valueOf(sdf.format(new Date()))))){
            Map<String,String> map = redisWrapperClient.hgetAll(MessageFormat.format(INFO_PUBLISH_KEY_TEMPLATE, String.valueOf(sdf.format(new Date()))));
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String[] investDataViewValues = entry.getValue().split("\\|");
                InvestDataView investDataView = new InvestDataView();
                investDataView.setProductName(investDataViewValues[0]);
                investDataView.setTotalInvestAmount(investDataViewValues[1]);
                investDataView.setCountInvest(Integer.parseInt(investDataViewValues[2]));
                investDataView.setAvgInvestAmount(investDataViewValues[3]);
                investDataViewList.add(investDataView);
            }
        }
        else{
            investDataViewList = investMapper.getInvestDetail();
            for(InvestDataView investDataView: investDataViewList){
                redisWrapperClient.hset(MessageFormat.format(INFO_PUBLISH_KEY_TEMPLATE, String.valueOf(sdf.format(new Date()))), investDataView.getProductName(), investDataView.ConvertInvestDataViewToString(), 60*60*24);
            }
        }
        return investDataViewList;
    }
}
