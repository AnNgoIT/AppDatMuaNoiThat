package ute.fit.noithatapp.Activity.Adapter;

import static ute.fit.noithatapp.Contants.Const.ROOT_URL;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ute.fit.noithatapp.Api.OrderApi;
import ute.fit.noithatapp.Contants.RetrofitServer;
import ute.fit.noithatapp.Model.OrderModel;
import ute.fit.noithatapp.R;

public class ManageOrderAdapter extends ArrayAdapter<OrderModel> {
    private List<OrderModel> orders;
    private OrderApi orderApi;
    private RetrofitServer retrofitServer;


    public ManageOrderAdapter(Context context, List<OrderModel> orders) {
        super(context, 0, orders);
        this.orders = orders;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        retrofitServer = new RetrofitServer();
        orderApi = retrofitServer.getRetrofit(ROOT_URL).create(OrderApi.class);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.order_manage_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textViewOrderId = convertView.findViewById(R.id.textViewOrderId);
            viewHolder.textViewCustomerName = convertView.findViewById(R.id.textViewState);
            viewHolder.textViewOrderDate = convertView.findViewById(R.id.textViewOrderDate);
            viewHolder.buttonConfirm = convertView.findViewById(R.id.buttonConfirm);
            viewHolder.buttonDelete = convertView.findViewById(R.id.buttonDelete);

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

        if (order != null) {
            if (order.getState().equals("processed")) {
                viewHolder.buttonConfirm.setEnabled(false);
                viewHolder.buttonConfirm.setAlpha(0.2f);
            } else {
                viewHolder.buttonConfirm.setEnabled(true);
                viewHolder.buttonConfirm.setAlpha(1.0f);
            }
        }
        // Thiết lập sự kiện click cho nút xác nhận đơn hàng
        viewHolder.buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int orderId = order.getOrderId();
                orderApi.changeStateOrder(orderId).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Chuyển trạng thái thành công
                            order.setState("processed");
                            notifyDataSetChanged();
                            Toast.makeText(getContext(), "Chuyển trạng thái thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            String errorMessage = "Lỗi " + response.code() + ": " + response.message();
                            Log.e("TAG", errorMessage);
                            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("TAG", "Gọi API thất bại: " + t.getMessage());
                        Toast.makeText(getContext(), "Gọi API thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        viewHolder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int orderId = order.getOrderId();
                orderApi.deleteOrder(orderId).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            // Xóa order thành công
                            notifyDataSetChanged();
                            // Gọi lại API để lấy danh sách đơn hàng mới
                            orderApi.getAllOrders().enqueue(new Callback<ArrayList<OrderModel>>() {
                                @Override
                                public void onResponse(Call<ArrayList<OrderModel>> call, Response<ArrayList<OrderModel>> response) {
                                    if (response.isSuccessful()) {
                                        ArrayList<OrderModel> updatedOrders = response.body();
                                        // Cập nhật danh sách đơn hàng
                                        updateData(updatedOrders);
                                        Toast.makeText(getContext(), "Xóa Order thành công", Toast.LENGTH_SHORT).show();
                                    } else {
                                        String errorMessage = "Lỗi " + response.code() + ": " + response.message();
                                        Log.e("TAG", errorMessage);
                                        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ArrayList<OrderModel>> call, Throwable t) {
                                    Log.e("TAG", "Gọi API thất bại: " + t.getMessage());
                                    Toast.makeText(getContext(), "Gọi API thất bại", Toast.LENGTH_SHORT).show();
                                }
                            });
                            Toast.makeText(getContext(), "Xóa Order thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            String errorMessage = "Lỗi " + response.code() + ": " + response.message();
                            Log.e("TAG", errorMessage);
                            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("TAG", "Gọi API thất bại: " + t.getMessage());
                        Toast.makeText(getContext(), "Gọi API thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
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
        ImageButton buttonConfirm;
        ImageButton buttonDelete;
    }
}

