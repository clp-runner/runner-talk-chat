package com.clprunner.runnertalkchat.global.document

import lombok.Getter
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import java.time.ZonedDateTime

@Getter
abstract class MongoBaseDocument(
    @Id var id: String? = null,
    @CreatedDate var createdAt: ZonedDateTime? = null,
    @LastModifiedDate var updatedAt: ZonedDateTime? = null
)
