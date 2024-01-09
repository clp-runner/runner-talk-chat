package com.clprunner.runnertalkchat.chat

import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@RequiredArgsConstructor
@Service
class ChatService(private val chatRepository: ChatRepository) {

}