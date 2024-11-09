package org.example.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bandbbs.http.model.Block
import com.example.bandbbs.http.model.Extra
import com.example.bandbbs.http.model.Node
import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.network.parseGetRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    init {
        fetchData("https://www.bandbbs.cn/")
    }

    // 使用MutableStateFlow来持有数据状态
    private val _dataState = MutableStateFlow<Resource<List<Block>>>(Resource.Loading())
    val dataState: StateFlow<Resource<List<Block>>> = _dataState

    fun fetchData(url: String) {
        viewModelScope.launch {
            try {
                val document = Ksoup.parseGetRequest(url)

                // 更新状态为成功，并携带数据
                _dataState.value = Resource.Success(
                    document.select(".block--category").map { it ->
                        Block(
                            name = it.select("h2").text(),
                            node = it.select("div.node-body").map { it1 ->
                                Node(
                                    description = it1.select("[data-shortcut=node-description]")
                                        .text(),
                                    // 这里应该选择正确的子元素来获取post和reply
                                    post = it1.select("div.node-stats span").first()?.text() ?: "",
                                    reply = it1.select("div.node-stats span").last()?.text() ?: "",
                                    extra = Extra(
                                        title = it1.select("div.node-extra-row").last()?.text()
                                            ?: "",
                                        avatar = it1.select(".avatar").select("img").attr("src")
                                            .takeIf { it.isNotEmpty() }?.let { url.plus(it) }
                                            ?: "",
                                        username = it1.select(".username").text(),
                                        time = it1.select(".node-extra-date").text(),
                                    )
                                )
                            }
                        )
                    }
                )
            } catch (e: Exception) {
                // 如果发生错误，更新状态为错误，并携带异常信息
                _dataState.value = Resource.Error(e.message ?: "Unknown error")
            }
        }
    }
}

// 定义资源状态sealed class来表示加载状态
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}