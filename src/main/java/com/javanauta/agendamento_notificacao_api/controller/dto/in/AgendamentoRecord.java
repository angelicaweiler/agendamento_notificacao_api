package com.javanauta.agendamento_notificacao_api.controller.dto.in;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record AgendamentoRecord(String emailDestinatario,
                                String telefoneDestinatario,
                                String mensagem,
                                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
                                LocalDateTime dataHoraEnvio) {
}
