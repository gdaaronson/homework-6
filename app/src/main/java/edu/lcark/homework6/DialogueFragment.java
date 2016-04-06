package edu.lcark.homework6;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Greg on 4/3/2016.
 */
public class DialogueFragment extends DialogFragment {

    private EditText title;
    private EditText snippet;
    private MapListener mapListener;

    public interface MapListener{
        void onOkayButtonPushed(String title, String snippet);
    }


    public void setMapListener(MapListener mapListener) {
        this.mapListener = mapListener;
    }

    public static DialogueFragment newInstance(String location, MapListener mapListener) {
        DialogueFragment frag = new DialogueFragment();
        frag.setMapListener(mapListener);
        Bundle args = new Bundle();
        args.putString("location", location);
        frag.setArguments(args);
        return frag;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_d, null);
        title = (EditText) rootView.findViewById(R.id.fragment_d_title);
        snippet = (EditText) rootView.findViewById(R.id.fragment_d_snippet);
        AlertDialog builder = new AlertDialog.Builder(getActivity())
                .setView(rootView)
                .setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mapListener.onOkayButtonPushed(title.getText().toString(), snippet.getText().toString());
            }
        })
        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create();
        return builder;
    }

}
