package uomaep.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResponseJsonWriter {
    private static ObjectMapper objMapper = new ObjectMapper();

    public static void writeJSON(HttpServletResponse res, Object obj) throws IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(objMapper.writeValueAsString(obj));
    }

    public static String writeJSON(Object obj) throws IOException {
        return objMapper.writeValueAsString(obj);
    }
}
