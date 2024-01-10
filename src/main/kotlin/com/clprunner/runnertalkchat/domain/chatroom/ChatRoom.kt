package com.clprunner.runnertalkchat.chatroom

import com.clprunner.runnertalkchat.global.document.MongoBaseDocument
import lombok.AccessLevel
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.NoArgsConstructor
import org.springframework.data.mongodb.core.mapping.Document
import java.time.ZonedDateTime

@Document(collection = "ChatRooms")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
class ChatRoom(
    private var name: String,
    private var description: String?,
    private var creatorId: String,
    private var participants: Array<String>,
    private var lastMessage: LastMessage? = null
) : MongoBaseDocument() {

    data class LastMessage(
        val content: String,
        val timestamp: ZonedDateTime
    )

    fun updateChatRoom(updatedChatRoom: ChatRoom) {
        this.name = updatedChatRoom.name
        this.description = updatedChatRoom.description
        this.creatorId = updatedChatRoom.creatorId
        this.participants = updatedChatRoom.participants
        updatedChatRoom.lastMessage?.let {
            updateLastMessage(it.content, it.timestamp)
        }
    }

    fun updateLastMessage(content: String, timestamp: ZonedDateTime) {
        this.lastMessage = LastMessage(content, timestamp)
        this.updatedAt = ZonedDateTime.now()
    }
}