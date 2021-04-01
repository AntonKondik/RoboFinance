package com.example.robofinance.service;

import com.example.robofinance.ApplicationTest;
import com.example.robofinance.dto.AddressDto;
import com.example.robofinance.dto.CustomerDto;
import com.example.robofinance.ws.CustomerController;
import javassist.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = { ApplicationTest.class })
@RunWith(SpringRunner.class)
public class CustomerServiceTest {

    private final AddressDto addressDto = AddressDto.builder()
            .country("Russia")
            .region("NSO")
            .city("Novosibirsk")
            .street("Krasniy prospect")
            .house("100")
            .flat("50")
            .build();

    private final AddressDto actualAddress = AddressDto.builder()
            .country("Germany")
            .region("FFF")
            .city("Berlin")
            .street("Bogatkova")
            .house("100")
            .flat("60")
            .build();

    private final CustomerDto customerDto = CustomerDto.builder()
            .registredAddress(addressDto)
            .actualAddress(addressDto)
            .firstName("Anton")
            .lastName("Kondik")
            .middleName("Alekseevich")
            .sex("male")
            .build();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CustomerController customerController;

    @Test
    public void createCustomer(){
        ResponseEntity<CustomerDto> responseEntity = customerController.createCustomer(customerDto);
        CustomerDto res = responseEntity.getBody();
        customerComparison(res);
        cleanBase();
    }

    @Test
    public void updateActualAddress() throws NotFoundException {
        customerController.createCustomer(customerDto);
        ResponseEntity<CustomerDto> responseEntity = customerController.updateActualAddress(actualAddress, 1L);
        AddressDto res = responseEntity.getBody().getActualAddress();
        assertThat(res.getCountry()).isEqualTo(actualAddress.getCountry());
        assertThat(res.getRegion()).isEqualTo(actualAddress.getRegion());
        assertThat(res.getCity()).isEqualTo(actualAddress.getCity());
        assertThat(res.getStreet()).isEqualTo(actualAddress.getStreet());
        assertThat(res.getHouse()).isEqualTo(actualAddress.getHouse());
        assertThat(res.getFlat()).isEqualTo(actualAddress.getFlat());
        cleanBase();
    }

    @Test
    public void searchCustomer(){
        customerController.createCustomer(customerDto);
        ResponseEntity<List<CustomerDto>> responseEntity = customerController.searchCustomer(customerDto.getFirstName(), customerDto.getLastName());
        List<CustomerDto> list = responseEntity.getBody();
        customerComparison(list.get(0));
        cleanBase();
    }

    private void customerComparison(CustomerDto res){
        assertThat(res.getId()).isEqualTo(1L);
        assertThat(res.getFirstName()).isEqualTo(customerDto.getFirstName());
        assertThat(res.getLastName()).isEqualTo(customerDto.getLastName());
        assertThat(res.getMiddleName()).isEqualTo(customerDto.getMiddleName());
        assertThat(res.getSex()).isEqualTo(customerDto.getSex());
        AddressDto regAddress = res.getRegistredAddress();
        assertThat(regAddress.getCountry()).isEqualTo(addressDto.getCountry());
        assertThat(regAddress.getRegion()).isEqualTo(addressDto.getRegion());
        assertThat(regAddress.getStreet()).isEqualTo(addressDto.getStreet());
        assertThat(regAddress.getHouse()).isEqualTo(addressDto.getHouse());
        assertThat(regAddress.getFlat()).isEqualTo(addressDto.getFlat());
        AddressDto actualAddress = res.getActualAddress();
        assertThat(actualAddress.getCountry()).isEqualTo(addressDto.getCountry());
        assertThat(actualAddress.getRegion()).isEqualTo(addressDto.getRegion());
        assertThat(actualAddress.getStreet()).isEqualTo(addressDto.getStreet());
        assertThat(actualAddress.getHouse()).isEqualTo(addressDto.getHouse());
        assertThat(actualAddress.getFlat()).isEqualTo(addressDto.getFlat());
    }

    private void cleanBase(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "customer", "address");
        jdbcTemplate.execute("ALTER SEQUENCE address_id_seq RESTART WITH 1;");
        jdbcTemplate.execute("ALTER SEQUENCE customer_id_seq RESTART WITH 1;");
    }
}
