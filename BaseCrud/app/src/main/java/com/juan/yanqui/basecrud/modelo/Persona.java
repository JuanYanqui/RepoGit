package com.juan.yanqui.basecrud.modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Persona {
    private int id_persona;
    private String cedula;
    private String nombre;
    private String apellido;
    private String sexo;

    public static Cursor listaPersonas(Context per){
        SQLiteHelper sq = new SQLiteHelper(per);
        String sql="select _rowid_ as _id,* from persona";
        return sq.getReadableDatabase().rawQuery(sql,null);
    }
    /*public void guardarPersona(Context per){
        SQLiteHelper sq = new SQLiteHelper(per);
        String sql;
        sql= "INSERT INTO persona (id_persona,cedula,nombre,apellido,sexo)";
        sql+="VALUES('"+getId_persona()+"','"+getCedula()+"','"+getNombre()+"','"+getApellido()+"','"+getSexo()+"')";
        sq.getWritableDatabase().execSQL(sql);
    }*/

    public boolean guardarPersona(Context person){
        try {
            SQLiteHelper cp = new SQLiteHelper(person);
            String sql = "INSERT INTO persona (id_persona,cedula,nombre,apellido,sexo) VALUES ('"+getId_persona()+"','"+getCedula()+"','"+getNombre()+"','"+getApellido()+"','"+getSexo()+"')";
            //Toast.makeText(person, "->"+sql, Toast.LENGTH_SHORT).show();
            cp.getWritableDatabase().execSQL(sql);
            return true;
        }catch (Exception e){
            e.toString();
            return false;
        }

    }
    public boolean modificarPersona(Context person, String id){
        try {
            SQLiteHelper cp = new SQLiteHelper(person);
            String sql = "UPDATE persona set cedula='"+getCedula()+"',nombre='"+getNombre()+"', apellido='"+getApellido()+"', sexo='"+getSexo()+"' where id_persona = '"+id+"'";
            //Toast.makeText(person, ""+sql, Toast.LENGTH_SHORT).show();
            cp.getWritableDatabase().execSQL(sql);
            return true;
        }catch (Exception e){
            e.toString();
            return false;
        }
    }
    public boolean eliminarPersona(Context person, String id){
        try {
            SQLiteHelper cp = new SQLiteHelper(person);
            SQLiteDatabase db = cp.getWritableDatabase();
            String sql = "DELETE FROM persona WHERE id_persona = '" + id + "';";
            Toast.makeText(person, "CorrectoElimina"+sql, Toast.LENGTH_SHORT).show();
            db.execSQL(sql);
            return true;
        }catch (Exception e){
            e.toString();
            return false;
        }
    }
    public List<Persona> Listar(Context person){
        List<Persona> listarPersonas = new ArrayList<>();
        SQLiteHelper cp = new SQLiteHelper(person);
        //SQLiteDatabase cursorbd = cp.getReadableDatabase();
        Cursor cursobd = cp.getReadableDatabase().rawQuery("select id_persona,cedula,nombre,apellido,sexo from persona", null);
        if(cursobd.moveToFirst()){
            do {
                Persona per = new Persona();
                //Toast.makeText(person, "salida->"+cursobd.getString(1), Toast.LENGTH_SHORT).show();
                per.setId_persona(cursobd.getInt(0));
                per.setCedula(cursobd.getString(1));
                per.setNombre(cursobd.getString(2));
                per.setApellido(cursobd.getString(3));
                //String n = cursobd.getString(3);
                per.setSexo(cursobd.getString(4));
                listarPersonas.add(per);
            }while (cursobd.moveToNext());
        }
        return listarPersonas;
    }

    public List<Persona> buscar(Context person, String ci){
        List<Persona> lista = new ArrayList<>();
        SQLiteHelper cp = new SQLiteHelper(person);
        //SQLiteDatabase cursorbd = cp.getReadableDatabase();
        Cursor cursobd = cp.getReadableDatabase().rawQuery("select id_persona,cedula,nombre,apellido,sexo from persona where id_persona='"+ci+"'", null);
        if(cursobd.moveToFirst()){
            do {
                Persona per = new Persona();
                Toast.makeText(person, "salida->"+cursobd.getString(1), Toast.LENGTH_SHORT).show();
                per.setId_persona(cursobd.getInt(0));
                per.setCedula(cursobd.getString(1));
                per.setNombre(cursobd.getString(2));
                per.setApellido(cursobd.getString(3));
                //String n = cursobd.getString(3);
                per.setSexo(cursobd.getString(4));
                lista.add(per);
            }while (cursobd.moveToNext());
        }else{
            Toast.makeText(person.getApplicationContext(), "No existe la persona con dicha cédula", Toast.LENGTH_SHORT).show();
        }
        return lista;
    }


    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id_persona='" + id_persona + '\'' +
                "cedula='" + cedula + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", sexo=" + sexo +
                '}';
    }
}
