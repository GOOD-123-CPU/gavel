package com.gavel.controller;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * File upload security contract: the extension whitelist must
 * include only document/image types and must never allow executables.
 */
class FileUploadWhitelistTest {

    @SuppressWarnings("unchecked")
    private Set<String> allowedExts() throws Exception {
        Field f = FileController.class.getDeclaredField("ALLOWED_EXTS");
        f.setAccessible(true);
        return (Set<String>) f.get(null);
    }

    @Test
    void whitelist_allowsCommonImagesAndDocs() throws Exception {
        Set<String> exts = allowedExts();
        assertTrue(exts.contains("jpg"));
        assertTrue(exts.contains("png"));
        assertTrue(exts.contains("pdf"));
        assertTrue(exts.contains("xlsx"));
    }

    @Test
    void whitelist_neverAllowsExecutables() throws Exception {
        Set<String> exts = allowedExts();
        assertFalse(exts.contains("exe"));
        assertFalse(exts.contains("sh"));
        assertFalse(exts.contains("bat"));
        assertFalse(exts.contains("jsp"));
        assertFalse(exts.contains("war"));
        assertFalse(exts.contains("jar"));
        assertFalse(exts.contains("php"));
        assertFalse(exts.contains("html"));
    }

    @Test
    void whitelist_isLowercaseOnly() throws Exception {
        for (String ext : allowedExts()) {
            assertTrue(ext.equals(ext.toLowerCase()), "non-lowercase extension: " + ext);
        }
    }
}
