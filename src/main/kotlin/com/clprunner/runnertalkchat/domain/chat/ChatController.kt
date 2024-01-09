package com.clprunner.runnertalkchat.chat

import org.springframework.web.bind.annotation.RestController
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.RequestMapping

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/chat/contents")
class ChatController(private val chatService: ChatService) {

}