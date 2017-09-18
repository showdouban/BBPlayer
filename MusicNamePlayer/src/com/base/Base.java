package com.base;

import java.util.List;

/**
 * Title: ssss
 * Description:
 * Copyright:Copyright(c)2015
 * Company: 四川久聚科技股份有限公司
 * CreateTime:2015/9/2  17:19
 *
 * @author liuwenxing
 * @version 1.0
 */
public class Base {

    /**
     * showapi_res_error : 
     * showapi_res_body : {"pagebean":{"allPages":4,"maxResult":20,"currentPage":1,"keyword":"泡沫","ret_code":0,"contentlist":[{"albumname":"Xposed","singerid":"13948","singername":"G.E.M. 邓紫棋","downUrl":"http://stream8.qqmusic.qq.com/31530858.mp3","albumpic_small":"http://imgcache.qq.com/music/photo/album/98/90_albumpic_123298_0.jpg","albumid":"123298","albumpic_big":"http://imgcache.qq.com/music/photo/album_300/98/300_albumpic_123298_0.jpg","m4a":"http://ws.stream.qqmusic.qq.com/1530858.m4a?fromtag=46","songid":1530858,"songname":"泡沫"}],"allNum":75}}
     * showapi_res_code : 0
     */
    private String showapi_res_error;
    private ShowapiResBodyEntity showapi_res_body;
    private int showapi_res_code;

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public void setShowapi_res_body(ShowapiResBodyEntity showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public ShowapiResBodyEntity getShowapi_res_body() {
        return showapi_res_body;
    }

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public static class ShowapiResBodyEntity {
        /**
         * pagebean : {"allPages":4,"maxResult":20,"currentPage":1,"keyword":"泡沫","ret_code":0,"contentlist":[{"albumname":"Xposed","singerid":"13948","singername":"G.E.M. 邓紫棋","downUrl":"http://stream8.qqmusic.qq.com/31530858.mp3","albumpic_small":"http://imgcache.qq.com/music/photo/album/98/90_albumpic_123298_0.jpg","albumid":"123298","albumpic_big":"http://imgcache.qq.com/music/photo/album_300/98/300_albumpic_123298_0.jpg","m4a":"http://ws.stream.qqmusic.qq.com/1530858.m4a?fromtag=46","songid":1530858,"songname":"泡沫"}],"allNum":75}
         */
        private PagebeanEntity pagebean;

        public void setPagebean(PagebeanEntity pagebean) {
            this.pagebean = pagebean;
        }

        public PagebeanEntity getPagebean() {
            return pagebean;
        }

        public static class PagebeanEntity {
            /**
             * allPages : 4
             * maxResult : 20
             * currentPage : 1
             * keyword : 泡沫
             * ret_code : 0
             * contentlist : [{"albumname":"Xposed","singerid":"13948","singername":"G.E.M. 邓紫棋","downUrl":"http://stream8.qqmusic.qq.com/31530858.mp3","albumpic_small":"http://imgcache.qq.com/music/photo/album/98/90_albumpic_123298_0.jpg","albumid":"123298","albumpic_big":"http://imgcache.qq.com/music/photo/album_300/98/300_albumpic_123298_0.jpg","m4a":"http://ws.stream.qqmusic.qq.com/1530858.m4a?fromtag=46","songid":1530858,"songname":"泡沫"}]
             * allNum : 75
             */
            private int allPages;
            private int maxResult;
            private int currentPage;
            private String keyword;
            private int ret_code;
            private List<ContentlistEntity> contentlist;
            private int allNum;

            public void setAllPages(int allPages) {
                this.allPages = allPages;
            }

            public void setMaxResult(int maxResult) {
                this.maxResult = maxResult;
            }

            public void setCurrentPage(int currentPage) {
                this.currentPage = currentPage;
            }

            public void setKeyword(String keyword) {
                this.keyword = keyword;
            }

            public void setRet_code(int ret_code) {
                this.ret_code = ret_code;
            }

            public void setContentlist(List<ContentlistEntity> contentlist) {
                this.contentlist = contentlist;
            }

            public void setAllNum(int allNum) {
                this.allNum = allNum;
            }

            public int getAllPages() {
                return allPages;
            }

            public int getMaxResult() {
                return maxResult;
            }

            public int getCurrentPage() {
                return currentPage;
            }

            public String getKeyword() {
                return keyword;
            }

            public int getRet_code() {
                return ret_code;
            }

            public List<ContentlistEntity> getContentlist() {
                return contentlist;
            }

            public int getAllNum() {
                return allNum;
            }

            
        }
    }
}
