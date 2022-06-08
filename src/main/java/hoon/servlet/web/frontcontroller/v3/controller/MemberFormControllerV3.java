package hoon.servlet.web.frontcontroller.v3.controller;

import hoon.servlet.web.frontcontroller.ModelView;
import hoon.servlet.web.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberFormControllerV3 implements ControllerV3 {

    @Override
    public ModelView process(Map<String, String> map) {
        return new ModelView("new-form");
    }
}
