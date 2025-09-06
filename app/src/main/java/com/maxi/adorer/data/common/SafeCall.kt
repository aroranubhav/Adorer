package com.maxi.adorer.data.common

import androidx.sqlite.SQLiteException
import com.maxi.adorer.common.Resource
import com.maxi.adorer.data.common.Constants.DB_EXCEPTION
import java.io.IOException

suspend fun <T> safeDbCall(safeCall: suspend () -> T): Resource<T> {
    return try {
        val response = safeCall()
        Resource.Success(response)
    } catch (e: SQLiteException) {
        Resource.DatabaseError(e.message ?: DB_EXCEPTION)
    } catch (e: IOException) {
        Resource.IOError(e.message.toString())
    } catch (e: Exception) {
        Resource.UnknownError
    }
}