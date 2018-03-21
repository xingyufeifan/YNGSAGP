package com.nandi.yngsagp.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.nandi.yngsagp.bean.AudioPath;
import com.nandi.yngsagp.bean.DangerUBean;
import com.nandi.yngsagp.bean.DisasterPoint;
import com.nandi.yngsagp.bean.DisasterUBean;
import com.nandi.yngsagp.bean.PhotoPath;
import com.nandi.yngsagp.bean.VideoPath;

import com.nandi.yngsagp.greendao.AudioPathDao;
import com.nandi.yngsagp.greendao.DangerUBeanDao;
import com.nandi.yngsagp.greendao.DisasterPointDao;
import com.nandi.yngsagp.greendao.DisasterUBeanDao;
import com.nandi.yngsagp.greendao.PhotoPathDao;
import com.nandi.yngsagp.greendao.VideoPathDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig audioPathDaoConfig;
    private final DaoConfig dangerUBeanDaoConfig;
    private final DaoConfig disasterPointDaoConfig;
    private final DaoConfig disasterUBeanDaoConfig;
    private final DaoConfig photoPathDaoConfig;
    private final DaoConfig videoPathDaoConfig;

    private final AudioPathDao audioPathDao;
    private final DangerUBeanDao dangerUBeanDao;
    private final DisasterPointDao disasterPointDao;
    private final DisasterUBeanDao disasterUBeanDao;
    private final PhotoPathDao photoPathDao;
    private final VideoPathDao videoPathDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        audioPathDaoConfig = daoConfigMap.get(AudioPathDao.class).clone();
        audioPathDaoConfig.initIdentityScope(type);

        dangerUBeanDaoConfig = daoConfigMap.get(DangerUBeanDao.class).clone();
        dangerUBeanDaoConfig.initIdentityScope(type);

        disasterPointDaoConfig = daoConfigMap.get(DisasterPointDao.class).clone();
        disasterPointDaoConfig.initIdentityScope(type);

        disasterUBeanDaoConfig = daoConfigMap.get(DisasterUBeanDao.class).clone();
        disasterUBeanDaoConfig.initIdentityScope(type);

        photoPathDaoConfig = daoConfigMap.get(PhotoPathDao.class).clone();
        photoPathDaoConfig.initIdentityScope(type);

        videoPathDaoConfig = daoConfigMap.get(VideoPathDao.class).clone();
        videoPathDaoConfig.initIdentityScope(type);

        audioPathDao = new AudioPathDao(audioPathDaoConfig, this);
        dangerUBeanDao = new DangerUBeanDao(dangerUBeanDaoConfig, this);
        disasterPointDao = new DisasterPointDao(disasterPointDaoConfig, this);
        disasterUBeanDao = new DisasterUBeanDao(disasterUBeanDaoConfig, this);
        photoPathDao = new PhotoPathDao(photoPathDaoConfig, this);
        videoPathDao = new VideoPathDao(videoPathDaoConfig, this);

        registerDao(AudioPath.class, audioPathDao);
        registerDao(DangerUBean.class, dangerUBeanDao);
        registerDao(DisasterPoint.class, disasterPointDao);
        registerDao(DisasterUBean.class, disasterUBeanDao);
        registerDao(PhotoPath.class, photoPathDao);
        registerDao(VideoPath.class, videoPathDao);
    }
    
    public void clear() {
        audioPathDaoConfig.clearIdentityScope();
        dangerUBeanDaoConfig.clearIdentityScope();
        disasterPointDaoConfig.clearIdentityScope();
        disasterUBeanDaoConfig.clearIdentityScope();
        photoPathDaoConfig.clearIdentityScope();
        videoPathDaoConfig.clearIdentityScope();
    }

    public AudioPathDao getAudioPathDao() {
        return audioPathDao;
    }

    public DangerUBeanDao getDangerUBeanDao() {
        return dangerUBeanDao;
    }

    public DisasterPointDao getDisasterPointDao() {
        return disasterPointDao;
    }

    public DisasterUBeanDao getDisasterUBeanDao() {
        return disasterUBeanDao;
    }

    public PhotoPathDao getPhotoPathDao() {
        return photoPathDao;
    }

    public VideoPathDao getVideoPathDao() {
        return videoPathDao;
    }

}