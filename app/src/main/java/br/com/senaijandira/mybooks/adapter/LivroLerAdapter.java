package br.com.senaijandira.mybooks.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.senaijandira.mybooks.LivroLerActivity;
import br.com.senaijandira.mybooks.LivroLidoActivity;
import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.Utils;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

/**
 * Created by 17259211 on 01/10/2018.
 */

public class LivroLerAdapter extends ArrayAdapter<Livro>{

    MyBooksDatabase myBooksDatabase;//injeção de dependencia


                                                //injeção de dependencia
    public LivroLerAdapter(Context contexto, MyBooksDatabase myBooksDatabase){
        super(contexto, 0, new ArrayList<Livro>());

        this.myBooksDatabase = myBooksDatabase;//injeção de dependencia
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if(v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.livro_ler_layout, parent, false);
        }

        Livro livroLer = getItem(position);

        criarLivroLer(livroLer, v);



        return v;
    }



    public void criarLivroLer(final Livro livroLer, View v){
        ImageView imgLivroLerCapa = v.findViewById(R.id.imgLivroLerCapa);
        TextView txtLivroLerDesc = v.findViewById(R.id.txtLivroLerDescricao);
        TextView txtLivroLerTitulo = v.findViewById(R.id.txtLivroLerTitulo);


        ImageView imgDelete = v.findViewById(R.id.imgDeleteLivroLer);
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                livroLer.setStatusLivro(0);
                myBooksDatabase.daoLivro().atualizar(livroLer);


            }
        });

        imgLivroLerCapa.setImageBitmap(Utils.toBitmap(livroLer.getCapa()));
        txtLivroLerDesc.setText(livroLer.getDescricao());
        txtLivroLerTitulo.setText(livroLer.getTitulo());

    }


}


