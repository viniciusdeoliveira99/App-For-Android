package br.com.navegacao;

public class PessoaAcesso {

    private long id;
    private String telefone;
    private String email;
    private String nome;
    private String idade;
    private String ocupacao;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getOcupacao() {
        return ocupacao;
    }

    public void setOcupacao(String ocupacao) {
        this.ocupacao = ocupacao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public PessoaAcesso(){

    }

    //CONSTRUTOR PESSOA
    public PessoaAcesso(String nome, String idade, String ocupacao) {
        this.nome = nome;
        this.idade = idade;
        this.ocupacao = ocupacao;
    }
}
