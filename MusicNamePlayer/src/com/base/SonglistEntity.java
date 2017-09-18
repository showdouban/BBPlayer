/**
 * 
 */
package com.base;

/**
 * Title: SonglistEntity.java
 * Description: 
 * Copyright:Copyright(c)2015
 * Company: 四川久聚科技股份有限公司
 * CreateTime:2015-9-4 下午6:00:32 
 * @author liuwenxing
 * @version 1.0
 */
public class SonglistEntity {

    /**
     * albumname : 我是歌手第二季 第1期
     * singerid : 13948
     * singername : G.E.M. 邓紫棋
     * downUrl : http://stream11.qqmusic.qq.com/35144031.mp3
     * albumid : 452381
     * songid : 5144031
     * songname : 泡沫
     * url : http://ws.stream.qqmusic.qq.com/5144031.m4a
     */
    private String albumname;
    private int singerid;
    private String singername;
    private String downUrl;
    private int albumid;
    private int songid;
    private String songname;
    private String url;

    public void setAlbumname(String albumname) {
        this.albumname = albumname;
    }

    public void setSingerid(int singerid) {
        this.singerid = singerid;
    }

    public void setSingername(String singername) {
        this.singername = singername;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public void setAlbumid(int albumid) {
        this.albumid = albumid;
    }

    public void setSongid(int songid) {
        this.songid = songid;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlbumname() {
        return albumname;
    }

    public int getSingerid() {
        return singerid;
    }

    public String getSingername() {
        return singername;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public int getAlbumid() {
        return albumid;
    }

    public int getSongid() {
        return songid;
    }

    public String getSongname() {
        return songname;
    }

    public String getUrl() {
        return url;
    }

}
