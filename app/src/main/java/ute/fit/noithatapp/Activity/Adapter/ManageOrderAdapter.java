package ute.fit.noithatapp.Activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ute.fit.noithatapp.Model.OrderModel;
import ute.fit.noithatapp.R;

public class ManageOrderAdapter extends ArrayAdapter<OrderModel> {
    private List<OrderModel> orders;

    public ManageOrderAdapter(Context context, List<OrderModel> orders) {
        super(context, 0, orders);
        this.orders = orders;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.order_manage_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textViewOrderId = convertView.findViewById(R.id.textViewOrderId);
            viewHolder.textViewCustomerName = convertView.findViewById(R.id.textViewCustomerName);
            viewHolder.textViewOrderDate = convertView.findViewById(R.id.textViewOrderDate);
            viewHolder.buttonConfirm = convertView.findViewById(R.id.buttonConfirm);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Lấy thông tin đơn hàng tại vị trí position
        OrderModel order = orders.get(position);

        // Hiển thị thông tin đơn hàng trong các TextView
        viewHolder.textViewOrderId.setText("Order ID: " + order.getOrderId());
        viewHolder.textViewCustomerName.setText("Trạng thái: " + order.getState());
        viewHolder.textViewOrderDate.setText("Order Date: " + order.getDate());

        // Thiết lập sự kiện click cho nút xác nhận đơn hàng
        viewHolder.buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý logic xác nhận đơn hàng
                // ...
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public OrderModel getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateData(ArrayList<OrderModel> orderList) {
        clear();
        addAll(orderList);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView textViewOrderId;
        TextView textViewCustomerName;
        TextView textViewOrderDate;
        Button buttonConfirm;
    }
}

