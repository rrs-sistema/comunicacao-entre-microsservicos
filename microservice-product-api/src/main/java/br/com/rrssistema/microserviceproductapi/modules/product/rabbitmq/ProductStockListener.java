package br.com.rrssistema.microserviceproductapi.modules.product.rabbitmq;

import br.com.rrssistema.microserviceproductapi.modules.product.dto.ProductStockDTO;
import br.com.rrssistema.microserviceproductapi.modules.product.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductStockListener {

    @Autowired
    private ProductService productService;

    @RabbitListener(queues = "${app-config.rabbit.queue.product-stock}")
    public void receiveProductStockMessage(ProductStockDTO product) {
        try {
            log.info("Recebendo mensagem: {}", new ObjectMapper().writeValueAsString(product));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        productService.updateProductStock(product);
    }
}
