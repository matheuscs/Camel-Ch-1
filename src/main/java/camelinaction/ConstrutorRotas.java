package camelinaction;

import org.apache.camel.builder.RouteBuilder;

public class ConstrutorRotas extends RouteBuilder {

	// torno configuravel a URI do canal
	String origem = "System_A/data/inbox";
	String destino = "System_B/data/outbox";
	String invalido = "System_A/data/Error";

	@Override
	public void configure() throws Exception {

		// Coloco a mensagem na fila correta
		from("file:" + origem + "?noop=true").process(new LogProcessor())
				.choice()
					.when(header("CamelFileName").endsWith(".xml"))
						.to("jms:incomingOrdersXML")
					.when(header("CamelFileName").endsWith(".csv"))
						.to("jms:incomingOrdersCSV")
					.otherwise()
						// coloco a mensagem no destino final
						.to("file:" + invalido);

		// Retiro a mensagem da fila
		from("jms:incomingOrdersXML")
		// transformo para o padrão desejado
				.bean(new Transormer(), "transformContent")
				// coloco a mensagem no destino final
				.to("file:" + destino + "?fileName=${file:name.noext}.CSV")
				// impeço o roteamento futuro desta mensagem
				.end();
		
		// Retiro a mensagem da fila
		from("jms:incomingOrdersCSV")
				// coloco a mensagem no destino final
				.to("file:" + destino + "?fileName=${file:name.noext}.CSV")
				// impeço o roteamento futuro desta mensagem
				.end();
		
	}

}