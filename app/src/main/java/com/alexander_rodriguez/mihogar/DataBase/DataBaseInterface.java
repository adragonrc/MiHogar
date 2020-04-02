package com.alexander_rodriguez.mihogar.DataBase;

import android.content.ContentValues;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alexander_rodriguez.mihogar.modelos.ModelUsuario;
import com.alexander_rodriguez.mihogar.TableCursor;

public interface DataBaseInterface {
    ContentValues getFilaInCuarto(String columnas, Object numCuarto);
    ContentValues getFilaInMensualidadActual(String columnas, Object idAlquiler);
    TableCursor getMensualidadesOfAlquiler(String columnas, Object idAlquiler);
    ContentValues getFilaInUsuariosOf(String columnas, Object DNI);
    TableCursor getAlquileresValidos(String columnas, String key, Object value);
    TableCursor getAllAlquileres(String columnas, String key, Object value);
    ContentValues getFilaAlquilerByCuartoOf(String columnas, Object numCuarto);
    ContentValues getFilaAlquilerOf(String columnas, Object id);
    ContentValues getFilaAlquilerByUserOf(String columnas, Object DNI);
    TableCursor getPagosOf(String columnas, Object idMensualidad);
    String[] getIdOfAllAlquileres();
    String[] consultarNumerosDeCuarto();
    void upDateUsuario(String columna, Object valor, Object DNI);
    void upDateCuarto(String columna, Object valor, Object numeroDeCuarto);
    void upDateAlquiler(String columna, String valor, Object id);
    boolean agregarCuarto(String numCuarto, String detalles, String precio, String URL);
    boolean agregarInquilino(String DNI, String nombres, String apellidoPat, String apellidoMat, String URI);
    boolean agregarAlquiler(String DNI, String numC, String fecha, String fecha_c);
    boolean agregarMensualidad(double costo, String fecha_i, long idA);
    boolean agregarPago(String fecha, long idM, long DNI);
    boolean agregarNuevoInquilino(ModelUsuario mu, String numC, double costo, @NonNull String fecha_i, @Nullable String fecha_c);
    String[] consultarNumerosDeCuartoDisponibles();
    boolean existIntoCuarto(String valor);
    boolean esUsuarioAntiguo(String valor);
    boolean esUsuarioInterno(String valor);
    void startScrips();

    Cursor getAllCuartos(String columnas);

    Cursor getallUsuarios(String columnas);
}

