package br.com.senaijandira.mybooks;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class MainActivity extends AppCompatActivity {

    LinearLayout listaLivros;



    public static Livro[] livros;

    //Variavel de acesso ao Bnaco
    private MyBooksDatabase myBooksDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Criado a instancia do banco de dados
        myBooksDb = Room.databaseBuilder(getApplicationContext(), MyBooksDatabase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();



        listaLivros = findViewById(R.id.listaLivros);






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


    @Override
    protected void onResume() {
        super.onResume();

        //Aqui faz um select no banco
        livros = myBooksDb.daoLivro().selecionarTodos();

        listaLivros.removeAllViews();

        //a cada livro no array Livros[], cria um novo livro
        for(Livro livro : livros){
            criarLivro(livro, listaLivros);
        }

    }

    // para apagar o livro precisa do objeto livro e do template que contem as inf do livro  que esta contido na variavel v
    private void deletarLivro(Livro livro, View v){

        //deletar livro do banco
        myBooksDb.daoLivro().deletar(livro);

        //remover o item (template onde está as informações do livro) da tela
        listaLivros.removeView(v);



    }

    //MÉTODO QUE ATUALIZA O STATUS DO LIVRO, PARA SABERMOS SE ELE ESTA PARA SER LIDO OU SE JÁ FOI LIDO
    private void atualizarStatusLivro(Livro livro){

        livro.setStatusLivro('2');//com status 2 sabemos que o livro ja foi lido

        myBooksDb.daoLivro().atualizar(livro);
        System.out.print(livro.getStatusLivro());

    }


    public void criarLivro(final Livro livro, ViewGroup root){//ViewGroup é onde vai ser colocado o novo livro, no caso o LinearLayout listaLivros



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
    }

    public void abrirCadastro(View view) {
        startActivity(new Intent(this, CadastroActivity.class));

    }


    public void abrirLivrosLidos(View view2) {
        startActivity(new Intent(this, LivroLidoActivity.class));
    }
}
