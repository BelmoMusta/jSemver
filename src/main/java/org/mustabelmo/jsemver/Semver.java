package org.mustabelmo.jsemver;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Semver implements Comparable<Semver> {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Semver semver)) {
            return false;
        }
        return major == semver.major && minor == semver.minor && patch == semver.patch;
    }
    @Override
    public int hashCode() {
        return Objects.hash(major, minor, patch);
    }
    @Override
    public int compareTo(Semver otherSemver) {
        final int result;
        if (otherSemver == null || !otherSemver.isValid()) {
            result = 1;
        } else if (!isValid()) {
            result = -1;
        } else {
            int majorCompare = Integer.compare(major, otherSemver.major);
            int minorCompare = Integer.compare(minor, otherSemver.minor);
            int patchCompare = Integer.compare(patch, otherSemver.patch);
            result = Integer.compare(4 * majorCompare + 2 * minorCompare + patchCompare, 0);
        }

        return result;
    }
    public boolean isInRange(String low, String up) {
        return isInRange(Semver.parse(low), Semver.parse(up));
    }
    public boolean isInRange(Semver low, Semver up) {
        return isGreaterThanOrEqual(low) && isLessThanOrEqual(up);
    }
    public boolean isLessThanOrEqual(Semver up) {
        return compareTo(up) <= 0;
    }
    public boolean isGreaterThanOrEqual(Semver low) {
        return compareTo(low) >= 0;
    }
}