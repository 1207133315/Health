package com.bw.health.util;

import com.bw.health.core.WDApplication;
import com.bw.health.dao.DaoMaster;
import com.bw.health.dao.LoginBeanDao;

/**
 * com.bw.health.util
 *
 * @author 李宁康
 * @date 2019 2019/07/15 21:02
 */
public class GetDaoUtil {
    private static GetDaoUtil getDaoUtil=new GetDaoUtil();

    public static GetDaoUtil getGetDaoUtil() {
        return getDaoUtil;
    }
    private  GetDaoUtil(){}

    public LoginBeanDao getUserDao(){
         LoginBeanDao loginBeanDao = DaoMaster.newDevSession(WDApplication.getContext(), LoginBeanDao.TABLENAME).getLoginBeanDao();
         return loginBeanDao;
    }
}
