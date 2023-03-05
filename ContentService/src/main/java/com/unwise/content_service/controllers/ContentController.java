package com.unwise.content_service.controllers;

import com.unwise.content_service.dto.ContentDTO;
import com.unwise.content_service.entities.Content;
import com.unwise.content_service.repositories.ContentRepository;
import com.unwise.content_service.exceptions.ContentNotFoundException;
import com.unwise.content_service.repositories.CreditsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ContentController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private  ContentRepository contentRepository;

    //Create
    @PostMapping(value= "/contents" ,consumes = "application/json", produces = "application/json")
    ResponseEntity<Void> create(@RequestBody Content newContent) {

        Long id = contentRepository.save(newContent).getContent_id();
        return ResponseEntity.created(URI.create("/contents/" + id.toString())).build();
    }

    //Read all
    @GetMapping("/contents")
    ResponseEntity<List<EntityModel<ContentDTO>>> all() {
        var contentList =  contentRepository.findAll();
        var contentDTOList = contentList.stream()
                .map(this::convertToDto)
                .map(a ->  a.add(linkTo(methodOn(ContentController.class).one(a.getContent_id())).withSelfRel() )
                            .add(linkTo(methodOn(ContentController.class).all()).withRel("parent"))
                )
                .collect(Collectors.toList());

        //Mereu returnam 200
        return new ResponseEntity<>(contentDTOList, HttpStatus.OK );
    }

    //Read after id
    @GetMapping("/contents/{id}")
    ResponseEntity<EntityModel<ContentDTO> >one(@PathVariable Long id) {

        Optional<Content> content = contentRepository.findById(id);

        if(content.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            var contendDTO = convertToDto(content.get());

            var entityModel = EntityModel.of(contendDTO);
            entityModel.add(linkTo(methodOn(ContentController.class).one(contendDTO.getContent_id())).withSelfRel());
            entityModel.add(linkTo(methodOn(ContentController.class).all()).withRel("parent"));

            return new ResponseEntity<>(entityModel, HttpStatus.OK );
        }
    }

    //Update by id
    @PutMapping(value= "/contents/{id}",consumes = "application/json", produces = "application/json")
    ResponseEntity<Void> addOrUpdateContent(@RequestBody Content content, @PathVariable Long id) {

        Optional<Content> existingContent = contentRepository.findById(id);

        content.setContent_id(id);
        contentRepository.save(content);

        if(existingContent.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return ResponseEntity.created(URI.create("/contents/" + id.toString())).build();
        }
    }

    //Delete by id
    @DeleteMapping("/contents/{id}")
    public ResponseEntity<Void> deleteArtistById(@PathVariable Long id) {

        Optional<Content> existingContent = contentRepository.findById(id);
        if(existingContent.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            contentRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //AUX
    private ContentDTO convertToDto(Content post) {
        ContentDTO contentDTO = modelMapper.map(post, ContentDTO.class);
        return contentDTO;
    }


}
