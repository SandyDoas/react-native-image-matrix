
package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
////
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.NativeModule;
import java.util.Map;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.Arguments;

import android.graphics.Bitmap;  
import android.graphics.BitmapFactory;  
import android.os.Bundle;  
import android.widget.ImageView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.*;
import java.util.*;
import java.io.FileOutputStream;


///
public class RNImageMatrixModule extends ReactContextBaseJavaModule {
  private final ReactApplicationContext reactContext;

  public RNImageMatrixModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

@ReactMethod
  public void getImageUrl(String path,String type,int rows, int columns, Callback callback) {
    WritableArray srcs = Arguments.createArray();
    WritableArray indces = Arguments.createArray();
    
    File imgFile = new  File(path);
    if(imgFile.exists()){
      Bitmap myBitmap = BitmapFactory.decodeFile(path);
      int width = myBitmap.getWidth();
      int height = myBitmap.getHeight();
      int subImgWidth = width/columns;
      int subImgHeight = height/rows;
      
      String subImgPath = path.substring(0, path.lastIndexOf("/")+1) + type; 
      int count = 0 ;
      
      for(int y = 0 ; y < height-1 ; y = y+subImgHeight ){
        for(int x = 0 ; x < width-1 ; x = x+subImgWidth ){  
          
          Bitmap subBitmap = Bitmap.createBitmap(myBitmap,x,y,subImgWidth,subImgHeight);

          try{
            File file = new File(subImgPath+count+".jpg");
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            subBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.close();
          }catch(Exception ex){
            //callback.invoke(path);
          }
          srcs.pushString(subImgPath+count+".jpg");
          count++;
        }
      }
      callback.invoke(srcs);
    }else{
      callback.invoke(path);
    }
  }
  
  @Override
  public String getName() {
    return "RNImageMatrix";
  }
}
