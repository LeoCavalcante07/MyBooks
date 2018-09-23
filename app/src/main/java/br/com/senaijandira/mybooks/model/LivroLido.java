package br.com.senaijandira.mybooks.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(foreignKeys = @ForeignKey(entity = Livro.class, parentColumns = "id", childColumns = "idLivro"))
public class LivroLido extends Livro {

  // @PrimaryKey(autoGenerate = true)
    //private int id;




    //@ForeignKey(entity = Livro.class, String[] parentColumns = ["id"], childColumns = ["idLivro"]);

    private int idLivro;

    public int getLivro(){
        return idLivro;
    }

    public void setIdLivro(int idLivro){
        this.idLivro = idLivro;
    }




}
