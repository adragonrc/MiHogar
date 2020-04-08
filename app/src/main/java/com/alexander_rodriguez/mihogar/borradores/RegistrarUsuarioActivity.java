package com.alexander_rodriguez.mihogar.borradores;

public class RegistrarUsuarioActivity {/*
    private EditText etDNI;
    private EditText etNombre;
    private EditText etApellidoPat;
    private EditText etApellidoMat;
    private EditText etPrecio;
    private EditText etCorreo;
    private EditText etNumeroTelef;

    private ImageView ivPhoto;

    private Spinner spNumCuarto;
    private Spinner spDia;
    private Spinner spMes;
    private Spinner spAnio;

    private Spinner spPlazo;

    private RadioGroup radioGroup;
    private String currentImagePath;
    private Bitmap bmGuardar;

    private boolean cancelDialog;

    private DialogInterface.OnClickListener positivo = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Intent i = new Intent(getContext(), HistorialUsuarioActivity.class);
            i.putExtra(TUsuario.DNI, etDNI.getText().toString());
            startActivity(i);

        }
    };
    private DialogInterface.OnClickListener negativo = (dialog, which) -> {
        presenter.confirmar();
        agregarAlquiler();
    };

    @Override
    protected void iniciarComandos() {
        setTitle("Agregar Inquilino");


        ActionBar ab =  getSupportActionBar();
        if (ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
        }
        etDNI.requestFocus();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == BACK_PRESSED)
            onBackPressed();
        return super.onContextItemSelected(item);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_new_user_p1;
    }

    @NonNull
    @Override
    protected interfaz.presenter createPresenter() {
        return new Presenter(this);
    }

    private void agregarAlquiler(){
        //String fecha = spDia.getSelectedItem().toString()+ "/"  + spMes.getSelectedItem().toString() +"/" +spAnio.getSelectedItem().toString();
        String fecha ;
        Save s = new Save();
        int plazo = spPlazo.getSelectedItemPosition();

        fecha =  MyAdminDate.buidFecha(spAnio.getSelectedItem().toString(), spMes.getSelectedItem().toString(), spDia.getSelectedItem().toString());
        currentImagePath = s.SaveImage(this, bmGuardar);
        ModelUsuario mu = new ModelUsuario(etDNI.getText().toString(),  etNombre.getText().toString(), etApellidoPat.getText().toString(), etApellidoMat.getText().toString(), etNumeroTelef.getText().toString(), etCorreo.getText().toString(), "0", currentImagePath);
        presenter.agregarUsuario( mu,
                spNumCuarto.getSelectedItem().toString(),
                etPrecio.getText().toString(),
                plazo,
                fecha,
                presenter.doPago(radioGroup));
    }

    @Override
    public void onClickTomarFoto(View view) {
        CropImage.activity().setMaxCropResultSize(0,0)
                .setAllowFlipping(true)
                .setAspectRatio(15,10)
                .setMinCropResultSize(1000,750)
                .setMaxCropResultSize(3000,2250)
                .start(this);
    }

    public void close(){
        onBackPressed();
    }

    @Override
    public void mostrarEtPlazo() {
        (findViewById(R.id.tilPlazo)).setVisibility(View.VISIBLE);
    }

    @Override
    public void ocultarEtPlazo() {
        (findViewById(R.id.tilPlazo)).setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, "Campo vacio: "+ error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialog(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(s).
                setMessage("Usuario ya existe").
                setPositiveButton("ver historia", positivo).
                setNegativeButton("Agregar", negativo);
        builder.create().show();
    }

    public void prepararSpinsers(ArrayAdapter<String> adapter){
        Date date = new Date();
        int posYear = date.getYear()-119;
        int posMes = date.getMonth();
        int posDia = date.getDate()-1;
        spDia.setSelection(posDia);
        spMes.setSelection(posMes);
        spAnio.setSelection(posYear);
        Spinner spinner =findViewById(R.id.spNumCuartos);
        spinner.setAdapter(adapter);
        String numCuarto = getIntent().getStringExtra(TCuarto.NUMERO);
        if (numCuarto != null) spinner.setSelection(adapter.getPosition(numCuarto));
    }

    @Override
    public void sinCuartos() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mensaje").setMessage("No hay cuartos disponibles")
                .setPositiveButton("Agregar nuevo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cancelDialog = false;
                        startActivity(new Intent(RegistrarUsuarioActivity.this, AgregarCuarto.class));
                    }
                }).setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onBackPressed();
                        }
                    });
        AlertDialog a =builder.create();
        a.setOnDismissListener(dialog -> {
            if (cancelDialog) {
                RegistrarUsuarioActivity.this.onBackPressed();
            }
        });
        a.setOnCancelListener(dialog -> cancelDialog = true);
        a.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                bmGuardar = BitmapFactory.decodeFile(result.getUri().getPath());
                ivPhoto.setImageURI(result.getUri());
            }else{
                if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    Exception e = result.getError();
                    Toast.makeText(this, "Posible Error es: "+ e, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    protected void iniciarViews(){
        etDNI = findViewById(R.id.etDNI);
        etNombre = findViewById(R.id.etNombre);
        etApellidoPat = findViewById(R.id.etApellidoPat);
        etApellidoMat = findViewById(R.id.etApellidoMat);
        etPrecio = findViewById(R.id.etPrecio);
//        etNumeroTelef = findViewById(R.id.etNumeroTelefono);
        etCorreo = findViewById(R.id.etCorreo);

        spNumCuarto = findViewById(R.id.spNumCuartos);
        spDia = findViewById(R.id.spDia);
        spMes= findViewById(R.id.spMes);
        spAnio= findViewById(R.id.spAnio);
        spPlazo = findViewById(R.id.spPlazo);

        radioGroup = findViewById(R.id.radioGrup);
        ivPhoto = findViewById(R.id.ivPhoto);
        spPlazo.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) presenter);
    }

    @Override
    public void onClickPositive(View v) {
        agregarAlquiler();
    }

    @Override
    public void onClickNegative(View v) {
        onBackPressed();
    }*/
}