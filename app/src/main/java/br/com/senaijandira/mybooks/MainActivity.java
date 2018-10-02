package br.com.senaijandira.mybooks;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
            if(livro.getStatusLivro() == 2){
                alert(livro, "Erro", "Não é possivel adicionar um livro já lido para a tela de livros para ler", "Mudar mesmo assim", "Cancelar", 2);
            }else{
                livro.setStatusLivro(1);//com status 1 sabemos que o livro está para ser lido*/
            }

        }else if(opt == 2){
            if(livro.getStatusLivro() == 1){
                alert(livro,"Erro", "Você deseja marcar o livro '"+livro.getTitulo()+"' como lido? Isso fará com que ele saia da lista de livros para ler", "OK", null, 1);
            }else{
                livro.setStatusLivro(2);//com status 2 sabemos que o livro ja foi lido
            }

        }

        myBooksDb.daoLivro().atualizar(livro);

    }






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




    // o metodo recebe um objeto do tipo livro pois o usuario podera mudar o status do livro, caso ele queira
    public void alert(final Livro livro, String titulo, String mensagem, final String positive, final String negative, final int statusAtual){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(titulo);
        alertDialogBuilder.setMessage(mensagem);

        //caso o usuario queira forçar a mudança do status do livro entra aqui
        alertDialogBuilder.setPositiveButton(positive, new DialogInterface.OnClickListener() {


            public void onClick(final DialogInterface dialog, int which) {
                String mensagem;
                if(statusAtual == 1){
                    mensagem  = "Isso fará com que o livro "+livro.getTitulo()+" saia da lista de livros para ler. Deseja continuar?";
                }else{
                    mensagem  = "Isso fará com que o livro "+livro.getTitulo()+" saia da lista de livros lidos. Deseja continuar?";
                }

                alertAtualizarStatus(livro, statusAtual, mensagem);
            }
        });

        alertDialogBuilder.setNegativeButton(negative, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialogBuilder.show();
    }


    public void alertAtualizarStatus(final Livro livro, final int statusAtual, String mensagem){
        final AlertDialog.Builder alertAtualizar = new AlertDialog.Builder(this);
        alertAtualizar.setTitle("Atenção!");
        alertAtualizar.setMessage(mensagem);

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

}
