package integracionip.impresoras;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class PaymentDialog extends AppCompatDialogFragment {
    private TextView tvRecibo;
    private ExampleDialogListener listener;
    private String resumen, type;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_payment_dialog, null);
        tvRecibo = view.findViewById(R.id.tv_payyyy);
        resumen = getArguments().getString("info");
        type = getArguments().getString("type");

        if(type.equals("config")){

            builder.setView(view)
                    .setTitle("Configuración de cliente")
                    .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setPositiveButton("GUARDAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            listener.applyTexts();
                            dismiss();
                        }
                    });

        } else if(type.equals("gestion")){
            builder.setView(view)
                    .setTitle("Gestión de cartera")
                    .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setPositiveButton("GUARDAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            listener.applyTexts();
                            dismiss();
                        }
                    });
        }else{
            builder.setView(view)
                    .setTitle("Resumen del pago")
                    .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setPositiveButton("RECAUDAR", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            listener.applyTexts();
                            dismiss();
                        }
                    });
        }
        System.out.println(resumen);
        tvRecibo.setText(resumen);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void applyTexts();
    }

}