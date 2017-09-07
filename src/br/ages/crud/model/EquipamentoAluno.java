package br.ages.crud.model;

import java.util.Date;

public class EquipamentoAluno {

    private int id;
    private Equipamento equipamento;
    private Usuario aluno;
    private String observacao;
    private Date data_retirada;
    private Date data_entrega;

    public EquipamentoAluno() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public Usuario getAluno() {
        return aluno;
    }

    public void setAluno(Usuario aluno) {
        this.aluno = aluno;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Date getDataRetirada() {
        return data_retirada;
    }

    public void setDataRetirada(Date data_retirada) {
        this.data_retirada = data_retirada;
    }

    public Date getDataEntrega() {
        return data_entrega;
    }

    public void setDataEntrega(Date data_entrega) {
        this.data_entrega = data_entrega;
    }

    @Override
    public String toString() {
        return "EquipamentoAluno{" +
                "id=" + id +
                ", equipamento=" + equipamento +
                ", aluno=" + aluno +
                ", observacao='" + observacao + '\'' +
                ", data_retirada=" + data_retirada +
                ", data_entrega=" + data_entrega +
                '}';
    }
}
