package com.clprunner.runnertalkchat.chatroom

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/chat/rooms")
@OpenAPIDefinition(info = Info(title = "Chat Room API", version = "v1", description = "Chat Room API v1"))
class ChatRoomController(private val chatRoomService: ChatRoomService) {

    // 채팅방 단일 조회
    @GetMapping("/{id}")
    fun getChatRoom(@PathVariable id: String): ResponseEntity<ChatRoom> {
        val chatRoom = chatRoomService.getChatRoom(id)

        return ResponseEntity.ok().body(chatRoom)
    }

    // 채팅방 목록 조회
    @GetMapping
    fun getChatRooms(): ResponseEntity<List<ChatRoom>> {
        val chatRooms = chatRoomService.getChatRooms()

        return ResponseEntity.ok().body(chatRooms)
    }

    // 채팅방 생성
    @PostMapping
    @ResponseBody
    fun createChatRoom(chatRoom: ChatRoom): ResponseEntity<ChatRoom> {
        val createdChatRoom = chatRoomService.createChatRoom(chatRoom)

        return ResponseEntity.ok().body(createdChatRoom)
    }


    // 채팅방 수정
    @PutMapping("/{id}")
    @ResponseBody
    fun updateChatRoom(@PathVariable id: String, chatRoom: ChatRoom): ResponseEntity<ChatRoom> {
        val updatedChatRoom = chatRoomService.updateChatRoom(id, chatRoom)

        return ResponseEntity.ok().body(updatedChatRoom)
    }

    // 채팅방 삭제
    @DeleteMapping("/{id}")
    fun deleteChatRoom(@PathVariable id: String): ResponseEntity<Unit> {
        chatRoomService.deleteChatRoom(id)

        return ResponseEntity.noContent().build()
    }
}
