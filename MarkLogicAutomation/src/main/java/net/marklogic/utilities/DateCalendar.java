package net.marklogic.utilities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateCalendar {

	public String getFutureDate() {
		LocalDate localDate = LocalDate.now();
		int futureDate = Integer.parseInt(DateTimeFormatter.ofPattern("dd").format(localDate)) + 1;
		return String.valueOf(futureDate);

	}

	public static String dateTimeFileName() {
		LocalDateTime currentTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-uuuu HH:mm:ss");
		return currentTime.format(formatter).replace("-", "").replace(":", "").replace(".", "").replaceAll(" ", "");
	}

}
