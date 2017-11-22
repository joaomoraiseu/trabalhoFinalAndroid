package com.eworld.harasfal.Classes;


import java.util.ArrayList;

public class Item {
    private boolean IsVideo;

    private boolean Curtido;

    private String Tipo;
    private String Data;

    private String ItemId;
    private String LinkFotoItem;
    private ArrayList<Foto> Fotos;
    private ArrayList<Video> Videos;

    private String Observacao;

    private String Titulo;

    private String Descricao;

    private String QtdeCurtidas;

    private String QtdeComentarios;

    public String getObservacao() {
        return Observacao;
    }

    public void setObservacao(String observacao) {
        Observacao = observacao;
    }

    public boolean isVideo() {
        return IsVideo;
    }

    public void setVideo(boolean video) {
        IsVideo = video;
    }

    public boolean isCurtido() {
        return Curtido;
    }

    public void setCurtido(boolean curtido) {
        Curtido = curtido;
    }

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String ItemId) {
        this.ItemId = ItemId;
    }

    public ArrayList<Foto> getFotos() {
        return Fotos;
    }

    public void setFotos(ArrayList<Foto> fotos) {
        Fotos = fotos;
    }

    public ArrayList<Video> getVideos() {
        return Videos;
    }

    public void setVideos(ArrayList<Video> videos) {
        Videos = videos;
    }

    public String getLinkFotoItem() {
        return LinkFotoItem;
    }

    public void setLinkFotoItem(String linkFotoItem) {
        LinkFotoItem = linkFotoItem;
    }

    public String getQtdeComentarios() {
        return QtdeComentarios;
    }

    public void setQtdeComentarios(String qtdeComentarios) {
        QtdeComentarios = qtdeComentarios;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }

    public String getQtdeCurtidas() {
        return QtdeCurtidas;
    }

    public void setQtdeCurtidas(String QtdeCurtidas) {
        this.QtdeCurtidas = QtdeCurtidas;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }
}
