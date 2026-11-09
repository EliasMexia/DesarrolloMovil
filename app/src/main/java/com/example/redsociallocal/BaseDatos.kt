package com.example.redsociallocal

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "publicaciones")
data class Publicacion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val descripcion: String,
    val fotoUri: String,
    val fecha: String
)

@Dao
interface PublicacionDao {
    @Query("SELECT * FROM publicaciones ORDER BY id DESC")
    fun obtenerTodas(): Flow<List<Publicacion>>

    @Insert
    suspend fun insertar(publicacion: Publicacion)
}

@Database(entities = [Publicacion::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun publicacionDao(): PublicacionDao
}