package com.teamwave.albumservice.service;

import com.teamwave.albumservice.dto.FaixaDTO;
import com.teamwave.albumservice.model.Faixa;

import java.util.UUID;

public interface FaixaService extends AbstractService<Faixa, UUID> {
    Faixa create(FaixaDTO faixaDTO, String authorization);
}
