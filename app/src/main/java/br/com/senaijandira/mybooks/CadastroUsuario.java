package br.com.senaijandira.mybooks;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.senaijandira.mybooks.db.MyBooksDatabase;

import br.com.senaijandira.mybooks.model.Usuario;

public class CadastroUsuario extends Activity {

    MyBooksDatabase myBooksDb;

    Button btnGravar;

    EditText edNome;
    EditText edSenha;
    EditText edEmail;
    EditText edSenhaConfirm;


    String nome;
    String senha;
    String senhaConfirm;
    String email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);


        btnGravar = findViewById(R.id.btnGravar);


        myBooksDb = Room.databaseBuilder(getApplicationContext(), MyBooksDatabase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();


        edEmail = findViewById(R.id.edEmail);
        edNome = findViewById(R.id.edNome);
        edSenha = findViewById(R.id.edSenha);
        edSenhaConfirm = findViewById(R.id.edSenhaConfirm);

    }

    @Override
    protected void onResume() {
        super.onResume();

        btnGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gravarUsuario();
            }
        });

    }

    public void gravarUsuario() {

        nome = edNome.getText().toString();
        email = edEmail.getText().toString();
        senha = edSenha.getText().toString();
        senhaConfirm = edSenhaConfirm.getText().toString();

        if(senha.equals(senhaConfirm)){

            Usuario usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setSenha(senha);
            usuario.setNome(nome);


            myBooksDb.usuarioDao().inserir(usuario);

            alert("Parabéns", "Usuário "+usuario.getNome()+" cadastrado com sucesso!", "OK", null);



        }else{
            alert("Atenção", "Erro ao confirmar a senha", null, "Tentar Novamente");
        }

    }

    public void alert(String titulo, String mensagem, String positive, String negative){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(titulo);
        alertDialogBuilder.setMessage(mensagem);
        alertDialogBuilder.setPositiveButton(positive, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), Login.class));
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
