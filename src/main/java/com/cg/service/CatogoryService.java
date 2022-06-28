package com.cg.service;

import com.cg.model.Catogory;
import com.cg.model.City;
import com.cg.utils.DBConText;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatogoryService implements ICatogoryService{

    private static final String SELECT_ALL_CATOGORY=
            "SELECT * FROM category;";
    @Override
    public List<Catogory> findAll() {
        List<Catogory> list = new ArrayList<>();
        try {
            Connection conn = DBConText.getConnection();
            PreparedStatement ps = conn.prepareCall(SELECT_ALL_CATOGORY);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                list.add(new Catogory(id,name));

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    public static void main(String[] args) {
        CatogoryService catogoryService = new CatogoryService();
        List<Catogory> list = catogoryService.findAll();
        for(Catogory o : list){
            System.out.println(o);
        }
    }
}
