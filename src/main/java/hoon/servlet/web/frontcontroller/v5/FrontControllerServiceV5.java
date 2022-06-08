package hoon.servlet.web.frontcontroller.v5;

import hoon.servlet.web.frontcontroller.ModelView;
import hoon.servlet.web.frontcontroller.MyView;
import hoon.servlet.web.frontcontroller.v3.ControllerV3;
import hoon.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hoon.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hoon.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hoon.servlet.web.frontcontroller.v4.ControllerV4;
import hoon.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hoon.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hoon.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hoon.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServiceV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServiceV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();


    public FrontControllerServiceV5() {
        initHandlerMappingMap(); //핸들러 매핑 초기화
        initHandlerAdapters(); //어댑터 초기화
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    private void initHandlerMappingMap() {
        //v3
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form",new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save",new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members",new MemberListControllerV3());

        //v4
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form",new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save",new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members",new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1. 핸들러(실행할 컨트롤러 객체) 조회
        Object handler = getHandler(request);

        if(handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //2. 핸들러를 처리할 수 있는 핸들러 어댑터 조회
        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        //3. 핸들러 호출 -> 비지니스 로직 실행 -> ModelView 반환(화면명, 화면에 그릴 데이터)
        ModelView modelView = adapter.handle(request, response, handler);

        //4. viewResolver 호출과 MyView 반환
        MyView myView = viewResolver(modelView);

        //5. render 호출
        myView.render(modelView.getModel(), request, response);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        MyHandlerAdapter adapter;
        for (MyHandlerAdapter findingAdapter : handlerAdapters) {
            if(findingAdapter.supports(handler)) {
                return findingAdapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다.");
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private MyView viewResolver(ModelView modelView) {
        return new MyView("/WEB-INF/views/" + modelView.getViewName() + ".jsp");
    }

}