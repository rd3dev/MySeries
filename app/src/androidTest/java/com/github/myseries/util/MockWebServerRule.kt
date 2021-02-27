package com.github.myseries.util

import com.github.myseries.util.di.FakeCompositionRoot
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.rules.ExternalResource

class MockWebServerRule : ExternalResource() {
    private lateinit var server: MockWebServer

    override fun before() {
        super.before()
        server = MockWebServer()
        FakeCompositionRoot.BASE_URL = server.url("/").toString()
    }

    override fun after() {
        server.shutdown()
        super.after()
    }

    fun arrangeResponse(code: Int, fileName: String = "", serverUrl: String = "") {
        val response = buildResponse(code, fileName)
        if (serverUrl.isNotEmpty()) {
            server.dispatcher = object : Dispatcher() {
                override fun dispatch(request: RecordedRequest): MockResponse {
                    if (request.path == "/search/shows?q=girls") {
                        return response
                    }
                    throw Exception("server url not called")
                }
            }
        } else {
            server.enqueue(
                response
            )
        }
    }

    private fun buildResponse(code: Int, fileName: String): MockResponse {
        return MockResponse().apply {
            setResponseCode(code)
            setBody(
                if (fileName.isEmpty()) {
                    ""
                } else {
                    MockResponseFileReader.readResource(fileName)
                }
            )
        }
    }
}
