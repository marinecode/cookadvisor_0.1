package com.romanov.advisor.services;

import com.romanov.advisor.config.ServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import recipes.Recipe;

import java.util.List;

@Service
public class StorageApiService {

    @Autowired
    private RestTemplate rest;
    @Autowired
    private ServiceConfig config;


    ResponseEntity<List<Recipe>> getRecipeByTypes(String[] types){

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl( config.getStorageUrl() + "/recipe/bytypes")
                                                          .queryParam("types", (Object[]) types).build();
        return rest.exchange( uriComponents.toUriString(), HttpMethod.GET, HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<Recipe>>() {});
    }

    ResponseEntity<List<String>> getRecipeNamesByIngs( String[] ings ){

        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl( config.getStorageUrl() + "/recipe/names/byings")
                .queryParam("ings", (Object[]) ings).build();

        return rest.exchange( uriComponents.toUriString(), HttpMethod.GET, HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<String>>() {});
    }

}
