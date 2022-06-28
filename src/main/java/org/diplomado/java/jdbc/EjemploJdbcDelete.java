package org.diplomado.java.jdbc;

import org.diplomado.java.jdbc.modelo.Producto;
import org.diplomado.java.jdbc.repositorio.ProductoRepositorioImpl;
import org.diplomado.java.jdbc.repositorio.Repositorio;
import org.diplomado.java.jdbc.util.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Manuel Vargas
 */

public class EjemploJdbcDelete {
    public static void main(String[] args) {
        try(Connection conn = ConexionBaseDatos.getInstance().getConnection()) {
            Repositorio<Producto> repositorio = new ProductoRepositorioImpl(conn);
            System.out.println("Listar");
            repositorio.listar().forEach(System.out::println);

            System.out.println("Obtener Por id");
            System.out.println(repositorio.porId(1L));

            System.out.println("Editar producto");
            repositorio.eliminar(3L);
            System.out.println("Producto eliminado con exito");
            repositorio.listar().forEach(System.out::println);

        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
