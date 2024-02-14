package com.amzmall.project.qna.repository;

import com.amzmall.project.qna.domain.entity.Qna;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QnaRepository extends JpaRepository<Qna, Long>  {
	Optional<Qna> findByQnaId(String qnaId);
    List<Qna> findAllByCustomerEmail(String email, Pageable pageable);
}
