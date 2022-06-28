package org.diplomado.java.jdbc;
import org.diplomado.java.jdbc.modelo.Categoria;
import org.diplomado.java.jdbc.modelo.Producto;
import org.diplomado.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.diplomado.java.jdbc.repositorio.Repositorio;
import org.diplomado.java.jdbc.util.ConexionBaseDatos;
import java.sql.*;
import java.util.Date;

public class EjemploJDBC {
    public static void main(String[] args) {

        try(Connection conn = ConexionBaseDatos.getConnection()) {

            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }

            try {

                Repositorio<Producto> repositorio = new ProductoRepositorioImpl(conn);
                System.out.println("=============== LISTAR ====================");
                repositorio.listar().forEach(System.out::println);

                System.out.println("=============== Obtener por Id ====================");
                System.out.println(repositorio.porId(2L));
                System.out.println("=============== Insertar nuevo producto ====================");
                Producto producto = new Producto();
                producto.setNombre("Producto prueba");
                producto.setPrecio(500);
                producto.setFechaRegistro(new Date());
                Categoria categoria = new Categoria();
                categoria.setId(2);
                producto.setCategoria(categoria);

                repositorio.guardar(producto);
                System.out.println("Registro guardado correctamente");
                repositorio.listar().forEach(System.out::println);


                //Eliminar producto
                System.out.println("=====================Eliminar=================");
                repositorio.eliminar(3L);
                System.out.println("Producto eliminado con exito");
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
