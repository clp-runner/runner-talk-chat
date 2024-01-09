package com.clprunner.runnertalkchat.chat

import com.clprunner.runnertalkchat.global.document.MongoBaseDocument
import lombok.*
import org.jetbrains.annotations.NotNull
import org.springframework.data.mongodb.core.mapping.Document
import java.sql.Timestamp

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Getter
@Document(collection = "ChatContents")
data class Chat(
    @NotNull
    val chatroom: Long,
    @NotNull
    val type: String,
    @NotNull
    val content: String,
    @NotNull
    val sender: Short,
    val readBy: Array<Short>,

    @NotNull
    val timestamp: Timestamp,
    val dbSave: DBSave
) : MongoBaseDocument() {
    data class DBSave(
        val publish: Boolean,
        val subscribe: Boolean
    )
}