package orion.rssreader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter {

    private DataHelper mHelper;

    public DataAdapter(Context context) {
        mHelper = new DataHelper(context);
    }

    public List<SubscribedItem> getChildren(int id) {
        String query = null;
        if (id == -1) {
            query = "SELECT " + mHelper.ID_COLUMN + ", " + mHelper.TITLE_COLUMN + ", " +
                    mHelper.TYPE_COLUMN + ", " + mHelper.ICON_URL_COLUMN +
                    " FROM " + mHelper.SUBSCRIBED_ITEM_TABLE + ", " + mHelper.SUBSCRIBED_ITEMS_RELATIONSHIPS_TABLE +
                    " WHERE " + mHelper.PARENT_ID_COLUMN + " IS NULL" +
                    " AND " + mHelper.ID_COLUMN + "=" + mHelper.CHILD_ID_COLUMN;
        } else {
            query = "SELECT " + mHelper.ID_COLUMN + ", " + mHelper.TITLE_COLUMN + ", " +
                    mHelper.TYPE_COLUMN + ", " + mHelper.ICON_URL_COLUMN +
                    " FROM " + mHelper.SUBSCRIBED_ITEM_TABLE + ", " + mHelper.SUBSCRIBED_ITEMS_RELATIONSHIPS_TABLE +
                    " WHERE " + mHelper.PARENT_ID_COLUMN + "=" + id +
                    " AND " + mHelper.ID_COLUMN + "=" + mHelper.CHILD_ID_COLUMN;
        }

        List<SubscribedItem> items = new ArrayList<>();
        SQLiteDatabase db = mHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex(mHelper.ID_COLUMN);
                int titleIndex = cursor.getColumnIndex(mHelper.TITLE_COLUMN);
                int typeIndex = cursor.getColumnIndex(mHelper.TYPE_COLUMN);
                int iconUrlIndex = cursor.getColumnIndex(mHelper.ICON_URL_COLUMN);

                int uid = cursor.getInt(idIndex);
                String title = cursor.getString(titleIndex);
                String type = cursor.getString(typeIndex);
                String iconUrl = cursor.getString(iconUrlIndex);

                SubscribedItem item = null;

                if (type.equals("folder")) {
                    item = new SubscribedFolder(title);
                    item.setId(uid);
                } else if (type.equals("channel")) {
                    item = new RssChannel();
                    item.setId(uid);
                    RssChannel channel = (RssChannel) item;
                    channel.setTitle(title);
                    channel.setIconUrl(iconUrl);
                }

                items.add(item);
            }
            cursor.close();
        }
        return items;
    }

    public long createFolder(int currentId, SubscribedFolder folder) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(mHelper.TYPE_COLUMN, "folder");
        contentValues.putNull(mHelper.FEEDID_COLUMN);
        contentValues.put(mHelper.TITLE_COLUMN, folder.getTitle());
        contentValues.putNull(mHelper.WEBSITE_COLUMN);
        contentValues.putNull(mHelper.DESCRIPTION_COLUMN);
        contentValues.putNull(mHelper.ICON_URL_COLUMN);
        contentValues.putNull(mHelper.VISUAL_URL_COLUMN);

        long id = db.insert(mHelper.SUBSCRIBED_ITEM_TABLE, null, contentValues);

        contentValues = new ContentValues();
        if (currentId == -1) {
            contentValues.putNull(mHelper.PARENT_ID_COLUMN);
        } else {
            contentValues.put(mHelper.PARENT_ID_COLUMN, currentId);
        }
        contentValues.put(mHelper.CHILD_ID_COLUMN, id);
        db.insert(mHelper.SUBSCRIBED_ITEMS_RELATIONSHIPS_TABLE, null, contentValues);
        return id;
    }

    public RssChannel getChannel(int id) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        RssChannel channel = null;
        String query = "SELECT *" +
                " FROM " + mHelper.SUBSCRIBED_ITEM_TABLE +
                " WHERE " + mHelper.ID_COLUMN + "=" + id;

        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            channel = new RssChannel();
            cursor.moveToFirst();
            int idIndex = cursor.getColumnIndex(mHelper.ID_COLUMN);
            int feedIdIndex = cursor.getColumnIndex(mHelper.FEEDID_COLUMN);
            int titleIndex = cursor.getColumnIndex(mHelper.TITLE_COLUMN);
            int websiteIndex = cursor.getColumnIndex(mHelper.WEBSITE_COLUMN);
            int descriptionIndex = cursor.getColumnIndex(mHelper.DESCRIPTION_COLUMN);
            int iconUrlIndex = cursor.getColumnIndex(mHelper.ICON_URL_COLUMN);
            int visualUrlIndex = cursor.getColumnIndex(mHelper.VISUAL_URL_COLUMN);

            channel.setId(id);
            channel.setFeedId(cursor.getString(feedIdIndex));
            channel.setTitle(cursor.getString(titleIndex));
            channel.setWebsite(cursor.getString(websiteIndex));
            channel.setDescription(cursor.getString(descriptionIndex));
            channel.setIconUrl(cursor.getString(iconUrlIndex));
            channel.setVisualUrl(cursor.getString(visualUrlIndex));
        }

        return channel;
    }

    public void removeItem(int id) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(mHelper.SUBSCRIBED_ITEMS_RELATIONSHIPS_TABLE, mHelper.CHILD_ID_COLUMN + "=" + id, null);
        db.delete(mHelper.SUBSCRIBED_ITEM_TABLE, mHelper.ID_COLUMN + "=" + id, null);
    }

    public long createSubscribeChannel(int currentId, RssChannel channel) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(mHelper.TYPE_COLUMN, "channel");
        contentValues.put(mHelper.FEEDID_COLUMN, channel.getFeedId());
        contentValues.put(mHelper.TITLE_COLUMN, channel.getTitle());
        contentValues.put(mHelper.WEBSITE_COLUMN, channel.getWebsite());
        contentValues.put(mHelper.DESCRIPTION_COLUMN, channel.getDescription());
        contentValues.put(mHelper.ICON_URL_COLUMN, channel.getIconUrl());
        contentValues.put(mHelper.VISUAL_URL_COLUMN, channel.getVisualUrl());

        long id = db.insert(mHelper.SUBSCRIBED_ITEM_TABLE, null, contentValues);

        contentValues = new ContentValues();
        if (currentId == -1) {
            contentValues.putNull(mHelper.PARENT_ID_COLUMN);
        } else {
            contentValues.put(mHelper.PARENT_ID_COLUMN, currentId);
        }
        contentValues.put(mHelper.CHILD_ID_COLUMN, id);
        db.insert(mHelper.SUBSCRIBED_ITEMS_RELATIONSHIPS_TABLE, null, contentValues);

        return id;
    }

    public int getParentId(int id) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String query = "SELECT " + mHelper.PARENT_ID_COLUMN +
                " FROM " + mHelper.SUBSCRIBED_ITEMS_RELATIONSHIPS_TABLE +
                " WHERE " + mHelper.CHILD_ID_COLUMN + "=" + id;
        Cursor cursor = db.rawQuery(query, null);
        int parentId;
        if (cursor != null) {
            cursor.moveToFirst();
            int idIndex = cursor.getColumnIndex(mHelper.PARENT_ID_COLUMN);
            parentId = cursor.getInt(idIndex);
            return parentId == 0 ? -1 : parentId;
        }
        return -1;
    }

    static class DataHelper extends SQLiteOpenHelper {
        private Context mContext;

        private static final String DATABASE_NAME = "RssReaderUserData";
        private static final int DATABASE_VERSION = 4;

        // table
        private static final String SUBSCRIBED_ITEM_TABLE = "SUBSCRIBED_ITEMS";
        private static final String SUBSCRIBED_ITEMS_RELATIONSHIPS_TABLE = "SUBSCRIBED_ITEMS_RELATIONSHIPS";
        private static final String BOOKMARK_FEED_TABLE = "BOOKMARK_FEED";
        private static final String RECENT_FEED_TABLE = "RECENT_FEED";

        // column
        private static final String ID_COLUMN = "_id";
        private static final String TYPE_COLUMN = "type";
        private static final String FEEDID_COLUMN = "feed_id";
        private static final String TITLE_COLUMN = "title";
        private static final String WEBSITE_COLUMN = "website";
        private static final String DESCRIPTION_COLUMN = "description";
        private static final String ICON_URL_COLUMN = "icon_url";
        private static final String VISUAL_URL_COLUMN = "visual_url";

        private static final String PARENT_ID_COLUMN = "parent_id";
        private static final String CHILD_ID_COLUMN = "child_id";

        private static final String LINK_COLUMN = "link";

        // query
        private static final String CREATE_SUBSCRIBED_ITEMS_TABLE_SQL =
                "CREATE TABLE " + SUBSCRIBED_ITEM_TABLE + " (" +
                        ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        FEEDID_COLUMN + " VARCHAR(255), " +
                        TYPE_COLUMN + " VARCHAR(255), " +
                        TITLE_COLUMN + " VARCHAR(255), " +
                        WEBSITE_COLUMN + " VARCHAR(255), " +
                        DESCRIPTION_COLUMN + " VARCHAR(255), " +
                        ICON_URL_COLUMN + " VARCHAR(255), " +
                        VISUAL_URL_COLUMN + " VARCHAR(255));";

        private static final String CREATE_SUBSCRIBED_ITEMS_RELATIONSHIPS_TABLE_SQL =
                "CREATE TABLE " + SUBSCRIBED_ITEMS_RELATIONSHIPS_TABLE + " (" +
                        PARENT_ID_COLUMN + " INTEGER, " +
                        CHILD_ID_COLUMN + " INTEGER, " +
                        " FOREIGN KEY (" + PARENT_ID_COLUMN + ") REFERENCES " + SUBSCRIBED_ITEM_TABLE + "(" + ID_COLUMN + ")," +
                        " FOREIGN KEY (" + CHILD_ID_COLUMN + ") REFERENCES " + SUBSCRIBED_ITEM_TABLE + "(" + ID_COLUMN + ")," +
                        " PRIMARY KEY (" + PARENT_ID_COLUMN + ", " + CHILD_ID_COLUMN + ")" +
                        ");";

        private static final String CREATE_BOOKMARK_CHANNELS_TABLE_SQL =
                "CREATE TABLE " + BOOKMARK_FEED_TABLE + " (" +
                        ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        TITLE_COLUMN + " VARCHAR(255), " +
                        LINK_COLUMN + " VARCHAR(255));";

        private static final String CREATE_RECENT_CHANNELS_TABLE_SQL =
                "CREATE TABLE " + RECENT_FEED_TABLE + " (" +
                        ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        TITLE_COLUMN + " VARCHAR(255), " +
                        LINK_COLUMN + " VARCHAR(255));";

        private static final String DROP_DATABASE_SQL = "DROP DATABASE " + DATABASE_NAME;


        public DataHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.e("DATABASE", "onCreate()");
            db.execSQL(CREATE_SUBSCRIBED_ITEMS_TABLE_SQL);
            db.execSQL(CREATE_SUBSCRIBED_ITEMS_RELATIONSHIPS_TABLE_SQL);
            db.execSQL(CREATE_BOOKMARK_CHANNELS_TABLE_SQL);
            db.execSQL(CREATE_RECENT_CHANNELS_TABLE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.e("DATABASE", "onUpgrade()");
            db.execSQL("DROP TABLE " + SUBSCRIBED_ITEM_TABLE);
            db.execSQL("DROP TABLE " + SUBSCRIBED_ITEMS_RELATIONSHIPS_TABLE);
            db.execSQL("DROP TABLE " + BOOKMARK_FEED_TABLE);
            db.execSQL("DROP TABLE " + RECENT_FEED_TABLE);
            onCreate(db);
        }
    }
}
