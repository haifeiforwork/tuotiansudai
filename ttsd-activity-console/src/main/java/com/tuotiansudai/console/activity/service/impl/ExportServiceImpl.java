package com.tuotiansudai.console.activity.service.impl;

import com.google.common.collect.Lists;
import com.tuotiansudai.console.activity.dto.AutumnExportDto;
import com.tuotiansudai.console.activity.dto.UserItemExportDto;
import com.tuotiansudai.console.activity.service.ExportService;
import com.tuotiansudai.repository.mapper.InvestMapper;
import com.tuotiansudai.repository.mapper.ReferrerRelationMapper;
import com.tuotiansudai.repository.model.InvestModel;
import com.tuotiansudai.repository.model.InvestStatus;
import com.tuotiansudai.repository.model.ReferrerRelationModel;
import com.tuotiansudai.repository.model.UserModel;
import com.tuotiansudai.service.UserService;
import com.tuotiansudai.util.AmountConverter;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExportServiceImpl implements ExportService{

    @Autowired
    private UserService userService;

    @Autowired
    private ReferrerRelationMapper referrerRelationMapper;

    @Autowired
    private InvestMapper investMapper;

    @Value(value = "#{new java.text.SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\").parse(\"${activity.autumn.startTime}\")}")
    private Date activityAutumnStartTime;

    @Value(value = "#{new java.text.SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\").parse(\"${activity.autumn.endTime}\")}")
    private Date activityAutumnEndTime;


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private Map<String, Map<String, List<String>>> getHomeMap(){
        Map<String, Map<String, List<String>>>  homeMap = new HashMap<String, Map<String, List<String>>>();
        int homeSeq = 1;
        List<UserModel> userModelList =  userService.findByRegisterTime(activityAutumnStartTime, activityAutumnEndTime);
        for(UserModel userModel:userModelList){
            List<ReferrerRelationModel> referrerRelationModelList = referrerRelationMapper.findByReferrerLoginNameAndLevel(userModel.getLoginName(), 1);
            if(referrerRelationModelList.size() == 1 && !homeMap.containsKey(referrerRelationModelList.get(0).getReferrerLoginName())){
                //找出当前团长的所有团员
                List<ReferrerRelationModel> referrerRelationModelListAll = referrerRelationMapper.findByReferrerLoginName(referrerRelationModelList.get(0).getReferrerLoginName());
                Map<String, List<String>> userMap = new HashMap<String, List<String>>();
                List<String> alluser = Lists.newArrayList();
                for(ReferrerRelationModel referrerRelationModel: referrerRelationModelListAll){
                    //过滤出只在活动期间内的团员
                    if(userModel.getLoginName().equals(referrerRelationModel.getLoginName()))
                    {
                        alluser.add(referrerRelationModel.getLoginName());
                    }
                }
                //团长本人
                alluser.add(referrerRelationModelList.get(0).getReferrerLoginName());

                userMap.put("团员" + homeSeq + "号家庭|"+ sdf.format(userModel.getRegisterTime()), alluser);
                homeMap.put(referrerRelationModelList.get(0).getReferrerLoginName(), userMap);
                homeSeq ++;
            }
        }
        return homeMap;
    }


    @Override
    public List<AutumnExportDto> getAutumnExport(){
        List<AutumnExportDto> autumnExportDtoList = Lists.newArrayList();
        Map<String, Map<String, List<String>>>  homeMap = getHomeMap();
        AutumnExportDto autumnExportDto = new AutumnExportDto();
        for(Map.Entry<String,  Map<String, List<String>>> entry:homeMap.entrySet()){
            long totalAmount = 0;
            Map<String, List<String>> allUser = entry.getValue();
            for(Map.Entry<String, List<String>> entry1:allUser.entrySet()){
                String nameAndRegisterTime[] = entry1.getKey().split("|");
                for(String loginName: entry1.getValue()){
                    totalAmount += investMapper.sumInvestAmount(null, loginName, null, null, null, new DateTime(nameAndRegisterTime[1]).withTimeAtStartOfDay().toDate(), new DateTime(nameAndRegisterTime[1]).withTime(23,59,59,0).toDate(), InvestStatus.SUCCESS, null);
                }
                List<InvestModel> investModelList = investMapper.findSuccessInvestByInvestTime(new DateTime(nameAndRegisterTime[1]).withTimeAtStartOfDay().toDate(), new DateTime(nameAndRegisterTime[1]).withTime(23,59,59,0).toDate());
                for(InvestModel investModel:investModelList){
                    autumnExportDto.setName(nameAndRegisterTime[0]);
                    autumnExportDto.setTotalAmount(totalAmount);
                    autumnExportDto.setInvestTime(new DateTime(nameAndRegisterTime[1]).toDate());
                    if(totalAmount > 5000000){
                        autumnExportDto.setPrize("50元红包");
                    }
                    if(totalAmount > 2000000){
                        autumnExportDto.setPrize("15元红包");
                    }
                    if(totalAmount > 1000000){
                        autumnExportDto.setPrize("5元红包");
                    }
                    autumnExportDto.setLoginName(investModel.getLoginName());
                    autumnExportDto.setInvestAmount(investModel.getAmount());
                    autumnExportDto.setJoinTime(investModel.getCreatedTime());
                    autumnExportDto.setMobile("222222");
                    autumnExportDtoList.add(autumnExportDto);
                }
            }
        }
        return autumnExportDtoList;
    }

    @Override
    public List<List<String>> buildAutumnList(List<AutumnExportDto> records) {
        List<List<String>> rows = Lists.newArrayList();
        for (AutumnExportDto record : records) {
            List<String> row = Lists.newArrayList();
            row.add(record.getName());
            row.add(AmountConverter.convertCentToString(record.getTotalAmount()));
            row.add(new DateTime(record.getInvestTime()).toString("yyyy-MM-dd"));
            row.add(record.getPrize());
            row.add(record.getLoginName());
            row.add(AmountConverter.convertCentToString(record.getInvestAmount()));
            row.add(new DateTime(record.getJoinTime()).toString("HH:mm:ss"));
            row.add(record.getMobile());
            rows.add(row);
        }
        return rows;
    }
}
