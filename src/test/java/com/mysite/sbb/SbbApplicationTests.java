package com.mysite.sbb;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class SbbApplicationTests {
	@Autowired
	private QuestionRepository questionRepository;

	@Test
	@DisplayName("질문조회")
	void t1() {
		List<Question> all = this.questionRepository.findAll();
		assertEquals(2, all.size());

		Question q = all.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}

	@Test
	@DisplayName("FindById")
	void t2() {
		Optional<Question> oq = this.questionRepository.findById(1);
		if(oq.isPresent()) {
			Question q = oq.get();
			assertEquals("sbb가 무엇인가요?", q.getSubject());
		}
	}

	@Test
	@DisplayName("FindBySubject")
	void t3() {
		Question q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals(1, q.getId());
	}

	@Test
	@DisplayName("findBySubjectAndContent")
	void t4() {
		Question q = this.questionRepository.findBySubjectAndContent(
				"sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
		assertEquals(1, q.getId());
	}

	@Test
	@DisplayName("findBySubjectLike")
	void t5() {
		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
		Question q = qList.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}

	@Test
	@DisplayName("질문수정")
	@Transactional
//	@Rollback(false)
	void t6() {
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		q.setSubject("수정된 제목");
		this.questionRepository.save(q);
	}

	@Test
	@DisplayName("질문삭제")
	@Transactional
//	@Rollback(false)
	void t7() {
		assertEquals(2, this.questionRepository.count());
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		this.questionRepository.delete(q);
		assertEquals(1, this.questionRepository.count());
	}

	@Autowired
	private AnswerRepository answerRepository;

	@Test
	@DisplayName("답변조회")
	@Transactional
	void t8() {
		Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer a = oa.get();
		assertEquals(2, a.getQuestion().getId());
	}

	@Test
	@DisplayName("질문 데이터를 통해 답변 데이터 찾기")
	@Transactional
	void t9() {
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		List<Answer> answerList = q.getAnswerList();

		assertEquals(1, answerList.size());
		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
	}
}
