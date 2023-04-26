package side.boardservice.domain.reply;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository  extends JpaRepository<Reply, Long> {
    List<Reply> findByPostingCode(Long postingCode, Sort sort);
}
