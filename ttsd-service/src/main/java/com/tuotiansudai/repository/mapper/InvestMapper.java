package com.tuotiansudai.repository.mapper;

import com.tuotiansudai.repository.model.InvestModel;
import com.tuotiansudai.repository.model.InvestStatus;
import com.tuotiansudai.repository.model.SortStyle;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface InvestMapper {
    /**
     * 创建投资记录
     *
     * @param investModel
     */
    void create(InvestModel investModel);

    /**
     * 更新投资记录的状态
     *
     * @param id
     * @param status
     */
    void updateStatus(@Param(value = "id") long id,
                      @Param(value = "status") InvestStatus status);

    /**
     * 根据ID查找对应的投资
     *
     * @param id
     * @return
     */
    InvestModel findById(@Param(value = "id") long id);

    /**
     * 查找用户的投资记录
     * 如果有分页插件的话，需要修改此返回类型
     *
     * @param loginName
     * @return
     */
    List<InvestModel> findByLoginNameOrderByTime(@Param(value = "loginName") String loginName,
                                                 @Param(value = "sortStyle") SortStyle sortStyle);

    /**
     * 计算标的的投资总额
     *
     * @param loanId
     * @return
     */
    long sumSuccessInvestAmount(@Param(value = "loanId") long loanId);

    /**
     * 分页获取投资记录
     *
     * @param loanId
     * @param index
     * @param pageSize
     * @param status
     * @return
     */
    List<InvestModel> findByStatus(@Param(value = "loanId") long loanId,
                                   @Param(value = "index") Integer index,
                                   @Param(value = "pageSize") Integer pageSize,
                                   @Param(value = "status") InvestStatus status);

    /**
     * 获取标的的投资记录数
     * @param loanId
     * @param status
     * @return
     */
    int findCountByStatus(@Param(value = "loanId") long loanId,
                      @Param(value = "status") InvestStatus status);

    /**
     * 获取所有投资成功的记录
     *
     * @param loanId
     * @return
     */
    List<InvestModel> findSuccessInvests(@Param(value = "loanId") long loanId);

    /**
     * 将指定时间前创建的，目前仍处于waiting状态的投资记录标记为失败
     * @param loanId
     * @param beforeTime
     */
    void cleanWaitingInvestBefore(@Param(value = "id") long loanId,
                                  @Param(value = "beforeTime") Date beforeTime);

    /**
     * 获取标的是否存在在指定时间后创建，目前仍处于waiting状态的投资记录
     * @param loanId
     * @param afterTime
     * @return
     */
    int findWaitingInvestCountAfter(@Param(value = "id") long loanId,
                                   @Param(value = "afterTime") Date afterTime);


}
