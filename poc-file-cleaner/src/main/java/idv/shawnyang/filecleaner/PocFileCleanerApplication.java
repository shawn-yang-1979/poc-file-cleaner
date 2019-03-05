package idv.shawnyang.filecleaner;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class PocFileCleanerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocFileCleanerApplication.class, args);
	}

	@Autowired
	private FileCleanerProperties fileCleanerProperties;

	@Autowired
	private FileCleaner fileCleaner;

	@EventListener(ContextRefreshedEvent.class)
	public void go() throws IOException {
		log.info("Begin");
		fileCleaner.retain("/volume", fileCleanerProperties.getSurvivalPeriod());
		log.info("End");
	}
}
