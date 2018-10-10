package br.com.senaijandira.mybooks;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.InputStream;

import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class  CadastroActivity extends AppCompatActivity {

    Bitmap livroCapa;
    ImageView imgLivroCapa;
    EditText txtTitulo, txtDescricao;
    Button btnSalvar;


    private final int COD_REQ_GALERY = 101;

    MyBooksDatabase myBooksDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        myBooksDb = Room.databaseBuilder(getApplicationContext(), MyBooksDatabase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();

        imgLivroCapa = findViewById(R.id.imgLivroCapa);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDescricao);
        Button btnSalvar = findViewById(R.id.btnSalvar);





        final int id = getIntent().getIntExtra("idLivro", 0);


        if(id > 0){
            final Livro livro = myBooksDb.daoLivro().selecionarLivroId(id);

            txtDescricao.setText(livro.getDescricao());
            txtTitulo.setText(livro.getTitulo());
            imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa()));
            livroCapa = Utils.toBitmap(livro.getCapa());

            btnSalvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    salvarLivro(livro);
                }
            });


        }else{

            final Livro livro = new Livro();

            btnSalvar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    salvarLivro(livro);
                }
            });
        }



    }

    public void abrirGaleria(View view) {

        //faz com que abra os documento do celular android
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        //Especifica que abrirá na parte de imagens
        intent.setType("image/*");

        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), COD_REQ_GALERY);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //result ok é quuando o usauario seleciona algo, pq ele pode não selecionar
        if(requestCode == COD_REQ_GALERY && resultCode == Activity.RESULT_OK){

            try{

                //pega o elemtento selecionado e transforma em binario
                InputStream input = getContentResolver().openInputStream(data.getData());

                //transforma o binario do elemento selecionado em bitmap
                livroCapa = BitmapFactory.decodeStream(input);

                //Exibindo na tela
                imgLivroCapa.setImageBitmap(livroCapa);


            }catch (Exception ex){
                ex.printStackTrace();;
            }

        }


    }


    public void salvarLivro(@Nullable Livro livro) {


            //Ao salvar o livro que acabou de ser cadastrado cria-se um novo livro


        if(imgLivroCapa == null || txtDescricao.getText().toString().equals("") || txtTitulo.getText().toString().equals("")){
            alert("Erro ao cadastrar", "Por Favor preencha todos os campos", "OK", null);
        }else{


           // livroCapa = Utils.toBitmap(imgLivroCapa);

            byte[] capa = Utils.toByteArray(livroCapa);
            String titulo = txtTitulo.getText().toString();
            String descricao = txtDescricao.getText().toString();



            if(livro.getId() > 0){

//                livro.setDescricao(txtDescricao.getText().toString());
//                livro.setTitulo(txtTitulo.getText().toString());
//                //livro.setCapa(Utils.toByteArray(Utils.toBitmap(imgLivroCapa)));
//                livro.setCapa(capa);

                livro.setCapa(capa);
                livro.setTitulo(titulo);
                livro.setDescricao(descricao);

                myBooksDb.daoLivro().atualizar(livro);
                alert("Pronto", "Livro atualizado com sucesso", "OK", null);
            }else{

                livro.setCapa(capa);
                livro.setTitulo(titulo);
                livro.setDescricao(descricao);

                myBooksDb.daoLivro().inserir(livro);
                alert("Pronto", "Livro Cadastrado com sucesso", "OK", null);
            }


        }



    }






    public void alert(String titulo, String mensagem, String positive, String negative){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(titulo);
        alertDialogBuilder.setMessage(mensagem);
        alertDialogBuilder.setPositiveButton(positive, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        alertDialogBuilder.setNegativeButton(negative, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialogBuilder.show();
    }






    public void abrirCadastro(View view) {
        startActivity(new Intent(this, CadastroActivity.class));

    }
}
