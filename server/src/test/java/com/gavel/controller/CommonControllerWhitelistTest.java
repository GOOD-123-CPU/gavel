package com.gavel.controller;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.gavel.entity.EIException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies the CommonController whitelist contract via reflection:
 * the WHITELIST must never grow to include sensitive tables/columns.
 */
class CommonControllerWhitelistTest {

    private static Map<String, Set<String>> whitelist() throws Exception {
        Method m = CommonController.class.getDeclaredMethod("check", String.class, String.class);
        assertNotNull(m);
        m.setAccessible(true);
        // Read the WHITELIST field
        var field = CommonController.class.getDeclaredField("WHITELIST");
        field.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<String, Set<String>> wl = (Map<String, Set<String>>) field.get(null);
        return wl;
    }

    @Test
    void whitelist_coversOnlyExpectedDropdowns() throws Exception {
        Map<String, Set<String>> wl = whitelist();
        // exactly the three tables the frontend needs for dropdowns
        assertTrue(wl.containsKey("paimaishangpin"));
        assertTrue(wl.containsKey("shangpinleixing"));
        assertTrue(wl.containsKey("yonghu"));
        assertEquals(3, wl.size());
    }

    @Test
    void whitelist_neverExposesSensitiveColumns() throws Exception {
        Map<String, Set<String>> wl = whitelist();
        for (Set<String> cols : wl.values()) {
            for (String col : cols) {
                String lower = col.toLowerCase();
                assertTrue(!lower.contains("mima") && !lower.contains("password")
                        && !lower.contains("shenfenzheng") && !lower.contains("token"),
                        "sensitive column leaked into whitelist: " + col);
            }
        }
    }

    @Test
    void check_rejectsInjectionShapedIdentifiers() throws Exception {
        Method m = CommonController.class.getDeclaredMethod("check", String.class, String.class);
        m.setAccessible(true);
        var invoker = new Object() {
            void call(String table, String column) {
                try {
                    m.invoke(new CommonController(), table, column);
                } catch (java.lang.reflect.InvocationTargetException e) {
                    throw (RuntimeException) e.getCause();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
        assertThrows(EIException.class, () -> invoker.call("users;drop", "password"));
        assertThrows(EIException.class, () -> invoker.call("users", "password"));
        assertThrows(EIException.class, () -> invoker.call("information_schema", "tables"));
    }
}
