package com.fekpal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.java2d.opengl.WGLSurfaceData;

import javax.servlet.http.HttpSession;
import java.net.HttpCookie;

import static java.lang.System.out;

@Service
public class TestSessionImpl {

    @Autowired
    private HttpSession session;

    public void testSessionIsNull(){
        out.println("session为:"+session);
    }
}
