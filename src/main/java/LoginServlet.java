
import beans.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import javax.xml.transform.sax.SAXSource;
import java.io.*;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;



import java.io.IOException;
import java.lang.reflect.Type;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@WebServlet("/loginServlet")
    public class LoginServlet extends HttpServlet {
    public String userName;
    public String password;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session;
        PrintWriter out = res.getWriter();
         userName = req.getParameter("uname");
         password = req.getParameter("psw");
        System.out.println(userName+ password);
        try {
            User loggedUser = new User(userName, password);

            InputStream inputStream =
                    getClass().getClassLoader().getResourceAsStream("/users.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream ));
            String json= reader.lines().collect(Collectors.joining());///???

            System.out.println(json);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type founderListType = new TypeToken<ArrayList<User>>(){}.getType(); //?????
            List<User> users = gson.fromJson(json, founderListType);//////???????
            boolean found = users.stream().anyMatch(user -> user.getUsername().equals(loggedUser.getUsername()) && user.getPassword().equals(loggedUser.getPassword()));
            System.out.println(found);
            if(found) {
                session = req.getSession();
                session.setAttribute("uname", userName);
                res.sendRedirect("lab4.jsp");
            }
            else{
                System.out.println( "register here");
                FileOutputStream fileOutputStream = new FileOutputStream("webapps/AJAX-lab1-1.0-SNAPSHOT/WEB-INF/classes/users.json");
                //because when I created file abeer.json it located in same directory with webapps so I want to create file in my war of app to make proj portable.
//                       FileOutputStream fileOutputStream = new FileOutputStream("test.json");

                users.add(loggedUser);
                String newJson = gson.toJson(users);
                System.out.println(newJson);

                fileOutputStream.write(newJson.getBytes());
                fileOutputStream.close();

                System.out.println( "register done ");
//                fileOutputStream.write(newJson.getBytes(Charset));//???
//                //File file = new File( getClass().getClassLoader().getResourceAsStream("/users.json"));
////                ServletContext context = getServletContext();
////                InputStream is = context.getResourceAsStream("/users.json");


            }
        } catch (FileNotFoundException e) {
            System.out.println("File NOt Found");
        } catch (IOException e) {
            System.out.println("Io Exception");
        }



    }
}









