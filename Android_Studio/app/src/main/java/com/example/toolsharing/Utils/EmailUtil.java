package com.example.toolsharing.Utils;

import android.app.Activity;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

public class EmailUtil {
    public static void sendEmail(Activity activity, String send, String sub, String body) {
        BackgroundMail.newBuilder(activity)
                .withUsername("cegep.toolsharing@gmail.com")
                .withPassword("group1toolsharing")
                .withMailTo(send)
                //.withMailCc("cc-email@gmail.com")
                //.withMailBcc("bcc-email@gmail.com")
                .withSubject(sub)
                .withBody(body)
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        //do some magic
                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {
                        //do some magic
                    }
                })
                .send();
    }
}
