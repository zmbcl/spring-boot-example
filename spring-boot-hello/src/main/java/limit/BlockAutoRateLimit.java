package limit;

import org.springframework.beans.factory.annotation.Value;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @auther hunters
 * @since 2021/3/29
 */
public class BlockAutoRateLimit extends RateLimit {
    /**
     * 限制访问时间
     */
    @Value("${limit.limitTime:3600000}")
    private long limitTimeMillis;

    /**
     * 限制访问次数
     */
    @Value("${limit.limitNumber:10}")
    private int limitNumber;

    /**
     * 访问最小安全时间
     */
    @Value("${limit.minSafeTime:1000}")
    private int minSafeTime;

    /**
     * ip记录
     */
    private ConcurrentHashMap<String, Long[]> ipMap = new ConcurrentHashMap<>();

    /**
     * 黑名单
     */
    private ConcurrentHashMap<String, Long> blockMap = new ConcurrentHashMap<>();

    @Override
    protected boolean autoBlockLimit(String ip) {
        cleanMap();
        if (isContainBlock(ip)) {
            return true;
        }
        if (ipMap.containsKey(ip)) {
            Long[] ipMessage = ipMap.get(ip);
            ipMessage[0] = ipMessage[0]++;
            if (ipMessage[0] > limitNumber) {
                Long ipAccessTime = ipMessage[1];
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - ipAccessTime <= minSafeTime) {
                    blockMap.put(ip, currentTimeMillis + limitTimeMillis);
                    return true;
                }
                else {
                    initIpNumber(ip);
                }
            }
        }
        else {
            initIpNumber(ip);
        }
        return false;
    }

    /**
     * 清理已过限制时间的IP&存储时间过长的正常IP
     */
    private void cleanMap() {
        ConcurrentHashMap.KeySetView<String, Long> blockKeySet = blockMap.keySet();
        Iterator<String> blockIterator = blockKeySet.iterator();
        while (blockIterator.hasNext()) {
            Long expireTimeMillis = blockMap.get(blockIterator.next());
            if (expireTimeMillis <= System.currentTimeMillis()) {
                blockIterator.remove();
            }
        }
        ConcurrentHashMap.KeySetView<String, Long[]> ipKeySet = ipMap.keySet();
        Iterator<String> ipIterator = ipKeySet.iterator();
        while (ipIterator.hasNext()) {
            Long[] ipMessage = ipMap.get(ipIterator.next());
            if (System.currentTimeMillis() - ipMessage[1] > 12 * 60 * 60 * 1000) {
                ipIterator.remove();
            }
        }
    }

    /**
     * 初始化IP信息
     */
    private void initIpNumber(String ip) {
        Long[] ipMessage = {0L, System.currentTimeMillis()};
        ipMap.put(ip, ipMessage);
    }

    /**
     * 是否包含在黑名单
     */
    private boolean isContainBlock(String ip) {
        return blockMap.containsKey(ip);
    }
}
