package side.boardservice.domain.board;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardsRepository extends JpaRepository<Boards, Long> {

    Boards findByPostingCode(Long postingCode);

    List<Boards> findByCategoryCode(Long categoryCode, Sort sort);
}
