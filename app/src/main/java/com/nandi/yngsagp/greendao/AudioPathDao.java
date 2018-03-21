package com.nandi.yngsagp.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.nandi.yngsagp.bean.AudioPath;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "AUDIO_PATH".
*/
public class AudioPathDao extends AbstractDao<AudioPath, Long> {

    public static final String TABLENAME = "AUDIO_PATH";

    /**
     * Properties of entity AudioPath.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Type = new Property(1, int.class, "type", false, "TYPE");
        public final static Property Path = new Property(2, String.class, "path", false, "PATH");
    }


    public AudioPathDao(DaoConfig config) {
        super(config);
    }
    
    public AudioPathDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"AUDIO_PATH\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"TYPE\" INTEGER NOT NULL ," + // 1: type
                "\"PATH\" TEXT);"); // 2: path
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"AUDIO_PATH\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AudioPath entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getType());
 
        String path = entity.getPath();
        if (path != null) {
            stmt.bindString(3, path);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AudioPath entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getType());
 
        String path = entity.getPath();
        if (path != null) {
            stmt.bindString(3, path);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public AudioPath readEntity(Cursor cursor, int offset) {
        AudioPath entity = new AudioPath( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // type
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // path
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AudioPath entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setType(cursor.getInt(offset + 1));
        entity.setPath(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(AudioPath entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(AudioPath entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(AudioPath entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}