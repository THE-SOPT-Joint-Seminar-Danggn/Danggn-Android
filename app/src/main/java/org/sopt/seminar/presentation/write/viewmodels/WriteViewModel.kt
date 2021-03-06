package org.sopt.seminar.presentation.write.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.seminar.presentation.write.screens.PictureData

class WriteViewModel : ViewModel() {
    var title = MutableLiveData<String>()
    var content = MutableLiveData<String>()
    var category = MutableLiveData<String>()
    var isSuccess = MutableLiveData(false)

    var imageCount = MutableLiveData(0)

    var price = MutableLiveData<String>()
    var isChecked = MutableLiveData(false)

    var imageList = MutableLiveData<List<PictureData>>()

    fun completeCheck() {
        return if (!title.value.isNullOrEmpty() && !category.value.isNullOrEmpty() && !price.value.isNullOrEmpty() && !content.value.isNullOrEmpty()) {
            isSuccess.value = true
        } else {
            isSuccess.value = false
        }
    }

    fun completePriceCheck() {
        return if (!price.value.isNullOrEmpty()) {
            isChecked.value = true
        } else {
            isChecked.value = false
        }
    }
    fun decreaseCount(){
        imageCount.value = imageCount.value?.minus(1)
    }

    fun getImageCount(): LiveData<Int> = imageCount
}


