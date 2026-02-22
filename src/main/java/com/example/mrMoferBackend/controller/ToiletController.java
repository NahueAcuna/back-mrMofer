package com.example.mrMoferBackend.controller;

import com.example.mrMoferBackend.dto.ToiletRequestDto;
import com.example.mrMoferBackend.dto.ToiletResponseDto;
import com.example.mrMoferBackend.entity.Toilet;
import com.example.mrMoferBackend.service.ToiletService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@RestController
@RequestMapping("/toilets")
public class ToiletController {

    private final ToiletService toiletService;

    @PostMapping
    public ResponseEntity<?> postToilet(@RequestBody ToiletRequestDto toiletRequestDto){

        try{
            ToiletRequestDto savedToilet = toiletService.createToilet(toiletRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedToilet);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<?> getToilets(){
        List<ToiletResponseDto> allToilets = toiletService.getToilets();
        return ResponseEntity.ok(allToilets);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> putToilet(@PathVariable long id,@RequestBody ToiletRequestDto toiletRequestDto){

        try{
            ToiletResponseDto toiletResponseDto = toiletService.putToilet(id,toiletRequestDto);
            return ResponseEntity.ok(toiletResponseDto);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id){
        try{

            ToiletResponseDto toiletResponseDto = toiletService.getById(id);
            return ResponseEntity.ok(toiletResponseDto);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteToilet(@PathVariable long id){
        try{
            ToiletResponseDto toiletResponseDto = toiletService.deleteToilet(id);
            return ResponseEntity.ok(toiletResponseDto);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
