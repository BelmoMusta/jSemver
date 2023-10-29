package org.mustabelmo.jsemver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

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
        Assertions.assertEquals(1, semver.major());
        Assertions.assertEquals(3, semver.minor());
        Assertions.assertEquals(5, semver.patch());
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

    @Test
    public void testSemverCompareToNull() {
        Semver semverA = Semver.parse("1.0.0");
        int compare = semverA.compareTo(null);
        Assertions.assertEquals(1, compare);
    }
    @Test
    public void testSemverInvalidCompareToNull() {
        Semver semverA = Semver.parse("xxx");
        Semver semverB = Semver.parse("1.0.0");
        int compare = semverA.compareTo(semverB);
        Assertions.assertEquals(-1, compare);
    }

    @Test
    public void testSemverCompareToInvalid() {
        Semver semverA = Semver.parse("1.0.0");
        Semver semverB = Semver.parse("xxx");
        int compare = semverA.compareTo(semverB);
        Assertions.assertEquals(1, compare);
    }
    @Test
    public void testSemverCompareTo() {
        Semver semverA = Semver.parse("1.0.0");
        Semver semverB = Semver.parse("1.0.1");
        int compare = semverA.compareTo(semverB);
        Assertions.assertEquals(-1, compare);
    }
    @Test
    public void testSemverCompareTo2() {
        Semver semverA = Semver.parse("1.2.3");
        Semver semverB = Semver.parse("1.2.3");
        int compare = semverA.compareTo(semverB);
        Assertions.assertEquals(0, compare);
    }
    @Test
    public void testSemverCompareTo3() {
        Semver semverA = Semver.parse("10.2.3");
        Semver semverB = Semver.parse("1.2.300000000");
        int compare = semverA.compareTo(semverB);
        Assertions.assertEquals(1, compare);
    }
    @Test
    public void testSemverCompareToEquality() {
        Semver semverA = Semver.parse("10.2.3");
        Semver semverB = Semver.parse("10.2.3");
        int compare = semverA.compareTo(semverB);
        Assertions.assertEquals(0, compare);
    }
    @Test
    public void testSemverEquqls() {
        Semver semverA = Semver.parse("10.2.3");
        Semver semverB = Semver.parse("10.2.3");
        int compare = semverA.compareTo(semverB);
        Assertions.assertTrue(semverA.equals(semverB));
    }
    @Test
    public void testSemverEquqlsNull() {
        Semver semverA = Semver.parse("10.2.3");
        Assertions.assertFalse(semverA.equals(null));
    }
    @Test
    public void testSemverHashCode() {
        Semver semverA = Semver.parse("10.2.3");
        Assertions.assertEquals(Objects.hash(semverA.major(),
                semverA.minor(),
                semverA.patch()), semverA.hashCode());
    }
    @Test
    public void testSemverIsInRange() {
        Semver semverA = Semver.parse("10.2.3");
        Assertions.assertTrue(semverA.isInRange("10.2.0", "10.2.4"));
    }
    @Test
    public void testSemverIsNotInRange() {
        Semver semverA = Semver.parse("1.2.3");
        Assertions.assertFalse(semverA.isInRange("10.2.0", "10.2.4"));
    }
}