package br.com.senaijandira.mybooks;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class LivroLidoActivity extends Activity{


    LinearLayout listaLivrosLidos;
    public static Livro[] livros;

    public static char[] statusLivro;


    private MyBooksDatabase myBooksDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livro_lido);

        listaLivrosLidos = findViewById(R.id.listaLivrosLidos);

        myBooksDb = Room.databaseBuilder(getApplicationContext(), MyBooksDatabase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();


        livros = new Livro[]{};





    }

    @Override
    protected void onResume() {
        super.onResume();

        //Coloca todos os livros do banco no array livros
        livros = myBooksDb.daoLivro().selecionarTodos();

        //array que contem o status dos livros
        statusLivro = myBooksDb.daoLivro().selecionarStatus();

        int i = 0;

        while(i <= livros.length){//loooping que verifica todos os livros do array

            Livro livroLido = livros[i];// a cada volta cria-se um livor que será criado como livro lido caso seu status seja igual a 2
            if(statusLivro.equals("2")){ //caso se cumpra essa condição significa o livro deve ser carregado como livro lido
                criarLivroLido(livroLido, listaLivrosLidos);
            }
        }
    }




    //MÉTODO TESTE
    public void criarLivroLido(final Livro livroLido, ViewGroup root){//ViewGroup é onde vai ser colocado o novo livro, no caso o LinearLayout listaLivros



        final View v = LayoutInflater.from(this).inflate(R.layout.livros_lido_layout, root,  false);//carrega o template livro_layout na variavel v

        ImageView imgLivroCapa = v.findViewById(R.id.imgLivroCapa);
        TextView txtLivroTitulo = v.findViewById(R.id.txtLivroTitulo);
        TextView txtLivroDescricao = v.findViewById(R.id.txtLivroDescricao);

        imgLivroCapa.setImageBitmap(Utils.toBitmap(livroLido.getCapa()));
        txtLivroTitulo.setText(livroLido.getTitulo());
        txtLivroDescricao.setText(livroLido.getDescricao());

        final int idLivro = livroLido.getId();


        root.addView(v);
    }
}
