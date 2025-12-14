package com.hardi.noteservice.config

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate

//@Configuration
//class MongoConfig {
//
//    @Value($$"${mongo.db.url}")
//    private lateinit var mongoUrl: String
//
//    @Value($$"${mongo.db.name}")
//    private lateinit var mongoDB: String
//
//    @Bean
//    fun mongoClient(): MongoClient = MongoClients.create(mongoUrl)
//
//    @Bean fun mongoTemplate(mongoClient: MongoClient) = MongoTemplate(mongoClient, mongoDB)
//}