package br.com.senaijandira.mybooks.fragments;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.senaijandira.mybooks.Login;
import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.Utils;
import br.com.senaijandira.mybooks.adapter.LivroLidoAdapter;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;
import br.com.senaijandira.mybooks.model.Usuario;

/**
 * Created by 17259211 on 08/10/2018.
 */

public class FragLivroLido extends Fragment {

    MyBooksDatabase myBooksDb;
    ListView listViewLivros;
    LivroLidoAdapter livroAdapter;

    Usuario usuarioLogado;

    public static Livro[] livros;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v;

        v = inflater.inflate(R.layout.fragment_livro, container, false);

        myBooksDb = Room.databaseBuilder(getContext(), MyBooksDatabase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();


        listViewLivros = v.findViewById(R.id.listViewLivros);
        livroAdapter = new LivroLidoAdapter(getContext(), myBooksDb);
        listViewLivros.setAdapter(livroAdapter);

        usuarioLogado = Login.usuarioLogado;

        //listaLivros = findViewById(R.id.listaLivros);


        //Criação livro fake

        livros = new Livro[]{};


        return v;
    }


    @Override
    public void onResume() {
        super.onResume();

        livros = myBooksDb.daoLivro().selecionarTodosLivrosLidos(usuarioLogado.getId());
        livroAdapter.clear();
        livroAdapter.addAll(livros);
    }
}
