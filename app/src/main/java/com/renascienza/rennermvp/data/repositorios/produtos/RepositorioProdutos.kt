package com.renascienza.rennermvp.data.repositorios.produtos

import com.renascienza.rennermvp.addOrRemove
import com.renascienza.rennermvp.data.repositorios.MockData
import com.renascienza.rennermvp.model.Oferta
import com.renascienza.rennermvp.model.Produto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

/**
 * A partir do momento em que a prova está focada nas telas e não
 * na funcionalidade, habilidades comuns a um bom repositório
 * (integridade, tolerância à falhas) foram omitidas
 * em nome da simplicidade e do prazo.
 *
 * Porém suporta concorrência básica, já que nossa arquitetura usa co-rotinas,
 * e separamos a interface de sua implementação, para deixar espaço
 * para uma abordagem melhor
 */
object RepositorioProdutosMock{

    private val favoritos = MutableStateFlow<Set<String>>(setOf())
    private val mutex = Mutex()

    suspend fun getProduto(ref: String) :Produto? {
        return withContext(Dispatchers.IO){
            MockData.produtos.find { produto ->
                produto.ref == ref
            }
        }
    }

    suspend fun getProdutos(): List<Produto> {
        return withContext(Dispatchers.IO){
            MockData.produtos
        }
    }

    suspend fun ofertas(): List<Oferta> {
        return withContext(Dispatchers.IO){
            MockData.ofertas
        }
    }

    fun favoritos(): Flow<Set<String>> {
        return favoritos
    }

    suspend fun alternarFavorito(ref: String) {
        /* apenas um pouco de resistência à concorrência, já que trabalhamos com co-rotinas
         */
        mutex.withLock {
            with(favoritos.value.toMutableSet()){
                addOrRemove(ref)
                favoritos.value = toSet()
            }
        }
    }

}