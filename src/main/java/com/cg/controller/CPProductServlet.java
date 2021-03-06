package com.cg.controller;

import com.cg.dto.ProductDTO;
import com.cg.model.Catogory;
import com.cg.model.Product;
import com.cg.service.CatogoryService;
import com.cg.service.ICatogoryService;
import com.cg.service.IProductService;
import com.cg.service.ProductService;
import com.cg.utils.Validate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@WebServlet(name ="CPProductServlet" , urlPatterns = "/cpa/product")
public class CPProductServlet extends HttpServlet {


    private static final long serialVersionUID = 1L;

    IProductService productService;
    ICatogoryService catogoryService;
    @Override
    public void init() throws ServletException {
        productService = new ProductService();
        catogoryService = new CatogoryService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html/charset=UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action){
            case "list":
                showListProduct(request, response);
                break;
            case "delete":
                showDeleteId(request, response);
                break;
            case "create":
                showCreateProduct(request, response);
                break;
            case "edit":
                showEditProduct(request, response);
                break;
            default:
                showListProduct(request, response);
                break;

        }
    }

    private void showEditProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cpa/product/Editproduct.jsp");
        String id = request.getParameter("id");

       Product product = productService.findById(Integer.parseInt(id));

       List<Catogory> list2 = catogoryService.findAll();
       request.setAttribute("listC" , list2);

       if(product != null){
           request.setAttribute("product", product);
       }
        dispatcher.forward(request, response);
    }

    private void showListProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/cpa/product/Listproduct.jsp");

        String strKeyword = request.getParameter("keyword");

        if(strKeyword!=null){
            List<ProductDTO> list1 = productService.searchProduct(strKeyword);
            request.setAttribute("listP", list1);
            dispatcher.forward(request, response);
        }else {
            List<ProductDTO> list = productService.findAllProductDTO();
            request.setAttribute("listP",list);

            dispatcher.forward(request, response);
        }


    }

    private void showDeleteId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("sid");
        productService.deleteId(id);

        response.sendRedirect("/cpa/product");

    }
    private void showCreateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/cpa/product/CreateProduct.jsp");

        dispatcher.forward(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html/charset=UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                doCreateProduct(request, response);
                break;
            case "edit":
                doUpdateProduct(request, response);
                break;
            default:
                doCreateProduct(request, response);
                break;
        }
    }
    private void doUpdateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cpa/product/Editproduct.jsp");

        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String strPrice = request.getParameter("price");
        String strQuantity = request.getParameter("quantity");
        String strIdCategory = request.getParameter("idCategory");
        String strSize = request.getParameter("size");
        String color = request.getParameter("color");
        String img = request.getParameter("file");

        List<String> errors = new ArrayList<>();

        if(title.equals("")){
            errors.add("Title kh??ng ????????c ?????? tr????ng");
        }
        if(!title.equals("GIA??Y TH???? THAO") && !title.equals("GIA??Y L??????I") && !title.equals("GIA??Y SNEAKERS")){
            errors.add("Danh sa??ch gia??y kh??ng t????n ta??i, ba??n vui lo??ng cho??n la??i!");
        }

        if(strPrice.equals("")){
            errors.add("Ti????n kh??ng ????????c ?????? tr????ng");
        }
        if(strIdCategory.equals("")){
            errors.add("IdCategory kh??ng ????????c ?????? tr????ng");
        }

        if(strSize.equals("")){
            errors.add("Size kh??ng ????????c ?????? tr????ng");
        }
        if(color.equals("")){
            errors.add("Color kh??ng ????????c ?????? tr????ng");
        }
        if(!color.equals("RED")&& !color.equals("BLUE") && !color.equals("YELLOW") && !color.equals("ORANGE") && !color.equals("BLACK") && !color.equals("WHITE")){
            errors.add("Nh????p sai ma??u trong danh sa??ch ba??n vui lo??ng cho??n la??i ma??u trong danh sa??ch");
        }
        if(img.equals("")){
            errors.add("A??nh kh??ng ????????c ?????? tr????ng");
        }

        boolean isPrice = Validate.isNumberValid(strPrice);
        if(!isPrice || strPrice.length() > 10){
            errors.add("L????i nh????p sai ki????u d???? li????u HO????C nh????p gia?? ti????n la?? s???? ??m HO????C gia?? ti????n l????n h??n 10 s????");
        }

        boolean isQuantity = Validate.isNumberValid(strQuantity);
        if(!isQuantity || strQuantity.length()<0 || strQuantity.length()>3){
            errors.add("Nh????p sai ki????u d???? li????u Quantity HO????C Nh????p sai s???? l??????ng Quantity la?? s???? ??m HO????C s???? l??????ng l????n h??n 999");
        }

        Product product = null;

        boolean success = false;

        if(errors.size() == 0){
            product = new Product(Integer.parseInt(id),title,BigDecimal.valueOf(Long.parseLong(strPrice)),Integer.parseInt(strQuantity),Integer.parseInt(strIdCategory),Integer.parseInt(strSize),color,img);
            success = productService.update(product);
        }

        if (success) {
            request.setAttribute("success", true);
        } else {
            errors.add("Th??m m????i th????t ba??i");
        }

        if(errors.size() > 0){
            request.setAttribute("errors",errors);
        }

        request.setAttribute("product", product);

        List<Catogory> list2 = catogoryService.findAll();
        request.setAttribute("listC" , list2);

        dispatcher.forward(request,response);
    }



    private void doCreateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/cpa/product/CreateProduct.jsp");

        String title = request.getParameter("title");
        String strPrice = request.getParameter("price");
        String strQuantity = request.getParameter("quantity");
        String strIdCategory = request.getParameter("idCategory");
        String strSize = request.getParameter("size");
        String color = request.getParameter("color");
        String img = request.getParameter("file");

        List<String> errors = new ArrayList<>();

        if(title.equals("")){
            errors.add("Title kh??ng ????????c ?????? tr????ng");
        }
        if(!title.equals("GIA??Y TH???? THAO") && !title.equals("GIA??Y L??????I") && !title.equals("GIA??Y SNEAKERS")){
            errors.add("Danh sa??ch gia??y kh??ng t????n ta??i, ba??n vui lo??ng cho??n la??i!");
        }

        if(strPrice.equals("")){
            errors.add("Ti????n kh??ng ????????c ?????? tr????ng");
        }
        if(strIdCategory.equals("")){
            errors.add("IdCategory kh??ng ????????c ?????? tr????ng");
        }
        if(strSize.equals("")){
            errors.add("Size kh??ng ????????c ?????? tr????ng");
        }
        if(color.equals("")){
            errors.add("Color kh??ng ????????c ?????? tr????ng");
        }
        if(!color.equals("RED")&& !color.equals("BLUE") && !color.equals("YELLOW") && !color.equals("ORANGE") && !color.equals("BLACK") && !color.equals("WHITE")){
            errors.add("Nh????p sai ma??u trong danh sa??ch ba??n vui lo??ng cho??n la??i ma??u trong danh sa??ch");
        }
        if(img.equals("")){
            errors.add("A??nh kh??ng ????????c ?????? tr????ng");
        }


        boolean isPrice = Validate.isNumberValid(strPrice);
        if(!isPrice || strPrice.length() > 10){
              errors.add("L????i nh????p sai ki????u d???? li????u HO????C nh????p gia?? ti????n la?? s???? ??m HO????C gia?? ti????n l????n h??n 10 s????");
        }

        boolean isQuantity = Validate.isNumberValid(strQuantity);
        if(!isQuantity || strQuantity.length()<0 || strQuantity.length()>3){
            errors.add("Nh????p sai ki????u d???? li????u Quantity HO????C Nh????p sai s???? l??????ng Quantity la?? s???? ??m HO????C s???? l??????ng l????n h??n 999");
        }


        boolean success = false;
        if(errors.size() == 0){
            Product product = new Product(title,BigDecimal.valueOf(Long.parseLong(strPrice)),Integer.parseInt(strQuantity),Integer.parseInt(strIdCategory),Integer.parseInt(strSize),color,img);
            success = productService.create(product);
        }

        if (success) {
            request.setAttribute("success", true);
        } else {
            errors.add("Th??m m????i th????t ba??i");
        }

        if(errors.size() > 0){
            request.setAttribute("errors",errors);
        }


        dispatcher.forward(request,response);
    }
}










































