package com.amzmall.project.qna.repository;

import com.amzmall.project.qna.domain.entity.Reply;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Optional<Reply> findByReplyId(int replyId);
    Optional<Reply> findByQuestionQuestionId(int questionInt);
}