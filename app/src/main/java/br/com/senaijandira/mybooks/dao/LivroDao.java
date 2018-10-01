package br.com.senaijandira.mybooks.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import br.com.senaijandira.mybooks.model.Livro;

@Dao  //indica que a classe é um Dao
public interface LivroDao {

    @Insert
    void inserir(Livro livro);

    @Update
    void atualizar(Livro livro);


    @Delete
    void deletar(Livro livro);

    @Query("select * from livro")
    Livro[] selecionarTodos();

    //saber qual o status do livro
    @Query("select statusLivro from livro")
    char[] selecionarStatus();

    //SEELCIONAR TODOS LIVROS LIDOS
    @Query("select * from livro where statusLivro = 2")
    Livro[] selecionarTodosLivrosLidos();

    @Query("select * from livro where statusLivro = 1")
    Livro[] selecionarTodosLivroLer();
}
