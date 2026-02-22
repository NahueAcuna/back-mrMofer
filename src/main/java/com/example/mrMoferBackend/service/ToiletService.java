package com.example.mrMoferBackend.service;

import com.example.mrMoferBackend.dto.ToiletRequestDto;
import com.example.mrMoferBackend.dto.ToiletResponseDto;
import com.example.mrMoferBackend.entity.Toilet;
import com.example.mrMoferBackend.mapper.ToiletMapper;
import com.example.mrMoferBackend.repository.ToiletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ToiletService {

    private final ToiletMapper toiletMapper;
    private final ToiletRepository toiletRepository;

    @Transactional
    public ToiletRequestDto createToilet(ToiletRequestDto toiletRequestDto){

    if(toiletRepository.existsByName(toiletRequestDto.getName())){

        throw new RuntimeException("El nombre ya esxiste");
    }
    Toilet toilet = toiletMapper.toEntity(toiletRequestDto);
    Toilet savedToillet = toiletRepository.save(toilet);
    return toiletMapper.toRequestDto(savedToillet);
    }
    public List<ToiletResponseDto> getToilets(){

        List<Toilet> totalToilets = toiletRepository.findAll();

        List<ToiletResponseDto> totalToiletsResponseDto = totalToilets.stream().map(t -> toiletMapper.toResponseDto(t)).collect(Collectors.toList());

        return totalToiletsResponseDto;
    }
    public ToiletResponseDto putToilet(long id, ToiletRequestDto toiletRequestDto){
        Toilet toilet = toiletRepository.findById(id).orElseThrow(() -> new RuntimeException("No existe ningun baño con esa id"));

        toiletMapper.updateEntityFromDto(toiletRequestDto,toilet);
        toiletRepository.save(toilet);

        return toiletMapper.toResponseDto(toilet);
    }
    public ToiletResponseDto getById(long id){
        Toilet toilet = toiletRepository.findById(id).orElseThrow(() -> new RuntimeException("No existe ningun baño con esa id"));
        return toiletMapper.toResponseDto(toilet);
    }
    public ToiletResponseDto deleteToilet(long id){
        Toilet toilet = toiletRepository.findById(id).orElseThrow(() ->new RuntimeException("No existe un baño con ese id"));
        toiletRepository.delete(toilet);
        return toiletMapper.toResponseDto(toilet);
    }
}
