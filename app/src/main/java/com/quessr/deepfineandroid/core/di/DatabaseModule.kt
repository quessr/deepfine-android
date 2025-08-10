package com.quessr.deepfineandroid.core.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.quessr.deepfineandroid.core.secure.DbKeyStore
import com.quessr.deepfineandroid.data.local.dao.HistoryDao
import com.quessr.deepfineandroid.data.local.dao.TodoDao
import com.quessr.deepfineandroid.data.local.db.DeepfineDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        dbKeyStore: DbKeyStore
    ): DeepfineDatabase {

        net.sqlcipher.database.SQLiteDatabase.loadLibs(context)

        val passphrase = SQLiteDatabase
            .getBytes(dbKeyStore.getOrCreatePassphraseString().toCharArray())
        val factory = SupportFactory(passphrase)

        return Room.databaseBuilder(
            context,
            DeepfineDatabase::class.java,
            "deepfine_db_encrypted"
        )
            .openHelperFactory(factory)
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    try {
                        db.query("PRAGMA cipher_version;").use { c ->
                            if (c.moveToFirst()) {
                                android.util.Log.d("DB", "cipher_version=${c.getString(0)} ✅")
                            } else {
                                android.util.Log.e("DB", "cipher_version query returned no rows ❌")
                            }
                        }
                    } catch (t: Throwable) {
                        android.util.Log.e("DB", "cipher_version check failed ❌", t)
                    }
                }
            })
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideTodoDao(db: DeepfineDatabase): TodoDao = db.todoDao()

    @Provides
    fun provideHistoryDao(db: DeepfineDatabase): HistoryDao = db.historyDao()
}