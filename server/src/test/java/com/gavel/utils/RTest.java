package com.gavel.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the R response wrapper.
 */
class RTest {

    @Test
    void newR_hasCodeZero() {
        R r = new R();
        assertEquals(0, r.get("code"));
    }

    @Test
    void ok_isChainableAndZeroCode() {
        R r = R.ok().put("token", "abc");
        assertEquals(0, r.get("code"));
        assertEquals("abc", r.get("token"));
    }

    @Test
    void error_withMessage_uses500() {
        R r = R.error("boom");
        assertEquals(500, r.get("code"));
        assertEquals("boom", r.get("msg"));
    }

    @Test
    void error_withCodeAndMessage() {
        R r = R.error(401, "unauthorized");
        assertEquals(401, r.get("code"));
        assertEquals("unauthorized", r.get("msg"));
    }

    @Test
    void ok_withMessage_zeroCode() {
        R r = R.ok("hello");
        assertEquals(0, r.get("code"));
        assertEquals("hello", r.get("msg"));
    }
}
