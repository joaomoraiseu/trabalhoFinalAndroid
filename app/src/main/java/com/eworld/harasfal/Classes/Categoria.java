package com.eworld.harasfal.Classes;

/**
 * Created by evox on 21/12/16.
 */

public class Categoria {



    private String CategoriaId;

    private String Descricao;

    private String UnidadeId;

    private String Nome;

    private String LinkFoto;

    public String getLinkFoto() {
        return LinkFoto;
    }

    public void setLinkFoto(String linkFoto) {
        LinkFoto = linkFoto;
    }

    public String getCategoriaId() {
        return CategoriaId;
    }

    public void setCategoriaId(String categoriaId) {
        CategoriaId = categoriaId;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public String getUnidadeId() {
        return UnidadeId;
    }

    public void setUnidadeId(String unidadeId) {
        UnidadeId = unidadeId;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [CategoriaId = "+CategoriaId+", Descricao = "+Descricao+", UnidadeId = "+UnidadeId+", Nome = "+Nome;
    }
}
