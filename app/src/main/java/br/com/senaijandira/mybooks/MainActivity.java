package br.com.senaijandira.mybooks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.senaijandira.mybooks.model.Livro;

public class MainActivity extends AppCompatActivity {

    LinearLayout listaLivros;

    public static Livro[] livros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        listaLivros.removeAllViews();

        //a cada livro no array Livros[], cria um novo livro
        for(Livro livro : livros){
            criarLivro(livro, listaLivros);
        }

    }

    public void criarLivro(Livro livro, ViewGroup root){//ViewGroup é onde vai ser colocado o novo livro, no caso o LinearLayout listaLivros

        View v = LayoutInflater.from(this).inflate(R.layout.livro_layout, root,  false);//carrega o template livro_layout na variavel v

        ImageView imgLivroCapa = v.findViewById(R.id.imgLivroCapa);
        TextView txtLivroTitulo = v.findViewById(R.id.txtLivroTitulo);
        TextView txtLivroDescricao = v.findViewById(R.id.txtLivroDescricao);


        imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa()));
        txtLivroTitulo.setText(livro.getTitulo());
        txtLivroDescricao.setText(livro.getDescricao());


        root.addView(v);
    }

    public void abrirCadastro(View view) {
        startActivity(new Intent(this, CadastroActivity.class));

    }
}
