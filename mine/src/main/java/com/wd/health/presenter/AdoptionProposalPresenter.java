package com.wd.health.presenter;

import com.bw.health.core.DataCall;
import com.bw.health.core.WDPresenter;
import com.wd.health.Minerequest;

import io.reactivex.Observable;

/**
 * @Auther: 郭亚杰
 * @Date:2019/7/31
 * @Description: 采纳病友圈优秀的评论
 */
public class AdoptionProposalPresenter extends WDPresenter<Minerequest> {
    public AdoptionProposalPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(Object... args) {
        return iRequest.adoptionProposal((String)args[0],(String)args[1],(String)args[2],(String)args[3]);
    }
}
