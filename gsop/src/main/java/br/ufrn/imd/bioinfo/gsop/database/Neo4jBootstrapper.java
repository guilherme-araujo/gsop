package br.ufrn.imd.bioinfo.gsop.database;

import static java.util.Arrays.asList;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.internal.kernel.api.exceptions.KernelException;
import org.neo4j.kernel.impl.proc.Procedures;
import org.neo4j.kernel.internal.GraphDatabaseAPI;

@WebListener
public class Neo4jBootstrapper implements ServletContextListener {

	private GraphDatabaseService graphDb;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Inicializando");
		String neo4jPath = "/home2/gfaraujo/neo4j-embedded";
		File f = new File(neo4jPath);
		GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
		graphDb = dbFactory.newEmbeddedDatabaseBuilder(f).loadPropertiesFromFile(neo4jPath+"/neo4j.conf").newGraphDatabase();

		Procedures procedures = ((GraphDatabaseAPI) graphDb).getDependencyResolver().resolveDependency(Procedures.class);

		List<Class<?>> apocProcedures = asList(apoc.generate.Generate.class);

		apocProcedures.forEach((proc) -> {
			try {
				procedures.registerProcedure(proc);
			} catch (KernelException e) {
				throw new RuntimeException("Error registering " + proc, e);
			}
		}

				);

		Backend.init(graphDb);
		System.out.println("Inicialização completa");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		if (graphDb != null) {
			graphDb.shutdown();
		}
	}
}