package com.tuotiansudai.cfca.contract.impl;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.mysql.jdbc.StringUtils;
import com.tuotiansudai.cfca.contract.ContractService;
import com.tuotiansudai.repository.mapper.*;
import com.tuotiansudai.repository.model.*;
import com.tuotiansudai.rest.client.mapper.UserMapper;
import com.tuotiansudai.util.AmountConverter;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Service
public class ContractServiceImpl implements ContractService {
    static Logger logger = Logger.getLogger(ContractServiceImpl.class);

    @Autowired
    private LoanMapper loanMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoanerDetailsMapper loanerDetailsMapper;

    @Autowired
    private InvestMapper investMapper;

    @Autowired
    private TransferApplicationMapper transferApplicationMapper;

    @Autowired
    private InvestRepayMapper investRepayMapper;

    @Autowired
    private TransferRuleMapper transferRuleMapper;

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static final String LOAN_CONTRACT_TEMPLATE = "loan_contract.pdf";

    public static final String TRANSFER_CONTRACT_TEMPLaTE = "transfer_contract.pdf";

    /**
     * 后缀为FTL
     */
    private static final String FTL = ".ftl";

    @Override
    public String getContract(String templateName, Map<String, String> dataModel) {
        Writer out = null;
        StringReader reader = null;
        try {
            out = new StringWriter();
            Configuration cfg = new Configuration(new Version(2, 3, 23));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setClassForTemplateLoading(ContractServiceImpl.class, "/");
            Template template = cfg.getTemplate(templateName + FTL);
            template.process(dataModel, out);
            reader = new StringReader(out.toString());
            out.flush();
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error(e.getLocalizedMessage(), e);
                }
            }
        }
        if (reader == null) {
            logger.error("template process exception, reader is null");
            return "";
        }

        StringBuilder content = new StringBuilder();
        String str;
        try {
            BufferedReader br = new BufferedReader(reader);
            while ((str = br.readLine()) != null) {
                content.append(str);
            }
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return content.toString();
    }

    @Override
    public String generateTransferContract(long transferApplicationId) {

        Map<String, String> dataModel = collectTransferContractModel(transferApplicationId);
        if (dataModel.isEmpty()) {
            return "";
        }
        return getContract("transferContract", dataModel).replace("&nbsp;", "&#160;");
    }


    @Override
    public Map<String, String> collectTransferContractModel(long transferApplicationId) {
        Map<String, String> dataModel = new HashMap<>();

        TransferApplicationModel transferApplicationModel = transferApplicationMapper.findById(transferApplicationId);
        if (null == transferApplicationModel) {
            return dataModel;
        }

        LoanModel loanModel = loanMapper.findById(transferApplicationModel.getLoanId());
        if (loanModel == null) {
            return dataModel;
        }

        UserModel transferUserModel = userMapper.findByLoginName(transferApplicationModel.getLoginName());
        dataModel.put("transferUserName", transferUserModel.getUserName());
        dataModel.put("transferMobile", transferUserModel.getMobile());
        dataModel.put("transferIdentityNumber", transferUserModel.getIdentityNumber());

        InvestModel investModel = investMapper.findById(transferApplicationModel.getInvestId());
        UserModel investUserModel = userMapper.findByLoginName(investModel.getLoginName());
        dataModel.put("transfereeUserName", investUserModel.getUserName());
        dataModel.put("transfereeMobile", investUserModel.getMobile());
        dataModel.put("transfereeIdentityNumber", investUserModel.getIdentityNumber());

        dataModel.put("loanerUserName", loanerDetailsMapper.getByLoanId(loanModel.getId()).getUserName());
        dataModel.put("loanerIdentityNumber", loanModel.getLoanerIdentityNumber());
        dataModel.put("loanAmount", AmountConverter.convertCentToString(loanModel.getLoanAmount()) + "元");

        DecimalFormat decimalFormat = new DecimalFormat("######0.##");
        dataModel.put("totalRate", decimalFormat.format((loanModel.getBaseRate() + loanModel.getActivityRate()) * 100) + "%");
        dataModel.put("periods", String.valueOf(loanModel.getPeriods() * 30) + "天");

        if (transferApplicationModel.getPeriod() != 1) {
            InvestRepayModel investRepayModel = investRepayMapper.findByInvestIdAndPeriod(transferApplicationModel.getTransferInvestId(), transferApplicationModel.getPeriod() - 1);
            dataModel.put("transferStartTime", simpleDateFormat.format(new LocalDate(investRepayModel.getRepayDate()).plusDays(1).toDate()));
        } else {
            if (loanModel.getType().equals(LoanType.INVEST_INTEREST_LUMP_SUM_REPAY) || loanModel.getType().equals(LoanType.INVEST_INTEREST_MONTHLY_REPAY)) {
                dataModel.put("transferStartTime", simpleDateFormat.format(investModel.getInvestTime()));
            } else if (loanModel.getType().equals(LoanType.LOAN_INTEREST_MONTHLY_REPAY) || loanModel.getType().equals(LoanType.LOAN_INTEREST_LUMP_SUM_REPAY)) {
                dataModel.put("transferStartTime", simpleDateFormat.format(loanModel.getRecheckTime()));
            }
        }

        InvestRepayModel investRepayModel = investRepayMapper.findByInvestIdAndPeriod(investModel.getId(), loanModel.getPeriods());
        dataModel.put("transferEndTime", simpleDateFormat.format(investRepayModel.getRepayDate()));

        dataModel.put("investAmount", AmountConverter.convertCentToString(transferApplicationModel.getInvestAmount()) + "元");
        dataModel.put("transferTime", simpleDateFormat.format(transferApplicationModel.getTransferTime()));
        dataModel.put("leftPeriod", String.valueOf(transferApplicationModel.getLeftPeriod()));
        dataModel.put("investId", String.valueOf(transferApplicationModel.getInvestId()));

        TransferRuleModel transferRuleModel = transferRuleMapper.find();
        String msg1;
        String msg2;
        String msg3;
        if (transferRuleModel.getLevelOneFee() != 0) {
            msg1 = MessageFormat.format("甲方持有债权30天以内的，收取转让本金的{0}%作为服务费用。", transferRuleModel.getLevelOneFee() * 100);
        } else {
            msg1 = "甲方持有债权30天以内的，暂不收取转服务费用。";
        }

        if (transferRuleModel.getLevelTwoFee() != 0) {
            msg2 = MessageFormat.format("甲方持有债权30天以上，90天以内的，收取转让本金的{0}%作为服务费用。", transferRuleModel.getLevelTwoFee() * 100);
        } else {
            msg2 = "甲方持有债权30天以上，90天以内的，暂不收取转服务费用。";
        }

        if (transferRuleModel.getLevelThreeFee() != 0) {
            msg3 = MessageFormat.format("甲方持有债权90天以上的，收取转让本金的{0}%作为服务费用。", transferRuleModel.getLevelThreeFee() * 100);
        } else {
            msg3 = "甲方持有债权90天以上的，暂不收取转服务费用。";
        }
        dataModel.put("msg1", msg1);
        dataModel.put("msg2", msg2);
        dataModel.put("msg3", msg3);

        return dataModel;
    }

    @Override
    public Map<String, String> collectInvestorContractModel(String investorLoginName, long loanId, long investId, String fullTime) {
        Map<String, String> dataModel = new HashMap<>();
        LoanModel loanModel = loanMapper.findById(loanId);
        UserModel agentModel = userMapper.findByLoginName(loanModel.getAgentLoginName());
        UserModel investorModel = userMapper.findByLoginName(investorLoginName);
        InvestModel investModel = investMapper.findById(investId);
        //
        DecimalFormat decimalFormat = new DecimalFormat("######0.##");
        dataModel.put("investorIdentityNumber", investorModel.getIdentityNumber());
        dataModel.put("loanerIdentityNumber", agentModel.getIdentityNumber());
        dataModel.put("loanName", loanModel.getName());
        dataModel.put("amountUpper", AmountConverter.getRMBStr(investModel.getAmount()));
        dataModel.put("amount", AmountConverter.convertCentToString(investModel.getAmount()));
        dataModel.put("totalRate", decimalFormat.format((loanModel.getBaseRate() + loanModel.getActivityRate()) * 100));
        //根据标的类型判断借款开始时间
        if (LoanType.INVEST_INTEREST_MONTHLY_REPAY.equals(loanModel.getType()) || LoanType.INVEST_INTEREST_LUMP_SUM_REPAY.equals(loanModel.getType())) {
            DateTime recheckTimeYear=new DateTime(investModel.getCreatedTime());
            dataModel.put("recheckTimeYear", String.valueOf(recheckTimeYear.getYear()));
            dataModel.put("recheckTimeMonth", String.valueOf(recheckTimeYear.getMonthOfYear()));
            dataModel.put("recheckTimeDay", String.valueOf(recheckTimeYear.getDayOfMonth()));
        } else {
            DateTime fullTimeDate = Strings.isNullOrEmpty(fullTime)?DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(fullTime):new DateTime(loanModel.getRecheckTime());
            dataModel.put("recheckTimeYear", String.valueOf(fullTimeDate.getYear()));
            dataModel.put("recheckTimeMonth", String.valueOf(fullTimeDate.getMonthOfYear()));
            dataModel.put("recheckTimeDay", String.valueOf(fullTimeDate.getDayOfMonth()));
        }
        DateTime endTimeDate = new DateTime(loanModel.getDeadline());
        dataModel.put("endTimeYear", String.valueOf(endTimeDate.getYear()));
        dataModel.put("endTimeMonth", String.valueOf(endTimeDate.getMonthOfYear()));
        dataModel.put("endTimeDay", String.valueOf(endTimeDate.getDayOfMonth()));
        dataModel.put("periods", loanModel.getPeriods() + "");
        dataModel.put("loanType", loanModel.getType().getName());
        dataModel.put("investorName", investorModel.getUserName());
        return dataModel;
    }

    @Override
    public byte[] printContractPdf(AnxinContractType anxinContractType, String loginName, long OrderId, Long investId) {
        String pdfTemplate;
        Map<String, String> dataMap;
        if (anxinContractType.equals(AnxinContractType.LOAN_CONTRACT)) {
            pdfTemplate = LOAN_CONTRACT_TEMPLATE;
            dataMap = collectInvestorContractModel(loginName, OrderId, investId, null);
        } else {
            pdfTemplate = TRANSFER_CONTRACT_TEMPLaTE;
            dataMap = collectTransferContractModel(OrderId);
        }

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            PdfStamper ps = new PdfStamper(new PdfReader(pdfTemplate), bos);

            AcroFields fields = ps.getAcroFields();
            fields.setSubstitutionFonts(Lists.newArrayList(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false)));
            fillPdfTemplate(anxinContractType, fields, dataMap);
            ps.setFormFlattening(true);
            ps.close();
            byte[] byteArray = bos.toByteArray();
            bos.close();
            return byteArray;
        } catch (Exception e) {
            logger.error("download old contract fail . message:{}", e);
        }

        return null;
    }

    private AcroFields fillPdfTemplate(AnxinContractType contractType, AcroFields fields, Map<String, String> dataMap) throws IOException, DocumentException {
        if (contractType.equals(AnxinContractType.LOAN_CONTRACT)) {
            dataMap.forEach((key,value)->{
                try {
                    fields.setField(key,value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } else {
            fields.setField("transferUserName", userMapper.findByLoginNameOrMobile(dataMap.get("transferMobile")).getUserName());
            fields.setField("transferMobile", dataMap.get("transferMobile"));
            fields.setField("transferIdentity", dataMap.get("transferIdentityNumber"));
            fields.setField("transfereeUserName", userMapper.findByLoginNameOrMobile(dataMap.get("transfereeMobile")).getUserName());
            fields.setField("transfereeMobile", dataMap.get("transfereeMobile"));
            fields.setField("transfereeIdentity", dataMap.get("transfereeIdentityNumber"));
            fields.setField("userName", dataMap.get("loanerUserName"));
            fields.setField("identity", dataMap.get("loanerIdentityNumber"));
            fields.setField("amount", dataMap.get("loanAmount"));
            fields.setField("totalRate", dataMap.get("totalRate"));
            fields.setField("periods", dataMap.get("periods"));
            fields.setField("transferStartTime", dataMap.get("transferStartTime"));
            fields.setField("transferEndTime", dataMap.get("transferEndTime"));
            fields.setField("investAmount", dataMap.get("investAmount"));
            fields.setField("transferTime", dataMap.get("transferTime"));
            fields.setField("leftPeriod", dataMap.get("leftPeriod"));
            fields.setField("msg1", dataMap.get("msg1"));
            fields.setField("msg2", dataMap.get("msg2"));
            fields.setField("msg3", dataMap.get("msg3"));
        }

        return fields;
    }
}