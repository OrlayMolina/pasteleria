package co.edu.uniquindio.ing.soft.pasteleria.infrastructure.persistence.adapter.config;

import org.springframework.stereotype.Component;

@Component
public class EmailTemplateConfig {

    public static final String bodyCreacionCuenta = "<!DOCTYPE html>\n" +
            "<html lang=\"es\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <title>Bienvenido a Pastelería Feliz</title>\n" +
            "    <style>\n" +
            "        @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap');\n" +
            "        \n" +
            "        body {\n" +
            "            font-family: 'Poppins', Arial, sans-serif;\n" +
            "            background-color: #FFEFEF;\n" +
            "            margin: 0;\n" +
            "            padding: 0;\n" +
            "            color: #333;\n" +
            "        }\n" +
            "        \n" +
            "        .wrapper {\n" +
            "            max-width: 650px;\n" +
            "            margin: 0 auto;\n" +
            "            padding: 20px;\n" +
            "        }\n" +
            "        \n" +
            "        .header {\n" +
            "            background-color: #FFD1DC;\n" +
            "            padding: 25px 20px;\n" +
            "            text-align: center;\n" +
            "            border-radius: 10px 10px 0 0;\n" +
            "            border-bottom: 3px solid #FFBAC8;\n" +
            "        }\n" +
            "        \n" +
            "        .logo {\n" +
            "            font-size: 28px;\n" +
            "            font-weight: 700;\n" +
            "            color: #000000;\n" +
            "            margin: 0;\n" +
            "            letter-spacing: 1px;\n" +
            "        }\n" +
            "        \n" +
            "        .tagline {\n" +
            "            font-size: 14px;\n" +
            "            color: #555;\n" +
            "            margin-top: 5px;\n" +
            "        }\n" +
            "        \n" +
            "        .container {\n" +
            "            background-color: white;\n" +
            "            padding: 30px;\n" +
            "            border-radius: 0 0 10px 10px;\n" +
            "            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);\n" +
            "        }\n" +
            "        \n" +
            "        .welcome-message {\n" +
            "            font-size: 18px;\n" +
            "            margin-bottom: 25px;\n" +
            "            line-height: 1.6;\n" +
            "        }\n" +
            "        \n" +
            "        .highlight {\n" +
            "            font-weight: 600;\n" +
            "            color: #FF769A;\n" +
            "        }\n" +
            "        \n" +
            "        .code-box {\n" +
            "            background-color: #FFF5F7;\n" +
            "            border: 1px dashed #FFB6C1;\n" +
            "            border-radius: 8px;\n" +
            "            padding: 15px;\n" +
            "            margin: 20px 0;\n" +
            "            text-align: center;\n" +
            "        }\n" +
            "        \n" +
            "        .activation-code {\n" +
            "            font-size: 24px;\n" +
            "            font-weight: 700;\n" +
            "            color: #FF4081;\n" +
            "            letter-spacing: 2px;\n" +
            "            margin: 10px 0;\n" +
            "        }\n" +
            "        \n" +
            "        .instructions {\n" +
            "            font-size: 15px;\n" +
            "            margin-top: 20px;\n" +
            "            color: #555;\n" +
            "        }\n" +
            "        \n" +
            "        .footer {\n" +
            "            text-align: center;\n" +
            "            margin-top: 30px;\n" +
            "            font-size: 12px;\n" +
            "            color: #777;\n" +
            "            padding-top: 20px;\n" +
            "            border-top: 1px solid #FFEBEF;\n" +
            "        }\n" +
            "        \n" +
            "        .social-icons {\n" +
            "            margin-top: 15px;\n" +
            "        }\n" +
            "        \n" +
            "        .social-icons a {\n" +
            "            color: #FF769A;\n" +
            "            text-decoration: none;\n" +
            "            margin: 0 8px;\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <div class=\"wrapper\">\n" +
            "        <div class=\"header\">\n" +
            "            <h1 class=\"logo\">Pastelería Feliz</h1>\n" +
            "            <p class=\"tagline\">Donde cada bocado es una sonrisa</p>\n" +
            "        </div>\n" +
            "\n" +
            "        <div class=\"container\">\n" +
            "            <p class=\"welcome-message\">¡Hola <span class=\"highlight\">[Nombres]</span>! 👋</p>\n" +
            "            <p>Nos complace darle la bienvenida a la familia de <strong>Pastelería Feliz</strong>. Estamos encantados de que te hayas registrado en nuestra plataforma.</p>\n" +
            "            \n" +
            "            <p>Para activar tu cuenta y comenzar a explorar nuestra dulce variedad de productos, necesitas verificar tu dirección de correo electrónico.</p>\n" +
            "            \n" +
            "            <div class=\"code-box\">\n" +
            "                <p>Tu código de activación es:</p>\n" +
            "                <p class=\"activation-code\">[Codigo_Activacion]</p>\n" +
            "            </div>\n" +
            "            \n" +
            "            <p class=\"instructions\">Ingresa este código en nuestra página de verificación para activar tu cuenta y comenzar a disfrutar de todos nuestros productos y promociones exclusivas.</p>\n" +
            "            \n" +
            "            <div class=\"footer\">\n" +
            "                <p>&copy; 2025 Pastelería Feliz. Todos los derechos reservados.</p>\n" +
            "                <p>Si no solicitaste esta cuenta, por favor ignora este correo.</p>\n" +
            "                <div class=\"social-icons\">\n" +
            "                    <a href=\"#\">Instagram</a> • \n" +
            "                    <a href=\"#\">Facebook</a> • \n" +
            "                    <a href=\"#\">Twitter</a>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "</body>\n" +
            "</html>\n";

    public static final String bodyActualizarPassword = "<!DOCTYPE html>\n" +
            "<html lang=\"es\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <title>Cambio de Contraseña - Pastelería Feliz</title>\n" +
            "    <style>\n" +
            "        @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap');\n" +
            "        \n" +
            "        body {\n" +
            "            font-family: 'Poppins', Arial, sans-serif;\n" +
            "            background-color: #FFEFEF;\n" +
            "            margin: 0;\n" +
            "            padding: 0;\n" +
            "            color: #333;\n" +
            "        }\n" +
            "        \n" +
            "        .wrapper {\n" +
            "            max-width: 650px;\n" +
            "            margin: 0 auto;\n" +
            "            padding: 20px;\n" +
            "        }\n" +
            "        \n" +
            "        .header {\n" +
            "            background-color: #FFD1DC;\n" +
            "            padding: 25px 20px;\n" +
            "            text-align: center;\n" +
            "            border-radius: 10px 10px 0 0;\n" +
            "            border-bottom: 3px solid #FFBAC8;\n" +
            "        }\n" +
            "        \n" +
            "        .logo {\n" +
            "            font-size: 28px;\n" +
            "            font-weight: 700;\n" +
            "            color: #000000;\n" +
            "            margin: 0;\n" +
            "            letter-spacing: 1px;\n" +
            "        }\n" +
            "        \n" +
            "        .tagline {\n" +
            "            font-size: 14px;\n" +
            "            color: #555;\n" +
            "            margin-top: 5px;\n" +
            "        }\n" +
            "        \n" +
            "        .container {\n" +
            "            background-color: white;\n" +
            "            padding: 30px;\n" +
            "            border-radius: 0 0 10px 10px;\n" +
            "            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);\n" +
            "        }\n" +
            "        \n" +
            "        .alert-icon {\n" +
            "            font-size: 48px;\n" +
            "            text-align: center;\n" +
            "            margin-bottom: 20px;\n" +
            "        }\n" +
            "        \n" +
            "        .message-title {\n" +
            "            font-size: 22px;\n" +
            "            font-weight: 600;\n" +
            "            margin-bottom: 20px;\n" +
            "            text-align: center;\n" +
            "            color: #FF769A;\n" +
            "        }\n" +
            "        \n" +
            "        .message-body {\n" +
            "            font-size: 16px;\n" +
            "            line-height: 1.6;\n" +
            "            margin-bottom: 25px;\n" +
            "        }\n" +
            "        \n" +
            "        .code-box {\n" +
            "            background-color: #FFF5F7;\n" +
            "            border: 1px dashed #FFB6C1;\n" +
            "            border-radius: 8px;\n" +
            "            padding: 15px;\n" +
            "            margin: 20px 0;\n" +
            "            text-align: center;\n" +
            "        }\n" +
            "        \n" +
            "        .activation-code {\n" +
            "            font-size: 24px;\n" +
            "            font-weight: 700;\n" +
            "            color: #FF4081;\n" +
            "            letter-spacing: 2px;\n" +
            "            margin: 10px 0;\n" +
            "        }\n" +
            "        \n" +
            "        .security-note {\n" +
            "            font-size: 14px;\n" +
            "            background-color: #FFF9FA;\n" +
            "            padding: 15px;\n" +
            "            border-left: 3px solid #FF769A;\n" +
            "            margin-top: 20px;\n" +
            "        }\n" +
            "        \n" +
            "        .footer {\n" +
            "            text-align: center;\n" +
            "            margin-top: 30px;\n" +
            "            font-size: 12px;\n" +
            "            color: #777;\n" +
            "            padding-top: 20px;\n" +
            "            border-top: 1px solid #FFEBEF;\n" +
            "        }\n" +
            "        \n" +
            "        .social-icons {\n" +
            "            margin-top: 15px;\n" +
            "        }\n" +
            "        \n" +
            "        .social-icons a {\n" +
            "            color: #FF769A;\n" +
            "            text-decoration: none;\n" +
            "            margin: 0 8px;\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <div class=\"wrapper\">\n" +
            "        <div class=\"header\">\n" +
            "            <h1 class=\"logo\">Pastelería Feliz</h1>\n" +
            "            <p class=\"tagline\">Donde cada bocado es una sonrisa</p>\n" +
            "        </div>\n" +
            "\n" +
            "        <div class=\"container\">\n" +
            "            <div class=\"alert-icon\">🔐</div>\n" +
            "            <h2 class=\"message-title\">Solicitud de Cambio de Contraseña</h2>\n" +
            "            \n" +
            "            <p class=\"message-body\">Hemos recibido una solicitud para restablecer la contraseña de tu cuenta en <strong>Pastelería Feliz</strong>. Para continuar con este proceso, utiliza el siguiente código de verificación:</p>\n" +
            "            \n" +
            "            <div class=\"code-box\">\n" +
            "                <p>Tu código de verificación es:</p>\n" +
            "                <p class=\"activation-code\">[Codigo_Activacion]</p>\n" +
            "                <p>Este código expirará en 30 minutos.</p>\n" +
            "            </div>\n" +
            "            \n" +
            "            <p class=\"message-body\">Ingresa este código en la página de restablecimiento de contraseña para continuar con el proceso y crear una nueva contraseña para tu cuenta.</p>\n" +
            "            \n" +
            "            <div class=\"security-note\">\n" +
            "                <strong>Nota de seguridad:</strong> Si no solicitaste cambiar tu contraseña, por favor ignora este correo o contacta inmediatamente con nuestro equipo de soporte para asegurar la protección de tu cuenta.\n" +
            "            </div>\n" +
            "            \n" +
            "            <div class=\"footer\">\n" +
            "                <p>&copy; 2025 Pastelería Feliz. Todos los derechos reservados.</p>\n" +
            "                <div class=\"social-icons\">\n" +
            "                    <a href=\"#\">Instagram</a> • \n" +
            "                    <a href=\"#\">Facebook</a> • \n" +
            "                    <a href=\"#\">Twitter</a>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "</body>\n" +
            "</html>\n";

    public static final String bodyAlertaInventario = "<!DOCTYPE html>\n" +
            "<html lang=\"es\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <title>Alerta de Inventario - Pastelería Feliz</title>\n" +
            "    <style>\n" +
            "        @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap');\n" +
            "        \n" +
            "        body {\n" +
            "            font-family: 'Poppins', Arial, sans-serif;\n" +
            "            background-color: #FFEFEF;\n" +
            "            margin: 0;\n" +
            "            padding: 0;\n" +
            "            color: #333;\n" +
            "        }\n" +
            "        \n" +
            "        .wrapper {\n" +
            "            max-width: 650px;\n" +
            "            margin: 0 auto;\n" +
            "            padding: 20px;\n" +
            "        }\n" +
            "        \n" +
            "        .header {\n" +
            "            background-color: #FFD1DC;\n" +
            "            padding: 25px 20px;\n" +
            "            text-align: center;\n" +
            "            border-radius: 10px 10px 0 0;\n" +
            "            border-bottom: 3px solid #FFBAC8;\n" +
            "        }\n" +
            "        \n" +
            "        .logo {\n" +
            "            font-size: 28px;\n" +
            "            font-weight: 700;\n" +
            "            color: #000000;\n" +
            "            margin: 0;\n" +
            "            letter-spacing: 1px;\n" +
            "        }\n" +
            "        \n" +
            "        .tagline {\n" +
            "            font-size: 14px;\n" +
            "            color: #555;\n" +
            "            margin-top: 5px;\n" +
            "        }\n" +
            "        \n" +
            "        .container {\n" +
            "            background-color: white;\n" +
            "            padding: 30px;\n" +
            "            border-radius: 0 0 10px 10px;\n" +
            "            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);\n" +
            "        }\n" +
            "        \n" +
            "        .alert-icon {\n" +
            "            font-size: 48px;\n" +
            "            text-align: center;\n" +
            "            margin-bottom: 20px;\n" +
            "        }\n" +
            "        \n" +
            "        .message-title {\n" +
            "            font-size: 22px;\n" +
            "            font-weight: 600;\n" +
            "            margin-bottom: 20px;\n" +
            "            text-align: center;\n" +
            "            color: #FF769A;\n" +
            "        }\n" +
            "        \n" +
            "        .message-body {\n" +
            "            font-size: 16px;\n" +
            "            line-height: 1.6;\n" +
            "            margin-bottom: 25px;\n" +
            "        }\n" +
            "        \n" +
            "        .alert-box {\n" +
            "            background-color: #FFF5F7;\n" +
            "            border: 1px dashed #FFB6C1;\n" +
            "            border-radius: 8px;\n" +
            "            padding: 15px;\n" +
            "            margin: 20px 0;\n" +
            "        }\n" +
            "        \n" +
            "        .alert-message {\n" +
            "            font-size: 16px;\n" +
            "            font-weight: 600;\n" +
            "            color: #FF4081;\n" +
            "            margin: 10px 0;\n" +
            "        }\n" +
            "        \n" +
            "        .highlight {\n" +
            "            font-weight: 600;\n" +
            "            color: #FF769A;\n" +
            "        }\n" +
            "        \n" +
            "        table {\n" +
            "            width: 100%;\n" +
            "            border-collapse: collapse;\n" +
            "            margin: 20px 0;\n" +
            "        }\n" +
            "        \n" +
            "        th {\n" +
            "            background-color: #FFD1DC;\n" +
            "            color: #333;\n" +
            "            font-weight: 600;\n" +
            "            text-align: left;\n" +
            "            padding: 12px;\n" +
            "            border-bottom: 2px solid #FFBAC8;\n" +
            "        }\n" +
            "        \n" +
            "        td {\n" +
            "            padding: 10px 12px;\n" +
            "            border-bottom: 1px solid #FFEBEF;\n" +
            "        }\n" +
            "        \n" +
            "        tr:nth-child(even) {\n" +
            "            background-color: #FFF9FA;\n" +
            "        }\n" +
            "        \n" +
            "        .status-normal {\n" +
            "            color: #4CAF50;\n" +
            "            font-weight: 600;\n" +
            "        }\n" +
            "        \n" +
            "        .status-warning {\n" +
            "            color: #FF9800;\n" +
            "            font-weight: 600;\n" +
            "        }\n" +
            "        \n" +
            "        .status-critical {\n" +
            "            color: #F44336;\n" +
            "            font-weight: 600;\n" +
            "        }\n" +
            "        \n" +
            "        .order-status {\n" +
            "            background-color: #FFF9FA;\n" +
            "            padding: 15px;\n" +
            "            border-left: 3px solid #FF769A;\n" +
            "            margin-top: 20px;\n" +
            "        }\n" +
            "        \n" +
            "        .action-needed {\n" +
            "            font-weight: 600;\n" +
            "            margin-top: 20px;\n" +
            "            color: #FF4081;\n" +
            "        }\n" +
            "        \n" +
            "        .footer {\n" +
            "            text-align: center;\n" +
            "            margin-top: 30px;\n" +
            "            font-size: 12px;\n" +
            "            color: #777;\n" +
            "            padding-top: 20px;\n" +
            "            border-top: 1px solid #FFEBEF;\n" +
            "        }\n" +
            "        \n" +
            "        .social-icons {\n" +
            "            margin-top: 15px;\n" +
            "        }\n" +
            "        \n" +
            "        .social-icons a {\n" +
            "            color: #FF769A;\n" +
            "            text-decoration: none;\n" +
            "            margin: 0 8px;\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <div class=\"wrapper\">\n" +
            "        <div class=\"header\">\n" +
            "            <h1 class=\"logo\">Pastelería Feliz</h1>\n" +
            "            <p class=\"tagline\">Donde cada bocado es una sonrisa</p>\n" +
            "        </div>\n" +
            "\n" +
            "        <div class=\"container\">\n" +
            "            <div class=\"alert-icon\">⚠️</div>\n" +
            "            <h2 class=\"message-title\">Alerta de Inventario</h2>\n" +
            "            \n" +
            "            <p class=\"message-body\">Estimado/a <span class=\"highlight\">[Destinatario]</span>,</p>\n" +
            "            \n" +
            "            <p class=\"message-body\">Se ha detectado una alerta de inventario relacionada con la orden <span class=\"highlight\">#[NumeroOrden]</span>. Por favor, revise los detalles a continuación:</p>\n" +
            "            \n" +
            "            <div class=\"alert-box\">\n" +
            "                <p class=\"alert-message\">[MensajeAlerta]</p>\n" +
            "            </div>\n" +
            "            \n" +
            "            <h3>Detalles de los insumos afectados:</h3>\n" +
            "            \n" +
            "            <table>\n" +
            "                <tr>\n" +
            "                    <th>Insumo</th>\n" +
            "                    <th>Requerido</th>\n" +
            "                    <th>Disponible</th>\n" +
            "                    <th>Estado</th>\n" +
            "                </tr>\n" +
            "                [DetallesAlerta]\n" +
            "            </table>\n" +
            "            \n" +
            "            <p class=\"action-needed\">Acción requerida:</p>\n" +
            "            <p class=\"message-body\">Es necesario revisar y reponer estos insumos lo antes posible para mantener la capacidad operativa de la pastelería y cumplir con los pedidos pendientes.</p>\n" +
            "            \n" +
            "            <div class=\"order-status\">\n" +
            "                <p><strong>Estado actual de la orden:</strong> [EstadoOrden]</p>\n" +
            "                <p>La orden ha sido registrada en el sistema, pero se recomienda revisar el inventario antes de proceder con su preparación.</p>\n" +
            "            </div>\n" +
            "            \n" +
            "            <div class=\"footer\">\n" +
            "                <p>Este es un mensaje automático generado por el sistema de inventario de Pastelería Feliz.</p>\n" +
            "                <p>&copy; 2025 Pastelería Feliz. Todos los derechos reservados.</p>\n" +
            "                <div class=\"social-icons\">\n" +
            "                    <a href=\"#\">Instagram</a> • \n" +
            "                    <a href=\"#\">Facebook</a> • \n" +
            "                    <a href=\"#\">Twitter</a>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "</body>\n" +
            "</html>\n";
}