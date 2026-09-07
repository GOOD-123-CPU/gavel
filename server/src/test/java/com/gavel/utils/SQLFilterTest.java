package com.gavel.utils;

import org.junit.jupiter.api.Test;

import com.gavel.entity.EIException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * SQL injection guard tests — the heart of the whitelist defense.
 */
class SQLFilterTest {

    @Test
    void sqlInject_nullOrBlank_returnsNull() {
        assertEquals(null, SQLFilter.sqlInject(null));
        assertEquals(null, SQLFilter.sqlInject(""));
        assertEquals(null, SQLFilter.sqlInject("   "));
    }

    @Test
    void sqlInject_validIdentifier_passes() {
        assertEquals("shangpinmingcheng", SQLFilter.sqlInject("shangpinmingcheng"));
        assertEquals("col_01", SQLFilter.sqlInject("col_01"));
    }

    @Test
    void sqlInject_rejectsNonIdentifierCharacters() {
        // classic injection payloads must all be rejected
        assertThrows(EIException.class, () -> SQLFilter.sqlInject("id; DROP TABLE users"));
        assertThrows(EIException.class, () -> SQLFilter.sqlInject("id' OR '1'='1"));
        assertThrows(EIException.class, () -> SQLFilter.sqlInject("id--"));
        assertThrows(EIException.class, () -> SQLFilter.sqlInject("id/*"));
        assertThrows(EIException.class, () -> SQLFilter.sqlInject("1=1"));
    }

    @Test
    void sqlInject_rejectsSqlKeywords() {
        assertThrows(EIException.class, () -> SQLFilter.sqlInject("select"));
        assertThrows(EIException.class, () -> SQLFilter.sqlInject("SELECT"));
        assertThrows(EIException.class, () -> SQLFilter.sqlInject("unionall"));
        assertThrows(EIException.class, () -> SQLFilter.sqlInject("truncate"));
        assertThrows(EIException.class, () -> SQLFilter.sqlInject("drop"));
        assertThrows(EIException.class, () -> SQLFilter.sqlInject("sleep"));
    }

    @Test
    void checkIdentifier_rejectsNullAndIllegal() {
        assertThrows(EIException.class, () -> SQLFilter.checkIdentifier(null));
        assertThrows(EIException.class, () -> SQLFilter.checkIdentifier("bad-name"));
        assertThrows(EIException.class, () -> SQLFilter.checkIdentifier("bad name"));
        assertDoesNotThrow(() -> SQLFilter.checkIdentifier("good_name_1"));
    }

    @Test
    void checkWhitelisted_acceptsWhitelistedTableAndColumn() {
        // The whitelist maps table name AND "table.column" keys (CommonController-style)
        var whitelist = java.util.Set.of("paimaishangpin", "paimaishangpin.paimaishangpin",
                "yonghu", "yonghu.yonghuming");
        assertDoesNotThrow(() -> SQLFilter.checkWhitelisted("paimaishangpin", "paimaishangpin", whitelist));
        assertDoesNotThrow(() -> SQLFilter.checkWhitelisted("yonghu", "yonghuming", whitelist));
    }

    @Test
    void checkWhitelisted_rejectsNonWhitelistedTableOrColumn() {
        var whitelist = java.util.Set.of("paimaishangpin.paimaishangpin");
        assertThrows(EIException.class, () -> SQLFilter.checkWhitelisted("users", "password", whitelist));
        assertThrows(EIException.class, () -> SQLFilter.checkWhitelisted("paimaishangpin", "id", whitelist));
        assertThrows(EIException.class, () -> SQLFilter.checkWhitelisted(null, "id", whitelist));
        assertThrows(EIException.class, () -> SQLFilter.checkWhitelisted("paimaishangpin", null, whitelist));
    }
}
