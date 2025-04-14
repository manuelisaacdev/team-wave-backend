package com.teamwave.albumservice.repository;

import com.teamwave.albumservice.model.Faixa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FaixaRepository extends JpaRepository<Faixa, UUID> {

}
