package com.alexander_rodriguez.mihogar.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alexander_rodriguez.mihogar.DataBase.items.ItemRoom;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemUser;
import com.alexander_rodriguez.mihogar.DataBase.models.TMonthlyPayment;
import com.alexander_rodriguez.mihogar.DataBase.models.TPayment;
import com.alexander_rodriguez.mihogar.DataBase.models.TRental;
import com.alexander_rodriguez.mihogar.DataBase.models.TRentalTenant;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.TableCursor;
import com.alexander_rodriguez.mihogar.modelos.ModelUsuario;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;

public class FDAdministrator implements DBInterface{
    public final static int GOOGLE_SING = 123;
    public final static String TAG_SUCCESS = "tag_success";
    public final static String TAG_FAILURE = "tag_failure";
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
    public Task<DocumentSnapshot> getRoom(String numCuarto) {
        return getCuartoDR(numCuarto).get();
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

    @Nullable
    public static  Iterator<QueryDocumentSnapshot> getTasksIterator(@NotNull Task<QuerySnapshot> t){
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
                .whereEqualTo(mContext.getString(R.string.mdRentalCurrentId), null)
                .get()
                .addOnCompleteListener(task -> {

                    Iterator<QueryDocumentSnapshot> iterator = getTasksIterator(task);
                    if(iterator != null)
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
                   Iterator<QueryDocumentSnapshot> iterator = getTasksIterator(task);

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
    public ContentValues getRentByRoom(String columnas, Object numCuarto) {
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
    public void updateCurrentRoomRent(String numCuarto, String rentalId){
        getCuartoDR(numCuarto).update(mContext.getString(R.string.mdRentalCurrentId), rentalId);
    }

    @Override
    public void updateTenantRoomNum(String numCuarto, int num){
        getCuartoDR(numCuarto).update(mContext.getString(R.string.mdTenantsNumber), num);
    }

    @Override
    public void updateCurrentRentMP(String rentalID, DocumentReference id){
        getRentalDR(rentalID).update(mContext.getString(R.string.mdCurrentMP), id);
    }

    public DocumentReference getDocument(DocumentReference documentReference){
        return firestore.document(documentReference.getPath());
    }
    @Override
    public Task<Void> agregarCuarto(ItemRoom room) {

        return getCuartoDR(room.getRoomNumber()).set(room.getCuartoRoot());
    }

    @Override
    public CollectionReference getAlquilerCR(){
        return hogarDocument.collection(mContext.getString(R.string.cAlquileres));
    }

    @Override
    public DocumentReference getRentalDR(String rentalId) {
        return getAlquilerCR().document(rentalId);
    }

    @Override
    public CollectionReference getUserCR() {
        return hogarDocument.collection(mContext.getString(R.string.cTenant));
    }

    @Override
    public CollectionReference getAlquilerUserCR(){
        return hogarDocument.collection(mContext.getString(R.string.cRentalTenant));
    }

    @Override
    public CollectionReference getCuartoCR() {
        return hogarDocument.collection(mContext.getString(R.string.cCuartos));
    }

    @Override
    public DocumentReference getCuartoDR(String numCuarto) {
        return getCuartoCR().document(numCuarto);
    }

    @Override
    public CollectionReference getMonthlyPaymentCR(String rentalId){
        return getRentalDR(rentalId).collection(mContext.getString(R.string.cMonthlyPayment));
    }

    @Override
    public CollectionReference getPaymentCR() {
        return hogarDocument.collection(mContext.getString(R.string.cPayment));
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
    public Task<Void> agregarInquilino(ItemUser mu) {
        WriteBatch batch = firestore.batch();

        batch.set(getUserCR().document(mu.getDni()), mu);

        return getUserCR().document(mu.getDni()).set(mu);
    }


    @Override
    public Task<Void> agregarInquilinos(ArrayList<ItemUser> list, String rentalId) {
        WriteBatch batch = firestore.batch();
        for (ItemUser u: list) {
            TRentalTenant tRentalTenant = new TRentalTenant(rentalId, u.getDni(), u.isMain(), true);
            batch.set(getUserCR().document(u.getDni()), u);
            batch.set(getAlquilerUserCR().document(), tRentalTenant);
        }
        return batch.commit();
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
    public Task<DocumentReference> agregarAlquiler(TRental model) {
        return getAlquilerCR().add(model);
    }

    //Recordar analizar posicion de mensualidad
    @Override
    public Task<DocumentReference> agregarMensualidad(TMonthlyPayment monthlyPayment) {

        return getMonthlyPaymentCR(monthlyPayment.getRentalId()).add(monthlyPayment);
    }

    @Override
    public Task<DocumentReference> agregarPago(TPayment payment) {
        return getPaymentCR().add(payment);

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
    public Task<QuerySnapshot> consultarNumerosDeCuartoDisponibles() {
        return getCuartoCR().whereEqualTo(mContext.getString(R.string.mdRentalCurrentId), null).get();
    }

    @Override
    public boolean existIntoCuarto(String nCuarto) {
        getCuartoDR(nCuarto)
        .get()
        .addOnCompleteListener(task -> {

        });

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

    public interface CallBack{
        void onSuccess(String tag);
        void onFailure(String tag);
    }
}
