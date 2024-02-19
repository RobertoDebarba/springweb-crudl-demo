package br.com.cedup.trabalho1.controller.pessoa;

public class PessoaDTO {

    public int id;
    public String nome;
    public int idade;

    public PessoaDTO(int id, String nome, int idade) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
    }

    public PessoaDTO() {
    }
}
