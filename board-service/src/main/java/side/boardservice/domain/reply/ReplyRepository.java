package side.boardservice.domain.reply;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import side.boardservice.domain.post.Post;

import java.util.List;

public interface ReplyRepository  extends JpaRepository<Reply, Long> {
}
