package com.eworld.harasfal.Classes;

/**
 * Created by evox on 11/11/16.
 */

public class Pessoa
{
    private boolean IsCliente;

    private String DataNascimento;

    private String Email;

    private String Identificador;

    private String LinkFoto;

    private String Nome;

    private String  PerfilFace;

    private String Celular;

    private boolean isAutorizado;

    public String getDataNascimento() {
        return DataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        DataNascimento = dataNascimento;
    }

    public boolean isCliente() {
        return IsCliente;
    }

    public void setCliente(boolean cliente) {
        IsCliente = cliente;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLinkFoto() {
        return LinkFoto;
    }

    public void setLinkFoto(String linkFoto) {
        LinkFoto = linkFoto;
    }

    public boolean isAutorizado() {
        return isAutorizado;
    }

    public void setAutorizado(boolean autorizado) {
        isAutorizado = autorizado;
    }

    public String getIdentificador ()
    {
        return Identificador;
    }

    public void setIdentificador (String Identificador)
    {
        this.Identificador = Identificador;
    }

    public String getPerfilFace() {
        return PerfilFace;
    }

    public void setPerfilFace(String perfilFace) {
        PerfilFace = perfilFace;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String celular) {
        Celular = celular;
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
        return "ClassPojo [IsCliente = "+IsCliente+", Email = "+Email+", Identificador = "+Identificador+", LinkFoto = "+LinkFoto+", Nome = "+Nome+", isAutorizado = "+ isAutorizado +"]";
    }
}