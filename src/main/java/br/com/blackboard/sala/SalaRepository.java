package br.com.blackboard.sala;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Integer> {
    boolean existsByPredioAndNumero(String predio, String numero);
}