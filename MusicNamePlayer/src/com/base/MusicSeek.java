package com.base;

import java.util.List;

/**
 * Title: MusicSeek
 * Description:
 * Copyright:Copyright(c)2015
 * Company: 四川久聚科技股份有限公司
 * CreateTime:2015/9/4  17:46
 *
 * @author liuwenxing
 * @version 1.0
 */
public class MusicSeek {

    /**
     * showapi_res_error : 
     * showapi_res_body : {"pagebean":{"totalpage":2,"picUrl":"http://imgcache.qq.com/music/common/upload/iphone_order_channel/20140123123339.jpg","currentPage":1,"title":"我是歌手榜","ret_code":0,"songlist":[{"albumname":"我是歌手第二季 第1期","singerid":13948,"singername":"G.E.M. 邓紫棋","downUrl":"http://stream11.qqmusic.qq.com/35144031.mp3","albumid":452381,"songid":5144031,"songname":"泡沫","url":"http://ws.stream.qqmusic.qq.com/5144031.m4a"}]}}
     * showapi_res_code : 0
     */
    private String showapi_res_error;
    private ShowapiResBody showapi_res_body;
    private int showapi_res_code;

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public void setShowapi_res_body(ShowapiResBody showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public ShowapiResBody getShowapi_res_body() {
        return showapi_res_body;
    }

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public static class ShowapiResBody {
        /**
         * pagebean : {"totalpage":2,"picUrl":"http://imgcache.qq.com/music/common/upload/iphone_order_channel/20140123123339.jpg","currentPage":1,"title":"我是歌手榜","ret_code":0,"songlist":[{"albumname":"我是歌手第二季 第1期","singerid":13948,"singername":"G.E.M. 邓紫棋","downUrl":"http://stream11.qqmusic.qq.com/35144031.mp3","albumid":452381,"songid":5144031,"songname":"泡沫","url":"http://ws.stream.qqmusic.qq.com/5144031.m4a"}]}
         */
        private Pagebean pagebean;

        public void setPagebean(Pagebean pagebean) {
            this.pagebean = pagebean;
        }

        public Pagebean getPagebean() {
            return pagebean;
        }

        public static class Pagebean {
            /**
             * totalpage : 2
             * picUrl : http://imgcache.qq.com/music/common/upload/iphone_order_channel/20140123123339.jpg
             * currentPage : 1
             * title : 我是歌手榜
             * ret_code : 0
             * songlist : [{"albumname":"我是歌手第二季 第1期","singerid":13948,"singername":"G.E.M. 邓紫棋","downUrl":"http://stream11.qqmusic.qq.com/35144031.mp3","albumid":452381,"songid":5144031,"songname":"泡沫","url":"http://ws.stream.qqmusic.qq.com/5144031.m4a"}]
             */
            private int totalpage;
            private String picUrl;
            private int currentPage;
            private String title;
            private int ret_code;
            private List<SonglistEntity> songlist;

            public void setTotalpage(int totalpage) {
                this.totalpage = totalpage;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public void setCurrentPage(int currentPage) {
                this.currentPage = currentPage;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setRet_code(int ret_code) {
                this.ret_code = ret_code;
            }

            public void setSonglist(List<SonglistEntity> songlist) {
                this.songlist = songlist;
            }

            public int getTotalpage() {
                return totalpage;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public int getCurrentPage() {
                return currentPage;
            }

            public String getTitle() {
                return title;
            }

            public int getRet_code() {
                return ret_code;
            }

            public List<SonglistEntity> getSonglist() {
                return songlist;
            }

           
        }
    }
}
