package com.bw.health.core;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;

import com.bw.health.util.LogUtils;
import com.google.gson.Gson;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class WDFragment extends Fragment {
	public Gson mGson = new Gson();
	public SharedPreferences mShare = WDApplication.getShare();

	private Unbinder unbinder;
	private View view;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


		// 每次ViewPager要展示该页面时，均会调用该方法获取显示的View
		long time = System.currentTimeMillis();
		 view = inflater.inflate(getLayoutId(),container,false);
		ARouter.getInstance().inject(this);
		unbinder = ButterKnife.bind(this,view);
		initView();
		LogUtils.e(this.toString()+"页面加载使用："+(System.currentTimeMillis()-time));
		return view;
	}

	@Nullable
	@Override
	public View getView() {
		return view;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}

	//	@Override
//	public void onResume() {
//		super.onResume();
//		if (!MTStringUtils.isEmpty(getPageName()))
//			MobclickAgent.onPageStart(getPageName()); // 统计页面
//	}
//
//	@Override
//	public void onPause() {
//		super.onPause();
//		if (!MTStringUtils.isEmpty(getPageName()))
//			MobclickAgent.onPageEnd(getPageName());// 统计页面
//	}

	/**
	 * 设置页面名字 用于友盟统计
	 */
	public abstract String getPageName();
	/**
	 * 设置layoutId
	 * @return
	 */
	protected abstract int getLayoutId();

	/**
	 * 初始化视图
	 */
	protected abstract void initView();

	/**
	 * @param mActivity 传送Activity的
	 */
	public void intent(Class mActivity) {
		Intent intent = new Intent(getContext(), mActivity);
		startActivity(intent);
	}

	/**
	 * @param path 传送Activity的
	 */
	public void intentByRouter(String path) {
		ARouter.getInstance().build(path)
				.navigation(getContext());
	}

	/**
	 * @param mActivity 传送Activity的
	 * @param bundle
	 */
	public void intent(Class mActivity, Bundle bundle) {
		Intent intent = new Intent(getContext(), mActivity);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	/**
	 * @param path 传送Activity的
	 * @param bundle
	 */
	public void intentByRouter(String path, Bundle bundle) {
		ARouter.getInstance().build(path)
				.with(bundle)
				.navigation(getContext());
	}

}
