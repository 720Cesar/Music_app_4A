package com.example.music_app4a.model

// ARCHIVO PARA VERIFICAR SI OBTENEMOS UNA RESPUESTA DEL API

data class ITunesResponse(

    val resultCount: Int,
    val results: List<Album>

)
