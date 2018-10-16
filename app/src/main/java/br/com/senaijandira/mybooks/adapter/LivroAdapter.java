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

import br.com.senaijandira.mybooks.CadastroActivity;
import br.com.senaijandira.mybooks.Login;
import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.Utils;
import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.fragments.FragLivro;
import br.com.senaijandira.mybooks.model.Livro;
import br.com.senaijandira.mybooks.model.Usuario;

public class LivroAdapter extends ArrayAdapter<Livro> {
    FragLivro fragLivro;

    MyBooksDatabase myBooksDb;




    public LivroAdapter(Context context, MyBooksDatabase myBooksDb){
        super(context, 0, new ArrayList<Livro>());

        this.myBooksDb = myBooksDb;
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




    public void criarLivro(final Livro livro, View v){



        ImageView imgLivroCapa = v.findViewById(R.id.imgLivroCapa);
        TextView txtLivroTitulo = v.findViewById(R.id.txtLivroTitulo);
        TextView txtLivroDescricao = v.findViewById(R.id.txtLivroDescricao);
        TextView txtLivrolido = v.findViewById(R.id.txtLivroLido);
        TextView txtLivrosParaler = v.findViewById(R.id.txtLivrosParaLer);

        ImageView imgDeleteLivro = v.findViewById(R.id.imgDeleteLivro);
        ImageView imgEditLivro = v.findViewById(R.id.imgEdit);


        imgDeleteLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(livro.getStatusLivro() == 0){
                    deletarLivro(livro);
                }else{
                    alert("Atenção", "Não é possivel apagar um livro que está em outra lista");
                }

            }
        });



        imgEditLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarLivro(livro);
            }
        });



        //click listener para adc Livro para ler
        txtLivrosParaler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(livro.getStatusLivro() == 0){
                    alert("Concluído", "Livro adcionado a livros para ler");
                }
                atualizarStatusLivro(livro, 1);
            }
        });


        //click listener para adc Livro lido
        txtLivrolido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(livro.getStatusLivro() == 0){
                    alert("Concluído", "Livro adcionado a livros lidos");
                }
                atualizarStatusLivro(livro, 2);

            }
        });




        imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa()));
        txtLivroTitulo.setText(livro.getTitulo());
        txtLivroDescricao.setText(livro.getDescricao());
    }


    private void deletarLivro(Livro livro){

        //deletar livro do banco
        myBooksDb.daoLivro().deletar(livro);

        remove(livro);
        //remover o item (template onde está as informações do livro) da tela
        //listaLivros.removeView(v);
        //listViewLivros.removeView(v);



    }



    private void editarLivro(Livro livro){
        Intent intent = new Intent(getContext(), CadastroActivity.class).putExtra("idLivro", livro.getId());
        getContext().startActivity(intent);

        CadastroActivity ca = new CadastroActivity();


    }


    private void atualizarStatusLivro(Livro livro, int opt){
        String mensagem;
        if(opt == 1){
            if(livro.getStatusLivro() == 2){
                mensagem  = "Isso fará com que o livro "+livro.getTitulo()+" saia da lista de livros lidos. Deseja continuar?";
                alertAtualizarStatus(livro,2, mensagem);
            }else if(livro.getStatusLivro() == 1){
                alert("Aviso", "O livro já está ma lista de livros lidos");
            }else{
                livro.setStatusLivro(1);//com status 1 sabemos que o livro está para ser lido*/
            }

        }else if(opt == 2){
            if(livro.getStatusLivro() == 1){
                mensagem  = "Deseja marcar o livro "+livro.getTitulo()+" como lido? Isso fará com que ele saia da lista de livros para ler.";
                alertAtualizarStatus(livro,1, mensagem);
            }else if(livro.getStatusLivro() == 2){
                alert("Aviso", "O livro já está na lista de livros lidos");
            }else{
                livro.setStatusLivro(2);//com status 2 sabemos que o livro ja foi lido
            }

        }

        myBooksDb.daoLivro().atualizar(livro);

    }


    public void alertAtualizarStatus(final Livro livro, final int statusAtual, String mensagem){
        final AlertDialog.Builder alertAtualizar = new AlertDialog.Builder(getContext());
        alertAtualizar.setTitle("Atenção!");
        alertAtualizar.setMessage(mensagem);
        alertAtualizar.setCancelable(false);

        alertAtualizar.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(statusAtual == 1){
                    livro.setStatusLivro(2);
                }else{
                    livro.setStatusLivro(1);
                }

                myBooksDb.daoLivro().atualizar(livro);
            }
        });

        alertAtualizar.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });


        alertAtualizar.show();

    }


    public void alert(String titulo, String mensagem){
        final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle(titulo);
        alert.setMessage(mensagem);
        alert.setCancelable(false);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }





}
