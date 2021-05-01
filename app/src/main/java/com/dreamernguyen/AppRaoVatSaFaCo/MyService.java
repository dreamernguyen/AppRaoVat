package com.dreamernguyen.AppRaoVatSaFaCo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.dreamernguyen.AppRaoVatSaFaCo.Activity.BaiVietChiTietActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.NhanTinActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Activity.TrangCaNhanActivity;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.ThongBao;
import com.dreamernguyen.AppRaoVatSaFaCo.Models.TinNhan;
import com.google.gson.Gson;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MyService extends Service {
    public static List<TinNhan> listTinNhanMoi = new ArrayList<>();
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(MainApplication.HOST);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);

        }
    }

    public MyService() {
    }

    @Override
    public void onCreate() {
        // The service is being created

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mSocket.connect();
        langNgheSocket();
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), MainApplication.CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_main)
                .setContentTitle("App Rao Vặt đang chạy ngầm ")
                .setColor(getResources().getColor(R.color.BlueViolet))
                .build();
        startForeground(123, notification);
        stopForeground(true);
        return super.onStartCommand(intent, flags, startId);

    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void langNgheSocket() {
        // Create an Intent for the activity you want to start

        mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                Log.d("Service", "onCreate: " + mSocket.connected() + "\nid" + mSocket.id());
                if (mSocket.connected()) {
                    Log.d("test", "onCreate: kết nối");
                } else {
                    Log.d("test", "onCreate: Chưa kết nối");
                }
                mSocket.on("tinNhan", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Gson gson = new Gson();
                        TinNhan tinNhan = gson.fromJson(args[0].toString(), TinNhan.class);
                        thongBaoTinNhanMoi(tinNhan);
                        Log.d("Service", "call: " + args[0]);

                    }
                });
                mSocket.on("thongBaoMoi", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Gson gson = new Gson();
                        ThongBao thongBao = gson.fromJson(args[0].toString(), ThongBao.class);
                        Log.d("Service", "call: " + args[0]);
                        if (thongBao.getIdNguoiDung().getId().equals(LocalDataManager.getIdNguoiDung())) {
                            thongBaoMoi(thongBao);
                        }
                    }
                });

            }
        });
        mSocket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("test", "Socket ngủm r");
                stopForeground(true);

                Notification notification = new NotificationCompat.Builder(getApplicationContext(), MainApplication.CHANNEL_ID3)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("SaFaCo xin thông báo : ")
                        .setColor(getResources().getColor(R.color.BlueViolet))
                        .setContentText("Đã mất kết nối với Server")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("Đã mất kết nối với Server"))
                        .build();
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                            int id = (int) new Date().getTime();
                notificationManager.notify(0, notification);
            }
        });
    }
    public void thongBaoTinNhanMoi(TinNhan tinNhan){
        if (tinNhan.getIdNguoiNhan().getId().equals(LocalDataManager.getIdNguoiDung())) {
            Intent resultIntent = new Intent(getApplicationContext(), NhanTinActivity.class);
            resultIntent.putExtra("activity", "ThongBao");
            resultIntent.putExtra("idNguoiDung", tinNhan.getIdNguoiGui().getId());
            resultIntent.putExtra("tenNguoiDung", tinNhan.getIdNguoiGui().getHoTen());
            listTinNhanMoi.add(tinNhan);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addNextIntentWithParentStack(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notification = new NotificationCompat.Builder(getApplicationContext(), MainApplication.CHANNEL_ID2)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle("Có tin nhắn mới từ " + tinNhan.getIdNguoiGui().getHoTen())
                    .setColor(getResources().getColor(R.color.BlueViolet))
                    .setContentText("Server thông báo qua socket : ")
                    .setContentIntent(resultPendingIntent)
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(tinNhan.getNoiDung()))
                    .build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                            int id = (int) new Date().getTime();
            notificationManager.notify(1, notification);
        }else {
            Log.d("Service", "call: Không phải tin của m");
        }


    }
    public void thongBaoMoi(ThongBao thongBao){

        if(thongBao.getLoaiThongBao().equals("BaiViet")){
            Intent resultIntent = new Intent(getApplicationContext(), BaiVietChiTietActivity.class);
            resultIntent.putExtra("chucNang", "xem");
            resultIntent.putExtra("idBaiViet", thongBao.getIdTruyXuat());

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
            stackBuilder.addNextIntentWithParentStack(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notification = new NotificationCompat.Builder(getApplicationContext(), MainApplication.CHANNEL_ID3)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle("SaFaCo xin thông báo : ")
                    .setColor(getResources().getColor(R.color.BlueViolet))
                    .setContentText("Có một thông báo mới dành cho bạn")
                    .setContentIntent(resultPendingIntent)
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(thongBao.getNoiDung()))
                    .build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                            int id = (int) new Date().getTime();
            notificationManager.notify(2, notification);
        }
        if(thongBao.getLoaiThongBao().equals("MatHang")){
            Intent resultIntent = new Intent(getApplicationContext(), BaiVietChiTietActivity.class);
            resultIntent.putExtra("chucNang", "xem");
            resultIntent.putExtra("idMatHang", thongBao.getIdTruyXuat());

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
            stackBuilder.addNextIntentWithParentStack(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notification = new NotificationCompat.Builder(getApplicationContext(), MainApplication.CHANNEL_ID3)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle("SaFaCo xin thông báo : ")
                    .setColor(getResources().getColor(R.color.BlueViolet))
                    .setContentText("Có một thông báo mới dành cho bạn")
                    .setContentIntent(resultPendingIntent)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(thongBao.getNoiDung()))
                    .build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                            int id = (int) new Date().getTime();
            notificationManager.notify(3, notification);
        }
        if(thongBao.getLoaiThongBao().equals("NguoiDung")){
            Intent resultIntent = new Intent(getApplicationContext(), TrangCaNhanActivity.class);
            resultIntent.putExtra("chucNang", "xem");
            resultIntent.putExtra("idNguoiDung", thongBao.getIdTruyXuat());

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
            stackBuilder.addNextIntentWithParentStack(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notification = new NotificationCompat.Builder(getApplicationContext(), MainApplication.CHANNEL_ID3)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setContentTitle("SaFaCo xin thông báo : ")
                    .setColor(getResources().getColor(R.color.BlueViolet))
                    .setContentText("Có một thông báo mới dành cho bạn")
                    .setContentIntent(resultPendingIntent)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(thongBao.getNoiDung()))
                    .build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                            int id = (int) new Date().getTime();
            notificationManager.notify(4, notification);
        }


    }

}