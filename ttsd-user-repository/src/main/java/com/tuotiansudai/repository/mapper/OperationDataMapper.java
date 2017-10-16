package com.tuotiansudai.repository.mapper;

import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface OperationDataMapper {

    List<Integer> findScaleByGender(Date endDate);

    long findCountInvestCityScale(Date endDate);

    List<Map<String, String>> findCountInvestCityScaleTop3(Date endDate);

    List<Map<String, String>> findAgeDistributionByAge(Date endDate);
}
