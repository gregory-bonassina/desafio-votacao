package com.example.desafiovotacao.services;

import java.util.List;

import com.example.desafiovotacao.dtos.CreatedAssociateDTO;
import com.example.desafiovotacao.dtos.responses.AssociateResponseDTO;

public interface IAssociateService {

    public AssociateResponseDTO create(CreatedAssociateDTO createdAssociateDTO);

    public List<AssociateResponseDTO> listAllAssociates();

    public AssociateResponseDTO findById(Integer associateId);
}
