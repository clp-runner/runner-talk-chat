package com.clprunner.runnertalkchat.chatroom

import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/chat/rooms")
class ChatRoomController(private val chatRoomService: ChatRoomService) {

}
