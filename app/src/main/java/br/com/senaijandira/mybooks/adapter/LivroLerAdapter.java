package br.com.senaijandira.mybooks.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.senaijandira.mybooks.MainActivity;
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


        TextView txtMarcarLido = v.findViewById(R.id.txtMarcarLido);

        txtMarcarLido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                livroLer.setStatusLivro(2);
                myBooksDatabase.daoLivro().atualizar(livroLer);

                remove(livroLer);
                alert("Pronto", "Livro "+livroLer.getTitulo()+" marcado como lido.", "OK", null);
            }
        });


        ImageView imgDelete = v.findViewById(R.id.imgDeleteLivroLer);
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                livroLer.setStatusLivro(0);
                myBooksDatabase.daoLivro().atualizar(livroLer);

                remove(livroLer);



            }
        });

        imgLivroLerCapa.setImageBitmap(Utils.toBitmap(livroLer.getCapa()));
        txtLivroLerDesc.setText(livroLer.getDescricao());
        txtLivroLerTitulo.setText(livroLer.getTitulo());

    }



    public void alert(String titulo, String mensagem, String positive, String negative){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

        alertDialogBuilder.setTitle(titulo);
        alertDialogBuilder.setMessage(mensagem);
        alertDialogBuilder.setPositiveButton(positive, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialogBuilder.setNegativeButton(negative, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialogBuilder.show();
    }


}


