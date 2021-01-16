package com.alexander_rodriguez.mihogar.DataBase;

import android.content.ContentValues;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alexander_rodriguez.mihogar.DataBase.items.ItemRoom;
import com.alexander_rodriguez.mihogar.DataBase.items.ItemTenant;
import com.alexander_rodriguez.mihogar.DataBase.models.TMonthlyPayment;
import com.alexander_rodriguez.mihogar.DataBase.models.TPayment;
import com.alexander_rodriguez.mihogar.DataBase.models.TRental;
import com.alexander_rodriguez.mihogar.TableCursor;
import com.alexander_rodriguez.mihogar.modelos.ModelUsuario;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;


public interface DBInterface {

    static ContentValues cursorToCV(@NotNull Cursor cursor){
        ContentValues cv = new ContentValues();
        for (int i = 0; i<cursor.getColumnCount(); i++){
            cv.put(cursor.getColumnName(i),cursor.getString(i));
        }
        return cv;
    }

    Task<AuthResult> sigIn(String email, String pass);

    DocumentReference getCuartoDR(String numCuarto);

    CollectionReference getCuartoCR();

    CollectionReference getRentalCR();

    DocumentReference getRentalDR(String rentalId);

    CollectionReference getUserCR();

    DocumentReference getUserDR(String dni);

    Task<DocumentSnapshot> getUser(String dni);

    CollectionReference getAlquilerUserCR();

    CollectionReference getMonthlyPaymentCR(String rentalId);

    CollectionReference getPaymentCR();

    boolean revertir(String tableName, String columKey, Object key);

    Task<DocumentSnapshot> getRoom(String numCuarto);

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

    Task<QuerySnapshot> getAllCuartosJoinAlquiler(String columnas);

    Task<QuerySnapshot> getEmptyRooms();

    Cursor getCuartosAlquilados(String columnas);

    Cursor getallUsuarios(String columnas);

    Cursor getAllUsuariosADDAlert(String columnas);

    Task<QuerySnapshot> getRentalTenant(String field, String ida);

    ContentValues getRentByRoom(String columnas, Object numCuarto);

    String[] getDniOfAlquilerUser(Object idAlquiler);

    ContentValues getFilaAlquilerOf(String columnas, Object id);

    ContentValues getFilaAlquilerByUserOf(String columnas, Object DNI);

    Task<QuerySnapshot> getPayments(String field, Object value);

    String[] getIdOfAllAlquileres();

    String[] consultarNumerosDeCuarto();

    String contAlquileresOf(String key, Object value);

    int contDniOfAlquilerUsuario(String idAlquiler);

    Task<Void> updateTenant(String field, Object valor, String DNI);

    Task<Void> updateRoom(String field, Object valor, String numeroDeCuarto);

    Task<Void> updateRental(String columna, Object valor, String id);

    void upDateAlquilerUsuario(String columna, Object valor, Object idAl, Object dni);

    Task<Void> agregarCuarto(ItemRoom room);

    boolean usuarioAlertado(Object DNI);

    boolean agregarInquilino(String DNI, String nombres, String apellidoPat, String apellidoMat, String URI);

    Task<Void> agregarInquilino(ItemTenant mu);

    Task<Void> agregarInquilinos(ArrayList<ItemTenant> list, String  idAlquiler);

    boolean agregarAlquiler(String numC, String fecha, String pagosRealizados, String numTel, String correo);

    boolean agregarAlquilerUsuario(long idAlquiler, String dni, boolean isMain);

    Task<DocumentReference> agregarAlquiler(TRental model);

    Task<DocumentReference> agregarMensualidad( TMonthlyPayment monthlyPayment );

    Task<DocumentReference> addPayment(TPayment payment);

    boolean agregarInquilinoExist(String DNI, String numC, double costo, @NonNull String fecha_i, @Nullable String fecha_c, String numTel, String correo);

    boolean agregarNuevoInquilino(ModelUsuario mu, String numC, double costo, @NonNull String fecha_i, @Nullable String fecha_c, String numTel, String correo);

    long getIDMaxMensualidad();

    long getIdMaxAlquiler();

    Task<QuerySnapshot> consultarNumerosDeCuartoDisponibles();

    boolean existIntoCuarto(String valor);

    Task<DocumentSnapshot> existeUsuario(String dni);

    boolean esUsuarioAntiguo(String dni);

    boolean esUsuarioInterno(String dni);

    void startScrips();

    Cursor gePagosRealizados(String idMensualidad);

    void agregarVoucher(String fileName, String id);

    Cursor getPago(String id);

    int getUsuarioResponsableDe(String idAlquiler);

    void updateCurrentRoomRent(String numCuarto, String rentalId);

    void updateCurrentRentMP(String rentalId, DocumentReference id);

    DocumentReference getDocument(DocumentReference currentMP);

    Task<QuerySnapshot> getAllRoom();

    Task<DocumentSnapshot> getRental(String currentRentalId);

    UploadTask saveRoomPhoto(String numeroCuarto, String path);

    UploadTask saveTenantPhoto(String dni, String path);

    String getRoomPhotoStoregeAsString(String numeroCuarto) ;

    String getTenantPhotoStoregeAsString(String dni);

    FileDownloadTask downloadRoomPhoto(String roomNumber,@NonNull File localFile);

    String getPathTenant(String DNI);

    String getPathRoom(String roomNumber);

    Task<QuerySnapshot> getTenantHistory(String dni);

    Task<QuerySnapshot> getRentalsOfRoom(String roomNumber);

    Task<Void> terminateContract(String id,  String motivo, String numeroCuarto);

    Task<AuthResult> sigInWithGoogle(GoogleSignInAccount account);

    FirebaseUser getCurrentUser();

    void initData();

    void signOut();
}
