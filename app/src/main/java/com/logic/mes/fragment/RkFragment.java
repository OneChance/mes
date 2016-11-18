package com.logic.mes.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.logic.mes.IScanReceiver;
import com.logic.mes.MyApplication;
import com.logic.mes.R;
import com.logic.mes.adapter.RkListAdapter;
import com.logic.mes.db.DBHelper;
import com.logic.mes.entity.process.RkDetail;
import com.logic.mes.entity.process.RkProduct;
import com.logic.mes.entity.server.ProcessUtil;
import com.logic.mes.entity.server.ServerResult;
import com.logic.mes.net.NetUtil;
import com.logic.mes.observer.ServerObserver;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import atownsend.swipeopenhelper.SwipeOpenItemTouchHelper;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class RkFragment extends BaseTagFragment implements RkListAdapter.ButtonCallbacks, IScanReceiver, ProcessUtil.SubmitResultReceiver, ServerObserver.ServerDataReceiver {

    public RkFragment() {
        this.tagNameId = R.string.rk_tab_name;
    }

    @InjectView(R.id.rk_v_tm_head)
    TextView tmHead;
    @InjectView(R.id.rk_product_list)
    RecyclerView listView;
    @InjectView(R.id.rk_save)
    Button save;
    @InjectView(R.id.rk_v_jzrq)
    Button jzrq;
    @InjectView(R.id.rk_v_hj)
    TextView hj;

    @InjectView(R.id.rk_b_submit)
    Button submit;
    @InjectView(R.id.rk_b_clear)
    Button clear;

    RkProduct product;
    RkListAdapter dataAdapter;
    FragmentActivity activity;
    IScanReceiver receiver;
    Calendar c;

    ProcessUtil.SubmitResultReceiver submitResultReceiver;
    ServerObserver serverObserver;

    @Override
    public void setReceiver() {
        receiver = this;
        MyApplication.getScanUtil().setReceiver(receiver, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.rk, container, false);
        ButterKnife.inject(this, view);
        activity = getActivity();
        receiver = this;
        submitResultReceiver = this;
        serverObserver = new ServerObserver(this, "rk", activity);

        if (c == null) {
            c = Calendar.getInstance();
        }

        jzrq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        jzrq.setText(year + "-" + (month + 1) + "-" + day);
                        product.setJzrq(jzrq.getText().toString());
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                        .get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.getDetailList().size() > 0) {
                    DBHelper.getInstance(activity).delete(RkProduct.class);
                    DBHelper.getInstance(activity).save(product);
                    MyApplication.toast(R.string.product_save_success);
                } else {
                    MyApplication.toast(R.string.tm_scan_first);
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.getDetailList().size() == 0) {
                    MyApplication.toast(R.string.tm_scan_first);
                } else if (jzrq.getText().equals("")) {
                    MyApplication.toast(R.string.jzrq_need);
                } else {
                    product.setCode("rk");
                    new ProcessUtil(activity).submit(submitResultReceiver, product);
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        if (product == null) {
            product = new RkProduct();
        }

        List<RkProduct> plist = DBHelper.getInstance(activity).query(RkProduct.class);
        if (plist.size() > 0) {
            product = plist.get(0);
            jzrq.setText(product.getJzrq());
            hj.setText(product.getHj());
        }

        dataAdapter = new RkListAdapter(getActivity(), product.getDetailList(), this);
        SwipeOpenItemTouchHelper helper = new SwipeOpenItemTouchHelper(new SwipeOpenItemTouchHelper.SimpleCallback(SwipeOpenItemTouchHelper.START | SwipeOpenItemTouchHelper.END));
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listView.setAdapter(dataAdapter);
        helper.attachToRecyclerView(listView);
        helper.setCloseOnAction(false);

        MyApplication.getScanUtil().setReceiver(receiver, 0);

        return view;
    }

    @Override
    public void removePosition(int position) {
        RkDetail p = product.getDetailList().get(position);
        if (p.getId() != 0) {
            DBHelper.getInstance(activity).delete(p);
        }
        dataAdapter.removePosition(position);
    }

    @Override
    public void scanReceive(String res, int scanCode) {
        tmHead.setText(res);
        NetUtil.SetObserverCommonAction(NetUtil.getServices(false).getBagData(res))
                .subscribe(serverObserver);
    }

    @Override
    public void scanError() {
        MyApplication.toast(R.string.server_error);
    }

    @Override
    public void submitOk() {
        clear();
    }

    @Override
    public void serverData(ServerResult res) {

        if (!checkExist(res.getVal("objCode"))) {
            RkDetail p = new RkDetail();
            p.setLb(res.getVal("objType"));
            p.setTm(res.getVal("objCode"));
            p.setSl(res.getVal("pieces"));
            product.getDetailList().add(p);

            int hjInt = hj.getText().equals("") ? 0 : Integer.parseInt(hj.getText().toString());
            hjInt = new BigDecimal(hjInt).add(new BigDecimal(p.getSl())).intValue();
            hj.setText(hjInt + "");
            product.setHj(hjInt + "");

            dataAdapter.notifyDataSetChanged();
        } else {
            MyApplication.toast(R.string.duplicate_data);
        }
    }

    @Override
    public void clear() {
        tmHead.setText(MyApplication.getResString(R.string.wait_scan));
        jzrq.setText("");
        hj.setText("");
        product.getDetailList().clear();
        dataAdapter.notifyDataSetChanged();
        DBHelper.getInstance(activity).delete(RkProduct.class);
    }

    @Override
    public void serverError() {

    }

    public boolean checkExist(String code) {

        for (RkDetail d : product.getDetailList()) {
            if (d.getTm().equals(code)) {
                return true;
            }
        }

        return false;
    }
}
