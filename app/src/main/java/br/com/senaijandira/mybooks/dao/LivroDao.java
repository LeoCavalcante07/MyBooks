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

    @Query("select * from livro where usuarioLivro = :idUsuario")
//    Livro[] selecionarTodos(int idUsuario);
    Livro[] selecionarTodos(int idUsuario);


    @Query("select * from livro where id = :idLivro")
    Livro selecionarLivroId(int idLivro);

    //saber qual o status do livro
    @Query("select statusLivro from livro")
    char[] selecionarStatus();

    //SEELCIONAR TODOS LIVROS LIDOS
    @Query("select * from livro where statusLivro = 2 and usuarioLivro = :idUsuario")
    Livro[] selecionarTodosLivrosLidos(int idUsuario);

    @Query("select * from livro where statusLivro = 1 and usuarioLivro = :idUsuario")
    Livro[] selecionarTodosLivroLer(int idUsuario);
}
