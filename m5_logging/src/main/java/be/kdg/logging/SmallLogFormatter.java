package be.kdg.logging;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

// 3.7 Maak een nieuwe package logging en schrijf daarin een klasse SmallLogFormatter, die overerft
//van de abstracte klasse java.util.logging.Formatter. Doe een override van de format-
//methode.
public class SmallLogFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        // 3.7 Extract LocalDateTime from LogRecord
        LocalDateTime ldt = LocalDateTime.ofInstant(
                record.getInstant(),
                ZoneId.systemDefault()
        );

        String time = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));

        // 3.7 Extract log message with parameters
        String message = MessageFormat.format(
                record.getMessage(),
                record.getParameters()
        );

        // 3.7 Build formatted string: date time Level: LEVEL melding: "message"
        return String.format("%s Level: %s melding: \"%s\"%n",
                time,
                record.getLevel(),
                message
        );
    }
}