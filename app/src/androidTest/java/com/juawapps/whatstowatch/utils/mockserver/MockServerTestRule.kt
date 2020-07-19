package com.juawapps.whatstowatch.utils.mockserver

import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class MockServerTestRule  : TestRule {
    companion object {
        const val DEFAULT_SERVER_PORT = 8080
    }

    lateinit var mockServer: MockWebServer
    lateinit var mockDispatcher: RequestDispatcher

    override fun apply(base: Statement, description: Description) = object : Statement() {

        override fun evaluate() {
            mockDispatcher = RequestDispatcher()
            mockServer = MockWebServer().apply {
                start(DEFAULT_SERVER_PORT)
                dispatcher = mockDispatcher
            }
            try {
                base.evaluate()
            } finally {
                mockServer.shutdown()
            }
        }
    }
}
