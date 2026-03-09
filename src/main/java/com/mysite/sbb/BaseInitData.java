package com.mysite.sbb;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    @Lazy
    private BaseInitData self;
    @Bean
    public ApplicationRunner baseInitDataApplicationRunner(){
        return args -> {
           self.work1();
        };
    }
    @Transactional
    public void work1() {
        if(questionRepository.count()>0) return;

        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q2);

        Answer a = new Answer();
        a.setContent("네 자동으로 생성됩니다.");
        a.setQuestion(q2);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
        a.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a);
    }
}
