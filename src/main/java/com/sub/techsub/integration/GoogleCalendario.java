package com.sub.techsub.integration;

import com.sub.techsub.entity.Agendamento;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

public class GoogleCalendario {

    //toDo Configurar crendenciais do Google para integracao
    /*

    public static void criarAgendamento(Agendamento agendamento) throws Exception {

        Credential credential = authorize();

        Calendar service = new Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName(agendamento.getStatus())
                .build();

        Event event = new Event()
                .setSummary("Notificacao do agendamento")
                .setLocation(agendamento.getEstabelecimento().getEndereco())
                .setDescription(agendamento.getProfissional().getServico().getNome());

        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 3600000);

        EventDateTime start = new EventDateTime()
                .setDateTime(new com.google.api.client.util.DateTime(startDate, TimeZone.getDefault()))
                .setTimeZone("America/Sao_Paulo");
        event.setStart(start);

        EventDateTime end = new EventDateTime()
                .setDateTime(new com.google.api.client.util.DateTime(endDate, TimeZone.getDefault()))
                .setTimeZone("America/Sao_Paulo");
        event.setEnd(end);


        String calendarId = "primary";
        event = service.events().insert(calendarId, event).execute();
        System.out.printf("Evento criado: %s\n", event.getHtmlLink());
    }

    private static Credential authorize() throws IOException {

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new FileReader(CREDENTIALS_FILE_PATH));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(),
                clientSecrets, Collections.singleton(CalendarScopes.CALENDAR)).setAccessType("offline").build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return flow.newAuthorizationCodeFlow().authorize("user");
    }

     */
}
