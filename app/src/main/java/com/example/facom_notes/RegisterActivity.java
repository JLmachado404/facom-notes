package com.example.facom_notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.facom_notes.database.DBHelperUser;

public class RegisterActivity extends AppCompatActivity {
    DBHelperUser helper = new DBHelperUser(this);
    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtUsuario;
    private EditText edtSenha;
    private EditText edtConfSenha;
    private Button btnSalvar;
    private User u;
    private User altUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtEmail);
        edtUsuario = findViewById(R.id.edtUsuario);
        edtSenha = findViewById(R.id.edtSenha);
        edtConfSenha = findViewById(R.id.edtConfSenha);
        btnSalvar=findViewById(R.id.btnSalvar);

        Intent it = getIntent();
        altUser = (User) it.getSerializableExtra("chave_contato");
        u = new User();

        if(altUser != null){
            btnSalvar.setText("ALTERAR");
            edtNome.setText(altUser.getName());
            edtEmail.setText(altUser.getEmail());
            edtUsuario.setText(altUser.getUser());
            edtSenha.setText(altUser.getPassword());
            edtConfSenha.setText(altUser.getPassword());
            u.setId(altUser.getId());
        }else{
            btnSalvar.setText("SALVAR");
        }
    }
    public void register(View view) {
        String name = edtNome.getText().toString();
        String email = edtEmail.getText().toString();
        String user = edtUsuario.getText().toString();
        String pass = edtSenha.getText().toString();
        String passCheck = edtConfSenha.getText().toString();

        if(!pass.equals(passCheck)){
            Toast toast = Toast.makeText(RegisterActivity.this,
                    "Senha diferente da confirmação de senha!",
                    Toast.LENGTH_SHORT);
            toast.show();
            edtSenha.setText("");
            edtConfSenha.setText("");
        }
        else{
            u.setName(name);
            u.setEmail(email);
            u.setUser(user);
            u.setPassword(pass);
            if(btnSalvar.getText().toString().equals("SALVAR")) {
                helper.insertUser(u);
                Toast toast = Toast.makeText(RegisterActivity.this,
                        "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT);
                toast.show();
            }else{
                helper.updateUser(u);
                helper.close();
            }
            clear();
            finish();
        }
    }
    public void clear(){
        edtNome.setText("");
        edtEmail.setText("");
        edtUsuario.setText("");
        edtSenha.setText("");
        edtConfSenha.setText("");
    }
    public void cancel(View view) {
        finish();
    }
}