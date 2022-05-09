package com.renascienza.rennermvp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.renascienza.rennermvp.data.repositorios.categorias.RepositorioCategoriasMock
import com.renascienza.rennermvp.data.repositorios.produtos.RepositorioProdutosMock
import com.renascienza.rennermvp.model.Categoria
import com.renascienza.rennermvp.model.Oferta
import com.renascienza.rennermvp.model.Produto
import com.renascienza.rennermvp.update
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed interface HomeUiState {

    val isLoading       :Boolean
    val searchInput     :String

    data class NoData(
        override val isLoading      :Boolean,
        override val searchInput    :String
    ) :HomeUiState

    data class HasData(
        val produtos                :List<Produto> = emptyList(),
        val categorias              :List<Categoria> = emptyList(),
        val ofertas                 :List<Oferta> = emptyList(),
        val favoritos               :Set<String> = emptySet<String>(),
        override val isLoading      :Boolean,
        override val searchInput    :String
    ) :HomeUiState
}

private data class HomeViewModelState(
    val produtos            :List<Produto> = emptyList(),
    val ofertas             :List<Oferta> = emptyList(),
    val categorias          :List<Categoria> = emptyList(),
    val favoritos           :Set<String>,
    val isLoading           :Boolean = false,
    val searchInput         :String = "",
) {
    fun toUiState(): HomeUiState =
        if (produtos.isEmpty()) {
            HomeUiState.NoData(
                isLoading   = isLoading,
                searchInput = searchInput
            )
        } else {
            HomeUiState.HasData(
                produtos        = produtos,
                categorias      = categorias,
                ofertas         = ofertas,
                favoritos       = favoritos,
                isLoading       = isLoading,
                searchInput     = searchInput
            )
        }
}

@OptIn(InternalCoroutinesApi::class)
class HomeViewModel : ViewModel(){

    private val viewModelState = MutableStateFlow(HomeViewModelState(isLoading = true, favoritos = emptySet()))

    init {
        refreshData()

        viewModelScope.launch {
            RepositorioProdutosMock.favoritos().collect { favoritos ->
                viewModelState.update { it.copy(favoritos = favoritos) }
            }
        }
    }

    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    fun produtosSnapshot() :List<Produto>{
        return if(uiState.value is HomeUiState.HasData){
            (uiState.value as HomeUiState.HasData).produtos.toList()
        }else{
            emptyList()
        }
    }

    fun ofertasSnapshot() :List<Oferta>{
        return if(uiState.value is HomeUiState.HasData){
            (uiState.value as HomeUiState.HasData).ofertas.toList()
        }else{
            emptyList()
        }
    }

    fun categoriasSnapshot() :List<Categoria>{
        return if(uiState.value is HomeUiState.HasData){
            (uiState.value as HomeUiState.HasData).categorias.toList()
        }else{
            emptyList()
        }
    }

    fun refreshData(){
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val produtos = RepositorioProdutosMock.getProdutos()
            val ofertas = RepositorioProdutosMock.ofertas()
            val categorias = RepositorioCategoriasMock.categorias()
            viewModelState.update { state ->
                    state.copy(produtos = produtos, ofertas = ofertas, categorias = categorias, isLoading = false)
            }
        }
    }

    fun favorito(ref: String) {
        val current = favoritos()
        if (current.contains(ref)) {
            viewModelState.update { it.copy(favoritos = current - ref) }
        } else {
            viewModelState.update { it.copy(favoritos = current + ref) }
        }
    }

    fun favoritos() = viewModelState.value.favoritos

    companion object {
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel() as T
            }
        }
    }

}
