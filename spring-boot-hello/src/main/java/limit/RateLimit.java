package limit;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 限流父类
 *
 * @auther hunters
 * @since 2021/3/29
 */
public class RateLimit {
    /**
     * 白名单
     */
    @Value("#{'${limit.whiteIps}'.split(',')}")
    private List<String> limitWhiteIps;
    /**
     * 令牌桶
     */
    private ConcurrentHashMap<String, RateLimiter> RATE_LIMITER = new ConcurrentHashMap<>();

    public boolean limit(String ip, String funcationName, double limitNum) {
            return whiteLimit(ip) ? true : blockLimit(ip) ? false : autoBlockLimit(ip) ? false : acquire(funcationName, limitNum);
    }

    protected boolean whiteLimit(String ip) {
        return limitWhiteIps.contains(ip);
    }

    protected boolean blockLimit(String ip) {
        return false;
    }

    protected boolean autoBlockLimit(String ip) {
        return false;
    }

    protected boolean acquire(String functionName, double limitNum) {
        RateLimiter rateLimiter;
        if (RATE_LIMITER.containsKey(functionName)) {
            rateLimiter = RATE_LIMITER.get(functionName);
        }
        else {
            RATE_LIMITER.put(functionName, RateLimiter.create(limitNum));
            rateLimiter = RATE_LIMITER.get(functionName);
        }
        if (rateLimiter.tryAcquire()) {
            return true;
        }
        return false;
    }


}
