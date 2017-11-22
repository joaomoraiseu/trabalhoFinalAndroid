package com.eworld.harasfal.Classes;

import java.util.ArrayList;

/**
 * Created by evox on 18/09/15.
 */
public class Mensagem
{
    private String OrigemId;

    private String Texto;

    private String DataEnvio;

    private String TipoMidia;

    private boolean Visualizada;

    private ArrayList<Foto> Fotos;

    private ArrayList<Video> Videos;

    public ArrayList<Video> getVideos() {
        return Videos;
    }

    public void setVideos(ArrayList<Video> videos) {
        Videos = videos;
    }

    public String getTipoMidia() {
        return TipoMidia;
    }

    public void setTipoMidia(String tipoMidia) {
        TipoMidia = tipoMidia;
    }

    public String getOrigemId ()
    {
        return OrigemId;
    }

    public void setOrigemId (String OrigemId)
    {
        this.OrigemId = OrigemId;
    }

    public String getTexto ()
    {
        return Texto;
    }

    public void setTexto (String Texto)
    {
        this.Texto = Texto;
    }

    public String getDataEnvio ()
    {
        return DataEnvio;
    }

    public void setDataEnvio (String DataEnvio)
    {
        this.DataEnvio = DataEnvio;
    }

    public boolean isVisualizada() {
        return Visualizada;
    }

    public void setVisualizada(boolean visualizada) {
        Visualizada = visualizada;
    }

    public ArrayList<Foto> getFotos() {
        return Fotos;
    }

    public void setFotos(ArrayList<Foto> fotos) {
        Fotos = fotos;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [OrigemId = "+OrigemId+", Texto = "+Texto+", DataEnvio = "+DataEnvio+", Visualizada = "+Visualizada+"]";
    }
}