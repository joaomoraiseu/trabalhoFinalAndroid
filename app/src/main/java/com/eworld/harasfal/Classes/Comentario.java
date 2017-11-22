package com.eworld.harasfal.Classes;

/**
 * Created by evox on 11/11/16.
 */
public class Comentario
{
    private String DataComentario;

    private String ComentarioId;

    private String OrigemId;
    private String NomePessoa;

    private String QtdeDescurtidas;

    private String Texto;

    private boolean ComentarioProprio;

    private boolean hasFoto;

    private String LinkFoto;

    private String QtdeCurtidas;

    public String getOrigemId() {
        return OrigemId;
    }

    public void setOrigemId(String origemId) {
        OrigemId = origemId;
    }

    public String getDataComentario ()
    {
        return DataComentario;
    }

    public void setDataComentario (String DataComentario)
    {
        this.DataComentario = DataComentario;
    }

    public String getComentarioId ()
    {
        return ComentarioId;
    }

    public void setComentarioId (String ComentarioId)
    {
        this.ComentarioId = ComentarioId;
    }

    public String getNomePessoa ()
    {
        return NomePessoa;
    }

    public void setNomePessoa (String NomePessoa)
    {
        this.NomePessoa = NomePessoa;
    }

    public String getQtdeDescurtidas ()
    {
        return QtdeDescurtidas;
    }

    public void setQtdeDescurtidas (String QtdeDescurtidas)
    {
        this.QtdeDescurtidas = QtdeDescurtidas;
    }

    public String getTexto ()
    {
        return Texto;
    }

    public void setTexto (String Texto)
    {
        this.Texto = Texto;
    }


    public boolean isComentarioProprio() {
        return ComentarioProprio;
    }

    public void setComentarioProprio(boolean comentarioProprio) {
        ComentarioProprio = comentarioProprio;
    }

    public boolean isHasFoto() {
        return hasFoto;
    }

    public void setHasFoto(boolean hasFoto) {
        this.hasFoto = hasFoto;
    }

    public String getLinkFoto ()
    {
        return LinkFoto;
    }

    public void setLinkFoto (String LinkFoto)
    {
        this.LinkFoto = LinkFoto;
    }

    public String getQtdeCurtidas ()
    {
        return QtdeCurtidas;
    }

    public void setQtdeCurtidas (String QtdeCurtidas)
    {
        this.QtdeCurtidas = QtdeCurtidas;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [DataComentario = "+DataComentario+", ComentarioId = "+ComentarioId+", NomePessoa = "+NomePessoa+", QtdeDescurtidas = "+QtdeDescurtidas+", Texto = "+Texto+", ComentarioProprio = "+ComentarioProprio+", hasFoto = "+hasFoto+", LinkFoto = "+LinkFoto+", QtdeCurtidas = "+QtdeCurtidas+"]";
    }
}
