package com.abuhrov.my_band.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val text: String
)
