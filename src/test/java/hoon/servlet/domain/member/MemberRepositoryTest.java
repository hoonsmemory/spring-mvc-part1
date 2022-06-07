package hoon.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void afterEach() {
        memberRepository.clearAll();
    }

    @Test
    @DisplayName("저장")
    public void save() throws Exception
    {
        // given
        Member member = new Member("hoon", 30);

        // when
        Member saveMember = memberRepository.save(member);

        // then
        Member findMember = memberRepository.findById(saveMember.getId());
        Assertions.assertThat(findMember).isEqualTo(saveMember);
    }
    
    @Test
    @DisplayName("조회")
    public void findAll() throws Exception
    {
        // given
        Member member1 = new Member("hoon", 30);
        memberRepository.save(member1);

        Member member2 = new Member("kim", 30);
        memberRepository.save(member2);

        // when
        List<Member> memberList = memberRepository.findAll();

        // then
        Assertions.assertThat(memberList.size()).isEqualTo(2);

    }

}