package hoon.servlet.web.frontcontroller.v3;

import hoon.servlet.web.frontcontroller.ModelView;
import hoon.servlet.web.frontcontroller.MyView;
import hoon.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hoon.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hoon.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServiceV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServiceV3() {
        controllerMap.put("/front-controller/v3/members/new-form",new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save",new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members",new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        //1. 컨트롤러(매핑정보) 조회
        String requestURI = request.getRequestURI();
        ControllerV3 controller = controllerMap.get(requestURI);
        if(controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //2. 요청 파라미터 map으로 변환
        Map<String, String> paramMap = createParamMap(request);

        //3. 컨트롤러 호출과 ModelView 반환
        ModelView modelView = controller.process(paramMap);

        //4. viewResolver 호출과 MyView 반환
        MyView myView = viewResolver(modelView);
        
        //5. render 호출
        myView.render(modelView.getModel(), request, response);
    }

    private MyView viewResolver(ModelView modelView) {
        return new MyView("/WEB-INF/views/" + modelView.getViewName() + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames()
                .asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}