package orion.rssreader;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by Ho Vu Anh Khoa on 24/06/2016.
 */
public class DialogQuestion {

    public interface OnDialogQuestionSuccessListener {
        void onDialogQuestionSuccess();
    }

    public interface OnDialogQuestionFailListener {
        void onDialogQuestionFail();
    }

    private OnDialogQuestionFailListener onDialogQuestionFailListener;
    private OnDialogQuestionSuccessListener onDialogQuestionSuccessListener;

    String mTitle;
    String mMessage;
    Context mContext;
    String mYesAnswer = "Yes";
    String mNoAnswer = "No";

    public DialogQuestion(Context context, String title, String message) {
        this.mTitle = title;
        this.mMessage = message;
        this.mContext = context;
    }

    public DialogQuestion(Context context, String title, String message, String yesAnswer, String noAnswer) {
        this.mTitle = title;
        this.mMessage = message;
        this.mContext = context;
        this.mYesAnswer = yesAnswer;
        this.mNoAnswer = noAnswer;
    }

    public void showDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);

        alert.setTitle(mTitle);
        alert.setMessage(mMessage);

        alert.setPositiveButton(mYesAnswer, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (onDialogQuestionSuccessListener != null) {
                    onDialogQuestionSuccessListener.onDialogQuestionSuccess();
                }
            }
        });

        alert.setNegativeButton(mNoAnswer, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (onDialogQuestionFailListener != null) {
                    onDialogQuestionFailListener.onDialogQuestionFail();
                }
            }
        });

        alert.show();
    }

    public void setOnDialogQuestionFailListener(OnDialogQuestionFailListener onDialogQuestionFailListener) {
        this.onDialogQuestionFailListener = onDialogQuestionFailListener;
    }

    public void setOnDialogQuestionSuccessListener(OnDialogQuestionSuccessListener onDialogQuestionSuccessListener) {
        this.onDialogQuestionSuccessListener = onDialogQuestionSuccessListener;
    }
}
