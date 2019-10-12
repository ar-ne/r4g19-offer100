package r4g19.offer100.utils.cjs;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import r4g19.offer100.ComponentBase;
import r4g19.offer100.autoconfigure.cjs.aliyunSMS.AliyunSMSConfig;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 短信发送工具类
 */
@Component
public class AliyunSMS extends ComponentBase {
    AliyunSMSConfig aliyunSMSConfig;
    IAcsClient acsClient;
    public AliyunSMS(AliyunSMSConfig aliyunSMSConfig) throws ClientException {
        this.aliyunSMSConfig = aliyunSMSConfig;
        DefaultProfile.addEndpoint( "cn-hangzhou", aliyunSMSConfig.getProduct(), aliyunSMSConfig.getDomain());
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", aliyunSMSConfig.getAccessKeyId(), aliyunSMSConfig.getAccessKeySecret());
        logger.info("正在连接到阿里云:{}",profile);
        acsClient = new DefaultAcsClient(profile);
        logger.info("连接成功:{}",acsClient);
    }

    public SendSmsResponse sendSms(String phoneNumbers, String JSONCode) throws ClientException {
        logger.trace("准备发送短信,config={},phone={},code={}",aliyunSMSConfig,phoneNumbers,JSONCode);
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phoneNumbers);
        request.setSignName(aliyunSMSConfig.getSignName());
        request.setTemplateCode(aliyunSMSConfig.getTemplateCode());
        request.setTemplateParam(JSONCode);

        return acsClient.getAcsResponse(request);
    }



    /**
     * @param bizId: 短信对应的bizId
     * @Description: 查询短信详情
     */
    public QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", aliyunSMSConfig.getAccessKeyId(), aliyunSMSConfig.getAccessKeySecret());
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", aliyunSMSConfig.getProduct(), aliyunSMSConfig.getDomain());
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber("15197447018");
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }
    //    public static void main(String[] args) throws ClientException, InterruptedException {
//
//        //发短信
//        SendSmsResponse response = sendSms("13340110608");
//        System.out.println("短信接口返回的数据----------------");
//        System.out.println("Code=" + response.getCode());
//        System.out.println("Message=" + response.getMessage());
//        System.out.println("RequestId=" + response.getRequestId());
//        System.out.println("BizId=" + response.getBizId());
//
//        // 对接上面的，如果这里不等待，直接查询，会查到一个空的结果
//        Thread.sleep(3000L);
//
//        //查明细
//        if (response.getCode() != null && response.getCode().equals("OK")) {
//            QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(response.getBizId());
//            System.out.println("短信明细查询接口返回数据----------------");
//            System.out.println("Code=" + querySendDetailsResponse.getCode());
//            System.out.println("Message=" + querySendDetailsResponse.getMessage());
//            int i = 0;
//            for (QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs()) {
//                System.out.println("SmsSendDetailDTO[" + i + "]:");
//                System.out.println("Content=" + smsSendDetailDTO.getContent());
//                System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
//                System.out.println("OutId=" + smsSendDetailDTO.getOutId());
//                System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
//                System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
//                System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
//                System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
//                System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
//            }
//            System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
//            System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
//        }
//
//    }
}