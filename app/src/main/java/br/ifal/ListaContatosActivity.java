package br.ifal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.List;

public class ListaContatosActivity extends AppCompatActivity {

    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contatos);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_ativity_form);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaContatosActivity.this, FormularioActivity.class);
                startActivity(intent);
            }
        });

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);

        ListView listView = findViewById(R.id.listview_mainativity);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_item, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Contato contato = (Contato) adapter
                .getItem(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);
        Intent i;
        switch (item.getItemId()) {
            case R.id.menu_editar:
                i = new Intent(ListaContatosActivity.this, FormularioActivity.class);
                i.putExtra("contato", contato);
                startActivity(i);
                break;
            case R.id.menu_apagar:
                ContatoDAO dao = new ContatoDAO(this);
                dao.remove(contato);
                break;
            case R.id.activity_lista_menu_sms:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("sms:"+contato.getFone()));
                startActivity(i);
                break;
            case R.id.activity_lista_menu_visitar_site:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://www.google.com/"));
                startActivity(i);
                break;
            case R.id.activity_lista_menu_end:
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("geo:0,0?q=" + contato.getEndereco()));
                startActivity(i);
                break;
            case R.id.activity_lista_menu_ligar:
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    String[] permissions = {Manifest.permission.CALL_PHONE};
                    ActivityCompat.requestPermissions(this, permissions, 123);
                } else {
                    i = new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel:" + contato.getFone()));
                    startActivity(i);
                }
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onResume() {
        super.onResume();

        //RECUPERA A LISTA DE PRODUTOS DO BD
        List<Contato> lista = new ContatoDAO(this).getContatosList();

        //LIMPAR A LISTA E ADCIONAR OS OBJETOS NOVAMENTE
        adapter.clear();
        adapter.addAll(lista);
    }
}
