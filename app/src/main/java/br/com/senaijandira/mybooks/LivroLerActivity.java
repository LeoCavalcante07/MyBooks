package br.com.senaijandira.mybooks;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import br.com.senaijandira.mybooks.adapter.LivroLerAdapter;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

/**
 * Created by 17259211 on 01/10/2018.
 */

public class LivroLerActivity extends Activity {

    MyBooksDatabase myBooksDb;
    Livro[] livros;
    ListView listViewLivroLer;

    LivroLerAdapter livroLerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livro_ler);

        myBooksDb = Room.databaseBuilder(getApplicationContext(), MyBooksDatabase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();

        listViewLivroLer = findViewById(R.id.listViewLivrosLer);
        livroLerAdapter = new LivroLerAdapter(this, myBooksDb);
        listViewLivroLer.setAdapter(livroLerAdapter);

        livros = new Livro[]{};


    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarLivroParaLer();

    }

    public void carregarLivroParaLer(){
        livros = myBooksDb.daoLivro().selecionarTodosLivroLer();
        livroLerAdapter.clear();
        livroLerAdapter.addAll(livros);
    }












    public void abrirMainActivity(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void abrirLivrosLidos(View view2) {
        startActivity(new Intent(this, LivroLidoActivity.class));
    }


    public void abrirLivrosLer(View view3) {

    }

}
