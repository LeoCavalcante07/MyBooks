package br.com.senaijandira.mybooks;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.InputStream;

import br.com.senaijandira.mybooks.db.MyBooksDatabase;
import br.com.senaijandira.mybooks.model.Livro;

public class  CadastroActivity extends AppCompatActivity {

    Bitmap livroCapa;
    ImageView imgLivroCapa;
    EditText txtTitulo, txtDescricao;


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

    public static int x = 0;

    public void salvarLivro(View view) {


            byte[] capa = Utils.toByteArray(livroCapa);
            String titulo = txtTitulo.getText().toString();
            String descricao = txtDescricao.getText().toString();

            //Ao salvar o livro que acabou de ser cadastrado cria-se um novo livro
            Livro livro = new Livro(0, capa, titulo, descricao, '0');



            //Insere na variavelestática do MainACtivity
            /*int tamanhoArray = MainActivity.livros.length;

            MainActivity.livros = Arrays.copyOf(MainActivity.livros, tamanhoArray+1);

            MainActivity.livros[tamanhoArray] = livro;*/

            //Inserir no banco de dados
            myBooksDb.daoLivro().inserir(livro);

           /* if(MainActivity.livros.length == x){
                alert("Parabéns", "Livro Cadastrado com sucesso!");
            }else{
                alert("Deu pau", "ocorreu um erro na gravação!");
            }

        x++;*/

    }



    public void alert(String titulo, String mensagem){
        AlertDialog.Builder alert  = new AlertDialog.Builder(this);

        alert.setTitle(titulo);
        alert.setMessage(mensagem);

        //alert.setCancelable(false);



        alert.create().show();
    }

    public void verificar(View view) {
        if(txtDescricao.getText().equals("Titulo") && txtTitulo.getText().equals("Descrição")){

            alert("aaaa", "preencha todos os campos");
        }else{
            salvarLivro(view);
        }
    }
}
