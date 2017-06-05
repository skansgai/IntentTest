package utill;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.yss.intent.intenttest.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yang on 2016/11/13.
 */

public class MP3Utill {
    ListView musicListView;
    MusicItenOnclickListener musicItenOnclickListener;
    SimpleAdapter mSimpleadapte;
    public List<Mp3info> getMp3infos(Context context){
        Cursor cursor=context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER
        );
        List<Mp3info> mp3infos=new ArrayList<Mp3info>();
        //去除数据库中的歌曲
        for (int i=0;i<cursor.getCount();i++){
            Mp3info mp3info=new Mp3info();
            cursor.moveToNext();
            long id=cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));//媒体文件的id
            String title=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));//媒体文件的标题
            String artist = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.ARTIST));//艺术家
            long duration = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media.DURATION));//时长
            long size = cursor.getLong(cursor
                    .getColumnIndex(MediaStore.Audio.Media.SIZE));  //文件大小
            String url = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Audio.Media.DATA));              //文件路径
            int isMusic = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));//是否为音乐
            if (isMusic != 0) {     //只把音乐添加到集合当中
                mp3info.setId(id);
                mp3info.setTitle(title);
                mp3info.setArtist(artist);
                mp3info.setDuration(duration);
                mp3info.setSize(size);
                mp3info.setUrl(url);
                mp3infos.add(mp3info);
            }
            Log.i("Adapter","获得了数据——————————————————————————");
        }
        return  mp3infos;
    }
    public void setListAdapter(List<Mp3info> mp3infos, Context context, View v){
        List<HashMap<String,String>> mapList=new ArrayList<HashMap<String, String>>();
        for (Iterator<Mp3info> iterator=mp3infos.iterator();iterator.hasNext();){
            Mp3info mp3info=iterator.next();
            HashMap<String,String> map=new HashMap<String,String>();
            map.put("title",mp3info.getTitle());
            map.put("Artist",mp3info.getArtist());
            map.put("duration",String.valueOf(mp3info.getDuration()));
            map.put("size",String.valueOf(mp3info.getSize()));
            map.put("url",mp3info.getUrl());
            mapList.add(map);
        }
        mSimpleadapte=new SimpleAdapter(context,mapList,
                R.layout.music_list_item_layout,new String[]{"title","Artist","duration"},
                new int[]{R.id.song_title,R.id.song_name,R.id.song_time});
        musicListView= (ListView) v.findViewById(R.id.music_list);
        musicListView.setAdapter(mSimpleadapte);
        musicListView.setOnItemClickListener(musicItenOnclickListener);
        Log.i("Adapter","适配了——————————————————————————"+mp3infos.size());
    }
}
