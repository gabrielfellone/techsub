package com.sub.techsub.entity;

import com.sub.techsub.controller.resources.requests.ProfissionalRequest;
import jakarta.persistence.*;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@Table(name = "profissional")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profissional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(name = "horarios_disponiveis", length = 255)
    private String horariosDisponiveis;

    private Double tarifas;

    @ManyToOne
    @JoinColumn(name = "avaliacao_id")
    private Avaliacao avaliacao;

    @ManyToOne
    @JoinColumn(name = "servico_id")
    private Servico servico;

    public Profissional(ProfissionalRequest profissionalRequest) {
        nome = profissionalRequest.getNome();
        horariosDisponiveis = profissionalRequest.getHorariosDisponiveis();
        tarifas = profissionalRequest.getTarifas();
    }
}