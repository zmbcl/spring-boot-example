package limit;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * @auther hunters
 * @since 2021/3/29
 */
public class BlockValueRateLimit extends RateLimit {
    /**
     * 白名单
     */
    @Value("#{'${limit.blockIps}'.split(',')}")
    private List<String> limitBlockIps;
    @Override
    protected boolean blockLimit(String ip) {
        return limitBlockIps.contains(ip);
    }
}
