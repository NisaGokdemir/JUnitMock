package com.gokdemir.unitmockdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gokdemir.unitmockdemo.controller.impl.RestPaymentMethodControllerImpl;
import com.gokdemir.unitmockdemo.dto.DtoPaymentMethod;
import com.gokdemir.unitmockdemo.dto.DtoPaymentMethodIU;
import com.gokdemir.unitmockdemo.exception.handler.GlobalExceptionHandler;
import com.gokdemir.unitmockdemo.exception.paymentmethod.PaymentMethodDomainException;
import com.gokdemir.unitmockdemo.service.IPaymentMethodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("RestPaymentMethodControllerImpl Unit Tests")
class RestPaymentMethodControllerImplTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private IPaymentMethodService paymentMethodService;

    @InjectMocks
    private RestPaymentMethodControllerImpl controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler()) // Exception handling testleri için
                .build();
    }

    @Nested
    @DisplayName("Save Payment Method Tests")
    class SavePaymentMethodTests {

        @Test
        @DisplayName("Given valid payment method request, when save, then return saved DTO")
        void givenValidRequest_whenSave_thenReturnSavedDto() throws Exception {
            // Given
            DtoPaymentMethodIU input = new DtoPaymentMethodIU("Credit Card");
            DtoPaymentMethod output = new DtoPaymentMethod(1L, "Credit Card");

            // When
            when(paymentMethodService.savePaymentMethod(any())).thenReturn(output);

            // Then
            mockMvc.perform(post("/api/payment-method/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(input)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.name").value("Credit Card"));

            verify(paymentMethodService).savePaymentMethod(any());
        }

        @Test
        @DisplayName("Given empty name, when save, then return bad request")
        void givenEmptyName_whenSave_thenReturnBadRequest() throws Exception {
            // Given
            DtoPaymentMethodIU input = new DtoPaymentMethodIU("");

            // When & Then
            mockMvc.perform(post("/api/payment-method/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(input)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errors[0]").value("Name cannot be blank"));
        }

        @Test
        @DisplayName("Given null name, when save, then return bad request")
        void givenNullName_whenSave_thenReturnBadRequest() throws Exception {
            // Given
            DtoPaymentMethodIU input = new DtoPaymentMethodIU(null);

            // When & Then
            mockMvc.perform(post("/api/payment-method/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(input)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Given long name (over 50 chars), when save, then return bad request")
        void givenLongName_whenSave_thenReturnBadRequest() throws Exception {
            // Given
            String longName = "A".repeat(51);
            DtoPaymentMethodIU input = new DtoPaymentMethodIU(longName);

            // When & Then
            mockMvc.perform(post("/api/payment-method/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(input)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("Update Payment Method Tests")
    class UpdatePaymentMethodTests {

        @Test
        @DisplayName("Given valid update request, when update, then return updated DTO")
        void givenValidRequest_whenUpdate_thenReturnUpdatedDto() throws Exception {
            // Given
            DtoPaymentMethodIU input = new DtoPaymentMethodIU("Updated Card");
            DtoPaymentMethod output = new DtoPaymentMethod(1L, "Updated Card");

            // When
            when(paymentMethodService.updatePaymentMethod(eq(1L), any())).thenReturn(output);

            // Then
            mockMvc.perform(put("/api/payment-method/update/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(input)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.name").value("Updated Card"));

            verify(paymentMethodService).updatePaymentMethod(eq(1L), any());
        }

        @Test
        @DisplayName("Given non-existent ID, when update, then return not found")
        void givenNonExistentId_whenUpdate_thenReturnNotFound() throws Exception {
            // Given
            DtoPaymentMethodIU input = new DtoPaymentMethodIU("Updated");

            // When
            when(paymentMethodService.updatePaymentMethod(eq(99L), any()))
                    .thenThrow(new PaymentMethodDomainException("Payment method not found"));

            // Then
            mockMvc.perform(put("/api/payment-method/update/99")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(input)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").value("Payment method not found"));
        }
    }

    @Nested
    @DisplayName("Delete Payment Method Tests")
    class DeletePaymentMethodTests {

        @Test
        @DisplayName("Given valid ID, when delete, then return deleted ID")
        void givenValidId_whenDelete_thenReturnId() throws Exception {
            // Given
            Long id = 1L;

            // When
            when(paymentMethodService.deletePaymentMethod(id)).thenReturn(id);

            // Then
            mockMvc.perform(delete("/api/payment-method/delete/{id}", id))
                    .andExpect(status().isOk())
                    .andExpect(content().string(id.toString()));

            verify(paymentMethodService).deletePaymentMethod(id);
        }

        @Test
        @DisplayName("Given non-existent ID, when delete, then return not found")
        void givenNonExistentId_whenDelete_thenReturnNotFound() throws Exception {
            // Given
            Long id = 99L;

            // When
            when(paymentMethodService.deletePaymentMethod(id))
                    .thenThrow(new PaymentMethodDomainException("Payment method not found"));

            // Then
            mockMvc.perform(delete("/api/payment-method/delete/{id}", id))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").value("Payment method not found"));
        }

        @Test
        @DisplayName("When service throws unexpected exception, then return internal server error")
        void whenServiceThrowsUnexpectedException_thenReturnInternalServerError() throws Exception {
            // Given
            Long id = 99L;

            // When
            doThrow(new RuntimeException("Database connection failed"))
                    .when(paymentMethodService).deletePaymentMethod(id);

            // Then
            mockMvc.perform(delete("/api/payment-method/delete/{id}", id))
                    .andExpect(status().isInternalServerError())
                    .andExpect(jsonPath("$.message").value("Database connection failed"));
        }
    }

    @Nested
    @DisplayName("Find Payment Method Tests")
    class FindPaymentMethodTests {

        @Test
        @DisplayName("Given existing ID, when find by ID, then return payment method")
        void givenExistingId_whenFindById_thenReturnPaymentMethod() throws Exception {
            // Given
            DtoPaymentMethod dto = new DtoPaymentMethod(1L, "Credit Card");

            // When
            when(paymentMethodService.findPaymentMethodById(1L)).thenReturn(dto);

            // Then
            mockMvc.perform(get("/api/payment-method/list/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1L))
                    .andExpect(jsonPath("$.name").value("Credit Card"));
        }

        @Test
        @DisplayName("Given non-existent ID, when find by ID, then return not found")
        void givenNonExistentId_whenFindById_thenReturnNotFound() throws Exception {
            // Given
            Long id = 99L;

            // When
            when(paymentMethodService.findPaymentMethodById(id))
                    .thenThrow(new PaymentMethodDomainException("Payment method not found"));

            // Then
            mockMvc.perform(get("/api/payment-method/list/{id}", id))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message").value("Payment method not found"));
        }
    }

    @Nested
    @DisplayName("Get All Payment Methods Tests")
    class GetAllPaymentMethodsTests {

        @Test
        @DisplayName("When payment methods exist, then return list")
        void whenPaymentMethodsExist_thenReturnList() throws Exception {
            // Given
            List<DtoPaymentMethod> list = List.of(
                    new DtoPaymentMethod(1L, "Credit Card"),
                    new DtoPaymentMethod(2L, "PayPal")
            );

            // When
            when(paymentMethodService.getAllPaymentMethods()).thenReturn(list);

            // Then
            mockMvc.perform(get("/api/payment-method/list-all"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].id").value(1L))
                    .andExpect(jsonPath("$[1].id").value(2L));
        }

        @Test
        @DisplayName("When no payment methods exist, then return empty list")
        void whenNoPaymentMethodsExist_thenReturnEmptyList() throws Exception {
            // Given
            List<DtoPaymentMethod> emptyList = Collections.emptyList();

            // When
            when(paymentMethodService.getAllPaymentMethods()).thenReturn(emptyList);

            // Then
            mockMvc.perform(get("/api/payment-method/list-all"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", empty()));
        }
    }

    @Nested
    @DisplayName("Get Pageable Payment Methods Tests")
    class GetPageablePaymentMethodsTests {

        @Test
        @DisplayName("Given pageable request, when get page, then return paged result")
        void givenPageableRequest_whenGetPage_thenReturnPagedResult() throws Exception {
            // Given
            Pageable pageable = PageRequest.of(0, 2);
            List<DtoPaymentMethod> content = List.of(
                    new DtoPaymentMethod(1L, "Credit Card"),
                    new DtoPaymentMethod(2L, "PayPal")
            );
            PageImpl<DtoPaymentMethod> page = new PageImpl<>(content, pageable, 10);

            // When
            when(paymentMethodService.getPageableResponse(any())).thenReturn(page);

            // Then
            mockMvc.perform(get("/api/payment-method/list-pageable")
                            .param("page", "0")
                            .param("size", "2"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content", hasSize(2)))
                    .andExpect(jsonPath("$.totalElements").value(10))
                    .andExpect(jsonPath("$.content[0].name").value("Credit Card"));
        }

        @Test
        @DisplayName("When no results, then return empty page")
        void whenNoResults_thenReturnEmptyPage() throws Exception {
            // Given
            Pageable pageable = PageRequest.of(0, 2);
            PageImpl<DtoPaymentMethod> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

            // When
            when(paymentMethodService.getPageableResponse(any())).thenReturn(emptyPage);

            // Then
            mockMvc.perform(get("/api/payment-method/list-pageable")
                            .param("page", "0")
                            .param("size", "2"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content", empty()));
        }
    }
}