/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author David
 */
public class BBDD_manager {

    Connection connect = null;

    public boolean connection() {
        try {
            String url = "jdbc:oracle:thin:@192.168.1.44:1521/videoclub";
            String user = "sys as sysdba";
            String password = "root";

            Class.forName("oracle.jdbc.driver.OracleDriver");

            connect = DriverManager.getConnection(url, user, password);

            if (connect != null) {

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public void disconnection() {
        try {
            connect.close();
        } catch (Exception e) {
        }
    }

    public String[][] getPlatforms() {
        String[][] data;
        Statement sta;
        try {
            sta = connect.createStatement();
            ResultSet r = sta.executeQuery("SELECT COUNT(*) as counter FROM plataformas s");
            r.next();
            data = new String[r.getInt("counter")][5];
            ResultSet rs = sta.executeQuery("SELECT s.nombre nombre ,s.precio precio ,s.fechaSalida fechasalida , s.años_Aire() añosAire from plataformas s");
            int i = 0;
            while (rs.next()) {
                data[i][0] = rs.getString("nombre");
                data[i][1] = rs.getString("precio");
                data[i][2] = rs.getString("fechasalida");
                data[i][3] = rs.getString("añosAire");
                i++;
            }
            return data;
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    public boolean addSeries(String id, String name, String director, String platform, String date) {
        Statement sta;
        try {
            sta = connect.createStatement();
            sta.executeUpdate("INSERT INTO series  VALUES ( '" + id + "','" + name + "' , '" + director + "' , (SELECT REF(pt) from plataformas pt WHERE pt.nombre='" + platform + "') ,  TO_DATE('" + date + "','YYYY-MM-DD'))");
            sta.close();
            return true;
        } catch (Exception e) {
            System.out.println(id + " " + name + " " + director + " " + platform + " " + date);
            System.err.println(e);
            return false;
        }
    }

    public boolean addMovie(String id, String name, String director, String platform, String date) {
        Statement sta;
        try {
            sta = connect.createStatement();
            sta.executeUpdate("INSERT INTO peliculas  VALUES ( '" + id + "','" + name + "' , '" + director + "' , (SELECT REF(pt) from plataformas pt WHERE pt.nombre='" + platform + "') ,  TO_DATE('" + date + "','YYYY-MM-DD'))");
            sta.close();
            return true;
        } catch (Exception e) {
            System.out.println(id + " " + name + " " + director + " " + platform + " " + date);
            System.err.println(e);
            return false;
        }
    }

    public boolean addPlatform(String id, String name, String price, String date) {
        Statement sta;
        try {
            sta = connect.createStatement();
            sta.executeUpdate("INSERT INTO  PLATAFORMAS(id,  nombre, precio, fechasalida) VALUES ( '" + id + "','" + name + "' , " + price + " ,  TO_DATE('" + date + "','YYYY-MM-DD'))");
            sta.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String[][] getSeriesandMovies(String plataforma) {
        String[][] data;
        Statement sta;
        try {
            sta = connect.createStatement();
            ResultSet r = sta.executeQuery("SELECT COUNT(*) as counter FROM series s WHERE s.plataforma.nombre ='" + plataforma + "'");
            r.next();
            int aux = r.getInt("counter");
            ResultSet r_ = sta.executeQuery("SELECT COUNT(*) as counter FROM peliculas p WHERE p.plataforma.nombre ='" + plataforma + "'");
            r_.next();
            aux += r_.getInt("counter");

            data = new String[aux][5];
            ResultSet rs = sta.executeQuery("SELECT s.nombre nombre ,s.director director ,s.plataforma.nombre plataforma , s.fechaSalida fechasalida from series s WHERE s.plataforma.nombre='" + plataforma + "'");

            int i = 0;
            while (rs.next()) {
                data[i][0] = rs.getString("nombre");
                data[i][1] = rs.getString("director");
                data[i][2] = rs.getString("plataforma");
                data[i][3] = rs.getString("fechasalida");
                i++;
            }

            ResultSet rs2 = sta.executeQuery("SELECT p.nombre nombre ,p.director director ,p.plataforma.nombre plataforma , p.fechaSalida fechasalida from peliculas p WHERE p.plataforma.nombre='" + plataforma + "'");
            while (rs2.next()) {
                data[i][0] = rs2.getString("nombre");
                data[i][1] = rs2.getString("director");
                data[i][2] = rs2.getString("plataforma");
                data[i][3] = rs2.getString("fechasalida");
                i++;
            }
            return data;
        } catch (Exception e) {
            return null;
        }
    }

    public String[][] getSeries() {
        String[][] data;
        Statement sta;
        try {
            sta = connect.createStatement();
            ResultSet r = sta.executeQuery("SELECT COUNT(*) as counter FROM series s");
            r.next();
            data = new String[r.getInt("counter")][5];
            ResultSet rs = sta.executeQuery("SELECT s.nombre nombre ,s.director director ,s.plataforma.nombre plataforma , s.fechaSalida fechasalida from series s");

            int i = 0;
            while (rs.next()) {
                data[i][0] = rs.getString("nombre");
                data[i][1] = rs.getString("director");
                data[i][2] = rs.getString("plataforma");
                data[i][3] = rs.getString("fechasalida");
                i++;
            }
            return data;
        } catch (Exception e) {
            return null;
        }
    }

    public String[][] getMovies() {
        String[][] data;
        Statement sta;
        try {
            sta = connect.createStatement();
            ResultSet r = sta.executeQuery("SELECT COUNT(*) as counter FROM peliculas s");
            r.next();
            data = new String[r.getInt("counter")][5];
            ResultSet rs = sta.executeQuery("SELECT s.nombre nombre ,s.director director ,s.plataforma.nombre plataforma , s.fechaSalida fechasalida from peliculas s");

            int i = 0;
            while (rs.next()) {
                data[i][0] = rs.getString("nombre");
                data[i][1] = rs.getString("director");
                data[i][2] = rs.getString("plataforma");
                data[i][3] = rs.getString("fechasalida");
                i++;
            }
            return data;
        } catch (Exception e) {
            return null;
        }
    }

    public void restoreDatabase() {
        Statement sta;
        try {
            sta = connect.createStatement();
            String[] query = {
                "DROP TYPE t_pelicula FORCE",
                "DROP TABLE peliculas FORCE",
                "DROP TYPE t_serie FORCE",
                "DROP TABLE series FORCE",
                "DROP TYPE t_plataforma FORCE",
                "DROP TABLE plataformas FORCE",
                "CREATE OR REPLACE TYPE t_plataforma AS OBJECT(ID VARCHAR (9),nombre VARCHAR(100),precio DECIMAL (11, 2),fechaSalida DATE,MEMBER FUNCTION años_Aire  RETURN NUMBER)",
                "CREATE OR REPLACE TYPE t_pelicula AS OBJECT(id VARCHAR(100), nombre VARCHAR(100), director VARCHAR(100),plataforma REF t_plataforma,fechaSalida DATE)",
                "CREATE OR REPLACE TYPE t_serie  AS OBJECT(id VARCHAR(100), nombre VARCHAR(100),director varchar(100),plataforma REF t_plataforma,fechaSalida DATE)",
                "CREATE TABLE plataformas OF t_plataforma",
                "CREATE TABLE peliculas OF t_pelicula",
                "CREATE TABLE series OF t_serie",
                "CREATE OR REPLACE TYPE BODY t_plataforma AS \n"
                + "    MEMBER FUNCTION años_Aire RETURN NUMBER IS\n"
                + "        d DATE;\n"
                + "        a NUMBER;\n"
                + "    BEGIN\n"
                + "        d:=sysdate;\n"
                + "        a:=to_char(d,'YYYY')-to_char(fechaSalida,'YYYY');\n"
                + "\n"
                + "        if (to_char(d,'MM')< to_char(fechaSalida,'MM')) OR \n"
                + "            ((to_char(d,'MM')=to_char(fechaSalida,'MM')) AND\n"
                + "            (to_char(d,'DD')<to_char(fechaSalida,'DD')))\n"
                + "        THEN\n"
                + "            a:=a-1;\n"
                + "        END IF;\n"
                + "        return a;\n"
                + "    END;\n"
                + "END;",
                "INSERT INTO plataformas VALUES('595838419','Netflix' , 10.95 , TO_DATE('1997-08-29','YYYY-MM-DD'))",
                "INSERT INTO plataformas VALUES('813954874','Disney+', 7.95, TO_DATE('2019-12-12','YYYY-MM-DD'))",
                "INSERT INTO plataformas VALUES('846452194','Amazon Prime video', 9.95,  TO_DATE( '2006-09-07','YYYY-MM-DD'))",
                "INSERT INTO peliculas VALUES ('1','El lobo de Wall Street', 'Martin Scorsese', (SELECT REF(pt) from plataformas pt WHERE pt.ID='595838419') ,TO_DATE( '2013-02-22','YYYY-MM-DD'))",
                "INSERT INTO peliculas VALUES ('2','Puñales por la espalda', 'Rian Johnson', (SELECT REF(pt) from plataformas pt WHERE pt.ID='846452194') ,TO_DATE( '2019-08-12','YYYY-MM-DD'))",
                "INSERT INTO peliculas VALUES ('3','Star wars 5 : El imperio contrataca', 'Irvin Kershner', (SELECT REF(pt) from plataformas pt WHERE pt.ID='813954874') ,TO_DATE( '1980-04-04','YYYY-MM-DD'))",
                "INSERT INTO series VALUES ('4', 'El mandaloriano', 'Jon Favreau', (SELECT REF(pt) from plataformas pt WHERE pt.ID='813954874') ,TO_DATE( '1977-05-25' ,'YYYY-MM-DD'))",
                "INSERT INTO series VALUES ('1','Peaky Blinders', 'Steven Knight', (SELECT REF(pt) from plataformas pt WHERE pt.ID='595838419') ,TO_DATE( '2013-02-22','YYYY-MM-DD'))",
                "INSERT INTO series VALUES ('2','Hijos de la Anarquia', 'Kurt Sutter', (SELECT REF(pt) from plataformas pt WHERE pt.ID='595838419') ,TO_DATE('2008-09-03','YYYY-MM-DD'))",
                "INSERT INTO series VALUES ('3','The Boys', 'Eric Kripke', (SELECT REF(pt) from plataformas pt WHERE pt.ID='846452194') ,TO_DATE(  '2019-07-26','YYYY-MM-DD'))",
                "INSERT INTO peliculas VALUES ('4','Star wars 4 : Una nueva esperanza', 'George Lucas', (SELECT REF(pt) from plataformas pt WHERE pt.ID='813954874') ,TO_DATE( '2019-12-12' ,'YYYY-MM-DD'))",
                "COMMIT"
            };
            for (int i = 0; i < query.length; i++) {
                sta.executeUpdate(query[i]);
            }
            sta.close();
        } catch (Exception e) {
        }
    }
}
