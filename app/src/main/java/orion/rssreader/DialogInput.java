package orion.rssreader;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

/**
 * Created by Ho Vu Anh Khoa on 24/06/2016.
 */
public class DialogInput {

    public interface OnDialogInputSuccessListener {
        void onDialogInputSuccess(String name);
    }

    public interface OnDialogInputFailListener {
        void onDialogInputFail();
    }

    private OnDialogInputFailListener onDialogInputFailListener;
    private OnDialogInputSuccessListener onDialogInputSuccessListener;

    String mTitle;
    String mMessage;
    Context mContext;

    public DialogInput(String title, String message, Context context) {
        this.mTitle = title;
        this.mMessage = message;
        this.mContext = context;
    }

    public void showDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);

        alert.setTitle(mTitle);
        alert.setMessage(mMessage);

        final EditText input = new EditText(mContext);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (onDialogInputSuccessListener != null) {
                    String name = input.getText().toString();
                    onDialogInputSuccessListener.onDialogInputSuccess(name);
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (onDialogInputFailListener != null) {
                    onDialogInputFailListener.onDialogInputFail();
                }
            }
        });

        alert.show();
    }

    public void setOnDialogInputFailListener(OnDialogInputFailListener onDialogInputFailListener) {
        this.onDialogInputFailListener = onDialogInputFailListener;
    }

    public void setOnDialogInputSuccessListener(OnDialogInputSuccessListener onDialogInputSuccessListener) {
        this.onDialogInputSuccessListener = onDialogInputSuccessListener;
    }
}
