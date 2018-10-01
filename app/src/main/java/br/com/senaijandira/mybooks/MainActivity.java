package br.com.senaijandira.mybooks;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity {

    LivrosAdapter livrosAdapter;

    ListView listViewLivros;

    public static Livro[] livros;

    //Variavel de acesso ao Bnaco
    private MyBooksDatabase myBooksDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Criado a instancia do banco de dados
        myBooksDb = Room.databaseBuilder(getApplicationContext(), MyBooksDatabase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();


        listViewLivros = findViewById(R.id.listViewLivros);
        livrosAdapter = new LivrosAdapter(this);
        listViewLivros.setAdapter(livrosAdapter);

        //listaLivros = findViewById(R.id.listaLivros);






        //Criação livro fake

        livros = new Livro[]{
/*
                new Livro(1, Utils.toByteArray(getResources(), R.drawable.pequeno_principe),
                        "O pequeno principe", getString(R.string.pequeno_principe)),

                new Livro(2, Utils.toByteArray(getResources(), R.drawable.cinquenta_tons_cinza),
                        "Cinquenta tons de cinza", getString(R.string.pequeno_principe)),

                new Livro(3, Utils.toByteArray(getResources(), R.drawable.kotlin_android),
                        "Kotlin com android", getString(R.string.pequeno_principe))*/


        };


    }


    public void carregarLivros(){
        //Aqui faz um select no banco
        livros = myBooksDb.daoLivro().selecionarTodos();
        livrosAdapter.clear();
        livrosAdapter.addAll(livros);

    }



    public class LivrosAdapter extends ArrayAdapter<Livro>{

        public LivrosAdapter(Context context){
            super(context, 0, new ArrayList<Livro>());
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = convertView;


            if(view == null){
                view = LayoutInflater.from(getContext()).inflate(R.layout.livro_layout, parent, false); //CARREGA O CONTEUDO DO TEMPLATE DOS LIVROS NA VARIAVEL view
            }

            Livro livro = getItem(position);

            criarLivro(livro, view);

            return view;
        }
    }


    public void criarLivro(final Livro livro, View v){

        ImageView imgLivroCapa = v.findViewById(R.id.imgLivroCapa);
        TextView txtLivroTitulo = v.findViewById(R.id.txtLivroTitulo);
        TextView txtLivroDescricao = v.findViewById(R.id.txtLivroDescricao);
        TextView txtLivrolido = v.findViewById(R.id.txtLivroLido);
        TextView txtLivrosParaler = v.findViewById(R.id.txtLivrosParaLer);

        ImageView imgDeleteLivro = v.findViewById(R.id.imgDeleteLivro);


        imgDeleteLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletarLivro(livro);
            }
        });



        //click listener para adc Livro para ler
        txtLivrosParaler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atualizarStatusLivro(livro, 1);
            }
        });


        //click listener para adc Livro lido
        txtLivrolido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                atualizarStatusLivro(livro, 2);
            }
        });




        imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa()));
        txtLivroTitulo.setText(livro.getTitulo());
        txtLivroDescricao.setText(livro.getDescricao());
    }


    @Override
    protected void onResume() {
        super.onResume();


        carregarLivros();



       //listaLivros.removeAllViews();

        //a cada livro no array Livros[], cria um novo livro
       /* for(Livro livro : livros){
            criarLivro(livro, listaLivros);
        }*/

    }

    // para apagar o livro precisa do objeto livro e do template que contem as inf do livro  que esta contido na variavel v
    private void deletarLivro(Livro livro){

        //deletar livro do banco
        myBooksDb.daoLivro().deletar(livro);

        livrosAdapter.remove(livro);
        //remover o item (template onde está as informações do livro) da tela
        //listaLivros.removeView(v);
        //listViewLivros.removeView(v);



    }

    //MÉTODO QUE ATUALIZA O STATUS DO LIVRO, PARA SABERMOS SE ELE ESTA PARA SER LIDO OU SE JÁ FOI LIDO
    private void atualizarStatusLivro(Livro livro, int opt){

        if(opt == 1){
            livro.setStatusLivro(1);//com status 1 sabemos que o livro está para ser lido
        }else if(opt == 2){
            livro.setStatusLivro(2);//com status 2 sabemos que o livro ja foi lido
        }

        myBooksDb.daoLivro().atualizar(livro);

    }


   /* public void criarLivro(final Livro livro, ViewGroup root){//ViewGroup é onde vai ser colocado o novo livro, no caso o LinearLayout listaLivros



        final View v = LayoutInflater.from(this).inflate(R.layout.livro_layout, root,  false);//carrega o template livro_layout na variavel v

        ImageView imgLivroCapa = v.findViewById(R.id.imgLivroCapa);
        TextView txtLivroTitulo = v.findViewById(R.id.txtLivroTitulo);
        TextView txtLivroDescricao = v.findViewById(R.id.txtLivroDescricao);

        ImageView imgDeleteLivro = v.findViewById(R.id.imgDeleteLivro);
        Button btnLivroLido = v.findViewById(R.id.btnLivroLido);


        //ASSIM QUE UM LIVRO FOR CRIADO ELE JÁ VEM COM OS METODOS ONCLICK PARA CHAMAR O ATUALIZA E O DELETAR
        imgDeleteLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletarLivro(livro, v);
            }
        });


        btnLivroLido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizarStatusLivro(livro);
            }
        });


        imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa()));
        txtLivroTitulo.setText(livro.getTitulo());
        txtLivroDescricao.setText(livro.getDescricao());

        final int idLivro = livro.getId();


        root.addView(v);
    }*/



    public void abrirCadastro(View view) {
        startActivity(new Intent(this, CadastroActivity.class));

    }


    public void abrirMainActivity(View view) {

    }

    public void abrirLivrosLidos(View view2) {
        startActivity(new Intent(this, LivroLidoActivity.class));
    }


    public void abrirLivrosLer(View view3) {
        startActivity(new Intent(this, LivroLerActivity.class));
    }

}
