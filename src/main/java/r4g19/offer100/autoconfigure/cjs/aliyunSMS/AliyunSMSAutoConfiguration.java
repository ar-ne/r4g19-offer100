package r4g19.offer100.autoconfigure.cjs.aliyunSMS;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import r4g19.offer100.autoconfigure.cjs.aliyunSMS.AliyunSMSConfig;
import r4g19.offer100.utils.cjs.AliyunSMS;

@Configuration
@EnableConfigurationProperties(AliyunSMSConfig.class)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnMissingBean(name = "aliyunSMS",search = SearchStrategy.ALL)
public class AliyunSMSAutoConfiguration {

    @Bean
    public AliyunSMS aliyunSMS(AliyunSMSConfig config) throws ClientException {
        return new AliyunSMS(config);
    }

}
