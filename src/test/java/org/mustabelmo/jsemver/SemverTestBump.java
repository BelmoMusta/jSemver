package org.mustabelmo.jsemver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

public class SemverTestBump {
    @Test
    public void testBumpMajor() {
        Semver semver = new Semver(1,2,3);
        Semver bumpedVersion = semver.bumpMajor();
        Assertions.assertEquals("2.0.0", bumpedVersion.toString());
    }
    @Test
    public void testBumpMinor() {
        Semver semver = new Semver(1,2,3);
        Semver bumpedVersion = semver.bumpMinor();
        Assertions.assertEquals("1.3.0", bumpedVersion.toString());
    }
    @Test
    public void testBumpPatch() {
        Semver semver = new Semver(1,2,3);
        Semver bumpedVersion = semver.bumpPatch();
        Assertions.assertEquals("1.2.4", bumpedVersion.toString());
    }

}