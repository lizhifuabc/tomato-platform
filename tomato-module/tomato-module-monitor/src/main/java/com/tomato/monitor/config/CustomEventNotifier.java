package com.tomato.monitor.config;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractEventNotifier;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 可以自定义事件通知处理
 *
 * @author lizhifu
 * @since 2023/8/26
 */
@Slf4j
@Component
public class CustomEventNotifier extends AbstractEventNotifier {

	protected CustomEventNotifier(InstanceRepository repository) {
		super(repository);
	}

	@Override
	protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
		return Mono.fromRunnable(() -> {
			// 实例状态改变事件
			if (event instanceof InstanceStatusChangedEvent) {
				String registrationName = instance.getRegistration().getName();
				String instanceId = event.getInstance().getValue();
				String status = ((InstanceStatusChangedEvent) event).getStatusInfo().getStatus();
				log.info("实例状态更改: 注册名称：{},实例ID：{},状态：{}", registrationName, instanceId, status);
			}
		});
	}

}
