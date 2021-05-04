package otus.amogilevskiy.ecom.inventory.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import otus.amogilevskiy.ecom.inventory.TestData;
import otus.amogilevskiy.ecom.inventory.repository.ProductItemRepository;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductItemController.class)
public class ProductItemControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductItemRepository productItemRepository;

    @Test
    void shouldReturnAllItems() throws Exception {
        var items = TestData.allProductItems();

        when(productItemRepository.findAll()).thenReturn(items);

        mvc.perform(get("/api/1.0/product-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.*", hasSize(2)));
    }

    @Test
    void shouldReturnAuthorById() throws Exception {
        var item = TestData.firstProductItem();

        when(productItemRepository.findById(item.getId())).thenReturn(Optional.of(item));

        mvc.perform(get("/api/1.0/product-items/" + item.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(item.getId()), Long.class));
    }

}
