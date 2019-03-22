package integracionip.impresoras;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class GestionDialog extends AppCompatDialogFragment {

    private TextView tvInfo;
    private RadioGroup radioGroup;
    private RadioButton rb1,rb2,rb3,rb4;
    private GestionDialog.ExampleDialogListener listener;
    private View view;
    private String option;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setCancelable(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_gestion, null);
        tvInfo = view.findViewById(R.id.tv_gestion);
        radioGroup = view.findViewById(R.id.radio_group);
        rb1 = view.findViewById(R.id.radioButton);
        rb2 = view.findViewById(R.id.radioButton2);
        rb3 = view.findViewById(R.id.radioButton3);
        rb4 = view.findViewById(R.id.radioButton4);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton rb = view.findViewById(checkedId);

                option = rb.getText().toString();

                if(option.equals("Titular Contactado")){
                    option = "1";
                }else if(option.equals("Mensaje con Tercero")){
                    option = "3";
                }else if(option.equals("No hay Contacto")){
                    option = "4";
                }else if(option.equals("Retirado")){
                    option = "15";
                }

            }
        });

        rb1.setChecked(true);
        tvInfo.setText("Selecciona la razón de la gestión de cartera");

        builder.setView(view)
                .setTitle("Gestión de cartera")
                .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.applyTextsGestion(option);
                        dismiss();
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (GestionDialog.ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void applyTextsGestion(String selected);
    }

}
