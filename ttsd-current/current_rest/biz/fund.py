# -*- coding: utf-8 -*-
import logging
from datetime import datetime, timedelta

from django.db.models import Sum

from current_rest import constants
from current_rest.biz import current_interest
from current_rest.models import Loan, CurrentAccount, CurrentDailyFundInfo

logger = logging.getLogger(__name__)


def generate_fund_tendency(days=7):
    date_list = _generate_date_list(days)
    loan_amount_list = _generate_loan_amount_list(date_list)
    user_balance_list = _generate_user_balance_list(days)
    loan_remain_amount_list = [l - u for l, u in zip(loan_amount_list, user_balance_list)]
    return dict(dates=date_list, loan_remain_amount_list=loan_remain_amount_list, user_balance_list=user_balance_list)


def list_fund_history(begin_date, end_date):
    summarise = CurrentDailyFundInfo.objects.filter(date__gte=begin_date, date__lte=end_date)
    histories = [
        (x.config_quota_amount if x.config_quota_status == constants.DAILY_QUOTA_STATUS_APPROVED else x.quota_amount,
         x.invest_amount if x.invest_amount else 0, x.date) for x in summarise]
    quota_amount_list = [x[0] for x in histories]
    invest_amount_list = [x[1] for x in histories]
    date_list = [x[2] for x in histories]
    return dict(dates=date_list, quota_amount_list=quota_amount_list, invest_amount_list=invest_amount_list)


def _generate_date_list(days):
    today = datetime.now().date()
    return [today + timedelta(days=d) for d in range(days)]


def _generate_user_balance_list(days):
    l = []
    balance = CurrentAccount.objects.aggregate(total_amount=Sum('balance')).pop('total_amount')
    l.append(balance)
    for d in range(days - 1):
        balance += current_interest.calculate_interest(balance)
        l.append(balance)
    return l


def _generate_loan_amount_list(dates):
    def sum_loan_amount_by_date(date):
        amount = Loan.objects.filter(effective_date__gte=date, expiration_date__lte=date).aggregate(
            total_amount=Sum('amount')).pop('total_amount')
        return amount if amount else 0

    return [sum_loan_amount_by_date(d) for d in dates]
