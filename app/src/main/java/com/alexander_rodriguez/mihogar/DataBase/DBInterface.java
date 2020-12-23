package com.alexander_rodriguez.mihogar.DataBase;

import android.content.ContentValues;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alexander_rodriguez.mihogar.TableCursor;
import com.alexander_rodriguez.mihogar.modelos.ModelUsuario;
import com.alexander_rodriguez.mihogar.viewregistraralquiler.ModelAA;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public interface DBInterface {

    static ContentValues cursorToCV(@NotNull Cursor cursor){
        ContentValues cv = new ContentValues();
        for (int i = 0; i<cursor.getColumnCount(); i++){
            cv.put(cursor.getColumnName(i),cursor.getString(i));
        }
        return cv;
    }

    boolean revertir(String tableName, String columKey, Object key);

    ContentValues getFilaInCuarto(String columnas, Object numCuarto);

    ContentValues getFilaInMensualidadActual(String columnas, Object idAlquiler);

    TableCursor getMensualidadesOfAlquiler(String columnas, Object idAlquiler);

    ContentValues getFilaInUsuariosOf(String columnas, Object DNI);

    String getValueOn(String columna, String tableName, String key, Object value);

    TableCursor getAlquileresValidos(String columnas, String key, Object value);

    ArrayList<String> getCuartosAlquilados();

    ArrayList<String> getDniEnCasa();

    Cursor getAllAlquileres(String columnas, String key, Object value);

    Cursor getAllAlquileresJoinUserExept(String columnas, String key, Object value, Object idAlquIgnore);

    Cursor getAllAlquileres(String columnas);

    Cursor getAllAlquilerJoinUser(String columnas);

    Cursor getAllCuartos(String columnas);

    Cursor getAllCuartosJoinAlquiler(String columnas);

    Cursor getCuartosLibres(String columnas);

    Cursor getCuartosAlquilados(String columnas);

    Cursor getallUsuarios(String columnas);

    Cursor getAllUsuariosADDAlert(String columnas);

    Cursor getUsuariosForAlquiler(String columnas, String ida);

    ContentValues getFilaAlquilerByCuartoOf(String columnas, Object numCuarto);

    String[] getDniOfAlquilerUser(Object idAlquiler);

    ContentValues getFilaAlquilerOf(String columnas, Object id);

    ContentValues getFilaAlquilerByUserOf(String columnas, Object DNI);

    TableCursor getPagosOf(String columnas, Object idMensualidad);

    String[] getIdOfAllAlquileres();

    String[] consultarNumerosDeCuarto();

    String contAlquileresOf(String key, Object value);

    int contDniOfAlquilerUsuario(String idAlquiler);

    void upDateUsuario(String columna, Object valor, Object DNI);

    void upDateCuarto(String columna, Object valor, Object numeroDeCuarto);

    void upDateAlquiler(String columna, Object valor, Object id);

    void upDateAlquilerUsuario(String columna, Object valor, Object idAl, Object dni);

    boolean agregarCuarto(String numCuarto, String detalles, String precio, String path);

    boolean usuarioAlertado(Object DNI);

    boolean agregarInquilino(String DNI, String nombres, String apellidoPat, String apellidoMat, String URI);

    boolean agregarInquilino(ModelUsuario mu);

    boolean agregarAlquiler(String numC, String fecha, String pagosRealizados, String numTel, String correo);

    boolean agregarAlquilerUsuario(long idAlquiler, String dni, boolean isMain);

    boolean agregarAlquiler(ModelAA model);

    boolean agregarMensualidad(double costo, String fecha_i, long idA);

    boolean agregarPago(String fecha, long idM, long DNI);

    boolean agregarInquilinoExist(String DNI, String numC, double costo, @NonNull String fecha_i, @Nullable String fecha_c, String numTel, String correo);

    boolean agregarNuevoInquilino(ModelUsuario mu, String numC, double costo, @NonNull String fecha_i, @Nullable String fecha_c, String numTel, String correo);

    long getIDMaxMensualidad();

    long getIdMaxAlquiler();

    String[] consultarNumerosDeCuartoDisponibles();

    boolean existIntoCuarto(String valor);

    boolean existeUsuario(String dni);

    boolean esUsuarioAntiguo(String dni);

    boolean esUsuarioInterno(String dni);

    void startScrips();

    Cursor gePagosRealizados(String idMensualidad);

    void agregarVoucher(String fileName, String id);

    Cursor getPago(String id);

    int getUsuarioResponsableDe(String idAlquiler);
}
