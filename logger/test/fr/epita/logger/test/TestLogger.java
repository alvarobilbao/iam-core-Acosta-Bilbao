package fr.epita.logger.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import fr.epita.logger.Logger;
import fr.epita.logger.services.configuration.LoggerConfigurationService;

public class TestLogger {

	private static final Logger logger = new Logger(TestLogger.class);

	@Test
	void testErrorLogging() throws FileNotFoundException {
		// given / when
				logger.error("first error");

				// then
				final String pathname = LoggerConfigurationService.getInstance().getConfigurationValue("log.path");
				final File file = new File(pathname);
				assertTrue("file exists", file.exists());
				boolean found = false;
				if (file.exists()) {
					final Scanner scanner = new Scanner(file);

					while (scanner.hasNext()) {
						final String nextLine = scanner.nextLine();
						found = found || nextLine.contains("first error");
					}
					assertTrue("success log", found);
					scanner.close();
				}
	}

}
