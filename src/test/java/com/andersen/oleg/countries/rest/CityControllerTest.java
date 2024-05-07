package com.andersen.oleg.countries.rest;

import com.andersen.oleg.countries.entity.City;
import com.andersen.oleg.countries.entity.Country;
import com.andersen.oleg.countries.service.CityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CityController.class)
public class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityService cityService;

    @Test
    @WithMockUser
    void whenAuthorizedThenStatusOk() throws Exception {
        Page<City> page = new PageImpl<>(getCityList());

        when(cityService.getAllCities(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/cities")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void whenPageIsEmptyThenNoContent() throws Exception {
        Page<City> page = Page.empty();

        when(cityService.getAllCities(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/cities")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenGetUniqueCitiesWithoutAuthorizationThenUnauthorized() throws Exception {
        Page<City> page = Page.empty();

        when(cityService.getAllCities(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/cities/list")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void whenGetUniqueCitiesThenOk() throws Exception {
        when(cityService.getUniqueCityNames()).thenReturn(Set.of());

        mockMvc.perform(get("/cities/list"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void whenCitiesFoundByCountryThenOk() throws Exception {
        List<City> cities = getCityList();

        when(cityService.getAllByCountryName("Germany")).thenReturn(cities);

        mockMvc.perform(get("/cities/filter")
                        .param("countryName", "Germany"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void whenCitiesNotFoundByCountryThenNotFound() throws Exception {
        when(cityService.getAllByCountryName("France")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/cities/filter")
                        .param("countryName", "France"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void whenCitiesFoundByCityNameThenOk() throws Exception {
        List<City> cities = getCityList();

        when(cityService.searchCityByName("bo")).thenReturn(cities);

        mockMvc.perform(get("/cities/search")
                        .param("cityName", "bo"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "user")
    void whenCitiesNotFoundByCityNameThenOk() throws Exception {
        when(cityService.searchCityByName("bo")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/cities/search")
                        .param("cityName", "bo"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(value = "user", authorities = {"ROLE_USER"})
    public void whenRoleNotEditorThenForbidden() throws Exception {
        Long cityId = 1L;
        String cityName = "New City";
        MockMultipartFile file = new MockMultipartFile(
                "logo", "logo.png", "image/png", new byte[]{1, 2, 3, 4});

        when(cityService.updateCity(eq(cityId), eq(cityName), any(MultipartFile.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/cities/edit/1")
                        .file(file)
                        .param("city", cityName)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        }))
                .andExpect(status().isForbidden());

        verify(cityService, times(0)).updateCity(eq(cityId), eq(cityName), any(MultipartFile.class));
    }

    private List<City> getCityList() {
        return List.of(
                new City(1L, "Bonn", null, new Country("Germany")),
                new City(2L, "Bochum", null, new Country("Germany")));
    }
}
