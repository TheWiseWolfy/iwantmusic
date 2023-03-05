package com.unwise.content_service.controllers;

import com.unwise.content_service.dto.CreditDTO;
import com.unwise.content_service.embeddable.CreditKey;
import com.unwise.content_service.entities.Content;
import com.unwise.content_service.entities.Credits;
import com.unwise.content_service.exceptions.ContentNotFoundException;
import com.unwise.content_service.repositories.CreditsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CreditsController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private  CreditsRepository repository;

    CreditsController() {
    }

    @GetMapping("/credits")
    ResponseEntity<List<EntityModel<CreditDTO>>> all() {
        var creditsList =  repository.findAll();

        var creditsDTOList = creditsList.stream()
                .map(this::convertToDto)
                .map(a ->  a.add(linkTo(methodOn(CreditsController.class).one(a.getId())  ).withSelfRel() ) )
                .collect(Collectors.toList());

        return new ResponseEntity<>(creditsDTOList, HttpStatus.OK);
    }
    //TODO Unique get for credits
    EntityModel<Credits> one( CreditKey id) {

        Credits credit = repository.findById(id) //
                .orElseThrow(() -> new ContentNotFoundException(id.getContentID()));


        var entityModel = EntityModel.of(credit, //
                linkTo(methodOn(CreditsController.class).one(id)).withSelfRel(),
                linkTo(methodOn(CreditsController.class).all()).withRel("employees"));

        return entityModel;
    }


    @PostMapping(value= "/credits" ,consumes = "application/json", produces = "application/json")
    Credits newEmployee(@RequestBody Credits newCredit) {
        return repository.save(newCredit);
    }

    private CreditDTO convertToDto(Credits post) {
        CreditDTO creditDTO = modelMapper.map(post, CreditDTO.class);
        return creditDTO;
    }

}
