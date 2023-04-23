package side.boardservice.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardsRepository extends JpaRepository<Boards, Long> {

    Boards findByPostingCode(Long postingCode);
}
