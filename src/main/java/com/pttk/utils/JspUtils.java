
package com.pttk.utils;

import jakarta.servlet.ServletContext;
import java.io.File;

public class JspUtils {
    public static String escapeHtml(Object value) {
        if (value == null) {
            return "";
        }

        return value.toString()
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    public static String getOrDefaultFile(ServletContext application, String filepath, String def) {
        if (filepath == null || filepath.trim().isEmpty()) {
            return def;
        }

        String realPath = application.getRealPath("/" + filepath);

        if (realPath == null || !new File(realPath).exists()) {
            return def;
        }

        return filepath;
    }
}
