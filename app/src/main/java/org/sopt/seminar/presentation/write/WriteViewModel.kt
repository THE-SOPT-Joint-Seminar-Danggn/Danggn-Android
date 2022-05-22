package org.sopt.seminar.presentation.write


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WriteViewModel : ViewModel() {
    var title = MutableLiveData<String>()
    var content = MutableLiveData<String>()
    var category = MutableLiveData<String>()
    var isSuccess = MutableLiveData(false)

    var price = MutableLiveData<String>()
    var isChecked = MutableLiveData(false)

    fun completeCheck(): Boolean {
        return if (!title.value.isNullOrEmpty() && !category.value.isNullOrEmpty() && !price.value.isNullOrEmpty() &&!content.value.isNullOrEmpty()) {
            isSuccess.value = true
            true
        } else {
            isSuccess.value = false
            false
        }
    }

    fun completePriceCheck(): Boolean {
        return if (!price.value.isNullOrEmpty()) {
            isChecked.value = true
            true
        } else {
            isChecked.value = false
            false
        }
    }
}


