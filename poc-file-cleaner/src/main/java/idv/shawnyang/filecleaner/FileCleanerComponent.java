package idv.shawnyang.filecleaner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
public class FileCleanerComponent {

	@Autowired
	private FileCleanerProperties fileCleanerProperties;

	@Autowired
	private ThreadPoolTaskScheduler fileCleanerThreadPoolTaskScheduler;

	@Autowired
	private FileUtilsComponent fileUtilsComponent;

	@EventListener(ContextRefreshedEvent.class)
	void init() {
		fileCleanerProperties.getFileRetentionPolicies().stream().forEach(//
				policy -> //
				fileCleanerThreadPoolTaskScheduler.schedule(//
						() -> fileUtilsComponent.retain(policy.getFileVolumeRoot(), policy.getSurvivalPeriod()),
						new CronTrigger(policy.getScheduledCron())));
	}

}
