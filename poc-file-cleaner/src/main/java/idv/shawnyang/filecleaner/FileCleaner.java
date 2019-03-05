package idv.shawnyang.filecleaner;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileCleaner {
	/**
	 * Reference: https://www.baeldung.com/java-delete-directory
	 * 
	 * @author SHAWN.SH.YANG
	 *
	 */
	private class Visitor implements FileVisitor<Path> {
		public Visitor(Duration survivalPeriod) {
			super();
			this.survivalPeriod = survivalPeriod;
		}

		private Duration survivalPeriod;

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
			try {
				if (attrs.lastModifiedTime().toMillis() < System.currentTimeMillis() - survivalPeriod.toMillis()) {
					log.info("Last modified time: " + attrs.lastModifiedTime().toString());
					Files.delete(file);
					log.info("Delete: " + file.toString());
				}
			} catch (IOException ignore) {
				log.error(ignore.getMessage(), ignore);
			}
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) {
			if (exc != null) {
				log.error(exc.getMessage(), exc);
			}
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
			if (exc != null) {
				log.error(exc.getMessage(), exc);
			}
			try {
				if (isEmptyDir(dir)) {
					log.info("Dir is empty: " + dir.toString());
					Files.delete(dir);
					log.info("Delete: " + dir.toString());
				} else {
					log.info("Dir is not empty: " + dir.toString());
				}
			} catch (IOException ignore) {
				log.error(ignore.getMessage(), ignore);
			}
			return FileVisitResult.CONTINUE;
		}

		/**
		 * Reference:
		 * https://stackoverflow.com/questions/5930087/how-to-check-if-a-
		 * directory-is-empty-in-java
		 * 
		 * @param dir
		 * @return
		 * @throws IOException
		 */
		private boolean isEmptyDir(Path dir) throws IOException {
			try (Stream<Path> streamPath = Files.list(dir)) {
				return !streamPath.findAny().isPresent();
			}
		}
	}

	public void retain(String startPathString, Duration survivalPeriod) throws IOException {
		Path startPath = Paths.get(startPathString);
		Files.walkFileTree(startPath, new Visitor(survivalPeriod));
	}
}
