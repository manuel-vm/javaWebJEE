package org.diplomado.java.jdbc.repositorio;

import org.diplomado.java.jdbc.modelo.Categoria;
import org.diplomado.java.jdbc.modelo.Producto;
import org.diplomado.java.jdbc.util.ConexionBaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepositorioImpl implements Repositorio<Categoria> {

    private Connection conn;

    @Override
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public CategoriaRepositorioImpl(Connection conn) {
        this.conn = conn;
    }

    public CategoriaRepositorioImpl() {
    }

    private Categoria crearObjetoCategoria(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setId(rs.getInt("categoria_id"));
        categoria.setNombre(rs.getString("categoria"));
        return categoria;
    }

    @Override
    public List<Categoria> listar() {
        List<Categoria> categorias = new ArrayList<>();
        try
                (Statement stmt = conn.createStatement();
                 ResultSet resultado = stmt.executeQuery(
                         "SELECT * categorias")) {
            {
                while (resultado.next()) {
                    Categoria categoria = crearObjetoCategoria(resultado);
                    categorias.add(categoria);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return categorias;
    }


    @Override
    public Categoria porId(Long id) throws SQLException {
        Categoria categoria = null;
        try (PreparedStatement stmt = conn.
                prepareStatement(
                        "SELECT p.*, c.nombre as categoria FROM productos as p INNER JOIN categorias as c ON (p.categoria_id = c.id) WHERE p.id = ?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    categoria = crearObjetoCategoria(rs);
                }
            }

        }
        return categoria;
    }

    @Override
    public Categoria guardar(Categoria categoria) throws SQLException {
        String sql = null;
        if ((categoria != null) && (categoria.getId() != null) && (categoria.getId() > 0)) {
            sql = "UPDATE categorias SET nombre=? WHERE id=?";

        } else {
            sql = "INSERT INTO categorias(nombre) VALUES(?)";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, categoria.getNombre());
            if (categoria.getId() != null && categoria.getId() > 0) {
                stmt.setLong(2, categoria.getId());
                //numero de ? desde 1 parameterindex
            }

            stmt.executeUpdate();
            if (categoria.getId() == null) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        categoria.setId(rs.getInt(1));
                    }
                }
            }
        }
        return categoria;
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM categorias WHERE id=?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    private Categoria crearCategoria(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setId((int) rs.getLong("id"));
        categoria.setNombre(rs.getString("nombre"));
        return categoria;
    }

}
