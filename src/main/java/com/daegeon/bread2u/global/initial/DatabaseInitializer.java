package com.daegeon.bread2u.global.initial;

import com.daegeon.bread2u.module.comment.entity.Comment;
import com.daegeon.bread2u.module.comment.repository.CommentRepository;
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
    private final CommentRepository commentRepository;

    @Autowired
    public DatabaseInitializer(MemberRepository memberRepository,
                               PostRepository postRepository,
                               CommentRepository commentRepository) {
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
        this.commentRepository=commentRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initializeData() {
        initializeMembers();
        initializePost();
        initalizeComment();
    }

    private void initializeMembers() {
        memberRepository.save(Member.builder()
                .membername("user1")
                .nickname("윈터")
                .email("test1@mail.com")
                .password("1234")
                .build());
        memberRepository.save(Member.builder()
                .membername("user2")
                .nickname("팜하니")
                .email("test2@mail.com")
                .password("1234")
                .build());
        memberRepository.save(Member.builder()
                .membername("user3")
                .nickname("Newjeans")
                .email("test3@mail.com")
                .password("1234")
                .build());
        memberRepository.save(Member.builder()
                .membername("user4")
                .nickname("애니비아")
                .email("test4@mail.com")
                .password("1234")
                .build());
    }

    private void initializePost() {
        Post post1 = Post.builder()
                .title("성심당 딸기시루케이크 판매 연장 됐대요!")
                .content("대전 가야할 이유 또 생겼습니다 ㅋ")
                .likes(2L)
                .view(10L)
                .build();
        Post post2 = Post.builder()
                .title("대전 1박2일로 여행가려고 하는데 평일에 성심당 줄 긴가요?")
                .content("ㅈㄱㄴ")
                .likes(0L)
                .view(0L)
                .build();
        Post post3 = Post.builder()
                .title("대전 은행동에 있는 땡큐베리머치도")
                .content("꽤 괜찮아요 ㅇㅇ 딸기파르페가 진짜 미친놈임")
                .likes(0L)
                .view(0L)
                .build();
        Post post4 = Post.builder()
                .title("빵해장 코스 공유합니다")
                .content("빵 먹고나서 가야하는 곳 top1은 태평소 소국밥, top2는 봉명동 온천쭈꾸미칼국수, top3은 중촌동에 보릿고개 추천합니다.")
                .likes(0L)
                .view(0L)
                .build();
        Post post5 = Post.builder()
                .title("한살이라도 어릴때 빵을 많이 먹어놔야")
                .content("소화기능 떨어졌을때 안서럽습니다 많이많이 최대한 압축해서 드셔놓으십쇼")
                .likes(0L)
                .view(0L)
                .build();
        postSave(post1, memberRepository.findOneByMembername("user1").orElseThrow());
        postSave(post2, memberRepository.findOneByMembername("user2").orElseThrow());
        postSave(post3, memberRepository.findOneByMembername("user3").orElseThrow());
        postSave(post4, memberRepository.findOneByMembername("user3").orElseThrow());
        postSave(post5, memberRepository.findOneByMembername("user4").orElseThrow());

    }

    private void initalizeComment() {
        Comment comment1 = Comment.builder()
                .content("ㅁㅊ 저번에 줄 만리장성이라 못갔었는데")
                .likes(0L)
                .build();
        Comment comment2 = Comment.builder()
                .content("대 상 혁")
                .likes(12L)
                .build();
        Comment comment3 = Comment.builder()
                .content("평일 오전~오후에는 여유롭고 괜찮아요. 그런데 목,금 저녁은 주말 오전이랑 비슷하다고 봐야됨")
                .likes(2L)
                .build();
        commentSave(postRepository.findById(1L).orElseThrow(), comment1
                , memberRepository.findOneByMembername("user1").orElseThrow());
        commentSave(postRepository.findById(1L).orElseThrow(), comment2
                , memberRepository.findOneByMembername("user2").orElseThrow());
        commentSave(postRepository.findById(2L).orElseThrow(), comment3
                , memberRepository.findOneByMembername("user3").orElseThrow());
    }

    private void postSave(Post post, Member member) {
        post.setMember(member);
        postRepository.save(post);
    }

    private void commentSave(Post post, Comment comment, Member member){
        comment.setPost(post);
        comment.setMember(member);
        commentRepository.save(comment);
    }
}