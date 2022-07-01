package com.example.facom_notes;

import android.content.Intent;
import android.os.Bundle;

import com.example.facom_notes.database.DBHelperUser;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DBHelperUser helper = new DBHelperUser(this);
    private EditText edtUsuario;
    private EditText edtSenha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtUsuario = findViewById(R.id.edtUsuario);
        edtSenha = findViewById(R.id.edtSenha);
    }
    public void connect(View view) {
        String user = edtUsuario.getText().toString();
        String pass = edtSenha.getText().toString();
        String password = helper.getPass(user);
        if(pass.equals(password)){
            Intent intent= new Intent(this, NotesActivity.class);
            intent.putExtra("chave_usuario",user);
            startActivity(intent);
        }
        else{
            Toast toast = Toast.makeText(MainActivity.this,
                    "Usuário ou senha inválido",Toast.LENGTH_LONG);
            toast.show();
        }
    }
    public void register(View view) {
        Intent it = new Intent(this, RegisterActivity.class);
        startActivity(it);
    }
}