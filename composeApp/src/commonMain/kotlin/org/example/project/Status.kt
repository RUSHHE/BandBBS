package org.example.project

// 定义资源状态sealed class来表示加载状态
sealed class Status<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : Status<T>(data)
    class Success<T>(data: T) : Status<T>(data)
    class Error<T>(message: String, data: T? = null) : Status<T>(data, message)
}