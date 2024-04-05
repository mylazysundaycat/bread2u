package com.daegeon.bread2u.initial;

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

import java.util.ArrayList;
import java.util.List;

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
        Member member1 = Member.builder()
                .membername("user1")
                .nickname("윈터")
                .email("test1@gmail.com")
                .password("1234")
                .build();
        member1.getRoles().add("USER");
        memberRepository.save(member1);

        memberRepository.save(Member.builder()
                .membername("user2")
                .nickname("팜하니")
                .email("test2@gmail.com")
                .password("1234")
                .build());
        memberRepository.save(Member.builder()
                .membername("user3")
                .nickname("Newjeans")
                .email("test3@gmail.com")
                .password("1234")
                .build());
        memberRepository.save(Member.builder()
                .membername("user4")
                .nickname("애니비아")
                .email("test4@gmail.com")
                .password("1234")
                .build());
        memberRepository.save(Member.builder()
                .membername("user5")
                .nickname("갓김치")
                .email("test5@gmail.com")
                .password("1234")
                .build());
        memberRepository.save(Member.builder()
                .membername("user6")
                .nickname("제니")
                .email("test6@gmail.com")
                .password("1234")
                .build());
        memberRepository.save(Member.builder()
                .membername("user7")
                .nickname("워뇨워뇨워뇨")
                .email("test7@gmail.com")
                .password("1234")
                .build());
    }

    private void initializePost() {
        Post test1 = Post.builder().title("테스트 게시물").content("테스트 게시물").likes(0L) .view(0L).build();
        Post test2 = Post.builder().title("테스트 게시물").content("테스트 게시물").likes(0L) .view(0L).build();
        Post test3 = Post.builder().title("테스트 게시물").content("테스트 게시물").likes(0L) .view(0L).build();
        Post test4 = Post.builder().title("테스트 게시물").content("테스트 게시물").likes(0L) .view(0L).build();
        Post test5 = Post.builder().title("테스트 게시물").content("테스트 게시물").likes(0L) .view(0L).build();
        Post test6 = Post.builder().title("테스트 게시물").content("테스트 게시물").likes(0L) .view(0L).build();
        Post test7 = Post.builder().title("테스트 게시물").content("테스트 게시물").likes(0L) .view(0L).build();
        Post test8 = Post.builder().title("테스트 게시물").content("테스트 게시물").likes(0L) .view(0L).build();
        Post test9 = Post.builder().title("테스트 게시물").content("테스트 게시물").likes(0L) .view(0L).build();
        Post test10 = Post.builder().title("테스트 게시물").content("테스트 게시물").likes(0L) .view(0L).build();
        Post test11 = Post.builder().title("테스트 게시물").content("테스트 게시물").likes(0L) .view(0L).build();

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
                .view(5L)
                .build();
        Post post3 = Post.builder()
                .title("대전 은행동에 있는 땡큐베리머치도")
                .content("꽤 괜찮아요 ㅇㅇ 딸기파르페가 진짜 미친놈임")
                .likes(0L)
                .view(4L)
                .build();
        Post post4 = Post.builder()
                .title("빵해장 코스 공유합니다")
                .content("빵 먹고나서 가야하는 곳 top1은 태평소 소국밥, top2는 봉명동 온천쭈꾸미칼국수, top3은 중촌동에 보릿고개 추천합니다.")
                .likes(0L)
                .view(15L)
                .build();
        Post post5 = Post.builder()
                .title("한살이라도 어릴때 빵을 많이 먹어놔야")
                .content("소화기능 떨어졌을때 안서럽습니다 많이많이 최대한 압축해서 드셔놓으십쇼")
                .likes(1L)
                .view(7L)
                .build();
        Post post6 = Post.builder()
                .title("빵에 김치 싸먹는것도 은근 히트임")
                .content("감자랑 고구마도 싸먹는데 빵이라고 안될게 뭐임?ㅋㅋ")
                .likes(1L)
                .view(13L)
                .build();
        Post post7 = Post.builder()
                .title("스테이씨도 왕가탕후루 모델 하는데 뉴진스도 성심당 모델 할만하지 않나요?")
                .content("이참에 파리바게트랑 뚜레주르 2인체제 말고 성심당까지 3인체제로 가보자고")
                .likes(8L)
                .view(32L)
                .build();
        Post post9 = Post.builder()
                .title("빵빵빠라빠빠빠 빵빵빵")
                .content("날위한 축배를 짠짠짠")
                .likes(4L)
                .view(15L)
                .build();
        Post post10 = Post.builder()
                .title("충대 앞에 굿즈샵 없애고 성심당 체인점으로 바꾸죠")
                .content("서연고 서성한 충경외시 ㄱㄴ")
                .likes(1L)
                .view(22L)
                .build();
        Post post11 = Post.builder()
                .title("딸기시루케이크 먹고싶은 날이다~~~~")
                .content("으아아~~")
                .likes(1L)
                .view(36L)
                .build();

        postSave(test1, memberRepository.findOneByMembername("user4").orElseThrow());
        postSave(test2, memberRepository.findOneByMembername("user4").orElseThrow());
        postSave(test3, memberRepository.findOneByMembername("user4").orElseThrow());
        postSave(test4, memberRepository.findOneByMembername("user4").orElseThrow());
        postSave(test5, memberRepository.findOneByMembername("user4").orElseThrow());
        postSave(test6, memberRepository.findOneByMembername("user4").orElseThrow());
        postSave(test7, memberRepository.findOneByMembername("user4").orElseThrow());
        postSave(test8, memberRepository.findOneByMembername("user4").orElseThrow());
        postSave(test9, memberRepository.findOneByMembername("user4").orElseThrow());
        postSave(test10, memberRepository.findOneByMembername("user4").orElseThrow());
        postSave(test11, memberRepository.findOneByMembername("user4").orElseThrow());

        postSave(post5, memberRepository.findOneByMembername("user4").orElseThrow());
        postSave(post4, memberRepository.findOneByMembername("user3").orElseThrow());
        postSave(post3, memberRepository.findOneByMembername("user3").orElseThrow());
        postSave(post2, memberRepository.findOneByMembername("user2").orElseThrow());
        postSave(post1, memberRepository.findOneByMembername("user1").orElseThrow());
        postSave(post6, memberRepository.findOneByMembername("user5").orElseThrow());
        postSave(post7, memberRepository.findOneByMembername("user2").orElseThrow());
        postSave(post9, memberRepository.findOneByMembername("user6").orElseThrow());
        postSave(post10, memberRepository.findOneByMembername("user7").orElseThrow());
        postSave(post11, memberRepository.findOneByMembername("user1").orElseThrow());


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
        commentSave(postRepository.findById(16L).orElseThrow(), comment1
                , memberRepository.findOneByMembername("user1").orElseThrow());
        commentSave(postRepository.findById(16L).orElseThrow(), comment2
                , memberRepository.findOneByMembername("user2").orElseThrow());
        commentSave(postRepository.findById(15L).orElseThrow(), comment3
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