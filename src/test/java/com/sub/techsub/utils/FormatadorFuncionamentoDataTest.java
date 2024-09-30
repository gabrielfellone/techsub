package com.sub.techsub.utils;


import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FormatadorFuncionamentoDataTest {

    @Test
    public void testFormataEValidaDataHoraAgendamento_ValidInput() {
        LocalDateTime dataAgendamento = LocalDateTime.of(2023, 9, 27, 10, 0); // Quarta-feira, 10:00
        String funcionamento = "Segunda Quarta 9:00-19:00";

        boolean result = FormatadorFuncionamentoData.formataEValidaDataHoraAgendamento(funcionamento, dataAgendamento);

        assertThat(result).isTrue();
    }

    @Test
    public void testFormataEValidaDataHoraAgendamento_InvalidDay() {
        LocalDateTime dataAgendamento = LocalDateTime.of(2023, 9, 29, 10, 0); // Sexta-feira, 10:00
        String funcionamento = "Segunda Quarta 9:00-19:00";

        assertThatThrownBy(() ->
                FormatadorFuncionamentoData.formataEValidaDataHoraAgendamento(funcionamento, dataAgendamento)
        ).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("A data do agendamento nao esta dentro dos dias disponiveis da semana");
    }

    @Test
    public void testFormataEValidaDataHoraAgendamento_InvalidTime() {
        LocalDateTime dataAgendamento = LocalDateTime.of(2023, 9, 27, 20, 0); // Quarta-feira, 20:00
        String funcionamento = "Segunda Quarta 9:00-19:00";

        assertThatThrownBy(() ->
                FormatadorFuncionamentoData.formataEValidaDataHoraAgendamento(funcionamento, dataAgendamento)
        ).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("O horario do agendamento nao esta disponivel");
    }

    @Test
    public void testValidaRangeDosDiasDiponiveis() {
        assertThat(FormatadorFuncionamentoData.validaRangeDosDiasDiponiveis(1, 3, 2)).isTrue(); // Segunda a Quarta, Dia 2 (Terça)
        assertThat(FormatadorFuncionamentoData.validaRangeDosDiasDiponiveis(3, 1, 2)).isTrue(); // Quarta a Segunda, Dia 2 (Terça)
        assertThat(FormatadorFuncionamentoData.validaRangeDosDiasDiponiveis(1, 3, 4)).isFalse(); // Segunda a Quarta, Dia 4 (Quinta)
    }

    @Test
    public void testValidaRangeHoraDisponivel() {
        LocalTime horaEntrada = LocalTime.of(9, 0);
        LocalTime horaSaida = LocalTime.of(17, 0);

        // Testes com hora válida
        assertThat(FormatadorFuncionamentoData.validaRangeHoraDisponivel(LocalDateTime.of(2023, 9, 27, 10, 0), horaEntrada, horaSaida)).isTrue();
        assertThat(FormatadorFuncionamentoData.validaRangeHoraDisponivel(LocalDateTime.of(2023, 9, 27, 9, 0), horaEntrada, horaSaida)).isTrue();
        assertThat(FormatadorFuncionamentoData.validaRangeHoraDisponivel(LocalDateTime.of(2023, 9, 27, 17, 0), horaEntrada, horaSaida)).isTrue();

        // Testes com hora inválida
        assertThat(FormatadorFuncionamentoData.validaRangeHoraDisponivel(LocalDateTime.of(2023, 9, 27, 8, 59), horaEntrada, horaSaida)).isFalse();
        assertThat(FormatadorFuncionamentoData.validaRangeHoraDisponivel(LocalDateTime.of(2023, 9, 27, 17, 1), horaEntrada, horaSaida)).isFalse();
    }
}
