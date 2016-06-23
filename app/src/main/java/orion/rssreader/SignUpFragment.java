package orion.rssreader;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SignUpFragment extends Fragment implements View.OnClickListener {

    private Button mSignUpButton;
    private TextView mUsernameText;
    private TextView mPasswordText;
    private OnSignUpListener mOnSignUpListener;

    private ProgressDialog mProgressDialog;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public interface OnSignUpListener {
        void onSignUp(String username, String password);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onActivityCreated(savedInstanceState);
        mOnSignUpListener = (OnSignUpListener) getActivity();

        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Signing up");

        mUsernameText = (TextView) getActivity().findViewById(R.id.signup_email_text);
        mPasswordText = (TextView) getActivity().findViewById(R.id.signup_password_text);
        mSignUpButton = (Button) getActivity().findViewById(R.id.signup_button);
        mSignUpButton.setOnClickListener(this);
    }

    public void showProgress() {
        mProgressDialog.show();
    }

    public void hideProgress() {
        mProgressDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup_button:
                mOnSignUpListener.onSignUp(mUsernameText.getText().toString(), mPasswordText.getText().toString());
                break;
        }
    }

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

}
