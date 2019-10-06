package br.ifal;

public class Contato {
    private int id;
    private String nome, fone, endereco;

    public Contato(int id, String nome, String fone, String endereco) {
        this.id = id;
        this.nome = nome;
        this.fone = fone;
        this.endereco = endereco;
    }

    public Contato(String nome, String fone, String endereco) {
        this.nome = nome;
        this.fone = fone;
        this.endereco = endereco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Contato{" +
                "nome='" + nome + '\'' +
                ", fone='" + fone + '\'' +
                ", endereco='" + endereco + '\'' +
                '}';
    }
}
