package com.springboot.app.NTTdata.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.NTTdata.Dao.ClienteRequest;
import com.springboot.app.NTTdata.model.Cliente;

@RestController
public class ClienteController {

	@PostMapping("/consulta_cliente")
    public ResponseEntity<?> consultaCliente(@RequestBody ClienteRequest request) {
        String tipo = request.getTipo();
        String numero = request.getNumero();

        if (!isValidTipoDocumento(tipo) || !isValidNumeroDocumento(numero)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo o número de documento no válido");
        }

        String clienteKey = tipo + "-" + numero;
        if (clienteData.containsKey(clienteKey)) {
            Cliente cliente = clienteData.get(clienteKey);
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado");
        }
    }

    private boolean isValidTipoDocumento(String tipo) {
        return tipo != null && (tipo.equals("C") || tipo.equals("P"));
    }

    private boolean isValidNumeroDocumento(String numero) {
        return numero != null && !numero.isEmpty();
    }

    // Datos quemados para la respuesta
    private static final Map<String, Cliente> clienteData = new HashMap<>();
    static {
        clienteData.put("C-12345678", new Cliente("Juan", "Carlos", "Pérez", "Gómez", "1234567890", "Calle 123", "Ciudad XYZ"));
        clienteData.put("P-98765432", new Cliente("María", "Elena", "López", "García", "9876543210", "Avenida 456", "Ciudad UVW"));
    }

}
