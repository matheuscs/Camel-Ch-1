package camelinaction;

import org.apache.camel.builder.RouteBuilder;

public class FirstRouteBuilder extends RouteBuilder {

	// torno configuravel as pastas de entrada e saida
	String origem = "data/inbox";
	String destino = "data/outbox";

	@Override
	public void configure() throws Exception {

		// from("file:data/inbox?noop=true")
		// Coloco a mensagem na fila correta
		from("file:" + origem + "?noop=true").process(new LogProcessor())

		.to("jms:incomingOrders");

		// Retiro a mensagem da fila transformo para o padrão desejado e coloco
		// a mensagem no destino final
		from("jms:incomingOrders").bean(new Transormer(), "transformContent")
				.to("file:" + destino).end();
	}

}