package com.sub.techsub.utils;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class FormatadorFuncionamentoData {

    private static final Map<String, DayOfWeek> diasDaSemanaMap = new HashMap<>();

    static {
        diasDaSemanaMap.put("Segunda", DayOfWeek.MONDAY);
        diasDaSemanaMap.put("Terça", DayOfWeek.TUESDAY);
        diasDaSemanaMap.put("Quarta", DayOfWeek.WEDNESDAY);
        diasDaSemanaMap.put("Quinta", DayOfWeek.THURSDAY);
        diasDaSemanaMap.put("Sexta", DayOfWeek.FRIDAY);
        diasDaSemanaMap.put("Sábado", DayOfWeek.SATURDAY);
        diasDaSemanaMap.put("Domingo", DayOfWeek.SUNDAY);
    }

    public static boolean formataEValidaDataHoraAgendamento(String dataFuncionamento, LocalDateTime dataAgendamento) {

        String[] diasDaSemana = dataFuncionamento.split(" ");

        if (diasDaSemana.length != 3) {
            throw new IllegalArgumentException("Formato de dia inválido do estabelecimento ou profissional. " +
                    "Use 'Dia Dia Horário' exemplo: Segunda Sexta 9:00-19:00");
        }

        String[] horas = diasDaSemana[2].split("-");

        if (horas.length != 2) {
            throw new IllegalArgumentException("Formato de horário inválido do estabelecimento ou profissional. " +
                    "Use 'HH:MM-HH:MM' as horas de entrada e saida com o -");
        }

        DateTimeFormatter formaterHora = DateTimeFormatter.ofPattern("H:mm");

        LocalTime horaEntrada = LocalTime.parse(horas[0], formaterHora);
        LocalTime horaSaida = LocalTime.parse(horas[1] , formaterHora);

        DayOfWeek diaInicio = diasDaSemanaMap.get(diasDaSemana[0]);
        DayOfWeek diaFim = diasDaSemanaMap.get(diasDaSemana[1]);

        DayOfWeek diaSemanaAgendamento = dataAgendamento.getDayOfWeek();

        boolean validaDiaSemana =
                validaRangeDosDiasDiponiveis(diaInicio.getValue(),diaFim.getValue(),diaSemanaAgendamento.getValue());

        if(validaDiaSemana){
            if(validaRangeHoraDisponivel(dataAgendamento, horaEntrada, horaSaida)){
                return true;
            } else throw new RuntimeException("O horario do agendamento nao esta disponivel");
        } else throw new RuntimeException("A data do agendamento nao esta dentro dos dias disponiveis da semana");
    }

    public static boolean validaRangeDosDiasDiponiveis(int diaInicio, int diaFim, int diaAgendado) {
        int diaLimiteMinimo = Math.min(diaInicio, diaFim);
        int diaLimiteMaximo = Math.max(diaInicio, diaFim);
        return diaAgendado >= diaLimiteMinimo && diaAgendado <= diaLimiteMaximo;
    }

    public static boolean validaRangeHoraDisponivel(LocalDateTime horaAgendado, LocalTime horaEntrada, LocalTime horaSaida) {
        LocalTime validarHora = horaAgendado.toLocalTime();
        if (horaEntrada.isAfter(horaSaida)) {
            return !validarHora.isBefore(horaEntrada) || !validarHora.isAfter(horaSaida);
        } else {
            return !validarHora.isBefore(horaEntrada) && !validarHora.isAfter(horaSaida);
        }
    }
}
