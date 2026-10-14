package ceu.dam.ad.pedidos.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class Pedido {

	private Long idPedido;
	private LocalDate fechaPedido;
	private String dniCliente;
	private BigDecimal total;
	private BigDecimal subtotal;
	private BigDecimal impuestos;
	private List<ArticuloPedido> articulos;
	

}
