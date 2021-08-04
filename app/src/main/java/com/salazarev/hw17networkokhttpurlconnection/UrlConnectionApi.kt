package com.salazarev.hw17networkokhttpurlconnection

import java.io.*
import java.lang.Exception
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class UrlConnectionApi : ClientApi() {

    companion object {
        const val OK_CODE = 200
        const val CREATED_CODE = 201
    }

    @Throws(IOException::class)
    fun getHttpsUrlConnection(): HttpsURLConnection {
        val url = URL(REQUEST_URL)
        val connection = url.openConnection() as HttpsURLConnection
        connection.connectTimeout = 5000
        connection.readTimeout = 5000
        return connection
    }

    override fun productAdd(title: String, price: Double, description: String): String {
        var connection: HttpsURLConnection? = null
        return try {
            connection = getHttpsUrlConnection().apply {
                addRequestProperty("Content-Type", "application/json")
                requestMethod = "POST"
                doOutput = true
            }
            BufferedWriter(OutputStreamWriter(connection.outputStream)).apply {
                write(getRequestBody(title, price, description))
                flush()
            }
            readResponse(connection)
        } catch (e: Exception) {
            e.toString()
        } finally {
            connection?.disconnect()
        }
    }

    override fun productsList(): String {
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

    private fun readResponse(connection: HttpsURLConnection): String {
        connection.connect()
        val responseCode = connection.responseCode
        return if (responseCode != OK_CODE && responseCode != CREATED_CODE)
            "Response code: $responseCode"
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
}