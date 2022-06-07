package hoon.servlet.basic.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import hoon.servlet.basic.HelloData;
import org.springframework.http.HttpHeaders;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setHeader(HttpHeaders.CONTENT_TYPE,"application/json;charset=utf-8");

        HelloData helloData = new HelloData();
        helloData.setUsername("hoon");
        helloData.setAge(30);

        String value = objectMapper.writeValueAsString(helloData);
        response.getWriter().write(value);
    }
}
