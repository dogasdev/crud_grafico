package model;

public class Desejo {
    private int id;
    private String nome;
    private Double preco;

    public Desejo(int id, String nome, Double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public int getID() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setID(int id){
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
        return;

    }

    public void setPreco(Double preco) {
        this.preco = preco;
        return;
    }

    @Override
    public String toString() {
        return "#" + id + "Desejo: " + nome + "PreÃ§o: R$" + preco;
    }
}