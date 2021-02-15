package com.alexander_rodriguez.mihogar.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alexander_rodriguez.mihogar.DataBase.items.ItemRoom;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemTenant;
import com.alexander_rodriguez.mihogar.DataBase.models.TAdvance;
import com.alexander_rodriguez.mihogar.DataBase.models.THouse;
import com.alexander_rodriguez.mihogar.DataBase.models.TMonthlyPayment;
import com.alexander_rodriguez.mihogar.DataBase.models.TPayment;
import com.alexander_rodriguez.mihogar.DataBase.models.TRental;
import com.alexander_rodriguez.mihogar.DataBase.models.TRentalTenant;
import com.alexander_rodriguez.mihogar.R;
import com.alexander_rodriguez.mihogar.TableCursor;
import com.alexander_rodriguez.mihogar.modelos.ModelUsuario;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
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
    public static FirebaseAuth.AuthStateListener authStatuslistener;
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
        //colegioReference = firestore.collection(mContext.getString(R.string.collection_colegio));
    }

    @Override
    public void setAuthStateListener(FirebaseAuth.AuthStateListener listener) {
        if(authStatuslistener != null);
            mAuth.removeAuthStateListener(authStatuslistener);
        authStatuslistener = listener;
        mAuth.addAuthStateListener(authStatuslistener);
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    @Override
    public void initData() {
        usuario = mAuth.getCurrentUser();
        if(usuario != null) {
            hogarReference = firestore.collection(mContext.getString(R.string.cHogar));
            hogarDocument = hogarReference.document(usuario.getUid());
        }
    }

    @Override
    public void signOut() {
        mAuth.signOut();
    }

    @Override
    public Task<AuthResult> createUser(String email, String pass) {
        return mAuth.createUserWithEmailAndPassword(email, pass);
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
        getHogarDocument().collection(mContext.getString(R.string.cRoom))
                .whereEqualTo(mContext.getString(R.string.mdRoomCurrentRentalId), null)
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
        getHogarDocument().collection(mContext.getString(R.string.cAlquileres))
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
        return getCuartoCR().whereEqualTo(mContext.getString(R.string.mdRoomCurrentRentalId), null).get();
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
    public Task<QuerySnapshot> getRentalTenant(String field, String ida) {
        return getRentalTenantCR().whereEqualTo(field, ida).get();

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
    public Task<QuerySnapshot> getPayments(String field, Object value) {
        return getPaymentCR().whereEqualTo(field, value).get();
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
        return "0";
    }

    @Override
    public int contDniOfAlquilerUsuario(String idAlquiler) {
        return 0;
    }

    @Override
    public Task<Void> updateHouseDetails(THouse house) {
        return getHogarDocument().set(house);
    }

    @Override
    public Task<Void> updateTenant(String field, Object valor, String DNI) {
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
    public Task<Void> updateMonthlyPayment(String rentalID, String id, String field, Object data) {
        return updateDocument(getMonthlyPaymentDR(rentalID, id), field, data);
    }

    @Override
    public Task<Void> updatePayment(String id, String field, Object data) {
        return updateDocument(getPaymentDR(id), field, data);
    }

    private Task<Void> updateDocument(DocumentReference doc, String field, Object data){
        return doc.update(field, data);
    }

    @Override
    public void upDateAlquilerUsuario(String columna, Object valor, Object idAl, Object dni) {

    }

    @Override
    public void updateCurrentRoomRent(String numCuarto, String rentalId){
        getCuartoDR(numCuarto).update(mContext.getString(R.string.mdRoomCurrentRentalId), rentalId);
    }

    @Override
    public void updateCurrentRentMP(String rentalID, DocumentReference id){
        getRentalDR(rentalID).update(mContext.getString(R.string.mdRentalCurrentMP), id);
    }

    public DocumentReference getDocument(DocumentReference documentReference){
        return firestore.document(documentReference.getPath());
    }

    @Override
    public Task<QuerySnapshot> getAllAdvance(String id) {
        return getAdvanceCR(id).get();
    }

    @Override
    public Task<QuerySnapshot> getAllRoom() {
        return getCuartoCR().get();
    }

    @Override
    public Task<QuerySnapshot> getAllTenants() {
        return getCuartoCR().get();
    }

    @Override
    public Task<DocumentSnapshot> getRental(String currentRentalId) {
        return getRentalDR(currentRentalId).get();
    }

    @Override
    public UploadTask saveRoomPhoto(String numeroCuarto, String path) {
        Uri file = Uri.fromFile(new File(path));
        StorageReference riversRef = getRoomPhotoStorage(numeroCuarto);
        return riversRef.putFile(file);
    }

    @Override
    public UploadTask saveTenantPhoto(String dni, @NotNull String path) {

        Uri file = Uri.fromFile(new File(path));
        StorageReference riversRef = getTenantPhotoStorage(dni);
        return riversRef.putFile(file);
    }

    @Override
    public String getRoomPhotoStoregeAsString(String numeroCuarto) {
        return mContext.getString(R.string.cHogar) + "/" +usuario.getUid()+"/"+mContext.getString(R.string.cRoom)+"/"+numeroCuarto+".jpg";
    }

    @Override
    public String getTenantPhotoStoregeAsString(String dni) {
        return mContext.getString(R.string.cHogar) + "/" +usuario.getUid()+"/"+mContext.getString(R.string.cTenant)+"/"+dni+".jpg";
    }

    @Override
    public FileDownloadTask downloadRoomPhoto(String roomNumber, File localFile) {
        return getRoomPhotoStorage(roomNumber).getFile(localFile);
    }

    @Override
    public String getPathTenant(String DNI) {
        return mContext.getString(R.string.cTenant) + "/" + DNI;
    }

    @Override
    public String getPathRoom(String roomNumber){
        return mContext.getString(R.string.cRoom) + "/" +  roomNumber;
    }

    @Override
    public Task<QuerySnapshot> getTenantHistory(String dni) {
        return getRentalTenantCR().whereEqualTo(mContext.getString(R.string.mdRTDni), dni).get();
    }

    private CollectionReference getRentalTenantCR() {
        return getHogarDocument().collection(mContext.getString(R.string.cRentalTenant));
    }

    private @NotNull StorageReference getRoomPhotoStorage(String numeroCuarto) {
        String path = getRoomPhotoStoregeAsString(numeroCuarto);
        return storageRef.child(path);
    }
    private @NotNull StorageReference getTenantPhotoStorage(String dni) {
        String path = getTenantPhotoStoregeAsString(dni);
        return storageRef.child(path);
    }

    @Override
    public Task<Void> agregarCuarto(ItemRoom room) {
        if (room.getPathImage() != null && !room.getPathImage().isEmpty()){
            saveRoomPhoto(room.getRoomNumber(), room.getPathImage());
        }
        return getCuartoDR(room.getRoomNumber()).set(room.getCuartoRoot());
    }

    @Override
    public Task<DocumentSnapshot> getHouseDR() {
        return getHogarDocument().get();
    }

    @Override
    public CollectionReference getRentalCR(){
        return getHogarDocument().collection(mContext.getString(R.string.cAlquileres));
    }

    @Override
    public DocumentReference getRentalDR(String rentalId) {
        return getRentalCR().document(rentalId);
    }

    @Override
    public CollectionReference getUserCR() {
        return getHogarDocument().collection(mContext.getString(R.string.cTenant));
    }

    @Override
    public Task<DocumentSnapshot> getUser(String dni){
        return getUserDR(dni).get();
    }

    @Override
    public CollectionReference getAlquilerUserCR(){
        return getHogarDocument().collection(mContext.getString(R.string.cRentalTenant));
    }

    @Override
    public CollectionReference getCuartoCR() {
        return getHogarDocument().collection(mContext.getString(R.string.cRoom));
    }

    @Override
    public Task<AuthResult> sigIn(String email, String pass) {
        return mAuth.signInWithEmailAndPassword(email, pass);
    }

    @Override
    public DocumentReference getCuartoDR(String numCuarto) {
        return getCuartoCR().document(numCuarto);
    }


    public DocumentReference getUserDR(String dni) {
        return getUserCR().document(dni);
    }

    @Override
    public CollectionReference getMonthlyPaymentCR(String rentalId){
        return getRentalDR(rentalId).collection(mContext.getString(R.string.cMonthlyPayment));
    }
    public DocumentReference getMonthlyPaymentDR(String rentalID, String id){
        return getMonthlyPaymentCR(rentalID).document(id);
    };

    @Override
    public CollectionReference getPaymentCR() {
        return getHogarDocument().collection(mContext.getString(R.string.cPayment));
    }

    public DocumentReference getPaymentDR(String id) {
        return getPaymentCR().document(id);
    }

    @Override
    public Task<DocumentSnapshot> getPayment(String id) {
        return getPaymentDR(id).get();
    }

    public CollectionReference getAdvanceCR(String id) {
        return getPaymentDR(id).collection(mContext.getString(R.string.cAdvance));
    }


    @Override
    public Task<QuerySnapshot> getRentalsOfRoom(String roomNumber) {
        return  getRentalCR().whereEqualTo(mContext.getString(R.string.mdRentalRoomNumber), roomNumber).get();
    }

    @Override
    public Task<Void> terminateContract(String id, String motivo, String numeroCuarto) {
        return firestore.runTransaction(transaction -> {
            updateRental(mContext.getString(R.string.mdRentalDepartureDate), Timestamp.now(), id);
            updateRental(mContext.getString(R.string.mdRentalReasonExit), motivo, id);
            updateRoom(mContext.getString(R.string.mdRoomCurrentRentalId), null, numeroCuarto);
            return null;
        });
    }

    @Override
    public Task<AuthResult> sigInWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        return mAuth.signInWithCredential(credential);
    }

    @Override
    public FirebaseUser getCurrentUser() {
        usuario = mAuth.getCurrentUser();
        return usuario;
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
    public Task<Void> agregarInquilino(ItemTenant mu) {
        WriteBatch batch = firestore.batch();

        batch.set(getUserCR().document(mu.getDni()), mu);

        return getUserCR().document(mu.getDni()).set(mu);
    }


    @Override
    public Task<Void> agregarInquilinos(ArrayList<ItemTenant> list, String rentalId) {
        WriteBatch batch = firestore.batch();
        for (ItemTenant u: list) {
            if(u.isMain()){
                getRentalDR(rentalId).update(mContext.getString(R.string.mdRentalMainTenant), u.getDni());
            }
            if(!u.getPath().isEmpty())
                saveTenantPhoto(u.getDni(), u.getPath()).addOnFailureListener(e -> {
                    Toast.makeText(mContext, mContext.getString(R.string.sUploadPhotoError), Toast.LENGTH_SHORT).show();
                });
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
    public Task<DocumentReference> addAdvanced(String id, TAdvance advance) {
        return getAdvanceCR(id).add(advance);
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
        return getCuartoCR().whereEqualTo(mContext.getString(R.string.mdRoomCurrentRentalId), null).get();
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
