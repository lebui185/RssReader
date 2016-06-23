package orion.rssreader;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import java.io.IOException;

public abstract class BaseAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    public interface OnCompleteListener<Result> {
        void onComplete(Result result);
    }

    public interface OnExceptionListener {
        void onException(Exception exception);
    }

    public interface OnIOExceptionListener {
        void onIOException(IOException exception);
    }

    private OnCompleteListener<Result> mOnCompleteListener;

    private Exception mException;
    private IOException mIOException;
    private OnExceptionListener mOnExceptionListener;
    private OnIOExceptionListener mOnIOExceptionListener;

    private boolean mIsComplete = false;
    private boolean mIsAborted = false;

    public void setOnCompleteListener(OnCompleteListener l) {
        mOnCompleteListener = l;
    }

    public void setOnExceptionListener(OnExceptionListener l) {
        mOnExceptionListener = l;
    }

    public void setOnIOExceptionListener(OnIOExceptionListener l) {
        mOnIOExceptionListener = l;
    }

    public void execute() {
        execute(null, null);
    }

    public void abort() {
        mIsAborted = true;
        cancel(true);
    }

    public boolean isComplete() {
        return mIsComplete;
    }

    public boolean isAborted() {
        return mIsAborted;
    }

    protected abstract Result doAsyncTask() throws Exception;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mIsComplete = false;
        mIsAborted = false;
    }

    @Override
    protected Result doInBackground(Params... params) {
        if (isCancelled()) {
            return null;
        }
        try {
            return doAsyncTask();
        }
        catch (IOException e) {
            mIOException = e;
            return null;
        }
        catch (Exception e) {
            mException = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        mIsComplete = true;

        if (isCancelled() || isAborted()) {
            return;
        }

        if (mIOException != null) {
            if (mOnIOExceptionListener != null)
                mOnIOExceptionListener.onIOException(mIOException);
            if (mOnExceptionListener != null)
                mOnExceptionListener.onException(mIOException);
        }
        else if (mException != null) {
            if (mOnExceptionListener != null)
                mOnExceptionListener.onException(mException);
        }
        else {
            if (mOnCompleteListener != null) {
                mOnCompleteListener.onComplete(result);
            }
        }
    }
}
