package com.tuotiansudai.service.impl;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tuotiansudai.client.RedisWrapperClient;
import com.tuotiansudai.dto.BasePaginationDataDto;
import com.tuotiansudai.dto.MysteriousPrizeDto;
import com.tuotiansudai.membership.repository.mapper.MembershipMapper;
import com.tuotiansudai.membership.repository.mapper.UserMembershipMapper;
import com.tuotiansudai.membership.repository.model.MembershipLevel;
import com.tuotiansudai.membership.repository.model.UserMembershipModel;
import com.tuotiansudai.membership.repository.model.UserMembershipType;
import com.tuotiansudai.repository.mapper.AccountMapper;
import com.tuotiansudai.repository.mapper.InvestMapper;
import com.tuotiansudai.repository.model.ActivityCategory;
import com.tuotiansudai.repository.model.GivenMembership;
import com.tuotiansudai.repository.model.HeroRankingView;
import com.tuotiansudai.service.HeroRankingService;
import com.tuotiansudai.transfer.repository.mapper.TransferApplicationMapper;
import com.tuotiansudai.util.AmountConverter;
import com.tuotiansudai.util.RandomUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.http.client.utils.DateUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class HeroRankingServiceImpl implements HeroRankingService {

    static Logger logger = Logger.getLogger(HeroRankingServiceImpl.class);

    @Autowired
    private InvestMapper investMapper;

    @Autowired
    private TransferApplicationMapper transferApplicationMapper;

    @Autowired
    private RandomUtils randomUtils;

    @Autowired
    private RedisWrapperClient redisWrapperClient;

    private final static String MYSTERIOUSREDISKEY = "console:mysteriousPrize";

    @Value("#{'${web.heroRanking.activity.period}'.split('\\~')}")
    private List<String> heroRankingActivityPeriod = Lists.newArrayList();

    @Value("#{'${activity.new.heroRanking.period}'.split('\\~')}")
    private List<String> newHeroRankingActivityPeriod = Lists.newArrayList();

    @Autowired
    private MembershipMapper membershipMapper;

    @Autowired
    private UserMembershipMapper userMembershipMapper;

    @Autowired
    private AccountMapper accountMapper;

    private int lifeSecond = 5184000;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Override
    public List<HeroRankingView> obtainHeroRanking(ActivityCategory activityCategory,Date tradingTime) {
        if (tradingTime == null) {
            logger.debug("tradingTime is null");
            return null;
        }
        tradingTime = new DateTime(tradingTime).withTimeAtStartOfDay().plusDays(1).minusMillis(1).toDate();

        List<String> activityPeriod = getActivityPeriod(activityCategory);
        List<HeroRankingView> heroRankingViews = investMapper.findHeroRankingByTradingTime(tradingTime, activityPeriod.get(0), activityPeriod.get(1));

        return CollectionUtils.isNotEmpty(heroRankingViews) && heroRankingViews.size() > 10 ? heroRankingViews.subList(0, 10) : heroRankingViews;
    }

    @Override
    public List<HeroRankingView> obtainHeroRankingReferrer(ActivityCategory activityCategory,Date tradingTime) {
        List<String> activityPeriod = getActivityPeriod(activityCategory);
        return investMapper.findHeroRankingByReferrer(tradingTime, activityPeriod.get(0), activityPeriod.get(1), 0, 10);
    }

    @Override
    public Map obtainHeroRankingAndInvestAmountByLoginName(ActivityCategory activityCategory,Date tradingTime, final String loginName) {
        if (tradingTime == null) {
            logger.debug("tradingTime is null");
            return null;
        }
        List<String> activityPeriod = getActivityPeriod(activityCategory);
        tradingTime = new DateTime(tradingTime).withTimeAtStartOfDay().plusDays(1).minusMillis(1).toDate();
        long count = transferApplicationMapper.findCountTransferApplicationByApplicationTime(loginName, tradingTime, activityPeriod.get(0));
        if (count > 0) {
            return Maps.newHashMap(ImmutableMap.<String, String>builder().
                    put("investRanking", "0").
                    put("investAmount","0").build());
        }

        int investRanking = 0;
        String investAmount = "0";
        List<HeroRankingView> heroRankingViews = investMapper.findHeroRankingByTradingTime(tradingTime, activityPeriod.get(0), activityPeriod.get(1));
        if (heroRankingViews != null) {
            investRanking = Iterators.indexOf(heroRankingViews.iterator(), new Predicate<HeroRankingView>() {
                @Override
                public boolean apply(HeroRankingView input) {
                    return input.getLoginName().equalsIgnoreCase(loginName);
                }
            }) + 1;

            if (investRanking > 0) {
                investAmount = AmountConverter.convertCentToString(heroRankingViews.get(investRanking - 1).getSumAmount());
            }
        }
        return Maps.newHashMap(ImmutableMap.<String, String>builder().
                put("investRanking", String.valueOf(investRanking)).
                put("investAmount",investAmount).build());
    }

    @Override
    public void saveMysteriousPrize(MysteriousPrizeDto mysteriousPrizeDto) {
        String prizeDate = new DateTime(mysteriousPrizeDto.getPrizeDate()).withTimeAtStartOfDay().toString("yyyy-MM-dd");
        redisWrapperClient.hsetSeri(MYSTERIOUSREDISKEY, prizeDate, mysteriousPrizeDto,lifeSecond);
    }

    @Override
    public MysteriousPrizeDto obtainMysteriousPrizeDto(String prizeDate) {
        return (MysteriousPrizeDto) redisWrapperClient.hgetSeri(MYSTERIOUSREDISKEY, prizeDate);
    }

    @Override
    public BasePaginationDataDto<HeroRankingView> findHeroRankingByReferrer(Date tradingTime, final String loginName, int index, int pageSize) {
        BasePaginationDataDto<HeroRankingView> baseListDataDto = new BasePaginationDataDto<>();

        Date activityBeginTime = DateTime.parse(heroRankingActivityPeriod.get(0), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        Date activityEndTime = DateTime.parse(heroRankingActivityPeriod.get(1), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        if (tradingTime.before(activityBeginTime) || tradingTime.after(activityEndTime)) {
            baseListDataDto.setStatus(false);
        } else {
            List<HeroRankingView> heroRankingViewList = investMapper.findHeroRankingByReferrer(tradingTime, heroRankingActivityPeriod.get(0), heroRankingActivityPeriod.get(1), (index - 1) * pageSize, pageSize);
            baseListDataDto.setStatus(true);
            if (CollectionUtils.isNotEmpty(heroRankingViewList)) {
                baseListDataDto.setRecords(Lists.transform(heroRankingViewList, new Function<HeroRankingView, HeroRankingView>() {
                    @Override
                    public HeroRankingView apply(HeroRankingView input) {
                        input.setLoginName(randomUtils.encryptMobile(loginName, input.getLoginName()));
                        return input;
                    }
                }));
            }
        }
        return baseListDataDto;
    }

    @Override
    public Integer findHeroRankingByReferrerLoginName(ActivityCategory activityCategory,final String loginName) {
        List<String> activityPeriod = getActivityPeriod(activityCategory);
        List<HeroRankingView> heroRankingViews = investMapper.findHeroRankingByReferrer(new Date(), activityPeriod.get(0), activityPeriod.get(1), 0, 20);
        if (CollectionUtils.isEmpty(heroRankingViews)) {
            return null;
        }
        return Iterators.indexOf(heroRankingViews.iterator(), new Predicate<HeroRankingView>() {
            @Override
            public boolean apply(HeroRankingView input) {
                return input.getLoginName().equalsIgnoreCase(loginName);
            }
        }) + 1;
    }

    @Override
    public GivenMembership receiveMembership(String loginName) {
        if (DateTime.parse(heroRankingActivityPeriod.get(0), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate().after(DateTime.now().toDate())) {
            return GivenMembership.NO_TIME;
        }

        if (DateTime.parse(heroRankingActivityPeriod.get(1), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate().before(DateTime.now().toDate())) {
            return GivenMembership.END_TIME;
        }

        if (loginName == null || loginName.equals("")) {
            return GivenMembership.NO_LOGIN;
        }

        if (accountMapper.findByLoginName(loginName) == null) {
            return GivenMembership.NO_REGISTER;
        }

        if (userMembershipMapper.findByLoginNameByType(loginName, UserMembershipType.GIVEN) != null) {
            return GivenMembership.ALREADY_RECEIVED;
        }

        long investAmount = investMapper.sumSuccessInvestAmountByLoginName(null, loginName);
        Date registerTime = accountMapper.findByLoginName(loginName).getRegisterTime();
        if (registerTime != null && DateTime.parse(heroRankingActivityPeriod.get(0), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate().after(registerTime) && investAmount < 100000) {
            return GivenMembership.ALREADY_REGISTER_NOT_INVEST_1000;
        }

        if (registerTime != null && DateTime.parse(heroRankingActivityPeriod.get(0), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate().after(registerTime) && investAmount >= 100000) {
            createUserMembershipModel(loginName, MembershipLevel.V5.getLevel());
            return GivenMembership.ALREADY_REGISTER_ALREADY_INVEST_1000;
        }

        createUserMembershipModel(loginName, MembershipLevel.V5.getLevel());
        return GivenMembership.AFTER_START_ACTIVITY_REGISTER;
    }

    private void createUserMembershipModel(String loginName, int level) {
        UserMembershipModel userMembershipModel = new UserMembershipModel(loginName,
                membershipMapper.findByLevel(level).getId(),
                DateTime.now().plusMonths(1).toDate(),
                new Date(),
                UserMembershipType.GIVEN);
        userMembershipMapper.create(userMembershipModel);
    }

    private List getActivityPeriod(ActivityCategory activityCategory){
        return activityCategory.equals(ActivityCategory.HERO_RANKING) ? heroRankingActivityPeriod : newHeroRankingActivityPeriod;
    }

    @Override
    public List<String> getActivityTime(){
        Date startTime = DateTime.parse(newHeroRankingActivityPeriod.get(0), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        Date endTime = DateTime.parse(newHeroRankingActivityPeriod.get(1), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();

        return Lists.newArrayList(sdf.format(startTime),sdf.format(endTime));
    }

}
