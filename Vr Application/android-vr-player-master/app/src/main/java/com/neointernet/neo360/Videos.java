package com.neointernet.neo360;

/**
 * Created by yasar on 3/1/18.
 */

public class Videos {

    public String videoname;
    public boolean isPaused = false;

    public Videos(String videoname) {
        this.videoname = videoname;
    }

    public boolean isPaused() {
        return isPaused;

    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public String getVideoname() {
        return videoname;
    }

    public void setVideoname(String videoname) {
        this.videoname = videoname;
    }
}
