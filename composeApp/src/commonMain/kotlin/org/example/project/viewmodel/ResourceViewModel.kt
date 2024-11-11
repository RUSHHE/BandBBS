package org.example.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.network.parseGetRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.project.Status
import org.example.project.model.Resource

class ResourceViewModel : ViewModel() {
    private val baseUri = "https://www.bandbbs.cn/"

    init {
        fetchData("https://www.bandbbs.cn/resources/")
    }

    // 使用MutableStateFlow来持有数据状态
    private val _dataState = MutableStateFlow<Status<List<Resource>>>(Status.Loading())
    val dataState: StateFlow<Status<List<Resource>>> = _dataState

    fun fetchData(url: String) {
        viewModelScope.launch {
            try {
                val document = Ksoup.parseGetRequest(url)

                // 更新状态为成功，并携带数据
                _dataState.value = Status.Success(
                    document.select(".structItem").map { it ->
                        Resource(
                            title = it.select(".structItem-title").select("a").text(),
                            subTitle = it.select("div.structItem-resourceTagLine").text(),
                            version = it.select(".structItem-version").select("span.u-muted")
                                .text(),
                            icon = it.select("div.structItem-iconContainer").select("a.avatar")
                                .select("img").attr("src")
                                .takeIf { it.isNotEmpty() }?.let { baseUri.plus(it) }
                                ?: "",
                            category = it.select("a.button").text(),
                            label = it.select("span.label").text(),
                            score = it.select("img[src='https://static.cloudflare.ltd/Bandbbs_CDN/styles/bandbbs_new_svg/star.svg']")
                                .parents().first()?.select("span")?.text() ?: "",
                            download = it.select("img[src='https://static.cloudflare.ltd/Bandbbs_CDN/styles/bandbbs_new_svg/download.svg']")
                                .parents().first()?.select("span")?.text() ?: "",
                            authorName = it.select("a.username").text(),
                            authorAvatar = it.select("div.node-extra-icon").select("a.avatar")
                                .select("img").attr("src")
                                .takeIf { it.isNotEmpty() }?.let { baseUri.plus(it) }
                                ?: "",
                            time = it.select("time").text(),
                        )
                    }
                )
            } catch (e: Exception) {
                // 如果发生错误，更新状态为错误，并携带异常信息
                _dataState.value = Status.Error(e.message ?: "Unknown error")
            }
        }
    }
}