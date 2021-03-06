package com.clever.www.busbar;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.clever.www.busbar.boxline.BoxLineFragment;
import com.clever.www.busbar.branch.BranchFragment;
import com.clever.www.busbar.home.HomeFragment;
import com.clever.www.busbar.line.LineFragment;
import com.clever.www.busbar.login.LoginStatus;
import com.clever.www.busbar.navigation.NavigationFragment;
import com.clever.www.busbar.net.NetRecvThread;
import com.clever.www.busbar.setting.SetCheckPwdActivity;

public class MainActivity extends AppCompatActivity {
    private NetRecvThread mNetRecvThread = new NetRecvThread();
    private NavigationFragment mNavigationFragment = null;
    private BoxLineFragment mBoxLineFragment = null;
    private BranchFragment mBranchFragment = null;
    private LineFragment mLineFragment = null;
    private HomeFragment mHomeFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        initFun();
        mNavigationFragment = (NavigationFragment) getSupportFragmentManager()
                .findFragmentById( R.id.navigation_lzy);
    }

    private void initFun() {
        mNetRecvThread.initNet();
    }


    private boolean startSetMenuActivity() {
        boolean ret = true;
        if(LoginStatus.isLogin) {
            SetCheckPwdActivity.actionStart(MainActivity.this);
        } else {
            Toast.makeText(this, R.string.set_no_login, Toast.LENGTH_SHORT).show();
            ret = false;
        }

        return  ret;
    }


    public void btmenuChanged(int id) {

        if(id == 4) {
            startSetMenuActivity();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideAllFragment(transaction);

        switch (id) {
            case 0:
                if(mHomeFragment == null){
                    mHomeFragment = new HomeFragment();
                    transaction.add(R.id.content,mHomeFragment);
                }
                transaction.show(mHomeFragment);
                break;

            case 1:
                if(mLineFragment == null){
                    mLineFragment = new LineFragment();
                    transaction.add(R.id.content,mLineFragment);
                }
                transaction.show(mLineFragment);
                break;

            case 2:
                if(mBranchFragment == null){
                    mBranchFragment = new BranchFragment();
                    transaction.add(R.id.content,mBranchFragment);
                }
                transaction.show(mBranchFragment);
                break;

            case 3:
                if(mBoxLineFragment == null){
                    mBoxLineFragment = new BoxLineFragment();
                    transaction.add(R.id.content, mBoxLineFragment);
                }
                transaction.show(mBoxLineFragment);
                break;

            default:
                mNavigationFragment.setHomeBtn();
                return;
        }
        transaction.commit();
    }


    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (mBoxLineFragment != null) fragmentTransaction.hide(mBoxLineFragment);
        if (mBranchFragment != null) fragmentTransaction.hide(mBranchFragment);
        if (mLineFragment != null) fragmentTransaction.hide(mLineFragment);
        if (mHomeFragment != null) fragmentTransaction.hide(mHomeFragment);
    }

}
