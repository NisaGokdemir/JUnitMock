package com.gokdemir.unitmockdemo.service;

import com.gokdemir.unitmockdemo.dto.DtoPaymentMethod;
import com.gokdemir.unitmockdemo.dto.DtoPaymentMethodIU;
import com.gokdemir.unitmockdemo.exception.paymentmethod.PaymentMethodDomainException;
import com.gokdemir.unitmockdemo.model.PaymentMethod;
import com.gokdemir.unitmockdemo.mapper.PaymentMethodMapper;
import com.gokdemir.unitmockdemo.repository.PaymentMethodRepository;
import com.gokdemir.unitmockdemo.service.impl.PaymentMethodServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentMethodServiceImplTest {
    @Mock
    private PaymentMethodRepository paymentMethodRepository;

    @Mock
    private PaymentMethodMapper paymentMethodMapper;

    @InjectMocks
    private PaymentMethodServiceImpl paymentMethodService;

    // === SAVE ===

    // Happy Path - State-based test
    @Test
    void savePaymentMethod_whenValidInput_thenReturnsDto() {
        DtoPaymentMethodIU dtoIU = new DtoPaymentMethodIU("Visa", "Credit Card");
        PaymentMethod entity = new PaymentMethod(1L, "Visa", "Credit Card");
        DtoPaymentMethod dto = new DtoPaymentMethod(1L, "Visa", "Credit Card");

        when(paymentMethodMapper.dtoPaymentIUToEntity(dtoIU)).thenReturn(entity);
        when(paymentMethodRepository.save(entity)).thenReturn(entity);
        when(paymentMethodMapper.entityToDtoPayment(entity)).thenReturn(dto);

        DtoPaymentMethod result = paymentMethodService.savePaymentMethod(dtoIU);

        assertThat(result).isEqualTo(dto);
        //savePaymentMethod() metodu çağrıldığında paymentMethodRepository.save() metodunun gerçekten aynı entity ile çağrıldığını doğrular
        verify(paymentMethodRepository).save(entity);
    }

    // Error Case - State-based test
    @Test
    void savePaymentMethod_whenMapperReturnsNull_thenThrowsException() {
        DtoPaymentMethodIU dtoIU = new DtoPaymentMethodIU("Visa", "Credit Card");
        when(paymentMethodMapper.dtoPaymentIUToEntity(dtoIU)).thenReturn(null);

        assertThatThrownBy(() -> paymentMethodService.savePaymentMethod(dtoIU))
                .isInstanceOf(PaymentMethodDomainException.class);
    }


    // === FIND BY ID ===

    // Happy Path - State-based test
    @Test
    void findPaymentMethodById_whenExists_thenReturnsDto() {
        Long id = 1L;
        PaymentMethod entity = new PaymentMethod(id, "Visa", "Credit Card");
        DtoPaymentMethod dto = new DtoPaymentMethod(id, "Visa", "Credit Card");

        when(paymentMethodRepository.findById(id)).thenReturn(Optional.of(entity));
        when(paymentMethodMapper.entityToDtoPayment(entity)).thenReturn(dto);

        DtoPaymentMethod result = paymentMethodService.findPaymentMethodById(id);

        assertThat(result).isEqualTo(dto);
    }

    // Error Case - State-based test
    @Test
    void findPaymentMethodById_whenNotExists_thenThrowsException() {
        Long id = 1L;
        when(paymentMethodRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> paymentMethodService.findPaymentMethodById(id))
                .isInstanceOf(PaymentMethodDomainException.class);
    }

    // === UPDATE ===

    // Error Case - State-based test
    @Test
    void updatePaymentMethod_whenMapperDoesNotUpdateFields_thenThrowsException() {
        Long id = 1L;
        DtoPaymentMethodIU dtoIU = new DtoPaymentMethodIU("Updated", "Debit Card");
        PaymentMethod entity = new PaymentMethod(id, "Visa", "Credit Card");

        when(paymentMethodRepository.findById(id)).thenReturn(Optional.of(entity));

        doAnswer(invocation -> {
            entity.setName("");
            entity.setDescription("");
            return null;
        }).when(paymentMethodMapper).DtoPaymentMethodIUToEntity(dtoIU, entity);

        assertThatThrownBy(() -> paymentMethodService.updatePaymentMethod(id, dtoIU))
                .isInstanceOf(PaymentMethodDomainException.class);
    }

    // Happy Path - Interaction-based test
    @Test
    void updatePaymentMethod_whenValidInput_thenReturnsUpdatedDto() {
        Long id = 1L;
        DtoPaymentMethodIU dtoIU = new DtoPaymentMethodIU("Updated", "Debit Card");
        PaymentMethod entity = new PaymentMethod(id, "Visa", "Credit Card");
        PaymentMethod updatedEntity = new PaymentMethod(id, "Updated", "Debit Card");
        DtoPaymentMethod updatedDto = new DtoPaymentMethod(id, "Updated", "Debit Card");

        when(paymentMethodRepository.findById(id)).thenReturn(Optional.of(entity));
        //side effect: entity'nin güncellenmesi için mapper'ın çağrılması
        //doAnswer kullanarak mapper'ın entity'yi güncellemesini sağlıyoruz
        doAnswer(invocation -> {
            entity.setName("Updated");
            entity.setDescription("Debit Card");
            return null;
        }).when(paymentMethodMapper).DtoPaymentMethodIUToEntity(dtoIU, entity);
        when(paymentMethodRepository.save(entity)).thenReturn(updatedEntity);
        when(paymentMethodMapper.entityToDtoPayment(updatedEntity)).thenReturn(updatedDto);

        DtoPaymentMethod result = paymentMethodService.updatePaymentMethod(id, dtoIU);

        assertThat(result).isEqualTo(updatedDto);
    }

    // Error Case - State-based test
    @Test
    void updatePaymentMethod_whenNotFound_thenThrowsException() {
        Long id = 99L;
        DtoPaymentMethodIU dtoIU = new DtoPaymentMethodIU("Updated", "Debit Card");

        when(paymentMethodRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> paymentMethodService.updatePaymentMethod(id, dtoIU))
                .isInstanceOf(PaymentMethodDomainException.class);
    }


    // === DELETE ===

    // Happy Path - Interaction-based test
    @Test
    void deletePaymentMethod_whenFound_thenDeletesAndReturnsId() {
        Long id = 1L;
        PaymentMethod entity = new PaymentMethod(id, "Visa", "Credit Card");

        when(paymentMethodRepository.findById(id)).thenReturn(Optional.of(entity));

        Long deletedId = paymentMethodService.deletePaymentMethod(id);

        verify(paymentMethodRepository).delete(entity);
        assertThat(deletedId).isEqualTo(id);
    }

    // Error Case - State-based test
    @Test
    void deletePaymentMethod_whenNotFound_thenThrowsException() {
        Long id = 2L;
        when(paymentMethodRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> paymentMethodService.deletePaymentMethod(id))
                .isInstanceOf(PaymentMethodDomainException.class);
    }


    // === GET ALL ===

    @Test
    void getAllPaymentMethods_whenMapperReturnsNull_thenThrowsException() {
        PaymentMethod entity = new PaymentMethod(1L, "Visa", "Credit Card");
        List<PaymentMethod> entityList = List.of(entity);

        when(paymentMethodRepository.findAll()).thenReturn(entityList);
        when(paymentMethodMapper.entitiesToDtoPaymentMethods(entityList)).thenReturn(null);

        assertThatThrownBy(() -> paymentMethodService.getAllPaymentMethods())
                .isInstanceOf(PaymentMethodDomainException.class);
    }

    // Edge Case - State-based test
    @Test
    void getAllPaymentMethods_whenRepositoryReturnsEmpty_thenThrowsException() {
        when(paymentMethodRepository.findAll()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> paymentMethodService.getAllPaymentMethods())
                .isInstanceOf(PaymentMethodDomainException.class);
    }

    // Happy Path - State-based test
    @Test
    void getAllPaymentMethods_whenHasData_thenReturnsList() {
        List<PaymentMethod> entities = List.of(new PaymentMethod(1L, "Visa", "Credit Card"));
        List<DtoPaymentMethod> dtos = List.of(new DtoPaymentMethod(1L, "Visa", "Credit Card"));

        when(paymentMethodRepository.findAll()).thenReturn(entities);
        when(paymentMethodMapper.entitiesToDtoPaymentMethods(entities)).thenReturn(dtos);

        List<DtoPaymentMethod> result = paymentMethodService.getAllPaymentMethods();

        assertThat(result).hasSize(1);
        assertThat(result).isEqualTo(dtos);
    }

    // === PAGEABLE ===

    // Happy Path - State-based test
    @Test
    void getPageableResponse_whenPageRequested_thenReturnsPageOfDtos() {
        Pageable pageable = PageRequest.of(0, 2);
        PaymentMethod entity = new PaymentMethod(1L, "Visa", "Credit Card");
        Page<PaymentMethod> page = new PageImpl<>(List.of(entity));
        DtoPaymentMethod dto = new DtoPaymentMethod(1L, "Visa", "Credit Card");

        when(paymentMethodRepository.findAll(pageable)).thenReturn(page);
        when(paymentMethodMapper.entityToDtoPayment(entity)).thenReturn(dto);

        Page<DtoPaymentMethod> result = paymentMethodService.getPageableResponse(pageable);

        assertThat(result.getContent()).containsExactly(dto);
    }

    // Edge Case - State-based test
    @Test
    void getPageableResponse_whenPageIsEmpty_thenThrowsException() {
        Pageable pageable = PageRequest.of(0, 2);
        Page<PaymentMethod> emptyPage = new PageImpl<>(Collections.emptyList());

        when(paymentMethodRepository.findAll(pageable)).thenReturn(emptyPage);

        assertThatThrownBy(() -> paymentMethodService.getPageableResponse(pageable))
                .isInstanceOf(PaymentMethodDomainException.class);
    }

    //EKSTRA TESTLER
    // Aynı isimle kayıt eklemeye çalışıldığında hata fırlatılması (Duplicate Check)
    // Veritabanı save() işlemi exception fırlatırsa uygun şekilde yakalanıyor mu?

}
