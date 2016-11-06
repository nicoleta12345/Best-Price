// package ro.ace.ucv;
//
// import java.util.ArrayList;
// import java.util.List;
//
// import javax.ws.rs.GET;
// import javax.ws.rs.Path;
// import javax.ws.rs.PathParam;
// import javax.ws.rs.Produces;
// import javax.ws.rs.core.MediaType;
// import javax.ws.rs.core.Response;
//
// import org.springframework.stereotype.Controller;
//
// @Path("/wines")
// @Controller
// public class WineResource {
//
// @GET
// @Path("search/{query}")
// @Produces({ MediaType.APPLICATION_JSON })
// public Response findByName(@PathParam("query") String query) {
// System.out.println("findByName: " + query);
// List<Point> products = new ArrayList<>();
// products.add(new Point("a", 1.2));
// products.add(new Point("b", 3.56));
//
// return Response.status(200).entity(products).build();
// }
//
// }
