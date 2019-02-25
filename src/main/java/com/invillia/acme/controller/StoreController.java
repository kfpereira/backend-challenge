package com.invillia.acme.controller;

import com.invillia.acme.controller.makers.MakeStore;
import com.invillia.acme.domain.model.Address;
import com.invillia.acme.domain.model.Store;
import com.invillia.acme.domain.services.AddressService;
import com.invillia.acme.domain.services.StoreService;
import com.invillia.acme.viewer.AddressVM;
import com.invillia.acme.viewer.CityVM;
import com.invillia.acme.viewer.StoreOutVM;
import com.invillia.acme.viewer.StoreVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.invillia.acme.controller.makers.MakeStore.toStore;
import static com.invillia.acme.controller.makers.MakeStore.toStoreOutVM;

@Api(tags = { "storeController" })
@RestController
@RequestMapping("/store")
public class StoreController {

    private final StoreService service;
    private final AddressService addressService;

    @Autowired
    public StoreController(StoreService service, AddressService addressService) {
        this.service = service;
        this.addressService = addressService;
    }

    @ApiOperation(value = "Create a new Store.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StoreOutVM createStore(HttpServletRequest request, @Valid @RequestBody StoreVM store) {
        return toStoreOutVM(service.save(store.getName(), getAddress(store)));
    }

    @ApiOperation(value = "Update a Store.")
    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public StoreOutVM updateStore(HttpServletRequest request, @Valid @RequestBody StoreOutVM store) {
        return toStoreOutVM(service.update(toStore(store)));
    }

    @ApiOperation(value = "Retrieve a Store - all.")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<StoreOutVM> retrieveStore(HttpServletRequest request) {
        return service.findAll()
                .stream()
                .map(MakeStore::toStoreOutVM)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Retrieve a Store by name.")
    @GetMapping(value = "/name")
    @ResponseStatus(HttpStatus.OK)
    public StoreOutVM retrieveStoreByName(HttpServletRequest request, @RequestParam String name) {
        Store store = service.findByName(name);
        return store == null? null : toStoreOutVM(store);
    }

    @ApiOperation(value = "Retrieve a Store by Address.")
    @GetMapping(value = "/address")
    @ResponseStatus(HttpStatus.OK)
    public StoreOutVM retrieveStoreByAddress(HttpServletRequest request, @RequestParam String street, String number, String addOn, String cep) {
        Store store = service.findByAddress(street, number, addOn, cep);
        return store == null? null : toStoreOutVM(store);
    }

    private Address getAddress(@Valid @RequestBody StoreVM store) {
        AddressVM addressVM = store.getAddress();
        CityVM cityVM = addressVM.getCity();
        return addressService.save(
                addressVM.getStreetName(),
                addressVM.getNumberHome(),
                addressVM.getAddressAddOn(),
                addressVM.getNeighborhood(),
                addressVM.getCep(),
                cityVM.getNameCity(),
                cityVM.getUf()
        );
    }
}
