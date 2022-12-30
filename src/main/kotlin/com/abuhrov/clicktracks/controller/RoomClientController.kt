package com.abuhrov.clicktracks.controller

import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.server.router

@RestController
@RequestMapping("/rooms")
class RoomClientController {
    private val roomClients: MutableMap<String, MutableSet<String>> = mutableMapOf()

    @GetMapping("/{roomId}/clients")
    fun getClients(@PathVariable roomId: String) = roomClients[roomId]

    @PostMapping("/{roomId}/connect/{clientId}")
    fun connect(@PathVariable roomId: String, @PathVariable clientId: String): Set<String>? {
        if (roomId !in roomClients) roomClients[roomId] = mutableSetOf()
        roomClients[roomId]?.add(clientId)
        return roomClients[roomId]?.minus(clientId)
    }

    @PostMapping("/{roomId}/disconnect/{clientId}")
    fun disconnect(@PathVariable roomId: String, @PathVariable clientId: String) {
        roomClients[roomId]?.remove(clientId)
        if (roomClients[roomId].isNullOrEmpty()) roomClients.remove(roomId)
    }

    @Bean
    fun route() = router {
        GET("/") { ok().render("main") }
    }
}
