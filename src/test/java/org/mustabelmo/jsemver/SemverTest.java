package org.mustabelmo.jsemver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SemverTest {
    @Test
    public void testInitSemver() {
        Semver semver = new Semver();
        Assertions.assertEquals("0.0.0", semver.toString());
    }

    @Test
    public void testSemverParseOk() {
        Semver semver = Semver.parse("1.0.2");
        Assertions.assertTrue(semver.isValid());
    }

    @Test
    public void testSemverParseKo() {
        Semver semver = Semver.parse("1.0.a");
        Assertions.assertFalse(semver.isValid());
    }
    @Test
    public void testFunctions() {
        Semver semver = Semver.parse("1.3.5");
        Assertions.assertEquals(1,semver.major());
        Assertions.assertEquals(3,semver.minor());
        Assertions.assertEquals(5,semver.patch());
    }

    @Test
    public void testSemverCoerce() {
        Semver semver = Semver.coerce("v3");
        Assertions.assertEquals("3.0.0", semver.toString());
    }
    @Test
    public void testSemverCoerce2() {
        Semver semver = Semver.coerce("v3.1");
        Assertions.assertEquals("3.1.0", semver.toString());
    }
    @Test
    public void testSemverCoerce3() {
        Semver semver = Semver.coerce("v3.1.6");
        Assertions.assertEquals("3.1.6", semver.toString());
    }
    @Test
    public void testSemverCoerce4() {
        Semver semver = Semver.coerce("v3.1.");
        Assertions.assertEquals("3.1.0", semver.toString());
    }
    @Test
    public void testSemverCoerce5() {
        Semver semver = Semver.coerce("v3.");
        Assertions.assertEquals("3.0.0", semver.toString());
    }
    @Test
    public void testSemverCoerceIncorrectVersion() {
        Semver semver = Semver.coerce("v");
        Assertions.assertFalse(semver.isValid());
    }
}