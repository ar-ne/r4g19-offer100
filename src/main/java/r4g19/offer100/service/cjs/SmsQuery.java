package r4g19.offer100.service.cjs;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Description: 短信查询实体
 */
@Data
@ToString
public class SmsQuery {
    private String bizId;
    private String phoneNumber;
    private Date sendDate;
    private Long pageSize;
    private Long currentPage;
}