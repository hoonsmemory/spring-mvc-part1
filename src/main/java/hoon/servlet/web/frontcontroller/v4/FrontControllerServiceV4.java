package hoon.servlet.web.frontcontroller.v4;

import hoon.servlet.web.frontcontroller.ModelView;
import hoon.servlet.web.frontcontroller.MyView;
import hoon.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hoon.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hoon.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServiceV4 extends HttpServlet {

    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerServiceV4() {
        controllerMap.put("/front-controller/v4/members/new-form",new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save",new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members",new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        //1. 컨트롤러(매핑정보) 조회
        String requestURI = request.getRequestURI();
        ControllerV4 controller = controllerMap.get(requestURI);
        if(controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //2. 요청 파라미터 map으로 변환
        Map<String, String> paramMap = createParamMap(request);

        //3. 컨트롤러 호출과 ModelView 반환
        Map<String, Object> model = new HashMap<>(); //뷰로 보낼 모델
        String viewName = controller.process(paramMap, model); //모델 객체 전달

        //4. viewResolver 호출과 MyView 반환
        MyView myView = viewResolver(viewName);
        
        //5. render 호출
        myView.render(model, request, response);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames()
                .asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}