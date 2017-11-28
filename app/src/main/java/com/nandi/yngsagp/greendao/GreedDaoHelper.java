package com.nandi.yngsagp.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.nandi.yngsagp.bean.AudioPath;
import com.nandi.yngsagp.bean.DangerUBean;
import com.nandi.yngsagp.bean.DisasterUBean;
import com.nandi.yngsagp.bean.PhotoPath;
import com.nandi.yngsagp.bean.VideoPath;

import java.util.List;

/**
 * Created by ChenPeng on 2017/11/17.
 */

public class GreedDaoHelper {
    private static DaoSession daoSession;

    /**
     * 初始化greenDao
     * 建议放在Application 中进行
     */

    public static void initDatabase(Context context) {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, "gsAgd_db", null);
        SQLiteDatabase database = devOpenHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
    }

    public static void insertPhoto(PhotoPath photoPath) {
        daoSession.getPhotoPathDao().insertOrReplace(photoPath);

    }


    public static List<PhotoPath> queryPhoto(int type) {
        return daoSession.getPhotoPathDao().queryBuilder().where(PhotoPathDao.Properties.Type.eq(type)).list();
    }

    public static void deletePhoto(PhotoPath photoPath) {
        daoSession.getPhotoPathDao().delete(photoPath);
        daoSession.getPhotoPathDao().deleteInTx();
    }
    public static void deletePhotoList(List<PhotoPath> photoPaths) {
        daoSession.getPhotoPathDao().deleteInTx(photoPaths);
    }
    public static void insertVideo(VideoPath videoPath) {
        daoSession.getVideoPathDao().insertOrReplace(videoPath);
    }


    public static VideoPath queryVideo(int type) {
        return daoSession.getVideoPathDao().queryBuilder().where(VideoPathDao.Properties.Type.eq(type)).unique();
    }

    public static void deleteVideo(VideoPath videoPath) {
        daoSession.getVideoPathDao().delete(videoPath);
    }

    public static void insertAudio(AudioPath audioPath) {
        daoSession.getAudioPathDao().insertOrReplace(audioPath);
    }


    public static AudioPath queryAudio(int type) {
        return daoSession.getAudioPathDao().queryBuilder().where(AudioPathDao.Properties.Type.eq(type)).unique();
    }

    public static void deleteAudio(AudioPath audioPath) {
        daoSession.getAudioPathDao().delete(audioPath);
    }

    public static void insertDisaster(DisasterUBean disasterUBean) {
        daoSession.getDisasterUBeanDao().insertOrReplace(disasterUBean);
    }


    public static DisasterUBean queryDisaster() {
        return daoSession.getDisasterUBeanDao().queryBuilder().unique();
    }

    public static void deleteDisaster() {
        daoSession.getDisasterUBeanDao().deleteAll();
    }
    public static void deleteDanger() {
        daoSession.getDangerUBeanDao().deleteAll();
    }
    public static void insertDanger(DangerUBean dangerUBean) {
        daoSession.getDangerUBeanDao().insertOrReplace(dangerUBean);
    }


    public static DangerUBean queryDanger() {
        return daoSession.getDangerUBeanDao().queryBuilder().unique();
    }
    public static void deleteAll(){
        daoSession.getPhotoPathDao().deleteAll();
        daoSession.getDangerUBeanDao().deleteAll();
        daoSession.getDisasterUBeanDao().deleteAll();
        daoSession.getAudioPathDao().deleteAll();
        daoSession.getVideoPathDao().deleteAll();
    }
}
