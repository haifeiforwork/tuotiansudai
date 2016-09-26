package com.tuotiansudai.service;


import com.tuotiansudai.dto.BasePaginationDataDto;
import com.tuotiansudai.dto.MysteriousPrizeDto;
import com.tuotiansudai.repository.model.ActivityCategory;
import com.tuotiansudai.repository.model.GivenMembership;
import com.tuotiansudai.repository.model.HeroRankingView;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface HeroRankingService {

    BasePaginationDataDto<HeroRankingView> findHeroRankingByReferrer(Date tradingTime, String loginName, int index, int pageSize);

    Integer findHeroRankingByReferrerLoginName(ActivityCategory activityCategory,String loginName);

    List<HeroRankingView> obtainHeroRanking(ActivityCategory activityCategory,Date tradingTime);

    List<HeroRankingView> obtainHeroRankingReferrer(ActivityCategory activityCategory,Date tradingTime);

    Map obtainHeroRankingAndInvestAmountByLoginName(ActivityCategory activityCategory,Date tradingTime, String loginName);

    void saveMysteriousPrize(MysteriousPrizeDto MysteriousPrizeDto);

    MysteriousPrizeDto obtainMysteriousPrizeDto(String prizeDate);

    GivenMembership receiveMembership(String loginName);

    List<String> getActivityTime();
}
