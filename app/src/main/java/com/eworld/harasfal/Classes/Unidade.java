package com.eworld.harasfal.Classes;

import java.util.ArrayList;

/**
 * Created by evox on 03/11/16.
 */

public class Unidade
{
    private String AvaliacaoUsuario;

    private String Email;

    private ArrayList<Foto> Fotos;

    private String Descricao;

    private String Latitude;

    private ArrayList<String> Telefones;

    private String Longitude;

    private String UnidadeId;

    private String Endereco;

    private String Nome;

    public String getAvaliacaoUsuario ()
    {
        return AvaliacaoUsuario;
    }

    public void setAvaliacaoUsuario (String AvaliacaoUsuario)
    {
        this.AvaliacaoUsuario = AvaliacaoUsuario;
    }

    public String getEmail ()
{
    return Email;
}

    public void setEmail (String Email)
    {
        this.Email = Email;
    }

    public ArrayList<Foto> getFotos() {
        return Fotos;
    }

    public void setFotos(ArrayList<Foto> fotos) {
        Fotos = fotos;
    }

    public ArrayList<String> getTelefones() {
        return Telefones;
    }

    public void setTelefones(ArrayList<String> telefones) {
        Telefones = telefones;
    }

    public String getDescricao ()
    {
        return Descricao;
    }

    public void setDescricao (String Descricao)
    {
        this.Descricao = Descricao;
    }

    public String getLatitude ()
    {
        return Latitude;
    }

    public void setLatitude (String Latitude)
    {
        this.Latitude = Latitude;
    }



    public String getLongitude ()
    {
        return Longitude;
    }

    public void setLongitude (String Longitude)
    {
        this.Longitude = Longitude;
    }

    public String getUnidadeId ()
    {
        return UnidadeId;
    }

    public void setUnidadeId (String UnidadeId)
    {
        this.UnidadeId = UnidadeId;
    }

    public String getEndereco ()
{
    return Endereco;
}

    public void setEndereco (String Endereco)
    {
        this.Endereco = Endereco;
    }

    public String getNome ()
    {
        return Nome;
    }

    public void setNome (String Nome)
    {
        this.Nome = Nome;
    }

   /* @Override
    public String toString()
    {
        return "ClassPojo [AvaliacaoUsuario = "+AvaliacaoUsuario+", Email = "+Email+", fotoses = "+ fotoses +", Descricao = "+Descricao+", Latitude = "+Latitude+", Telefones = "+Telefones+", Longitude = "+Longitude+", UnidadeId = "+UnidadeId+", Endereco = "+Endereco+", Nome = "+Nome+"]";
    }*/
}
