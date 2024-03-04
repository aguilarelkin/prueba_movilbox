package com.app.movilbox.ui.product

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
class ProductViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {
    private var _stateList = MutableStateFlow<List<ProductModel>>(emptyList())
    val stateList: StateFlow<List<ProductModel>> = _stateList

    private var _stateCategories = MutableStateFlow<List<String>>(emptyList())
    val stateCategories: StateFlow<List<String>> = _stateCategories

    private var _stateQuery = MutableStateFlow<String>("")
    val stateQuery: StateFlow<String> = _stateQuery

    private var _stateFilter = MutableStateFlow<Boolean>(false)
    val stateFilter: StateFlow<Boolean> = _stateFilter

    private var _stateDelete = MutableStateFlow<Boolean>(false)
    val stateDelete: StateFlow<Boolean> = _stateDelete

    fun onChangedQuery(query: String) {
        _stateQuery.value = query
    }

    fun onChangedDelete() {
        _stateDelete.value = false
        _stateFilter.value = false
        _stateQuery.value = ""
    }

    /**
     * get list products
     */
    fun getProduct() {
        viewModelScope.launch {
            val result: List<ProductModel> = withContext(Dispatchers.IO) {
                productRepository.getProducts()
            }
            if (result.isNotEmpty()) {
                _stateList.value = result
            }
        }
    }

    /**
     * Get List categories
     */
    fun getCategories() {
        viewModelScope.launch {
            val result: List<String> = withContext(Dispatchers.IO) {
                productRepository.getCategories()
            }
            if (result.isNotEmpty()) {
                _stateCategories.value = result
            }

        }
    }

    /**
     * Search product
     */
    fun searchProduct(data: String) {
        viewModelScope.launch {
            val result: List<ProductModel> = withContext(Dispatchers.IO) {
                productRepository.searchProduct(data)
            }
            if (result.isNotEmpty()) {
                _stateList.value = result
            } else {
                _stateList.value = emptyList()
            }
        }
    }

    /**
     * Delete product id
     */
    fun deleteProductId(id: String) {
        viewModelScope.launch {
            val result: ProductModel = withContext(Dispatchers.IO) {
                productRepository.deleteProduct(id)
            }
            if (result.title.isNotEmpty()) {
                _stateDelete.value = true
                _stateList.value = emptyList()
            }
        }
    }

    /**
     * Filter products of the list of productmodel
     */
    fun filterProducts(filter: String) {
        _stateFilter.value = true
        _stateList.value = when (filter) {
            "Precio" -> stateList.value.sortedBy { it.price }
            "Descuento" -> stateList.value.sortedBy { it.discountPercentage }
            "Categoria" -> stateList.value.sortedBy { it.category }
            "Stock" -> stateList.value.sortedBy { it.stock }
            "Marca" -> stateList.value.sortedBy { it.brand }
            "Rating" -> stateList.value.sortedBy { it.rating }
            else -> {
                _stateFilter.value = false
                stateList.value.sortedByDescending { it.rating }
            }
        }
    }

}