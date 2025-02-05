package com.javanauta.agendamento_notificacao_api.controller;

import com.javanauta.agendamento_notificacao_api.business.AgendamentoService;
import com.javanauta.agendamento_notificacao_api.business.mapper.IAgendamentoMapper;
import com.javanauta.agendamento_notificacao_api.controller.dto.in.AgendamentoRecord;
import com.javanauta.agendamento_notificacao_api.controller.dto.out.AgendamentoRecordOut;
import com.javanauta.agendamento_notificacao_api.infrastructure.entities.Agendamento;
import com.javanauta.agendamento_notificacao_api.infrastructure.enums.StatusNotificacaoEnum;
import com.javanauta.agendamento_notificacao_api.infrastructure.repositories.AgendamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository repository;

    @Mock
    private IAgendamentoMapper agendamentoMapper;

    @InjectMocks
    private AgendamentoService agendamentoService;

    private AgendamentoRecord agendamentoRecord;
    private AgendamentoRecordOut agendamentoRecordOut;
    private Agendamento agendamentoEntity;

    @BeforeEach
    void setUp() {
        // Dados de entrada
        agendamentoRecord = new AgendamentoRecord(
                "email@email.com",
                "55887996578",
                "Favor retornar a loja com urgência",
                LocalDateTime.of(2025, 1, 2, 11, 1, 1)
        );

        // Crie uma entidade de exemplo. Caso utilize record ou classe, adapte conforme necessário.
        agendamentoEntity = Agendamento.builder()
                .id(1L)
                .dataHoraEnvio( LocalDateTime.of(2025, 1, 2, 11, 1, 1))
                .emailDestinatario("email@email.com")
                .telefoneDestinatario("55887996578")
                .mensagem("Favor retornar a loja com urgência")
                .dataHoraAgendamento(LocalDateTime.now())
                .build();

        // Resultado esperado
        agendamentoRecordOut = new AgendamentoRecordOut(
                1L,
                "email@email.com",
                "55887996578",
                "Favor retornar a loja com urgência",
                LocalDateTime.of(2025, 1, 2, 11, 1, 1),
                StatusNotificacaoEnum.AGENDADO
        );
    }

    @Test
    void deveGravarAgendamento() {
        // Define o comportamento do mapper: mapeia o DTO para a entidade
        when(agendamentoMapper.paraEntity(agendamentoRecord)).thenReturn(agendamentoEntity);
        // Simula a operação de salvar a entidade no repositório
        when(repository.save(agendamentoEntity)).thenReturn(agendamentoEntity);
        // Mapeia a entidade salva para o DTO de saída
        when(agendamentoMapper.paraOut(agendamentoEntity)).thenReturn(agendamentoRecordOut);

        // Chama o método a ser testado
        AgendamentoRecordOut resultado = agendamentoService.gravarAgendamento(agendamentoRecord);

        // Verifica se o resultado é o esperado
        assertEquals(agendamentoRecordOut, resultado, "O objeto retornado deve ser igual ao esperado");
    }
}