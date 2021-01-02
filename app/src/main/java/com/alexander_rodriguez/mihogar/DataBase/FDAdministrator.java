package com.alexander_rodriguez.mihogar.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class FDAdministrator implements DBInterface{
    public final static int GOOGLE_SING = 123;
    public final static String TAG_SUCCESS = "tag_success";
    public final static String TAG_FAILURE = "tag_failure";
    private FirebaseFirestore firestore;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private FirebaseAuth mAuth;
    private final Context mContext;
    private FirebaseUser usuario;

    private CollectionReference hogarReference;
    private DocumentReference hogarDocument;

    public FDAdministrator(Context mContext){
        this.mContext = mContext;
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        firestore = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null)
            FirebaseAuth.getInstance().signInWithEmailAndPassword("alexrodriguez@gmail.com","hola12").addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    usuario = authResult.getUser();
                    hogarReference = firestore.collection(mContext.getString(R.string.cHogar));
                    hogarDocument = hogarReference.document(usuario.getUid());
                    Toast.makeText(mContext, "login success", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(mContext, "can not login", Toast.LENGTH_SHORT).show();
                        }
                    });

        else{
            Toast.makeText(mContext, "really success", Toast.LENGTH_SHORT).show();
            usuario = mAuth.getCurrentUser();
            hogarReference = firestore.collection(mContext.getString(R.string.cHogar));
            hogarDocument = hogarReference.document(usuario.getUid());
        }
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
        hogarDocument.collection(mContext.getString(R.string.cRoom))
                .whereEqualTo(mContext.getString(R.string.mdCurrentRentalId), null)
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
    public Task<QuerySnapshot> getAllCuartosJoinAlquiler(String columnas) {
        return null;
    }

    @Override
    public Task<QuerySnapshot> getEmptyRooms() {
        return getCuartoCR().whereEqualTo(mContext.getString(R.string.mdroomCurrentRentalId), null).get();
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
    public Task<Void> upDateUser(String field, Object valor, String DNI) {
    return getUserDR(DNI).update(field, valor);
    }

    @Override
    public Task<Void> updateRoom(String field, Object valor, String numeroDeCuarto) {
        return getCuartoDR(numeroDeCuarto).update(field, valor);
    }

    @Override
    public Task<Void> updateRental(String field, Object valor, String id) {
        return getRentalDR(id).update(field, valor);
    }

    @Override
    public void upDateAlquilerUsuario(String columna, Object valor, Object idAl, Object dni) {

    }

    @Override
    public void updateCurrentRoomRent(String numCuarto, String rentalId){
        getCuartoDR(numCuarto).update(mContext.getString(R.string.mdCurrentRentalId), rentalId);
    }

    @Override
    public void updateTenantRoomNum(String numCuarto, int num){
        getCuartoDR(numCuarto).update(mContext.getString(R.string.mdRoomTenantsNumber), num);
    }

    @Override
    public void updateCurrentRentMP(String rentalID, DocumentReference id){
        getRentalDR(rentalID).update(mContext.getString(R.string.mdRentalCurrentMP), id);
    }

    public DocumentReference getDocument(DocumentReference documentReference){
        return firestore.document(documentReference.getPath());
    }

    @Override
    public Task<QuerySnapshot> getAllRoom() {
        return getCuartoCR().get();
    }

    @Override
    public Task<DocumentSnapshot> getRental(String currentRentalId) {
        return getRentalDR(currentRentalId).get();
    }

    @Override
    public UploadTask saveRoomPhoto(String numeroCuarto, String path) {
        Uri file = Uri.fromFile(new File(path));
        StorageReference riversRef = getRoomPhotoStorege(numeroCuarto);
        return riversRef.putFile(file);
    }

    @Override
    public String getRoomPhotoStoregeAsString(String numeroCuarto) {
        return mContext.getString(R.string.cHogar) + "/" +usuario.getUid()+"/"+mContext.getString(R.string.cRoom)+"/"+numeroCuarto+".jpg";
    }

    @Override
    public FileDownloadTask downloadRoomPhoto(String roomNumber, File localFile) {
        return getRoomPhotoStorege(roomNumber).getFile(localFile);
    }

    @Override
    public String getPathTenant(String DNI) {
        return mContext.getString(R.string.cTenant) + "/" + DNI;
    }

    @Override
    public String getPathRoom(String roomNumber){
        return mContext.getString(R.string.cRoom) + "/" +  roomNumber;
    }
    private @NotNull StorageReference getRoomPhotoStorege(String numeroCuarto) {
        String path = getRoomPhotoStoregeAsString(numeroCuarto);
        return storageRef.child(path);
    }

    @Override
    public Task<Void> agregarCuarto(ItemRoom room) {

        return getCuartoDR(room.getRoomNumber()).set(room.getCuartoRoot());
    }

    @Override
    public CollectionReference getRentalCR(){
        return hogarDocument.collection(mContext.getString(R.string.cAlquileres));
    }

    @Override
    public DocumentReference getRentalDR(String rentalId) {
        return getRentalCR().document(rentalId);
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
        return hogarDocument.collection(mContext.getString(R.string.cRoom));
    }

    @Override
    public DocumentReference getCuartoDR(String numCuarto) {
        return getCuartoCR().document(numCuarto);
    }

    public DocumentReference getUserDR(String numCuarto) {
        return getUserCR().document(numCuarto);
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
            if(u.isMain()){
                getRentalDR(rentalId).update(mContext.getString(R.string.mdRentalMainTenant), u.getDni());
            }
            TRentalTenant tRentalTenant = new TRentalTenant(rentalId, u.getDni(), u.isMain(), true);
            batch.set(getUserCR().document(u.getDni()), u.getRoot());
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
        return getRentalCR().add(model);
    }

    //Recordar analizar posicion de mensualidad
    @Override
    public Task<DocumentReference> agregarMensualidad(TMonthlyPayment monthlyPayment) {
        return getMonthlyPaymentCR(monthlyPayment.getRentalId()).add(monthlyPayment);
    }

    @Override
    public Task<DocumentReference> addPayment(TPayment payment) {
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
        return getCuartoCR().whereEqualTo(mContext.getString(R.string.mdCurrentRentalId), null).get();
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
    public Task<DocumentSnapshot> existeUsuario(String dni) {
        return  getUserDR(dni).get();
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
