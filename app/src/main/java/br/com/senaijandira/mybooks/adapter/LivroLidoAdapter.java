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

import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.Utils;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

/**
 * Created by 17259211 on 08/10/2018.
 */

public class LivroLidoAdapter extends ArrayAdapter<Livro> {

    MyBooksDatabase myBooksDb;

    public LivroLidoAdapter(Context contexto, MyBooksDatabase myBooksDb){
        super(contexto, 0, new ArrayList<Livro>());

        this.myBooksDb = myBooksDb;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if(v == null){
            //v = LayoutInflater.from(getContext()).inflate(R.layout.livro_lido_layout, parent, false);
            v = LayoutInflater.from(getContext()).inflate(R.layout.livro_lido_layout, parent, false);
        }

        Livro livroLido = getItem(position);

        criarLivroLido(livroLido, v);

        return v;
    }



    public void criarLivroLido(final Livro livroLido, View v){
        ImageView imgLivroCapa = v.findViewById(R.id.imgLivroLerCapa);
        //TextView txtTitulo = v.findViewById(R.id.txtTitulo);
        TextView txtLivroDesc = v.findViewById(R.id.txtLivroLerDescricao);
        TextView txtLivroTitulo = v.findViewById(R.id.txtLivroLerTitulo);

        ImageView imgDelete = v.findViewById(R.id.imgDeleteLivroLer);
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                livroLido.setStatusLivro(0);
                myBooksDb.daoLivro().atualizar(livroLido);
                remove(livroLido);

            }
        });

        imgLivroCapa.setImageBitmap(Utils.toBitmap(livroLido.getCapa()));
        txtLivroDesc.setText(livroLido.getDescricao());
        txtLivroTitulo.setText(livroLido.getTitulo());

    }
}
