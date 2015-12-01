package com.ttsd.special.services;

import com.esoft.archer.user.model.User;
import com.esoft.jdp2p.invest.model.Invest;

import java.util.Date;

public interface ConferenceSaleService {

    void processIfInActivityForBindCard(String orderId, User user);

    void processIfInActivityForInvest(Invest invest);

    void reissueMissedReward(Date deadlineDate);
}
