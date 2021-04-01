package com.example.robofinance.service;

import com.example.robofinance.ApplicationTest;
import com.example.robofinance.dto.AddressDto;
import com.example.robofinance.dto.CustomerDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

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
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MockMvc mvc;

    @Before
    public void before() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void createCustomer() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .post("/customer/create").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(customerDto))).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
        CustomerDto res = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), CustomerDto.class);
        customerComparison(res);
        cleanBase();
    }

    @Test
    public void updateActualAddress() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/customer/create").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(customerDto)));
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .put("/customer/updateActualAddress").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(actualAddress)).param("id", "1")).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
        AddressDto res = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), CustomerDto.class).getActualAddress();
        assertThat(res.getCountry()).isEqualTo(actualAddress.getCountry());
        assertThat(res.getRegion()).isEqualTo(actualAddress.getRegion());
        assertThat(res.getCity()).isEqualTo(actualAddress.getCity());
        assertThat(res.getStreet()).isEqualTo(actualAddress.getStreet());
        assertThat(res.getHouse()).isEqualTo(actualAddress.getHouse());
        assertThat(res.getFlat()).isEqualTo(actualAddress.getFlat());
        cleanBase();
    }

    @Test
    public void searchCustomer() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/customer/create").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(customerDto)));
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .get("/customer/get/{firstName}/{lastName}", "Anton", "Kondik")).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
        List<CustomerDto> list = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), new TypeReference<List<CustomerDto>>(){});
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
