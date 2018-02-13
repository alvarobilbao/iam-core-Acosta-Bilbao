package fr.epita.logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.epita.logger.services.configuration.LoggerConfigurationService;

public class Logger {

	// TODO makes it configurable!
	private static final String logPath = LoggerConfigurationService.getInstance().getConfigurationValue("log.path");
	private static PrintWriter pw;

	private static final String ERROR = "ERROR";
	private static final String INFO = "INFO";

	static {
		try {
			final File file = new File(logPath);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			pw = new PrintWriter(new FileOutputStream(file, true));
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private final Class<?> cls;

	public Logger(Class<?> cls) {
		this.cls = cls;

	}

	public void error(String message) {
		printMessage(message, ERROR);
	}

	public void info(String message) {
		printMessage(message, INFO);
	}

	private void printMessage(String message, String Level) {
		final String completeMessage = getTimeStamp() + " - " + Level + " - " + cls.getCanonicalName() + " " + message;
		pw.println(completeMessage);
		pw.flush();
	}

	private static String getTimeStamp() {
		final Date date = new Date();

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS");
		return sdf.format(date);
	}

	public void error(String message, Exception e) {
		printMessage(message, ERROR);
		e.printStackTrace(pw);
		pw.flush();
	}

}
