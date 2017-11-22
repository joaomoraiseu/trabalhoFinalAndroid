package com.eworld.harasfal.Classes;

/**
 * Created by evox on 08/12/16.
 */
public class Cliente
{
    private String Identificador;

    private String Foto;

    private String ClienteId;

    private String Nome;

    public String getIdentificador ()
    {
        return Identificador;
    }

    public void setIdentificador (String Identificador)
    {
        this.Identificador = Identificador;
    }

    public String getFoto ()
    {
        return Foto;
    }

    public void setFoto (String Foto)
    {
        this.Foto = Foto;
    }

    public String getClienteId ()
    {
        return ClienteId;
    }

    public void setClienteId (String ClienteId)
    {
        this.ClienteId = ClienteId;
    }

    public String getNome ()
    {
        return Nome;
    }

    public void setNome (String Nome)
    {
        this.Nome = Nome;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Identificador = "+Identificador+", Foto = "+Foto+", ClienteId = "+ClienteId+", Nome = "+Nome+"]";
    }
}