package com.example.comp1786cw2project2.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.comp1786cw2project2.feature.base.BaseViewModel
import com.example.comp1786cw2project2.local.database.UrlDao
import com.example.comp1786cw2project2.local.model.Url
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val urlDao: UrlDao
) : BaseViewModel() {
    private var _currentUrl: MutableLiveData<Url> = MutableLiveData()
    var currentUrl : LiveData<Url> = _currentUrl
    private var currentIndex = 0

    fun addUrl(url: String) {
        urlDao.addUrl(Url(url = url))
        val currentListUrl = urlDao.getListUrl()
        currentIndex = currentListUrl.size - 1
        _currentUrl.value = Url(url = url)
    }

    fun getCurrentUrl(index: Int) {
        val currentListUrl = urlDao.getListUrl()
        val range = currentListUrl.indices
        if (currentListUrl.isNotEmpty() && index in range) {
            _currentUrl.value = currentListUrl[index]
        }
    }

    fun onPreviousClicked() {
        val currentListUrl = urlDao.getListUrl()
        val range = currentListUrl.indices
        currentIndex--
        if (currentListUrl.isNotEmpty() && currentIndex in range) {
            getCurrentUrl(currentIndex)
        } else {
            currentIndex++
        }
    }

    fun onNextClicked() {
        val currentListUrl = urlDao.getListUrl()
        val range = currentListUrl.indices
        currentIndex++
        if (currentListUrl.isNotEmpty() && currentIndex in range) {
            getCurrentUrl(currentIndex)
        } else {
            currentIndex--
        }
    }
}