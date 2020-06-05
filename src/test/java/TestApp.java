


git add README.md

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.junit.Test;
import org.mockito.Mockito;


public  class TestApp extends Mockito {


    @Test
    public void testSocket(){
        MessengerServerEndPoint socket= new MessengerServerEndPoint();
        //socket.onMessage();
        System.out.println("");

    }

    @Test
    public void testServlet() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("uname")).thenReturn("me");
        when(request.getParameter("psw")).thenReturn("secret");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

      //  new LoginServlet().doPost(request, response);

     //   verify(request, atLeast(1)).getParameter("uname"); // only if you want to verify username was called...
        writer.flush(); // it may not have been flushed yet...
       // assertTrue(stringWriter.toString().contains("My expected string"));
    }




        @Test
         public void TestUserName(){
            LoginServlet loginservlet=new LoginServlet();

            loginservlet.password="data";
            assertNotNull(loginservlet.password);
            System.out.println( "   ");
          }


    @Test
    public void testDownloadServlet() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("messages")).thenReturn("msg");


        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        new DownloadServlet().doPost(request, response);

        verify(request, atLeast(1)).getParameter("messages"); // only if you want to verify username was called...
        writer.flush(); // it may not have been flushed yet...
        assertTrue(stringWriter.toString().contains(""));
    }




}
