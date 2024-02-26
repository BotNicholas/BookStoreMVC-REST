package org.teamwork.spring.bookstoremvcrest.service.impl;

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
    private final CostumerRepository costumerRepository;
    private final AbstractMapper mapper;

    public CostumerServiceImpl(CostumerRepository costumerRepository, AbstractMapper mapper) {
        this.costumerRepository = costumerRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CostumerDTO> findAll() {
        List<Costumer> costumers = costumerRepository.findAll();
        return costumers.stream().map(costumer -> mapper.toDTO(costumer, CostumerDTO.class)).collect(Collectors.toList());
    }

    @Override
    public CostumerDTO findByKey(Integer key) {
        return mapper.toDTO(costumerRepository.findById(key), CostumerDTO.class);
    }

    @Override
    public CostumerDTO save(CostumerDTO obj) {
        Costumer costumer = costumerRepository.save(mapper.toEntity(obj, Costumer.class));
        return mapper.toDTO(costumer, CostumerDTO.class);
    }

    @Override
    public CostumerDTO update(Integer key, CostumerDTO obj) {
        Costumer costumer = mapper.toEntity(obj, Costumer.class);
        costumer.setId(key);
        costumerRepository.save(costumer);
        return mapper.toDTO(costumer, CostumerDTO.class);
    }

    @Override
    public void delete(Integer key) {
        costumerRepository.deleteById(key);
    }
}
