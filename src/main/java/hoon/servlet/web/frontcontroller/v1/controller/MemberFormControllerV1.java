package hoon.servlet.web.frontcontroller.v1.controller;

import hoon.servlet.web.frontcontroller.v1.ControllerV1;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberFormControllerV1 implements ControllerV1 {
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(viewPath);// 컨트롤러 -> 뷰로 이동할 때 사용
        requestDispatcher.forward(request, response);//다른 서블릿이나 JSP로 이동할 수 있는 기능이다. 서버 내부에서 다시 호출이발생한다.
    }
}
