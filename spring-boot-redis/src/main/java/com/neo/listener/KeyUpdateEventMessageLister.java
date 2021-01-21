package com.neo.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisKeyExpiredEvent;
import org.springframework.data.redis.listener.KeyspaceEventMessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * @Auther: bcl
 * @Description: 配置redis键空间监听
 * @Date: Create in 8:50 下午 2021/1/20
 */
@Component
public class KeyUpdateEventMessageLister extends KeyspaceEventMessageListener implements ApplicationEventPublisherAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(KeyUpdateEventMessageLister.class);
    private ApplicationEventPublisher publisher;

    public KeyUpdateEventMessageLister(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    @Override
    protected void doHandleMessage(Message message) {
        this.publishEvent(new RedisKeyExpiredEvent(message.getBody()));
    }

    protected void publishEvent(RedisKeyExpiredEvent event) {
        this.publisher.publishEvent(event);
    }

    protected void doRegister(RedisMessageListenerContainer listenerContainer) {
        // 配置监听所有的lpush指令
        listenerContainer.addMessageListener(this, new PatternTopic("__keyevent@*__:lpush"));
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        LOGGER.info("测试redis监听：{}", message.toString());
    }
}
