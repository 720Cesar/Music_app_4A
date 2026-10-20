package com.example.music_app4a

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION,
){

    companion object{
        private const val DATABASE_NAME = "musica.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_USUARIOS = "usuarios"
        private const val COL_ID = "id"
        private const val COL_NOMBRE = "nombre"
        private const val COL_CORREO = "correo"
        private const val COL_PASSWORD = "password"

    }

    // MÉTODO PARA CREAR LA BASE DE DATOS
    override fun onCreate(db: SQLiteDatabase?) {
        val crearTablaUsuarios = """
            CREATE TABLE $TABLE_USUARIOS (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NOMBRE TEXT NOT NULL,
                $COL_CORREO TEXT NOT NULL UNIQUE,
                $COL_PASSWORD TEXT NOT NULL
            )
        """.trimIndent()


        // Crear tabla de SQL
        db?.execSQL(crearTablaUsuarios)

    }

    // MÉTODO PARA ACTUALIZAR LA BASE DE DATOS
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIOS")
        onCreate(db)
    }

    // MÉTODO PARA INSERTAR REGISTRO
    fun registrarUsuario(nombre : String, correo : String, password : String) : Boolean{

        // Crear variable para que la base de datos se pueda modificar
        val db = writableDatabase

        // Crear variable para recibir los datos que escribe el usuario
        val datos = ContentValues().apply {
            put(COL_NOMBRE, nombre)
            put(COL_CORREO, correo)
            put(COL_PASSWORD, password)
        }

        // Insertar datos en la tabla
        val resultado = db.insert(TABLE_USUARIOS, null, datos)

        // Cerrar conexión con la BD
        db.close()

        // Comparar el valor del resultado de la inserción y regresarlo como Boolean
        return resultado != -1L

    }

    // MÉTODO PARA CONSULTAR A UN USUARIO
    fun iniciarSesion(correo : String, password: String) : Boolean{

        val db = readableDatabase

        val query_sql = """
            SELECT * FROM $TABLE_USUARIOS
            WHERE $COL_CORREO = ? AND $COL_PASSWORD = ?
        """.trimIndent()

        // Crear cursor para recorrer la información
        val cursor = db.rawQuery(
            query_sql, arrayOf(correo, password)
        )

        // Crear variable de tipo Bool que permita saber si el cursor encontró un registro
        val usuarioEncontrado = cursor.count > 0

        cursor.close()
        db.close()


        return usuarioEncontrado


    }

}