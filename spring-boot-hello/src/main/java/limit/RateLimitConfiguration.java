package limit;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther hunters
 * @since 2021/3/29
 */
@Configuration
public class RateLimitConfiguration {
    @Bean
    @ConditionalOnProperty(name = "limit.rateLimit", havingValue = "blockValue")
    public BlockValueRateLimit blockValueRateLimit() {
        return new BlockValueRateLimit();
    }

    @Bean
    @ConditionalOnProperty(name = "limit.rateLimit", havingValue = "blockAuto")
    @ConditionalOnMissingBean
    public BlockAutoRateLimit blockAutoRateLimit() {
        return new BlockAutoRateLimit();
    }
}
