package org.teamwork.spring.bookstoremvcrest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamwork.spring.bookstoremvcrest.mapper.abstraction.AbstractMapper;
import org.teamwork.spring.bookstoremvcrest.model.Costumer;
import org.teamwork.spring.bookstoremvcrest.model.dto.CostumerDTO;
import org.teamwork.spring.bookstoremvcrest.service.DefaultService;
import org.teamwork.spring.bookstoremvcrest.repository.CostumerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CostumerServiceImpl implements DefaultService<CostumerDTO, Costumer, Integer> {
    @Autowired
    private CostumerRepository costomerRepository;
    @Autowired
    private AbstractMapper mapper;

    @Override
    public List<CostumerDTO> findAll() {
        List<Costumer> costumers = costomerRepository.findAll();
        List<CostumerDTO> costumerDTOS = costumers.stream().map(costumer -> mapper.toDTO(costumer, CostumerDTO.class)).collect(Collectors.toList());

        return costumerDTOS;
    }

    @Override
    public CostumerDTO findByKey(Integer key) {
        return mapper.toDTO(costomerRepository.findById(key), CostumerDTO.class);
    }

    @Override
    public CostumerDTO save(CostumerDTO obj) {
        Costumer costumer = costomerRepository.save(mapper.toEntity(obj, Costumer.class));
        return mapper.toDTO(costumer, CostumerDTO.class);
    }

    @Override
    public CostumerDTO update(Integer key, CostumerDTO obj) {
        Costumer costumer = mapper.toEntity(obj, Costumer.class);
        costumer.setId(key);
        costomerRepository.save(costumer);
        return mapper.toDTO(costumer, CostumerDTO.class);
    }

    @Override
    public void delete(Integer key) {
        costomerRepository.deleteById(key);
    }
}
