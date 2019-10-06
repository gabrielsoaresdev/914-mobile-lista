package br.ifal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FormularioActivity extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextTelefone;
    private EditText editTextEndereco;
    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        editTextNome = findViewById(R.id.edittext_nome);
        editTextTelefone = findViewById(R.id.edittext_telefone);
        editTextEndereco = findViewById(R.id.edittext_endereco);
        Button button = findViewById(R.id.button_form);
        Intent intent = getIntent();
        if(intent.hasExtra("contato")) {
            contato =
                    (Contato) intent.getSerializableExtra("contato");
            editTextEndereco.setText(contato.getNome());
            editTextTelefone.setText(contato.getFone());
            editTextEndereco.setText(contato.getEndereco());
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContatoDAO contatoDAO = new ContatoDAO(FormularioActivity.this);
                if(contato == null) {
                    contatoDAO.salvar(new Contato(
                            editTextNome.getText().toString(),
                            editTextTelefone.getText().toString(),
                            editTextEndereco.getText().toString()
                    ));
                }
                else {
                    contato.setNome(editTextNome.getText().toString());
                    contato.setEndereco(editTextEndereco.getText().toString());
                    contato.setFone(editTextTelefone.getText().toString());
                    contatoDAO.atualizar(contato);
                }
                finish();
            }
        });
    }
}
