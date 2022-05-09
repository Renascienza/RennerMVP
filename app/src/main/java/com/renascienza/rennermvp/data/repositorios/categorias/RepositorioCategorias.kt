package com.renascienza.rennermvp.data.repositorios.categorias

import com.renascienza.rennermvp.data.repositorios.MockData
import com.renascienza.rennermvp.model.Categoria
import kotlinx.coroutines.Dispatchers
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

object RepositorioCategoriasMock{

    suspend fun categorias() :List<Categoria>{
        return withContext(Dispatchers.IO){
            MockData.categorias
        }
    }

    suspend fun getCategoria(codigo: String) :Categoria{
        return withContext(Dispatchers.IO){
            MockData.categorias.first { categoria ->
                categoria.codigo == codigo
            }
        }
    }

}