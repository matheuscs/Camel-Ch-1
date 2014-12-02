package camelinaction;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class LogProcessor implements Processor{
//somente loga o processo
	public void process(Exchange exchange) throws Exception {
		System.out.println("Arquivo "+exchange.getIn().getHeader("CamelFileName") + " roteado com sucesso");
	}

}