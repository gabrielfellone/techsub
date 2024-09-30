package com.sub.techsub.repository;

import com.sub.techsub.entity.reference.EstabelecimentoProfissional;
import com.sub.techsub.entity.reference.EstabelecimentoProfissionalId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstabelecimentoProfissionalRepository extends JpaRepository<EstabelecimentoProfissional, EstabelecimentoProfissionalId> {

}
