package com.socialbox.service.impl;

import com.socialbox.service.InviteLinkService;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Random;

@Service
public class InviteLinkServiceImpl implements InviteLinkService {

    @Override
    public String createLink(int len) {

        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = upper.toLowerCase(Locale.ROOT);
        String digits = "0123456789";

        String alphaNum = upper + lower + digits;
        int alphaNumLen = alphaNum.length();
        char[] alphaNumArray = alphaNum.toCharArray();
        char[] buffer = new char[len];

        Random random = new Random();
        for(int i=0;i<len;i++)
            buffer[i] = alphaNumArray[random.nextInt(alphaNumLen)];


        return ("https://social-boxx.herokuapp.com/invite/" + new String(buffer));
    }
}
