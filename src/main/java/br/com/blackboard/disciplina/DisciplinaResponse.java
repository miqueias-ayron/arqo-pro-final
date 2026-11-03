package br.com.blackboard.disciplina;

import br.com.blackboard.professor.ProfessorResponse;

public class DisciplinaResponse {

    private int id;
    private String codigo;
    private String nome;
    private int cargaHoraria;
    private ProfessorResponse professor;

    public DisciplinaResponse(Disciplina disciplina) {
        this.id = disciplina.getId();
        this.codigo = disciplina.getCodigo();
        this.nome = disciplina.getNome();
        this.cargaHoraria = disciplina.getCargaHoraria();
        this.professor = new ProfessorResponse(disciplina.getProfessor());
    }

    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public ProfessorResponse getProfessor() {
        return professor;
    }
}


