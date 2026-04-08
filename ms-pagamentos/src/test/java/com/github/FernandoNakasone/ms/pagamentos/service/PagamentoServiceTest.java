package com.github.FernandoNakasone.ms.pagamentos.service;

import com.github.FernandoNakasone.ms.pagamentos.exceptions.ResourceNotFoundException;
import com.github.FernandoNakasone.ms.pagamentos.repositories.PagamentoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @InjectMocks
    private PagamentoService pagamentoService;

    private Long existngId;
    private Long nonExistingId;

    @BeforeEach
    void setUp(){
        existngId = 1L;
        nonExistingId = Long.MAX_VALUE;
    }

    @Test
    void deletePagamenoByIdShouldDeleteWhenIdExists(){
        //Arrange - prepara o comportamento do mock
        Mockito.when(pagamentoRepository.existsById(existngId)).thenReturn(true);

        pagamentoService.deletePagamentoById(existngId);

        //Verifica que o mock pagamentoRepository recebeu uma chamada ao medoto existsById
        Mockito.verify(pagamentoRepository).existsById(existngId);

        //Verifica se o metodo deleteById do repository foi chamado exatamente 1 vez (padrão)
        Mockito.verify(pagamentoRepository, Mockito.times(1)).deleteById(existngId);
    }

    @Test
    @DisplayName("deletePagamentoById deveria lançar ResourceNotFoundException quando o Id não existir")
    void deletePagamentoByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){

        //Arrange
        Mockito.when(pagamentoRepository.existsById(nonExistingId)).thenReturn(false);

        //Act + Assert
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> {
                    pagamentoService.deletePagamentoById(nonExistingId);
                });

        Mockito.verify(pagamentoRepository).existsById(nonExistingId);

        Mockito.verify(pagamentoRepository, Mockito.never()).deleteById(Mockito.anyLong());
    }
}