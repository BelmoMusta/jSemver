package org.mustabelmo.jsemver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Semver {
    private static final String REGEX = "(\\d+)\\.(\\d+)\\.(\\d+)";
    private static final String COERCE_REGEX = "^v(\\d+)(\\.(\\d+))?(\\.(\\d+))?";

    private final int major;
    private final int minor;
    private final int patch;
    public Semver() {
        this(0, 0, 0);
    }
    public Semver(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }
    public static Semver parse(String version) {

        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(version);
        if (matcher.find() && matcher.groupCount() == 3) {
            final int major = parseStringToInt(matcher.group(1));
            final int minor = parseStringToInt(matcher.group(2));
            final int patch = parseStringToInt(matcher.group(3));
            return new Semver(major, minor, patch);
        }
        return new Semver(-1, -1, -1);
    }
    public static Semver coerce(String version) {
        Pattern pattern = Pattern.compile(COERCE_REGEX);
        Matcher matcher = pattern.matcher(version);
        if (matcher.find()) {
            String group = matcher.group(1);
            final int major = parseStringToInt(group);
            final int minor = parseStringToInt(matcher.group(3));
            final int patch = parseStringToInt(matcher.group(5));
            return new Semver(major, minor, patch);
        }
        return new Semver(-1, -1, -1);
    }
    private static int parseStringToInt(String str) {
        if (str == null) {
            return 0;
        }
        return Integer.parseInt(str);
    }

    @Override
    public String toString() {
        return "%d.%d.%d".formatted(major, minor, patch);
    }
    public boolean isValid() {
        return major * minor * patch != -1;
    }

    public int major() {
        return major;
    }
    public int minor() {
        return minor;
    }

    public int patch() {
        return patch;
    }
}