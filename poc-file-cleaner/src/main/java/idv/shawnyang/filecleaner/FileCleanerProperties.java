package idv.shawnyang.filecleaner;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * @author SCOTT.SU
 * @Date 2018-12-04 上午10:30:37
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "file-cleaner")
public class FileCleanerProperties {

	private Duration survivalPeriod = Duration.ofDays(365);
}
