package br.com.blackboard.curso;


import br.com.blackboard.disciplina.DisciplinaResponse;
import br.com.blackboard.professor.ProfessorResponse;

import java.util.Comparator;
import java.util.List;

public class CursoResponse {
    private int id;
    private String nome;
    private int duracaoSemestres;
    private ProfessorResponse coordenador;
    private List<DisciplinaResponse> disciplinas;

    public CursoResponse(Curso curso) {
        this.id = curso.getId();
        this.nome = curso.getNome();
        this.duracaoSemestres = curso.getDuracaoSemestres();
        this.coordenador = new ProfessorResponse(curso.getCoordenador());
        this.disciplinas = curso.getDisciplinas().stream()
                .sorted(Comparator.comparingInt(disciplina -> disciplina.getId()))
                .map(DisciplinaResponse::new)
                .toList();

    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getDuracaoSemestres() {
        return duracaoSemestres;
    }

    public List<DisciplinaResponse> getDisciplinas() {
        return disciplinas;
    }

    public ProfessorResponse getCoordenador() {
        return coordenador;
    }
}
