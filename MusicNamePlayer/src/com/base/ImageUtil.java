package com.base;

/**
 * Title: ImageUtil
 * Description:图片返回各种缩略图
 * Copyright:Copyright(c)2015
 * Company: 四川久聚科技股份有限公司
 * CreateTime:2015/9/5  14:34
 *
 * @author zouzheng
 * @version 1.0
 */
public class ImageUtil {
    /**
     * 首页广告大图//商家店铺
     * @return
     */
    public  static String getAdvertTop(String image_url){
        return image_url+"@1e_750w_408h_1c_0i_1o_90Q_1x.jpg";
    }

    /**
     * 首页图标
     * @return
     */
    public static String getAdvertIcon(String image_url){
        return image_url+"@1e_168w_150h_1c_0i_1o_90Q_1x.jpg";
    }

    /**
     * 首页更多
     * @return
     */
    public static String getAdvertMore(String image_url){
        return image_url+"@1e_400w_600h_1c_0i_1o_90Q_1x.jpg";
    }

    /**
     * 商品小图
     * @return
     */
    public static String getGoodsThumb(String image_url){
        return image_url+"@1e_120w_120h_1c_0i_1o_90Q_1x.jpg";
    }

    /**
     * 商品详情
     * @return
     */
    public static String getGoodsDetail(String image_url){
        return image_url+"@1e_750w_750h_1c_0i_1o_90Q_1x.jpg";
    }
}
