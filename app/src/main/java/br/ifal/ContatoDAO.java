package br.ifal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ContatoDAO {
    private ConexaoOpenHelper conexao;
    private static final String TABLE = "CONTATO";

    public ContatoDAO(Context context) {
        conexao = new ConexaoOpenHelper(context);
    }

    private ContentValues buildContentValues(Contato contato) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("NOME", contato.getNome());
        contentValues.put("FONE", contato.getFone());
        contentValues.put("ENDERECO", contato.getEndereco());
        return contentValues;
    }

    public void salvar(Contato contato) {
        SQLiteDatabase db = conexao.getWritableDatabase();
        ContentValues contentValues = buildContentValues(contato);
        db.insert(TABLE, null, contentValues);
        db.close();
    }

    public List<Contato> getContatosList() {
        List<Contato> list = new ArrayList<>();

        String sql = "SELECT * FROM " + TABLE;
        SQLiteDatabase sqlDb = conexao.getWritableDatabase();
        Cursor cursor = sqlDb.rawQuery(sql, null);

        while(cursor.moveToNext()) {
            list.add(new Contato(
                cursor.getInt(cursor.getColumnIndexOrThrow("ID")),
                cursor.getString(cursor.getColumnIndexOrThrow("NOME")),
                cursor.getString(cursor.getColumnIndexOrThrow("FONE")),
                cursor.getString(cursor.getColumnIndexOrThrow("ENDERECO"))
            ));
        }
        cursor.close();
        sqlDb.close();
        return list;
    }

    public boolean selectByFone(String fone) {
        SQLiteDatabase db = conexao.getReadableDatabase();
        String[] parametros = {fone};
        String sql = "SELECT * FROM " + TABLE
                + " WHERE FONE = ?";
        Cursor cursor = db.rawQuery(sql, parametros);

        boolean exist = cursor.moveToNext();
        cursor.close();
        db.close();

        return exist;
    }

    public void remove(Contato contato) {
        SQLiteDatabase db = conexao.getWritableDatabase();
        String[] parametros = {String.valueOf(contato.getId())};
        db.delete(TABLE,"id = ?", parametros);
        db.close();
    }

    public void atualizar(Contato contato) {
        SQLiteDatabase db = conexao.getWritableDatabase();
        ContentValues dados = buildContentValues(contato);
        String[] parametros = {String.valueOf(contato.getId())};

        db.update(TABLE, dados,"id = ?", parametros);
        db.close();
    }
}
