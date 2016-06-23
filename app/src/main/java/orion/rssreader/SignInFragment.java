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

public class SignInFragment extends Fragment implements View.OnClickListener {

    private Button mSignInButton;
    private TextView mUsernameText;
    private TextView mPasswordText;
    private OnSignInListener mOnSignInListener;

    private ProgressDialog mProgressDialog;


    public SignInFragment() {
        // Required empty public constructor
    }

    public interface OnSignInListener {
        void onSignIn(String username, String password);
    }

    // TODO: Rename and change types and number of parameters
    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
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
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mOnSignInListener = (OnSignInListener) getActivity();

        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Signing in");

        mUsernameText = (TextView) getActivity().findViewById(R.id.signin_email_text);
        mPasswordText = (TextView) getActivity().findViewById(R.id.signin_password_text);
        mSignInButton = (Button) getActivity().findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(this);
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
            case R.id.sign_in_button:
                mOnSignInListener.onSignIn(mUsernameText.getText().toString(), mPasswordText.getText().toString());
                break;
        }
    }
}
