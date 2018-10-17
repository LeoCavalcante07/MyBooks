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

public class Login extends Activity {

    MyBooksDatabase myBooksDb;

    public static Usuario usuarioLogado;


    EditText edEmail;
    EditText edSenha;
    Button btnLogar;

    String email;
    String senha;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = "";
        senha = "";

        myBooksDb = Room.databaseBuilder(getApplicationContext(), MyBooksDatabase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();

        edEmail = findViewById(R.id.edUsuarioLog);
        edSenha = findViewById(R.id.edSenhaLog);

        btnLogar = findViewById(R.id.btnLogar);


    }

    @Override
    protected void onResume() {
        super.onResume();



        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edEmail.getText().toString();
                senha = edSenha.getText().toString();
                logar(email, senha);
            }
        });

    }



    public void abrirCadastroUsuario(View view) {
        startActivity(new Intent(this, CadastroUsuario.class));
    }

    public void logar(String email, String senha){



       Usuario[] usuarios = myBooksDb.usuarioDao().selecionarLogin(email, senha);

        if(usuarios.length > 0){

            usuarioLogado = usuarios[0];

            startActivity(new Intent(this, MainActivity.class));
        }else{
            alert("Atenção!", "Usuário ou senha inexistente.", "OK", null);
        }
    }


    public void alert(String titulo, String mensagem, String positive, String negative){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

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
