package com.nandi.yngsagp.utils;

import android.location.Location;

/**
 * Date:2018/3/30
 * Time:下午2:40
 * author:qingsong
 */
public class GPSUtils {
    /**
     *
     * @note double 位置 转换成  分 秒
     *
     * */
    public static String gpsInfoConvert(double data){
        String ret_s = "";
        int tmp_i_du = (int)data;
        ret_s = String.valueOf(tmp_i_du)+"°";
        //度小数部分
        double tmp_d_du = data-tmp_i_du;
        int tmp_i_fen = (int)(tmp_d_du*60);
        ret_s = ret_s.concat(String.valueOf(tmp_i_fen)+"′");
        double tmp_d_fen = tmp_d_du*60 - tmp_i_fen;
        int tmp_i_miao = (int)(tmp_d_fen*60);
        ret_s = ret_s.concat(String.valueOf(tmp_i_miao)+"″");
        return ret_s;
    }

    //照片的度分秒转gps
    public static Double convertToDegree(String stringDMS){
        Float result = null;
        String[] DMS = stringDMS.split(",", 3);

        String[] stringD = DMS[0].split("/", 2);
        Double D0 = new Double(stringD[0]);
        Double D1 = new Double(stringD[1]);
        Double FloatD = D0/D1;

        String[] stringM = DMS[1].split("/", 2);
        Double M0 = new Double(stringM[0]);
        Double M1 = new Double(stringM[1]);
        Double FloatM = M0/M1;

        String[] stringS = DMS[2].split("/", 2);
        Double S0 = new Double(stringS[0]);
        Double S1 = new Double(stringS[1]);
        Double FloatS = S0/S1;

        result = new Float(FloatD + (FloatM/60) + (FloatS/3600));

        return (double) result;
    }
}
