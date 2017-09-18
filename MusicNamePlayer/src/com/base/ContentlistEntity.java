/**
 * 
 */
package com.base;

/**
 * Title: ContentlistEntity.java
 * Description: 
 * Copyright:Copyright(c)2015
 * Company: 四川久聚科技股份有限公司
 * CreateTime:2015-9-4 上午10:17:21 
 * @author liuwenxing
 * @version 1.0
 */
public class ContentlistEntity {

    /**
     * albumname : Xposed
     * singerid : 13948
     * singername : G.E.M. 邓紫棋
     * downUrl : http://stream8.qqmusic.qq.com/31530858.mp3
     * albumpic_small : http://imgcache.qq.com/music/photo/album/98/90_albumpic_123298_0.jpg
     * albumid : 123298
     * albumpic_big : http://imgcache.qq.com/music/photo/album_300/98/300_albumpic_123298_0.jpg
     * m4a : http://ws.stream.qqmusic.qq.com/1530858.m4a?fromtag=46
     * songid : 1530858
     * songname : 泡沫
     */
    private String albumname;
    private String singerid;
    private String singername;
    private String downUrl;
    private String albumpic_small;
    private String albumid;
    private String albumpic_big;
    private String m4a;
    private int songid;
    private String songname;

    public void setAlbumname(String albumname) {
        this.albumname = albumname;
    }

    public void setSingerid(String singerid) {
        this.singerid = singerid;
    }

    public void setSingername(String singername) {
        this.singername = singername;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public void setAlbumpic_small(String albumpic_small) {
        this.albumpic_small = albumpic_small;
    }

    public void setAlbumid(String albumid) {
        this.albumid = albumid;
    }

    public void setAlbumpic_big(String albumpic_big) {
        this.albumpic_big = albumpic_big;
    }

    public void setM4a(String m4a) {
        this.m4a = m4a;
    }

    public void setSongid(int songid) {
        this.songid = songid;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public String getAlbumname() {
        return albumname;
    }

    public String getSingerid() {
        return singerid;
    }

    public String getSingername() {
        return singername;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public String getAlbumpic_small() {
        return albumpic_small;
    }

    public String getAlbumid() {
        return albumid;
    }

    public String getAlbumpic_big() {
        return albumpic_big;
    }

    public String getM4a() {
        return m4a;
    }

    public int getSongid() {
        return songid;
    }

    public String getSongname() {
        return songname;
    }

}
