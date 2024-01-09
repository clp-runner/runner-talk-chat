package com.clprunner.runnertalkchat.global.config

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Value

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories(basePackages = ["com.clprunner.runnertalkchat"])
class MongoDBConfig: AbstractMongoClientConfiguration() {

    @Value("\${spring.data.mongodb.database}")
    lateinit var database: String

    @Value("\${spring.data.mongodb.host}")
    private lateinit var host: String

    @Value("\${spring.data.mongodb.port}")
    private lateinit var port: String

    @Value("\${spring.data.mongodb.username}")
    private lateinit var username: String

    @Value("\${spring.data.mongodb.password}")
    private lateinit var password: String

    override fun getDatabaseName(): String {
        return "database"
    }
    override fun mongoClient(): MongoClient {
        val connectionString = ConnectionString("mongodb://${username}:${password}@${host}:${port}/${database}?authSource=admin")

        val mongoClientSettings = MongoClientSettings
            .builder()
            .applyConnectionString(connectionString)
            .build()
        return MongoClients.create(mongoClientSettings)
    }
}