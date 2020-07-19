package com.juawapps.whatstowatch.utils.mockserver

import com.juawapps.whatstowatch.utils.getJsonFromAssets
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class RequestDispatcher : Dispatcher() {

    companion object {
        const val BASE_JSON_DIRECTORY = "json"
        const val DEFAULT_FILE = "default.json"
    }

    private val overrideResponsesMap = mutableMapOf<String, String>()

    override fun dispatch(request: RecordedRequest): MockResponse {
        val requestPath = request.path.removeQuery() ?: return MockResponse().setResponseCode(404)
        val overrideResponse = overrideResponsesMap[requestPath]

        val responseBody: String? = readJson(requestPath, overrideResponse ?: DEFAULT_FILE)

        return if (responseBody != null) {
            MockResponse().setResponseCode(200).setBody(responseBody)
        } else {
            MockResponse().setResponseCode(404)
        }
    }

    fun overrideResponse(requestPath: String, jsonFile: String) {
        overrideResponsesMap[requestPath] = jsonFile
    }

    private fun readJson(requestPath: String, jsonFile: String = DEFAULT_FILE): String? {
        return getJsonFromAssets("$BASE_JSON_DIRECTORY$requestPath/$jsonFile")
    }

    private fun String?.removeQuery() = this?.substringBefore('?')
}
