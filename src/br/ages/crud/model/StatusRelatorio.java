package br.ages.crud.model;

public enum StatusRelatorio {
    VALIDO,
    INVALIDO,
    REVISAO;

    public String toStringFormal() {
        switch (this) {
            case VALIDO:
                return "Aprovado";
            case INVALIDO:
                return "Recusado";
            case REVISAO:
                return "Em revisão";
            default:
                throw new IllegalArgumentException();
        }
    }
}
