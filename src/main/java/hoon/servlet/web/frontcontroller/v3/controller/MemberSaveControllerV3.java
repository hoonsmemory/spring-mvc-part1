package hoon.servlet.web.frontcontroller.v3.controller;

import hoon.servlet.domain.member.Member;
import hoon.servlet.domain.member.MemberRepository;
import hoon.servlet.web.frontcontroller.ModelView;
import hoon.servlet.web.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> map) {
        String username = map.get("username");
        int age = Integer.parseInt(map.get("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        ModelView modelView = new ModelView("save-result");
        modelView.getModel().put("member", member);

        return modelView;
    }
}
