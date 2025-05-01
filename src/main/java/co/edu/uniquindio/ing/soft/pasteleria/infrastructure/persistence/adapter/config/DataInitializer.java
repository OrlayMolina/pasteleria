package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.adapter.config;

import co.edu.uniquindio.ing.soft.pasteleria.application.dto.MensajeDTO;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.CreateSupplierCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.CreateSupplyCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.request.CreateUserCommand;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.SupplierResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.dto.response.SupplyResponse;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.input.ManageSupplierUseCase;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.input.ManageSupplyUseCase;
import co.edu.uniquindio.ing.soft.pasteleria.application.ports.input.ManageUserUseCase;
import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.Status;
import co.edu.uniquindio.ing.soft.pasteleria.domain.enums.TypeDocument;
import co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.repository.UserJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private UserJpaRepository usuarioRepository;

    @Autowired
    private ManageUserUseCase manageUserUseCase;

    @Autowired
    private ManageSupplierUseCase manageSupplierUseCase;
    @Autowired
    private ManageSupplyUseCase manageSupplyUseCase;

    @Override
    public void run(String... args) {
        try {
            initializeAdminUser();

            initializeSuppliers();

            initializeSupplies();

            logger.info("Data initialization completed successfully");
        } catch (Exception e) {

            logger.error("Error during data initialization: {}", e.getMessage(), e);
        }
    }

    private void initializeAdminUser() {
        try {
            if (usuarioRepository.findByEmail("admin@example.com").isEmpty()) {
                String randomUserDocument = generateRandomNumericId(10);
                CreateUserCommand command = new CreateUserCommand(
                        TypeDocument.CC,
                        randomUserDocument,
                        "3104859632",
                        "Auxiliar",
                        1200000F,
                        "Carolina",
                        null,
                        "Villanueva",
                        null,
                        "carolina@gmail.com",
                        "123456",
                        Status.ACTIVO,
                        true,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        null
                );
                manageUserUseCase.createUser(command);
                logger.info("Admin user created successfully");
            } else {
                logger.info("Admin user already exists, skipping creation");
            }
        } catch (Exception e) {
            logger.error("Error creating admin user: {}", e.getMessage());
        }
    }

    private void initializeSuppliers() {
        try {

            MensajeDTO<List<SupplierResponse>> suppliersResponse = manageSupplierUseCase.searchSupplier();
            List<SupplierResponse> suppliers = suppliersResponse.respuesta();

            if (suppliers == null || suppliers.isEmpty()) {

                createSupplierIfNotExists("Harina as de oros", "89452121", "asdeoros@gmail.com", "Sofia", "Huila", "+577425689");
                createSupplierIfNotExists("Huevos Santa Reyes", "7896555", "contacto@santareyes.com", "", "Bogotá", "+5716467878");
                createSupplierIfNotExists("Leche Alquería", "7895568", "info@alqueria.com.co", "", "Medellín", "+5743609080");
                createSupplierIfNotExists("Azúcar Manuelita", "88965588", "servicioalcliente@manuelita.com", "", "Cali", "+5723310999");
                logger.info("Suppliers initialized successfully");
            } else {
                logger.info("Suppliers already exist, skipping creation");
            }
        } catch (Exception e) {
            logger.error("Error checking or creating suppliers: {}", e.getMessage());
        }
    }

    private void createSupplierIfNotExists(String name, String supplierID, String email, String contactPerson, String location, String phone) {
        try {

            String randomSupplierId = generateRandomNumericId(9);
            CreateSupplierCommand command = new CreateSupplierCommand(
                    name,
                    TypeDocument.NIT,
                    randomSupplierId,
                    location,
                    phone,
                    email,
                    contactPerson,
                    null,
                    null,
                    null,
                    null,
                    null,
                    Status.ACTIVO,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    1L
            );
            manageSupplierUseCase.createSupplier(command);
            logger.info("Supplier {} created successfully", name);

        } catch (Exception e) {
            logger.warn("Could not create supplier {}: {}", name, e.getMessage());

        }
    }

    private String generateRandomNumericId(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append(random.nextInt(9) + 1);
        for (int i = 1; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private void initializeSupplies() {
        try {
            MensajeDTO<List<SupplyResponse>> suppliesResponse = manageSupplyUseCase.searchSupply();
            List<SupplyResponse> supplies = suppliesResponse.respuesta();

            if (supplies == null || supplies.isEmpty()) {
                MensajeDTO<List<SupplierResponse>> suppliersResponse = manageSupplierUseCase.searchSupplier();
                List<SupplierResponse> suppliers = suppliersResponse.respuesta();

                if (suppliers != null && !suppliers.isEmpty()) {
                    String harinaId = findSupplierIdByName(suppliers, "Harina as de oros");
                    String huevosId = findSupplierIdByName(suppliers, "Huevos Santa Reyes");
                    String lecheId = findSupplierIdByName(suppliers, "Leche Alquería");

                    if (harinaId != null) {
                        createSupplyIfNotExists(
                                "Harina de trigo",
                                5500.0,
                                harinaId,  // ID real del proveedor
                                LocalDate.now(),
                                LocalDate.now().plusMonths(6),
                                50,
                                "Gramos",
                                10,
                                LocalDateTime.now(),
                                LocalDateTime.now(),
                                1L
                        );
                    }

                    if (huevosId != null) {
                        createSupplyIfNotExists(
                                "Huevos frescos",
                                12000.0,
                                huevosId,
                                LocalDate.now(),
                                LocalDate.now().plusWeeks(3),
                                120,
                                "Gramos",
                                24,
                                LocalDateTime.now(),
                                LocalDateTime.now(),
                                1L
                        );
                    }

                    if (lecheId != null) {
                        createSupplyIfNotExists(
                                "Leche entera",
                                3800.0,
                                lecheId,
                                LocalDate.now(),
                                LocalDate.now().plusDays(15),
                                40,
                                "Gramos",
                                8,
                                LocalDateTime.now(),
                                LocalDateTime.now(),
                                1L
                        );
                    }

                    logger.info("Supplies initialized successfully");
                } else {
                    logger.warn("No suppliers found. Cannot create supplies without suppliers.");
                }
            } else {
                logger.info("Supplies already exist, skipping creation");
            }
        } catch (Exception e) {
            logger.error("Error checking or creating supplies: {}", e.getMessage(), e);
        }
    }

    // Método auxiliar para encontrar el ID de un proveedor por su nombre
    private String findSupplierIdByName(List<SupplierResponse> suppliers, String name) {
        for (SupplierResponse supplier : suppliers) {
            if (supplier.name().equals(name)) {
                return supplier.supplierID();
            }
        }
        logger.warn("Supplier with name {} not found", name);
        return null;
    }

    private void createSupplyIfNotExists(String name, Double price, String supplierID,
                                         LocalDate entryDate, LocalDate expirationDate,
                                         int quantity, String unitMeasurement, int minimumStock,
                                         LocalDateTime createdAt, LocalDateTime updatedAt,
                                         Long userModify) {
        try {
            CreateSupplyCommand command = new CreateSupplyCommand(
                    name,
                    price,
                    supplierID,
                    entryDate,
                    expirationDate,
                    quantity,
                    unitMeasurement,
                    minimumStock,
                    createdAt,
                    updatedAt,
                    userModify
            );

            manageSupplyUseCase.createSupply(command);
            logger.info("Supply {} created successfully", name);
        } catch (Exception e) {
            logger.warn("Could not create supply {}: {}", name, e.getMessage());
        }
    }
}