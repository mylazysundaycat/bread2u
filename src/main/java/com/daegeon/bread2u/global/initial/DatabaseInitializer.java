package com.daegeon.bread2u.global.initial;

import com.daegeon.bread2u.module.member.entity.Member;
import com.daegeon.bread2u.module.member.repository.MemberRepository;
import com.daegeon.bread2u.module.post.entity.Post;
import com.daegeon.bread2u.module.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Autowired
    public DatabaseInitializer(MemberRepository memberRepository,
                               PostRepository postRepository) {
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initializeData() {
        initializeMembers();
        initializePost();
    }

    private void initializeMembers() {
            memberRepository.save(Member.builder()
                    .membername("winter")
                    .nickname("윈터")
                    .email("test1@mail.com")
                    .password("1234")
                    .build());
    }

    private void initializePost() {
        Post post1 = Post.builder()
                .title("괜한 투정처럼 Thirsty 아니 사실 이건 Thirsty")
                .content("맘이 커질수록 Thirsty~")
                .likes(0L)
                .view(0L)
                .build();
        postSave(post1, memberRepository.findOneByMembername("winter").orElseThrow());
    }

    private void postSave(Post post, Member member) {
        post.setMember(member);
        postRepository.save(post);
    }
}