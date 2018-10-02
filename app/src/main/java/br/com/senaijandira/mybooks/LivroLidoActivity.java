package br.com.senaijandira.mybooks;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class LivroLidoActivity extends Activity{


    LinearLayout listaLivrosLidos;

    LivroLidoAdapter livroLidoAdapter;

    ListView listViewLivrosLidos;

    public static Livro[] livros;

    public static int[] statusLivro;


    private MyBooksDatabase myBooksDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livro_lido);

        //listaLivrosLidos = findViewById(R.id.listaLivrosLidos);

        listViewLivrosLidos = findViewById(R.id.listViewLivrosLidos);

        livroLidoAdapter = new LivroLidoAdapter(this);

        listViewLivrosLidos.setAdapter(livroLidoAdapter);


        myBooksDb = Room.databaseBuilder(getApplicationContext(), MyBooksDatabase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();


        livros = new Livro[]{};





    }

    public class LivroLidoAdapter extends ArrayAdapter<Livro>{

        public LivroLidoAdapter(Context contexto){
            super(contexto, 0, new ArrayList<Livro>());
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = convertView;


            if(view == null){
                view = LayoutInflater.from(getContext()).inflate(R.layout.livros_lido_layout, parent, false);
            }

            Livro livroLido = getItem(position);

            criarLivroLido(livroLido, view);

            return  view;
        }
    }




    /***********/





    /***********/
    @Override
    protected void onResume() {
        super.onResume();

        carregarLivrosLidos();

        //Coloca todos os livros do banco no array livros
       /* livros = myBooksDb.daoLivro().selecionarTodos();

        //array que contem o status dos livros
        statusLivro = myBooksDb.daoLivro().selecionarStatus();

        int i = 0;

        if(livros.length != 0){
            while(i <= livros.length-1){//loooping que verifica todos os livros do array

                Livro livroLido = livros[i];// a cada volta cria-se um livor que será criado como livro lido caso seu status seja igual a 2
                if(statusLivro[i] == '2'){ //caso se cumpra essa condição significa o livro deve ser carregado como livro lido
                    criarLivroLido(livroLido, listaLivrosLidos);
                }

                i++;
            }
        }*/

    }


    public void carregarLivrosLidos(){
        livros = myBooksDb.daoLivro().selecionarTodosLivrosLidos();
        livroLidoAdapter.clear();
        livroLidoAdapter.addAll(livros);

    }



    //MÉTODO TESTE
    public void criarLivroLido(final Livro livroLido, View v){//ViewGroup é onde vai ser colocado o novo livro, no caso o LinearLayout listaLivros

        ImageView imgLivroLidoCapa = v.findViewById(R.id.imgLivroLidoCapa);
        TextView txtLivroLidoTitulo = v.findViewById(R.id.txtLivroLidoTitulo);
        TextView txtLivroLidoDescricao = v.findViewById(R.id.txtLivroLidoDescricao);

        ImageView imgDeleteLivroLido = v.findViewById(R.id.imgDeleteLivro);

        imgDeleteLivroLido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                livroLido.setStatusLivro(0);
                myBooksDb.daoLivro().atualizar(livroLido);
            }
        });


        imgLivroLidoCapa.setImageBitmap(Utils.toBitmap(livroLido.getCapa()));
        txtLivroLidoTitulo.setText(livroLido.getTitulo());
        txtLivroLidoDescricao.setText(livroLido.getDescricao());

    }



    public void alert(String titulo, String mensagem){
        AlertDialog.Builder alert  = new AlertDialog.Builder(this);

        alert.setTitle(titulo);
        alert.setMessage(mensagem);

        //alert.setCancelable(false);



        alert.create().show();
    }
















    public void abrirMainActivity(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void abrirLivrosLidos(View view2) {

    }


    public void abrirLivrosLer(View view3) {
        startActivity(new Intent(this, LivroLerActivity.class));
    }
}
