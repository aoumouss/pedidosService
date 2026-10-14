package ceu.dam.ad.pedidos.service;


import ceu.dam.ad.pedidos.model.ArticuloPedido;
import ceu.dam.ad.pedidos.model.Pedido;

public interface ArticuloService {

	public ArticuloPedido consultarArticulos(Pedido pedido);

}
