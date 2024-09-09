package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example-unit");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            Categoria perecedero = Categoria.builder()
                    .denominacion("Perecederos")
                    .build();

            Categoria lacteos = Categoria.builder()
                    .denominacion("Lácteos")
                    .build();

            Categoria limpieza = Categoria.builder()
                    .denominacion("Limpieza")
                    .build();

            Articulo articulo1 = Articulo.builder()
                    .denominacion("Leche")
                    .cantidad(150)
                    .precio(1500)
                    .build();

            Articulo articulo2 = Articulo.builder()
                    .denominacion("Lavandina 1L")
                    .cantidad(200)
                    .precio(2000)
                    .build();

            articulo1.getCategorias().add(perecedero);
            articulo1.getCategorias().add(lacteos);
            articulo2.getCategorias().add(limpieza);

            perecedero.getArticulos().add(articulo1);
            lacteos.getArticulos().add(articulo1);
            limpieza.getArticulos().add(articulo2);

            Cliente cliente1 = Cliente.builder()
                    .nombre("Pedro")
                    .apellido("Vasquez")
                    .dni(42568745)
                    .build();
            Domicilio domicilio1 = Domicilio.builder()
                    .nombreCalle("San Martin")
                    .numero(250)
                    .build();
            cliente1.setDomicilio(domicilio1);

            Factura factura1 = Factura.builder()
                    .fecha("2024-09-05")
                    .numero(120)
                    .build();

            factura1.setCliente(cliente1);

            DetalleFactura linea1 = DetalleFactura.builder()
                    .articulo(articulo1)
                    .cantidad(2)
                    .subtotal(3000)
                    .build();

            DetalleFactura linea2 = DetalleFactura.builder()
                    .articulo(articulo2)
                    .cantidad(5)
                    .subtotal(10000)
                    .build();

            factura1.getFacturas().add(linea1);
            factura1.getFacturas().add(linea2);
            factura1.setTotal(13000);

            entityManager.persist(factura1);
            entityManager.flush();
            entityManager.getTransaction().commit();

        }catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
            System.out.println("No se pudo grabar la clase factura");
        }
        // Cerrar el EntityManager y el EntityManagerFactory
        entityManager.close();
        entityManagerFactory.close();
    }
}
