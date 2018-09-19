package br.com.senaijandira.mybooks.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class LivroLido {

   @PrimaryKey(autoGenerate = true)
    private int id;




    @ForeignKey(entity = Livro.class, String[] parentColumns = ["id"], childColumns = ["idLivro"]);
    private int idLivro;

    //Construtor
    public LivroLido(){

    }

    //Construtor
    public LivroLido(int id){

        this.id = id;


    }





    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
