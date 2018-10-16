package br.com.senaijandira.mybooks.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import br.com.senaijandira.mybooks.model.Usuario;

/**
 * Created by 17259211 on 15/10/2018.
 */

@Dao
public interface UsuarioDao {

    @Insert
    void inserir(Usuario usuario);

    @Delete
    void deletar(Usuario usuario);

    @Update
    void atualizar(Usuario usuario);

    @Query("select * from usuario where email=:email and senha=:senha")
    Usuario[] selecionarLogin(String email, String senha);

}
