package springbook.learningtest.junit;

import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.matchers.JUnitMatchers.hasItem;

import org.junit.Test;

public class JUnitTest {
    static Set<JUnitTest> testObjects = new HashSet<>();

    @Test public void test1() {
        assertThat(testObjects, is(not(hasItem(this))));
        testObjects.add(this);
    }

    @Test public void test2() {
        assertThat(testObjects, is(not(hasItem(this))));
        testObjects.add(this);
    }

    @Test public void test3() {
        assertThat(testObjects, is(not(hasItem(this))));
        testObjects.add(this);
    }
}
