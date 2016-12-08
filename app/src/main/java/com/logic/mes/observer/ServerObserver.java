package com.logic.mes.observer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.logic.mes.MyApplication;
import com.logic.mes.R;
import com.logic.mes.dialog.MaterialDialog;
import com.logic.mes.entity.server.ServerResult;

import java.util.Map;

import rx.Observer;


public class ServerObserver implements Observer<ServerResult> {

    public ServerDataReceiver receiver;
    public String code;
    public Context context;
    public MaterialDialog noticeDialog;
    public TextView textView;
    public String content;
    public String codeNotVali = ",rk,ck,";


    public ServerObserver(final ServerDataReceiver receiver, String code, Context context) {
        this.receiver = receiver;
        this.code = code;
        this.context = context;

        if (context != null && !codeNotVali.contains("," + code + ",") && noticeDialog == null) {
            //为需要验证是否已提交过数据的模块创建对话框
            noticeDialog = new MaterialDialog(this.context);
            View view = View.inflate(context, R.layout.dialog_msg, null);

            textView = (TextView) view.findViewById(R.id.dialog_msg_content);
            textView.setSingleLine(false);
            content = context.getResources().getString(R.string.process_submited);
            noticeDialog.setTitle(R.string.notice);
            noticeDialog.setContentView(view);

            noticeDialog.setPositiveButton(R.string.yes, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    receiver.serverData();
                    noticeDialog.dismiss(view);
                }
            });

            noticeDialog.setNegativeButton(R.string.no, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    noticeDialog.dismiss(view);
                }
            });
        }
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        //MyApplication.toast(R.string.server_error, false);
        receiver.serverError();
        e.printStackTrace();
    }

    @Override
    public void onNext(ServerResult res) {

        if (res.getCode().equals("1")) {
            MyApplication.toast(res.getInfo(), false);
        }

        receiver.setData(res);

        if (res.getDatas() != null) {
            if (res.getDatas().getBagDatas() != null && res.getDatas().getBagDatas().size() > 0) {

                Map<String, String> dataMap = res.getDatas().getBagDatas().get(0);
                String lrsj = dataMap.get(code + "_lrsj");

                //验证是否已经提交过
                if (lrsj != null && !lrsj.equals("") && context != null && !codeNotVali.contains("," + code + ",")) {
                    textView.setText(String.format(content, lrsj));
                    noticeDialog.show();
                } else {
                    receiver.serverData();
                }
            }
        } else {
            receiver.serverData();
        }
    }

    public interface ServerDataReceiver {
        /***
         * @param res 从服务器获得的数据
         * @Description 把数据传递给监者
         */
        void setData(ServerResult res);

        /***
         * @Description 通知监听者使用服务器返回的数据
         */
        void serverData();

        /***
         * @Description 通知监听者清除数据
         */
        void clear();

        /***
         * @Description 通知监听者响应异常
         */
        void serverError();
    }
}
