package com.bootcamp.wellstudy.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bootcamp.wellstudy.R;
import com.bootcamp.wellstudy.api.OkHttp3Downloader;
import com.bootcamp.wellstudy.api.PicassoOkHttpClient;
import com.bootcamp.wellstudy.model.Student;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;

import static com.bootcamp.wellstudy.Constants.API_BASE_URL;

public class CustomUsersGridViewAdapter extends BaseAdapter {
    private List<Student> students = new ArrayList<>();
    private Context context;
    private Map<String, Bitmap> mImages;

    public CustomUsersGridViewAdapter(Context context, List<Student> students) {
        this.context = context;
        this.students = students;
        mImages = new HashMap<>();
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public int getItemViewType(int position) {
        return Adapter.IGNORE_ITEM_VIEW_TYPE;
    }

    @Override
    public Object getItem(int i) {
        return students.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        PicassoOkHttpClient client = PicassoOkHttpClient.getInstance("admin", "abc125");
        OkHttpClient client2 = client.getService();

        final Picasso mPicasso = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(client2))
                .build();
        View customView = view;

        final Student studentItem = students.get(i);
        LayoutInflater li = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            holder = new ViewHolder();

            customView = li.inflate(R.layout.students_grid_view_item, null);
            holder.faculty = (TextView) customView.findViewById(R.id.faculty_textView);
            holder.name = (TextView) customView.findViewById(R.id.studentName_textView);
            holder.email = (TextView) customView.findViewById(R.id.emain_textView);
            holder.imageView = (ImageView) customView.findViewById(R.id.imageView2);
            customView.setTag(holder);
            Log.e("#########", "creating row for" + studentItem.getSsoId());

        } else {
            holder = (ViewHolder) customView.getTag();
            Log.e("#########", "NOT creating row for" + studentItem.getSsoId());
        }

        String baseUrl = API_BASE_URL + "admin/imageDisplay?id=";
        final String url = baseUrl + studentItem.getId();
        System.out.println("############ now url is: " + url);
        if (mImages.get(url) != null){ //this is needed to scrolling in gridview not reload the pictures
            holder.imageView.setImageBitmap(mImages.get(url));
        System.out.println("############ now url is: ");
            System.out.println("###########@"+mImages.get(url));
    }
        else {
            mPicasso.load(url).resize(150,150).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    System.out.println("#####################"+url);
                    mImages.put(url, bitmap);
                    holder.imageView.setImageBitmap(bitmap);
                    System.out.println("###########___"+mImages.get(url));
                }
                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    Picasso.with(context).load(R.drawable.user_thumbnail).into(holder.imageView);
                }
                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            });
        }

        holder.name.setText(studentItem.getFirstName()+ " "+ studentItem.getLastName());
        holder.faculty.setText("fdf");
        holder.email.setText(studentItem.getEmail());
        return customView;
    }

    public static class ViewHolder {
        //for gridview elements display correctly with scrolling
        ImageView imageView;
        TextView faculty;
        TextView email;
        TextView name;
    }

    private boolean writeResponseBodyToDisk(ResponseBody body,String filename) {
        try {
            File futureStudioIconFile = new File(context.getExternalFilesDir(null) + File.separator + filename);
            Log.d("####", "file not download: " +context.getExternalFilesDir(null) + File.separator + filename );
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    Log.d("####", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}

