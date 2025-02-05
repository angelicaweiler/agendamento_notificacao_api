package com.javanauta.agendamento_notificacao_api.business;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgendamentoServiceTest {

    @InjectMocks
    private AgendamentoService agendamentoService;

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private IAgendamentoMapper agendamentoMapper;

    private AgendamentoRecord agendamentoRecord;
    private AgendamentoRecordOut agendamentoRecordOut;
    private Agendamento agendamentoEntity;

    @BeforeEach
    void setUp() {

        agendamentoEntity = new Agendamento(1L, "email@email.com", "55887996578",
                LocalDateTime.of(2025, 1, 2, 11, 1, 1),
                LocalDateTime.now(),null,
                "Favor retornar a loja com urgência",
                StatusNotificacaoEnum.AGENDADO);

        agendamentoRecord = new AgendamentoRecord("email@email.com", "55887996578",
                "Favor retornar a loja com urgência", LocalDateTime.of(2025, 1, 2, 11, 1, 1));

        agendamentoRecordOut = new AgendamentoRecordOut(1L, "email@email.com", "55887996578",
                "Favor retornar a loja com urgência", LocalDateTime.of(2025, 1, 2, 11, 1, 1),
                StatusNotificacaoEnum.AGENDADO);
    }

    @Test
    void deveGravarAgendamentoComSucesso(){
        when(agendamentoMapper.paraEntity(agendamentoRecord)).thenReturn(agendamentoEntity);
        when(agendamentoRepository.save(agendamentoEntity)).thenReturn(agendamentoEntity);
        when(agendamentoMapper.paraOut(agendamentoEntity)).thenReturn(agendamentoRecordOut);

        AgendamentoRecordOut out = agendamentoService.gravarAgendamento(agendamentoRecord);

        verify(agendamentoMapper, times(1)).paraEntity(agendamentoRecord);
        verify(agendamentoRepository, times(1)).save(agendamentoEntity);
        verify(agendamentoMapper, times(1)).paraOut(agendamentoEntity);
        assertThat(out).usingRecursiveComparison().isEqualTo(agendamentoRecordOut);
    }
}
