package br.com.blackboard.evento;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Evento> findWithLockById(int id);
}
