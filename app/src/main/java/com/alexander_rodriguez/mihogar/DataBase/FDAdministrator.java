package com.alexander_rodriguez.mihogar.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.TableCursor;
import com.alexander_rodriguez.mihogar.modelos.ModelUsuario;
import com.alexander_rodriguez.mihogar.viewregistraralquiler.ModelAA;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;

public class FDAdministrator implements DBInterface{
    public final static int GOOGLE_SING = 123;

    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private Context mContext;
    private FirebaseUser usuario;

    private CollectionReference hogarReference;
    private DocumentReference hogarDocument;

    public FDAdministrator(Context mContext){
        this.mContext = mContext;

        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        usuario = mAuth.getCurrentUser();

        hogarReference = firestore.collection(mContext.getString(R.string.cHogar));
        hogarDocument = hogarReference.document(mAuth.getUid());
        //colegioReference = firestore.collection(mContext.getString(R.string.collection_colegio));
    }

    public CollectionReference getHogarReference() {
        return hogarReference;
    }

    public DocumentReference getHogarDocument() {
        return hogarDocument;
    }

    @Override
    public boolean revertir(String tableName, String columKey, Object key) {
        return false;
    }

    @Override
    public ContentValues getFilaInCuarto(String columnas, Object numCuarto) {
        return null;
    }

    @Override
    public ContentValues getFilaInMensualidadActual(String columnas, Object idAlquiler) {
        return null;
    }

    @Override
    public TableCursor getMensualidadesOfAlquiler(String columnas, Object idAlquiler) {
        return null;
    }

    @Override
    public ContentValues getFilaInUsuariosOf(String columnas, Object DNI) {
        return null;
    }

    @Override
    public String getValueOn(String columna, String tableName, String key, Object value) {
        return null;
    }

    @Override
    public TableCursor getAlquileresValidos(String columnas, String key, Object value) {
        return null;
    }

    private Iterator<QueryDocumentSnapshot> getIteratorForTask(Task<QuerySnapshot> t){
        if(t.isSuccessful()) {
            QuerySnapshot query = t.getResult();
            if (query != null && !query.isEmpty()) {
                return query.iterator();
            }
        }
        return null;
    }
    @Override
    public ArrayList<String> getCuartosAlquilados() {
        ArrayList<String> mList = new ArrayList<>();
        hogarDocument.collection(mContext.getString(R.string.cCuartos))
                .whereEqualTo(mContext.getString(R.string.fAlquilerCurrent), null)
                .get()
                .addOnCompleteListener(task -> {

                    Iterator<QueryDocumentSnapshot> iterator = getIteratorForTask(task);
                    while (iterator.hasNext()){
                        QueryDocumentSnapshot document = iterator.next();
                        mList.add(document.getId());
                    }
                });
        return mList;
    }

    @Override
    public ArrayList<String> getDniEnCasa() {
        return null;
    }

    @Override
    public Cursor getAllAlquileres(String columnas, String key, Object value) {
        hogarDocument.collection(mContext.getString(R.string.cAlquileres))
                .get()
                .addOnCompleteListener(task -> {
                   Iterator<QueryDocumentSnapshot> iterator = getIteratorForTask(task);

                });

        return null;
    }

    @Override
    public Cursor getAllAlquileresJoinUserExept(String columnas, String key, Object value, Object idAlquIgnore) {
        return null;
    }

    @Override
    public Cursor getAllAlquileres(String columnas) {
        return null;
    }

    @Override
    public Cursor getAllAlquilerJoinUser(String columnas) {
        return null;
    }

    @Override
    public Cursor getAllCuartos(String columnas) {
        return null;
    }

    @Override
    public Cursor getAllCuartosJoinAlquiler(String columnas) {
        return null;
    }

    @Override
    public Cursor getCuartosLibres(String columnas) {
        return null;
    }

    @Override
    public Cursor getCuartosAlquilados(String columnas) {
        return null;
    }

    @Override
    public Cursor getallUsuarios(String columnas) {
        return null;
    }

    @Override
    public Cursor getAllUsuariosADDAlert(String columnas) {
        return null;
    }

    @Override
    public Cursor getUsuariosForAlquiler(String columnas, String ida) {
        return null;
    }

    @Override
    public ContentValues getFilaAlquilerByCuartoOf(String columnas, Object numCuarto) {
        return null;
    }

    @Override
    public String[] getDniOfAlquilerUser(Object idAlquiler) {
        return new String[0];
    }

    @Override
    public ContentValues getFilaAlquilerOf(String columnas, Object id) {
        return null;
    }

    @Override
    public ContentValues getFilaAlquilerByUserOf(String columnas, Object DNI) {
        return null;
    }

    @Override
    public TableCursor getPagosOf(String columnas, Object idMensualidad) {
        return null;
    }

    @Override
    public String[] getIdOfAllAlquileres() {
        return new String[0];
    }

    @Override
    public String[] consultarNumerosDeCuarto() {
        return new String[0];
    }

    @Override
    public String contAlquileresOf(String key, Object value) {
        return null;
    }

    @Override
    public int contDniOfAlquilerUsuario(String idAlquiler) {
        return 0;
    }

    @Override
    public void upDateUsuario(String columna, Object valor, Object DNI) {

    }

    @Override
    public void upDateCuarto(String columna, Object valor, Object numeroDeCuarto) {

    }

    @Override
    public void upDateAlquiler(String columna, Object valor, Object id) {

    }

    @Override
    public void upDateAlquilerUsuario(String columna, Object valor, Object idAl, Object dni) {

    }

    @Override
    public boolean agregarCuarto(String numCuarto, String detalles, String precio, String path) {
        return false;
    }

    @Override
    public boolean usuarioAlertado(Object DNI) {
        return false;
    }

    @Override
    public boolean agregarInquilino(String DNI, String nombres, String apellidoPat, String apellidoMat, String URI) {
        return false;
    }

    @Override
    public boolean agregarInquilino(ModelUsuario mu) {
        return false;
    }

    @Override
    public boolean agregarAlquiler(String numC, String fecha, String pagosRealizados, String numTel, String correo) {
        return false;
    }

    @Override
    public boolean agregarAlquilerUsuario(long idAlquiler, String dni, boolean isMain) {
        return false;
    }

    @Override
    public boolean agregarAlquiler(ModelAA model) {
        return false;
    }

    @Override
    public boolean agregarMensualidad(double costo, String fecha_i, long idA) {
        return false;
    }

    @Override
    public boolean agregarPago(String fecha, long idM, long DNI) {
        return false;
    }

    @Override
    public boolean agregarInquilinoExist(String DNI, String numC, double costo, @NonNull String fecha_i, @Nullable String fecha_c, String numTel, String correo) {
        return false;
    }

    @Override
    public boolean agregarNuevoInquilino(ModelUsuario mu, String numC, double costo, @NonNull String fecha_i, @Nullable String fecha_c, String numTel, String correo) {
        return false;
    }

    @Override
    public long getIDMaxMensualidad() {
        return 0;
    }

    @Override
    public long getIdMaxAlquiler() {
        return 0;
    }

    @Override
    public String[] consultarNumerosDeCuartoDisponibles() {
        return new String[0];
    }

    @Override
    public boolean existIntoCuarto(String valor) {
        return false;
    }

    @Override
    public boolean existeUsuario(String dni) {
        return false;
    }

    @Override
    public boolean esUsuarioAntiguo(String dni) {
        return false;
    }

    @Override
    public boolean esUsuarioInterno(String dni) {
        return false;
    }

    @Override
    public void startScrips() {

    }

    @Override
    public Cursor gePagosRealizados(String idMensualidad) {
        return null;
    }

    @Override
    public void agregarVoucher(String fileName, String id) {

    }

    @Override
    public Cursor getPago(String id) {
        return null;
    }

    @Override
    public int getUsuarioResponsableDe(String idAlquiler) {
        return 0;
    }
}
