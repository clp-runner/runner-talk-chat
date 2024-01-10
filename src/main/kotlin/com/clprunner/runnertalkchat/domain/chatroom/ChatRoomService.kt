package com.clprunner.runnertalkchat.chatroom

import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@RequiredArgsConstructor
@Service
class ChatRoomService(private val chatRoomRepository: ChatRoomRepository) {

    // 채팅방 단일 조회
    fun getChatRoom(id: String): ChatRoom {
        return chatRoomRepository.findById(id).get()
    }

    // 채팅방 목록 조회
    fun getChatRooms(): List<ChatRoom> {
        return chatRoomRepository.findAll()
    }

    // 채팅방 생성
    fun createChatRoom(chatRoom: ChatRoom): ChatRoom {
        return chatRoomRepository.save(chatRoom)
    }

    // 채팅방 수정
    fun updateChatRoom(id: String, updatedChatRoomData: ChatRoom): ChatRoom {
        val chatRoom = chatRoomRepository.findById(id)
            .orElseThrow { NoSuchElementException("ChatRoom with id $id not found") }

        chatRoom.updateChatRoom(updatedChatRoomData)
        return chatRoomRepository.save(chatRoom)

    }

    // 채팅방 삭제
    fun deleteChatRoom(id: String) {
        chatRoomRepository.deleteById(id)
    }
}
