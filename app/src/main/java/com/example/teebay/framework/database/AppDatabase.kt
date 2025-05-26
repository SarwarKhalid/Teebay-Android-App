package com.example.teebay.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.teebay.framework.database.user.UserDao
import com.example.teebay.framework.database.user.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}