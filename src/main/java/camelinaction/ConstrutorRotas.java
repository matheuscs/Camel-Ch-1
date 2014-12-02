package camelinaction;

import org.apache.camel.builder.RouteBuilder;

public class ConstrutorRotas extends RouteBuilder {

	// torno configuravel as pastas de entrada e saida
	String origem = "data/inbox";
	String destino = "data/outbox";

	@Override
	public void configure() throws Exception {

		// Coloco a mensagem na fila correta
		from("file:" + origem + "?noop=true")
			.process(new LogProcessor())
			.to("jms:incomingOrders");
		
		// Retiro a mensagem da fila 
		from("jms:incomingOrders")
			//transformo para o padrão desejado 
			.bean(new Transormer(), "transformContent")
			//coloco a mensagem no destino final
			.to("file:" + destino + "?fileName=${file:name.noext}.CSV")
			//impeço o roteamento futuro desta mensagem
			.end();
	}

}