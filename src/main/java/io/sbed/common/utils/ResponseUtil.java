package io.sbed.common.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseUtil {
    public static void writeJSON(HttpServletResponse response, Object o) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println(o.toString());
        out.flush();
        out.close();
    }
}