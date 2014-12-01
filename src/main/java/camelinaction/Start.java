package camelinaction;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import javax.jms.ConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Start {
	public static void main(String[] args) {
		FirstRouteBuilder routeBuilder = new FirstRouteBuilder();
		
		//Cria o contexto do Camel para este execução
		CamelContext ctx = new DefaultCamelContext();
		
        // Cria uma fabrica de conexões local para Java Message Service (JMS) e a fila Ativa  Message Queue (MQ)
        ConnectionFactory connFact = 
            new ActiveMQConnectionFactory("vm://localhost");
        
        //Adiciona o serviço de mensagem no contexto
        ctx.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connFact));
        
		try {
			ctx.addRoutes(routeBuilder);
			ctx.start();
			Thread.sleep(5 * 60 * 1000);
			ctx.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
