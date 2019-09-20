package com.romanov.storage.controller;

import com.romanov.storage.repos.TypeRepo;
import com.romanov.storage.util.UserContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import recipes.Type;

import java.util.List;

@RestController()
@RequestMapping(value = "type", produces = "application/json")
@CrossOrigin(origins = "*")
public class TypeController {

    @Autowired
    private TypeRepo typeRepo;

    @GetMapping("/all")
    private List<Type> getAllTypes(){
        return typeRepo.findAll();
    }

    @PostMapping(value = "/add", consumes = "application/json")
    private Type addNewType( @RequestBody Type type ){
        type.setCreator( UserContextHolder.getContext().getUserName() );
        typeRepo.save( type );
        return type;
    }


}
