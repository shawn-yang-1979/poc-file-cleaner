package idv.shawnyang.filecleaner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Reference: https://www.baeldung.com/spring-task-scheduler
 * 
 * @author SHAWN.SH.YANG
 *
 */
@Configuration
public class FileCleanerConfig {

	@Bean
	public ThreadPoolTaskScheduler fileCleanerThreadPoolTaskScheduler(FileCleanerProperties fileCleanerProperties) {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(fileCleanerProperties.getPoolSize());
		threadPoolTaskScheduler.setThreadNamePrefix("FileCleanerThreadPoolTaskScheduler");
		return threadPoolTaskScheduler;
	}
}
