package com.example.contact;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;
import java.util.UUID;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link contactfragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link contactfragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class contactfragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBERprivate static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
    private Contact cont;
    EditText fnamefield;
    EditText lnamefield;
    EditText pinfield;
    EditText number;
    EditText relation;
    static String tags="crime_id";
//    private OnFragmentInteractionListener mListener;
//
//    public contactfragment() {
//        // Required empty public constructor
//    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment contactfragment.
//     */
//     TODO: Rename and change types and number of parameters
//    public static contactfragment newInstance(String param1, String param2) {
//        contactfragment fragment = new contactfragment();
//        Bundle args = new Bundle();
////        args.putString(ARG_PARAM1, param1);
////        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    UUID crimeId = (UUID) getArguments().getSerializable(tags);
    cont = Contactcreator.getContact(getActivity()).getcontactusingid(crimeId);
    Log.d("so gaya",""+crimeId);
}
public static contactfragment newInstance(UUID crimeId) {
    Bundle args = new Bundle();
    args.putSerializable(tags, crimeId);

    contactfragment fragment = new contactfragment();
    fragment.setArguments(args);
    return fragment;
}

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        cont=new Contact();
//        if (getArguments() != null) {
////            mParam1 = getArguments().getString(ARG_PARAM1);
////            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_contactfragment, container, false);
        fnamefield=(EditText)v.findViewById(R.id.firstname);
        fnamefield.setText(cont.firstname);
        lnamefield=(EditText)v.findViewById(R.id.lastname);
        lnamefield.setText(cont.lastname);
        pinfield=(EditText)v.findViewById(R.id.pinno);
        pinfield.setText(cont.pincode);

        number=(EditText)v.findViewById(R.id.phoneno);
        number.setText(cont.number);
        relation=(EditText)v.findViewById(R.id.organisation);
        relation.setText(cont.relation);
        fnamefield.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cont.setFirstname(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        lnamefield.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cont.setLastname(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        pinfield.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                int n=Integer.parseInt(String.valueOf(charSequence));
                cont.setPincode(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cont.setNumber(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        relation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cont.setRelation(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return v;
    }
    @Override
    public void onPause(){
    super.onPause();
    Contactcreator.getContact(getActivity()).updatecontact(cont);
    }


    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
