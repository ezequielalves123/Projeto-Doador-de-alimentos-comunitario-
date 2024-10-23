package com.doadordealimentoscomunitario.doaodealimentosprojeto.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.doadordealimentoscomunitario.doaodealimentosprojeto.MainActivity;
import com.doadordealimentoscomunitario.doaodealimentosprojeto.R;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private EditText edt_email_register, edt_senha_registrar, edt_confirmar_senha_registrar;
    private CheckBox ckb_mostrar_senha_registrar;
    private Button btn_registrar_register, btn_login_register;
    private ProgressBar loginProgressBar_register;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        edt_email_register = findViewById(R.id.edt_email_register);
        edt_senha_registrar = findViewById(R.id.edt_senha_registrar);
        edt_confirmar_senha_registrar = findViewById(R.id.edt_confirmar_senha_registrar);
        ckb_mostrar_senha_registrar = findViewById(R.id.ckb_mostrar_senha_registrar);
        btn_registrar_register = findViewById(R.id.btn_registrar_register);
        btn_login_register = findViewById(R.id.btn_login_register);
        loginProgressBar_register = findViewById(R.id.loginProgressBar_register);

        // Listener para mostrar ou esconder senha
        ckb_mostrar_senha_registrar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edt_senha_registrar.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edt_confirmar_senha_registrar.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    edt_senha_registrar.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edt_confirmar_senha_registrar.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        // Listener do botão de registro
        btn_registrar_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt_email_register.getText().toString().trim();
                String senha = edt_senha_registrar.getText().toString().trim();
                String confirmarSenha = edt_confirmar_senha_registrar.getText().toString().trim();

                if (email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
                    Toast.makeText(Register.this, "Todos os campos são obrigatórios.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!senha.equals(confirmarSenha)) {
                    Toast.makeText(Register.this, "As senhas não correspondem.", Toast.LENGTH_SHORT).show();
                    return;
                }

                loginProgressBar_register.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
                    loginProgressBar_register.setVisibility(View.INVISIBLE);
                    if (task.isSuccessful()) {
                        Toast.makeText(Register.this, "Registro bem-sucedido!", Toast.LENGTH_SHORT).show();
                        abrirTelaPrincipal();
                    } else {
                        Toast.makeText(Register.this, "Falha no registro: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        // Listener do botão de login
        btn_login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void abrirTelaPrincipal() {
        Intent intent = new Intent(Register.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
