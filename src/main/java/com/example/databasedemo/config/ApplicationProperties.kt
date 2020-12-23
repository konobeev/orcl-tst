package com.example.databasedemo.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.time.Duration

@ConstructorBinding
@ConfigurationProperties(prefix = "application")
data class ApplicationProperties(
        val region: String,
        val endpoint: String,
        val ttl: Duration
)