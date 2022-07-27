package com.juan.yanqui.basecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.juan.yanqui.basecrud.modelo.Persona;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText id_per,ce_per,nom_per, ape_per, sex_per;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        asignacion_controles();
        Button buttonGuardar=(Button) findViewById(R.id.btncrear);
        buttonGuardar.setOnClickListener(guardarListener);
        Button buttonListar=(Button) findViewById(R.id.btnlistar);
        buttonListar.setOnClickListener(listarListener);
        Button buttonModificar=(Button) findViewById(R.id.btnmodificar);
        buttonModificar.setOnClickListener(Modificarpersona);
        Button buttonEliminar=(Button) findViewById(R.id.btneliminar);
        buttonEliminar.setOnClickListener(Eliminarpersona);
        Button buttonBuscar=(Button) findViewById(R.id.btnbuscar);
        buttonBuscar.setOnClickListener(BuscarPersona);
    }
    private void asignacion_controles(){
        id_per = (EditText) findViewById(R.id.id_persona);
        ce_per = (EditText) findViewById(R.id.cedula_per);
        nom_per = (EditText) findViewById(R.id.nombre_per);
        ape_per = (EditText) findViewById(R.id.apellido_per);
        sex_per = (EditText) findViewById(R.id.sexo_per);

    }
    View.OnClickListener listarListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ListView listView=(ListView) findViewById(R.id.listaPersona);
            Cursor cursor= Persona.listaPersonas(getApplicationContext());
            String[] desde = new String[]{"id_persona","cedula","nombre","apellido","sexo"};
            int[] hasta = new int[]{R.id.txtid,R.id.txtcedula,R.id.txtnombre,R.id.txtapellido,R.id.txtsexo};

            CursorAdapter cursorAdapter= new SimpleCursorAdapter(
                    getApplicationContext(),
                    R.layout.detallelista,
                    cursor,
                    desde,
                    hasta,0
            );
            listView.setAdapter(cursorAdapter);
        }
    };
    View.OnClickListener guardarListener= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Persona persona = new Persona();
            persona.setId_persona(Integer.parseInt(id_per.getText().toString()));
            persona.setCedula(ce_per.getText().toString());
            persona.setNombre(nom_per.getText().toString());
            persona.setApellido(ape_per.getText().toString());
            persona.setSexo(sex_per.getText().toString());

            if(cedulaRepetida(id_per.getText().toString())){
                Toast.makeText(getApplicationContext(), "La cedula ya esta en la BD", Toast.LENGTH_SHORT).show();
            }else{
                if(persona.guardarPersona(getApplicationContext())){
                    Toast.makeText(getApplicationContext(), "La persona se creo correctamente", Toast.LENGTH_SHORT).show();
                    limpiarVista();
                }else{
                    Toast.makeText(getApplicationContext(), "No se pudo crear la persona.", Toast.LENGTH_SHORT).show();
                }
            }

        }
    };
    View.OnClickListener Modificarpersona= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Persona persona = new Persona();
            persona.setId_persona(Integer.parseInt(id_per.getText().toString()));
            persona.setCedula(ce_per.getText().toString());
            persona.setNombre(nom_per.getText().toString());
            persona.setApellido(ape_per.getText().toString());
            persona.setSexo(sex_per.getText().toString());

            if(persona.modificarPersona(getApplicationContext(), id_per.getText().toString())){
                Toast.makeText(getApplicationContext(), "La persona se edito correctamente", Toast.LENGTH_SHORT).show();
                limpiarVista();
            }else{
                Toast.makeText(getApplicationContext(), "Error al modificar", Toast.LENGTH_SHORT).show();
            }
        }
    };

    View.OnClickListener Eliminarpersona= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Persona persona = new Persona();
            if(persona.eliminarPersona(getApplicationContext(), id_per.getText().toString())){
                Toast.makeText(getApplicationContext(), "Correcto al eliminar la persona", Toast.LENGTH_SHORT).show();
                limpiarVista();
            }else{
                Toast.makeText(getApplicationContext(), "Error al elminar la persona", Toast.LENGTH_SHORT).show();
            }
        }
    };

    View.OnClickListener BuscarPersona= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        Persona persona = new Persona();
        List<Persona> listap = persona.buscar(getApplicationContext(), id_per.getText().toString());
        for (int i = 0; i < listap.size(); i++){
            Toast.makeText(getApplicationContext(), listap.get(i).getId_persona()+" "+listap.get(i).getCedula()+" "+listap.get(i).getNombre()+" "+listap.get(i).getApellido()+" "+listap.get(i).getSexo(), Toast.LENGTH_SHORT).show();
            ce_per.setText(listap.get(i).getCedula());
            nom_per.setText(listap.get(i).getNombre());
            ape_per.setText(listap.get(i).getApellido());
            sex_per.setText(listap.get(i).getSexo());

        }
    }
    };
    private boolean cedulaRepetida(String ci){
        Persona persona = new Persona();
        List<Persona> listap = persona.Listar(getApplicationContext());
        for (int i = 0; i < listap.size(); i++){
            if(listap.get(i).getCedula().equals(ci)){
                return true;
            }
        }
        return false;
    }
    private void limpiarVista(){
        id_per.setText(null);
        ce_per.setText(null);
        nom_per.setText(null);
        ape_per.setText(null);
        sex_per.setText(null);
    }


}