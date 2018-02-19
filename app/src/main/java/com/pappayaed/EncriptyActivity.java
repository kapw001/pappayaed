package com.pappayaed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.pappayaed.common.AESHelper;
import com.pappayaed.common.Encrypt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import static com.pappayaed.common.Encrypt.generateKey;

public class EncriptyActivity extends AppCompatActivity {
    private static final String TAG = "EncriptyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encripty);


        Properties properties = new Properties();
        try {
            InputStream in = getAssets().open("val/v.properties");

            Log.e(TAG, "onCreate: " + getCacheDir().getAbsolutePath());

//            File file = new File("/data/data/test/v.properties");

//            Log.e(TAG, "onCreate: fiule" + file.getAbsolutePath());

//            File file = new File("file:///android_asset/val/v");

//            Log.e(TAG, "onCreate: file " + file.toString());

            properties.load(in);

            String Username = properties.getProperty("username");
            String datetxt = properties.getProperty("date");

            String oDate = decryption(datetxt);


            Log.e(TAG, "onCreate: Test  dsfdsafsdusername " + Username + "  date   " + datetxt + "O date  " + oDate);

        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            InputStream inputStream = getAssets().open("val/validate");

            String inputreader = getStringFromInputStream(inputStream);

//            Log.e(TAG, "onCreate: " + inputreader);


        } catch (IOException e) {
            e.printStackTrace();
        }


//        String encryptedString = encryption("12/06/2017");
//        String decryptedString = decryption(encryptedString);
//
//        Log.e(TAG, "onCreate: en " + encryptedString + " de  " + decryptedString);

    }


//    private File createFileFromInputStream(InputStream inputStream) {
//
//        try{
//            File f = new File(inputStream);
//            OutputStream outputStream = new FileOutputStream(f);
//            byte buffer[] = new byte[1024];
//            int length = 0;
//
//            while((length=inputStream.read(buffer)) > 0) {
//                outputStream.write(buffer,0,length);
//            }
//
//            outputStream.close();
//            inputStream.close();
//
//            return f;
//        }catch (IOException e) {
//            //Logging exception
//        }
//
//        return null;
//    }

    // convert InputStream to String
    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

    public String encryption(String strNormalText) {
        String seedValue = "ABCDEFG";
        String normalTextEnc = "";
        try {
            normalTextEnc = AESHelper.encrypt(seedValue, strNormalText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return normalTextEnc;
    }

    public String decryption(String strEncryptedText) {
        String seedValue = "ABCDEFG";
        String strDecryptedText = "";
        try {
            strDecryptedText = AESHelper.decrypt(seedValue, strEncryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDecryptedText;
    }
}
