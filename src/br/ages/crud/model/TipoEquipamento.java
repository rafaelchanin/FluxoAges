package br.ages.crud.model;

public class TipoEquipamento {
    private int id;
   private String nome;

    public TipoEquipamento(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public TipoEquipamento() {
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

    @Override
    public String toString() {
        return "TipoEquipamento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }

}