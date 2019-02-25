package com.invillia.acme;

import com.invillia.acme.config.exceptions.RecordFoundException;
import com.invillia.acme.controller.StoreController;
import com.invillia.acme.core.FunctionalTest;
import com.invillia.acme.domain.model.Store;
import com.invillia.acme.domain.repositories.StoreRepository;
import com.invillia.acme.environments.EnvUf;
import com.invillia.acme.viewer.AddressVM;
import com.invillia.acme.viewer.CityVM;
import com.invillia.acme.viewer.StoreOutVM;
import com.invillia.acme.viewer.StoreVM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@FunctionalTest
class StoreControllerTest {

    private static final String MARINGA = "MARINGA";
    private static final String SALA_01 = "SL 01";

    @Autowired
    private StoreController controller;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private EnvUf envUf;

    @BeforeEach
    void setup() throws RecordFoundException {
        envUf.init();
    }

    @Test
    void shouldCreateStore() {
        StoreOutVM store = controller.createStore(null, getStoreVMOne());

        List<Store> all = storeRepository.findAll();
        assertEquals(1, all.size());
        assertEquals(store.getId(), all.get(0).getId());
    }

    @Test
    void shouldCreateJustOneStore() {
        StoreVM storeVM = getStoreVMTwo();

        StoreOutVM store =  controller.createStore(null, storeVM);

        List<Store> all = storeRepository.findAll();
        assertEquals(1, all.size());
        assertEquals(store.getId(), all.get(0).getId());

        assertThrows(DataIntegrityViolationException.class,
                () -> controller.createStore(null, storeVM),
                ""
        );
    }

    @Test
    void shouldUpdateAStore() {
        StoreOutVM store =  controller.createStore(null, getStoreVMThree());

        store.setName("TEST THREE UPDATED");
        store.setAddress(getAddressVM(
                getCityVM(MARINGA, "PR"),
                "87090990",
                "AV. ENGENHEIRO ALENCAR",
                "990",
                SALA_01,
                "ZONA 02"
        ));

        StoreOutVM storeUpdated =  controller.updateStore(null, store);

        Store storeDB = storeRepository.findAll().get(0);
        assertEquals(store.getId(), storeUpdated.getId());
        assertEquals("AV. ENGENHEIRO ALENCAR", storeDB.getAddress().getStreetName());
    }

    @Test
    void shouldGetAllStores() {
        controller.createStore(null, getStoreVMOne());
        controller.createStore(null, getStoreVMTwo());
        controller.createStore(null, getStoreVMThree());

        List<StoreOutVM> vmList = controller.retrieveStore(null);
        assertEquals(3, vmList.size());
    }

    @Test
    void shouldGetStoreByName() {
        controller.createStore(null, getStoreVMOne());
        controller.createStore(null, getStoreVMTwo());
        controller.createStore(null, getStoreVMThree());

        StoreOutVM testOne = controller.retrieveStoreByName(null, "TEST ONE");
        assertNotNull(testOne);
    }

    @Test
    void shouldNotGetStoreWithInvalidName() {
        controller.createStore(null, getStoreVMOne());
        controller.createStore(null, getStoreVMTwo());
        controller.createStore(null, getStoreVMThree());

        StoreOutVM testOne = controller.retrieveStoreByName(null, "TESTE ONE");
        assertNull(testOne);
    }

    @Test
    void shouldGetStoreByAddress() {
        controller.createStore(null, getStoreVMOne());
        controller.createStore(null, getStoreVMTwo());
        controller.createStore(null, getStoreVMThree());

        StoreOutVM testOne = controller.retrieveStoreByAddress(null, "AV. GUEDNER", "891", "AP 901", "87050390");
        assertNotNull(testOne);
    }

    private AddressVM getAddressVM(CityVM cityVM, String cep, String streetName, String numberHome, String addressAddOn, String neighborhood) {
        AddressVM addressVM = new AddressVM();
        addressVM.setCep(cep);
        addressVM.setStreetName(streetName);
        addressVM.setNumberHome(numberHome);
        addressVM.setAddressAddOn(addressAddOn);
        addressVM.setNeighborhood(neighborhood);
        addressVM.setCity(cityVM);
        return addressVM;
    }

    private CityVM getCityVM(String city, String uf) {
        CityVM cityVM = new CityVM();
        cityVM.setNameCity(city);
        cityVM.setUf(uf);
        return cityVM;
    }

    private StoreVM getStoreVMOne(String name, AddressVM address) {
        StoreVM storeVM = new StoreVM();
        storeVM.setName(name);
        storeVM.setAddress(address);
        return storeVM;
    }

    private StoreVM getStoreVMOne() {
        return getStoreVMOne("TEST ONE", getAddressVM(
                getCityVM(MARINGA, "PR"),
                "87050390",
                "AV. GUEDNER",
                "891",
                "AP 901",
                "ZONA 08"
        ));
    }

    private StoreVM getStoreVMTwo() {
        StoreVM storeVM = new StoreVM();
        storeVM.setName("TEST TWO");
        storeVM.setAddress(getAddressVM(
                getCityVM(MARINGA, "PR"),
                "87080590",
                "AV. ALZIRO ZARUR",
                "73",
                SALA_01,
                "JD UNIVERSITARIO"
        ));
        return storeVM;
    }

    private StoreVM getStoreVMThree() {
        StoreVM storeVM = new StoreVM();
        storeVM.setName("TEST THREE");
        storeVM.setAddress(getAddressVM(
                getCityVM(MARINGA, "PR"),
                "87080590",
                "AV. ALZIRO ZARUR",
                "73",
                SALA_01,
                "JD UNIVERSITARIO"
        ));
        return storeVM;
    }
}
