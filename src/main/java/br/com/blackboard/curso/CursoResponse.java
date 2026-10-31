package br.com.blackboard.curso;


import br.com.blackboard.professor.ProfessorResponse;

public class CursoResponse {
    private int id;
    private String nome;
    private int duracaoSemestres;
    private ProfessorResponse coordenador;

    public CursoResponse(Curso curso) {
        this.id = curso.getId();
        this.nome = curso.getNome();
        this.duracaoSemestres = curso.getDuracaoSemestres();
        this.coordenador = new ProfessorResponse(curso.getCoordenador());

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

    public ProfessorResponse getCoordenador() {
        return coordenador;
    }
}
