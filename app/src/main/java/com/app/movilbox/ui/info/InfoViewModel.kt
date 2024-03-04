package com.app.movilbox.ui.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.movilbox.domain.models.ProductModel
import com.app.movilbox.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    private var _stateProduct = MutableStateFlow<ProductModel>(ProductModel())
    val stateProduct: StateFlow<ProductModel> = _stateProduct

    /**
     * Get product for id
     */
    fun getProductId(id: String) {
        viewModelScope.launch {
            val result: ProductModel = withContext(Dispatchers.IO) {
                productRepository.getProduct(id)
            }
            _stateProduct.value = result
        }
    }
}