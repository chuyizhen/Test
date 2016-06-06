package app.yongjiasoftware.com.test;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import app.yongjiasoftware.com.test.presenter.LoginPresenter;
import app.yongjiasoftware.com.test.view.LoginView;

/**
 * @Title LoginActivity
 * @Description:
 * @Author: alvin
 * @Date: 2016/5/27.11:10
 * @E-mail: 49467306@qq.com
 */
public class LoginActivity extends AppCompatActivity implements LoginView {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private LoginPresenter mLoginPresenter;
    private EditText accountEditText, passwordEditText;
    private Button mButton;
    private ProgressDialog mProgressDialog;
    private RecyclerView mRecyclerView;
    private ArrayList<String> mDatas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginPresenter = new LoginPresenter();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("正在加载...");
        initDatas();
        initViews();
    }

    private void initViews() {
        accountEditText = (EditText) findViewById(R.id.id_account);
        passwordEditText = (EditText) findViewById(R.id.id_password);
        mButton = (Button) findViewById(R.id.id_btn_submit);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginPresenter.Login();
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(new MyAdapter());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    private void initDatas() {
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoginPresenter.attach(this);
    }


    @Override
    public String getAccount() {
        return accountEditText.getText().toString();
    }

    @Override
    public String getPassword() {
        return passwordEditText.getText().toString();
    }

    @Override
    public void showLoading() {
        if (mProgressDialog != null)
            mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null)
            mProgressDialog.hide();
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
        mLoginPresenter.dettach();
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(new TextView(LoginActivity.this));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.MyViewHolder holder, final int position) {
            holder.mTextView.setText(mDatas.get(position));
            holder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(LoginActivity.this, mDatas.get(position), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView mTextView;

            public MyViewHolder(TextView itemView) {
                super(itemView);
                mTextView = itemView;
                mTextView.setPadding(20, 20, 20, 20);
            }
        }
    }
}
