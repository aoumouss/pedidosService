package ceu.dam.ad.pedidos.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import ceu.dam.ad.pedidos.dao.ArticuloPedidoRepository;
import ceu.dam.ad.pedidos.dao.PedidoRepository;
import ceu.dam.ad.pedidos.model.ArticuloPedido;
import ceu.dam.ad.pedidos.model.Pedido;

public class PedidoServiceImpl implements PedidoService {

	private static final Logger logger = LoggerFactory.getLogger(PedidoServiceImpl.class);

	private PedidoRepository repo;
	private ArticuloPedidoRepository repoArticulo;

	public PedidoServiceImpl(PedidoRepository repo, ArticuloPedidoRepository repoArticulo) {
		this.repo = repo;
		this.repoArticulo = repoArticulo;
	}

	/**
	 * Recibe el id de un pedido y lo consulta en BBDD. Debe devolver el pedido con
	 * todos sus datos incluyendo sus artículos y sus totales. Lanza
	 * PedidoNotFoundException si el pedido no existe o PedidoException si hay un
	 * error.
	 * 
	 * @param idPedido
	 * @return
	 * @throws PedidoException
	 * @throws PedidoNotFoundException
	 */
	@Override
	public Pedido consultarPedido(Long idPedido) throws PedidoException, PedidoNotFoundException {
		try {
			logger.info("Solicitud para obtener datos sobre pedido con id: " + idPedido);
			Optional<Pedido> p = repo.findById(idPedido);
			Pedido pedido = p.orElseThrow(() -> new PedidoNotFoundException("La id no existe"));
			pedido.setArticulos(repoArticulo.findByIdPedido(idPedido));
			return pedido;
		} catch (DataAccessException e) {
			throw new PedidoException("No se ha encontrado el pedido con ese id");
		}
	}

	/**
	 * Recibe un pedido con su lista de artículos y debe guardarlo en BBDD. Tendrá
	 * que devolver el pedido con todos sus datos. Si hay algún error lanza
	 * PedidoException
	 * 
	 * @param pedido
	 * @return
	 * @throws PedidoException
	 */
	@Override
	public Pedido crearPedido(Pedido pedido) throws PedidoException {
		BigDecimal subtotal = BigDecimal.ZERO;
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal impuestos = BigDecimal.ZERO;

		List<ArticuloPedido> listaArticulos = pedido.getArticulos();
		for (ArticuloPedido articulo : listaArticulos) {
			subtotal = subtotal.add(articulo.getPrecioSinIva());
			if (articulo.getExentoIva() == false) {
				impuestos = articulo.getPrecioSinIva().multiply(BigDecimal.valueOf(1.21));
			}
		}
		total=subtotal.add(impuestos);
		pedido.setImpuestos(impuestos);
		pedido.setSubtotal(subtotal);
		pedido.setTotal(total);
		
		return repo.save(pedido);
		
	}

	/**
	 * Recibe un código de barras. Tendrá que devolver la lista de IDs de pedidos
	 * que incluyan algún artículo con ese código de barras. Si no hay ninguno,
	 * lanzará PedidoNotFoundException. Si hay algún error, lanzará PedidoException.
	 * 
	 * @param codBarras
	 * @return
	 * @throws PedidoException
	 * @throws PedidoNotFoundException
	 */
	@Override
	public List<Long> buscarPedidosArticulo(String codBarras) throws PedidoException, PedidoNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
