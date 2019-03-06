package idv.shawnyang.filecleaner;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "file-cleaner")
public class FileCleanerProperties {

	private int poolSize = 5;
	private List<FileRetentionPolicy> fileRetentionPolicies = new LinkedList<>();

	@Getter
	@Setter
	public static class FileRetentionPolicy {
		private String fileVolumeRoot;
		private String scheduledCron;
		private Duration survivalPeriod;
	}
}
