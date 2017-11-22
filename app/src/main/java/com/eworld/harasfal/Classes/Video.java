package com.eworld.harasfal.Classes;

/**
 * Created by evox on 03/11/16.
 */

public class Video
{
    private String Link;

    private String VideoId;
    private boolean MostrarVideo;

    public String Thumbnail;

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getVideoId() {
        return VideoId;
    }

    public void setVideoId(String videoId) {
        VideoId = videoId;
    }

    public boolean isMostrarVideo() {
        return MostrarVideo;
    }

    public void setMostrarVideo(boolean mostrarVideo) {
        MostrarVideo = mostrarVideo;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Link = "+Link+", FotoId = "+VideoId+"]";
    }
}
