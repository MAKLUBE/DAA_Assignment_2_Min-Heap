package cli;

import java.io.IOException;
import java.nio.file.*;

public final class CsvUtil {
    private CsvUtil() {}

    public static void appendWithHeader(Path path, String header, String row) throws IOException {
        boolean exists = Files.exists(path);
        if (!exists) {
            Files.createDirectories(path.getParent() == null ? Paths.get(".") : path.getParent());
            Files.writeString(path, header + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }
        Files.writeString(path, row + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public static String esc(String s) {
        if (s == null) return "";
        if (s.contains(",") || s.contains("\"") || s.contains("\n"))
            return "\"" + s.replace("\"", "\"\"") + "\"";
        return s;
    }
}
