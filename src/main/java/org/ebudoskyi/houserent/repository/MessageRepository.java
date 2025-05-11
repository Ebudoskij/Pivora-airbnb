package org.ebudoskyi.houserent.repository;

import org.ebudoskyi.houserent.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByFromUserId(Long fromUserId);

    List<Message> findByToUserId(Long toUserId);

    List<Message> findByFromUserIdAndToUserId(Long fromUserId, Long toUserId);

    List<Message> findByFromUserIdAndToUserIdAndTimestampAfter(Long fromUserId, Long toUserId, LocalDateTime timestamp);

    List<Message> findByToUserIdAndTimestampAfter(Long toUserId, LocalDateTime timestamp);

    List<Message> findByFromUserIdAndTimestampAfter(Long fromUserId, LocalDateTime timestamp);
}

