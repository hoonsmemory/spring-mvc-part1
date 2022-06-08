package hoon.servlet.web.frontcontroller.v3.controller;

import hoon.servlet.domain.member.Member;
import hoon.servlet.domain.member.MemberRepository;
import hoon.servlet.web.frontcontroller.ModelView;
import hoon.servlet.web.frontcontroller.v3.ControllerV3;

import java.util.List;
import java.util.Map;

public class MemberListControllerV3 implements ControllerV3 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> map) {
        List<Member> members = memberRepository.findAll();
        ModelView modelView = new ModelView("members");
        modelView.getModel().put("members", members);

        return modelView;
    }
}
