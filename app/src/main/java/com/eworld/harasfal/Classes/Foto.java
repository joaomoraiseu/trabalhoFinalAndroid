package com.eworld.harasfal.Classes;

/**
 * Created by evox on 03/11/16.
 */

public class Foto
{
    private String Link;

    private String FotoId;

    public String getLink ()
    {
        return Link;
    }

    public void setLink (String Link)
    {
        this.Link = Link;
    }

    public String getFotoId ()
    {
        return FotoId;
    }

    public void setFotoId (String FotoId)
    {
        this.FotoId = FotoId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Link = "+Link+", FotoId = "+FotoId+"]";
    }
}
