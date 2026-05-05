package com.notification.factory;

import com.notification.model.Channel;
import com.notification.strategy.NotificationProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class NotificationProviderFactory {

    private final Map<Channel, NotificationProvider> providerMap;

    public NotificationProviderFactory(List<NotificationProvider> providers) {
        providerMap = providers.stream()
                .collect(Collectors.toMap(
                        NotificationProvider::getChannel,
                        Function.identity()
                ));
    }

    public NotificationProvider getProvider(Channel channel) {
        NotificationProvider provider = providerMap.get(channel);

        if (provider == null) {
            throw new IllegalArgumentException("No provider found for channel: " + channel);
        }

        return provider;
    }

}
