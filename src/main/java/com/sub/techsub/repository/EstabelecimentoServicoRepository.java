package com.sub.techsub.repository;

import com.sub.techsub.entity.reference.EstabelecimentoServico;
import com.sub.techsub.entity.reference.EstabelecimentoServicoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstabelecimentoServicoRepository extends JpaRepository<EstabelecimentoServico, EstabelecimentoServicoId> {

}
