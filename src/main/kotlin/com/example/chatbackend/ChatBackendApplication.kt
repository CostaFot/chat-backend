package com.example.chatbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity
import org.springframework.cloud.gcp.data.datastore.repository.DatastoreRepository
import org.springframework.data.annotation.Id
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class ChatBackendApplication

fun main(args: Array<String>) {
    runApplication<ChatBackendApplication>(*args)
    println("Running")
}

@Entity
data class Photo(
    @Id
    var id: String? = null,
    var uri: String? = null,
    var label: String? = null
)

@Repository
interface PhotoRepository : DatastoreRepository<Photo, String>

@RestController
class HelloController(
    private val photoRepository: PhotoRepository
) {

    @GetMapping("/")
    fun hello() = "Hello!"

    @PostMapping("/photo")
    fun savePhoto(@RequestBody photo: Photo): String {
        return try {
            photoRepository.save(photo)
            println("Successfully added photo")
            "All good"
        } catch (e: Exception) {
            println(e.localizedMessage)
            e.localizedMessage
        }

    }
}