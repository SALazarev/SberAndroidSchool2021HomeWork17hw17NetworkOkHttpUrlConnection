package com.salazarev.hw17networkokhttpurlconnection

import com.salazarev.hw17networkokhttpurlconnection.ClientApi.Companion.REQUEST_URL
import java.io.*
import java.lang.Exception
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class UrlConnectionApi : ClientApi() {

    @Throws(IOException::class)
    fun getHttpsUrlConnection(): HttpsURLConnection {
        val url = URL(REQUEST_URL)
        val connection = url.openConnection() as HttpsURLConnection
        connection.connectTimeout = 5000
        connection.readTimeout = 5000
        return connection
    }

    private fun readResponse(connection: HttpsURLConnection): String {
        connection.connect()
        val responseCode = connection.responseCode
        return if (responseCode != 200 && responseCode != 201) "Response code: $responseCode"
        else {
            val builder = StringBuilder()
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            var line: String? = reader.readLine()
            while (line != null) {
                builder.append(line).append("\n")
                line = reader.readLine()
            }
            builder.toString()
        }
    }

    override fun postsAdd(userId: Long, title: String, body: String): String {
        var connection: HttpsURLConnection? = null
        return try {
            connection = getHttpsUrlConnection().apply {
                addRequestProperty("Content-Type", "application/json")
                requestMethod = "POST"
                doOutput = true
            }
            BufferedWriter(OutputStreamWriter(connection.outputStream)).apply {
                write(getRequestBody(userId, title, body))
                flush()
            }
            readResponse(connection)
        } catch (e: Exception) {
            e.toString()
        } finally {
            connection?.disconnect()
        }
    }

    override fun postsList(): String {
        var connection: HttpsURLConnection? = null
        return try {
            connection = getHttpsUrlConnection()
            readResponse(connection)
        } catch (e: Exception) {
            e.toString()
        } finally {
            connection?.disconnect()
        }
    }
}