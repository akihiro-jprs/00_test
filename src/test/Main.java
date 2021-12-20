package test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/Main")
@MultipartConfig
public class Main extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/view/form.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String name=request.getParameter("name");
        String word=request.getParameter("word");
        //name属性がpictのファイルをPartオブジェクトとして取得
        Part part=request.getPart("pict");
        //ファイル名を取得
        //String filename=part.getSubmittedFileName();//ie対応が不要な場合
        String filename=Paths.get(part.getSubmittedFileName()).getFileName().toString();
        //アップロードするフォルダ
        String path=getServletContext().getRealPath("/upload");
        //実際にファイルが保存されるパス確認
        System.out.println(path);
        //書き込み
        part.write(path+File.separator+filename);
        request.setAttribute("name",name);
        request.setAttribute("word", word);
        request.setAttribute("filename", filename);
        RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/view/result.jsp");
        rd.forward(request, response);
    }

}
