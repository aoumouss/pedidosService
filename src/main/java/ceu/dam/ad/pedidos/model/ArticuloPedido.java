package ceu.dam.ad.pedidos.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ArticuloPedido {

	private Long id;
	private Long idPedido;
	private String codBarras;
	private String descripcionArticulo;
	private BigDecimal precioSinIva;
	private Boolean exentoIva; 

}
