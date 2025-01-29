package com.Inovatech.Java.Inovatech.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.Inovatech.Java.Inovatech.model.Pedido;
import java.io.IOException;

public class PedidoDeserializer extends JsonDeserializer<Pedido> {

    @Override
    public Pedido deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        // Obtém o valor do JSON (neste caso, um número inteiro)
        Integer pedidoId = p.getValueAsInt();

        // Cria um objeto Pedido e define o ID
        Pedido pedido = new Pedido();
        pedido.setIdPedido(pedidoId);

        return pedido;
    }
}