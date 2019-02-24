package com.invillia.acme.controller;

import com.invillia.acme.viewer.StoreVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(tags = { "storeController" })
@RestController
@RequestMapping("/store")
public class StoreController {

    @ApiOperation(value = "Create a new Store.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createStore(HttpServletRequest request, @Valid @RequestBody StoreVM store) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
