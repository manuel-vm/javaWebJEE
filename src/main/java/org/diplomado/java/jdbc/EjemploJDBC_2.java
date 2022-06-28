package org.diplomado.java.jdbc;

import org.diplomado.java.jdbc.modelo.Categoria;
import org.diplomado.java.jdbc.modelo.Producto;
import org.diplomado.java.jdbc.repositorio.CategoriaRepositorioImpl;
import org.diplomado.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.diplomado.java.jdbc.repositorio.Repositorio;
import org.diplomado.java.jdbc.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class EjemploJDBC_2 {
    public static void main(String[] args) {

        try(Connection conn = ConexionBaseDatos.getConnection()) {

            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }

            try {

                Repositorio<Categoria> repositorioCategoria = new CategoriaRepositorioImpl(conn);
                System.out.println("=============== Insertar Nueva Categoria ====================");
                Categoria categoria = new Categoria();
                categoria.setNombre("ElectroHogar");
                Categoria nuevaCategoria = repositorioCategoria.guardar(categoria);
                System.out.println("Categoria guardada con exito " + nuevaCategoria.getId());

                Repositorio<Producto> repositorio = new ProductoRepositorioImpl(conn);
                System.out.println("===========  Listar  =======================");
                repositorio.listar().forEach(System.out::println);

                System.out.println("======= Obtener por ID =====================");
                System.out.println(repositorio.porId(1L));

                System.out.println("========= Insertar nuevo producto ==========");
                Producto producto = new Producto();
                producto.setNombre("Refrigerador Samsiing");
                producto.setPrecio(9900);
                producto.setFechaRegistro(new Date());
                producto.setSku("abcdfg123");

                producto.setCategoria(nuevaCategoria);
                repositorio.guardar(producto);
                System.out.println("Producto guardado con exito: " + producto.getId());
                repositorio.listar().forEach(System.out::println);

                //Hacemos un commit
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        /*
        try {
            Connection conn = DriverManager.getConnection(url,username,password);
            Statement stmt = conn.createStatement();
            ResultSet resultado = stmt.executeQuery("SELECT * FROM productos");
            while (resultado.next()){
                //System.out.println(resultado.getString("nombre"));
                System.out.print(resultado.getInt("id"));
                System.out.print("|");
                System.out.print(resultado.getString("nombre"));
                System.out.print("|");
                System.out.print(resultado.getDouble("precio"));
                System.out.print("|");
                System.out.print(resultado.getDate("fecha_registro"));
                System.out.print("|");
            }
            resultado.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
    }

}
